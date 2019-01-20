package com.aware.plugin.ambient_noise;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import com.aware.Aware;
import com.aware.Aware_Preferences;

import java.nio.ByteBuffer;

/**
 * Created by denzil on 31/07/16.
 */
public class AudioAnalyser extends IntentService {
    public static double sound_frequency;
    public static double sound_db;
    public static boolean is_silent;
    public static double sound_rms;

    public AudioAnalyser() {
        super(Aware.TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Get minimum size of the buffer for pre-determined audio setup and minutes
        int buffer_size = AudioRecord.getMinBufferSize(AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_SYSTEM), AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT) * 10;

        //Initialize audio recorder. Use MediaRecorder.AudioSource.VOICE_RECOGNITION to disable Automated Voice Gain from microphone and use DSP if available
        AudioRecord recorder = new AudioRecord(
                MediaRecorder.AudioSource.VOICE_RECOGNITION,
                AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_SYSTEM),
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                buffer_size);

        //Audio data buffer
        short[] audio_data = new short[buffer_size];

        while (recorder.getState() != AudioRecord.STATE_INITIALIZED) {
            //no-op while waiting microphone to initialise
        }

        if (recorder.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
            recorder.startRecording();
        }

        Log.d(Aware.TAG, "Collecting audio sample...");

        double now = System.currentTimeMillis();
        double elapsed = 0;
        while (elapsed < Integer.parseInt(Aware.getSetting(getApplicationContext(), Settings.PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE)) * 1000) {
            elapsed = System.currentTimeMillis() - now;
        }

        recorder.read(audio_data, 0, buffer_size);

        //Release microphone and stop recording
        recorder.stop();
        recorder.release();

        Log.d(Aware.TAG, "Finished audio sample...");

        AudioAnalysis audio_analysis = new AudioAnalysis(this, audio_data);
        sound_rms = audio_analysis.getRMS();
        sound_frequency = audio_analysis.getFrequency();
        sound_db = audio_analysis.getdB();
        is_silent = audio_analysis.isSilent(sound_db);

        ContentValues data = new ContentValues();
        data.put(Provider.AmbientNoise_Data.TIMESTAMP, System.currentTimeMillis());
        data.put(Provider.AmbientNoise_Data.DEVICE_ID, Aware.getSetting(getApplicationContext(), Aware_Preferences.DEVICE_ID));
        data.put(Provider.AmbientNoise_Data.FREQUENCY, sound_frequency);
        data.put(Provider.AmbientNoise_Data.DECIBELS, sound_db);
        data.put(Provider.AmbientNoise_Data.RMS, sound_rms);
        data.put(Provider.AmbientNoise_Data.IS_SILENT, is_silent);

        ByteBuffer byteBuff = ByteBuffer.allocate(2 * buffer_size);
        for (Short a : audio_data) byteBuff.putShort(a);
        data.put(Provider.AmbientNoise_Data.RAW, byteBuff.array());

        data.put(Provider.AmbientNoise_Data.SILENCE_THRESHOLD, Aware.getSetting(getApplicationContext(), Settings.PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD));

        getContentResolver().insert(Provider.AmbientNoise_Data.CONTENT_URI, data);

        if (Aware.DEBUG) Log.d(Aware.TAG, data.toString());

        //Share context
        if (Plugin.context_producer != null)
            Plugin.context_producer.onContext();

    }
}
