package com.aware.plugin.google.activity_recognition;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aware.utils.Converters;
import com.aware.utils.IContextCard;

import java.util.Calendar;

/**
 * New Stream UI cards<br/>
 * Implement here what you see on your Plugin's UI.
 *
 * @author denzilferreira
 */
public class ContextCard implements IContextCard {

    //Declare here all the UI elements you'll be accessing
    private View card;
    private TextView still, walking, running, biking, driving;

    public ContextCard() {
    }

    public View getContextCard(Context context) {
        //Load card information to memory
        LayoutInflater sInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        card = sInflater.inflate(R.layout.layout, null);

        //Initialize UI elements from the card
        still = (TextView) card.findViewById(R.id.activity_idle);
        walking = (TextView) card.findViewById(R.id.activity_walking);
        biking = (TextView) card.findViewById(R.id.activity_biking);
        running = (TextView) card.findViewById(R.id.activity_running);
        driving = (TextView) card.findViewById(R.id.activity_vehicle);

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        //Modify time to be at the begining of today
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        //Get stats for today
        still.setText(Converters.readable_elapsed(Stats.getTimeStill(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis())));
        walking.setText(Converters.readable_elapsed(Stats.getTimeWalking(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis())));
        running.setText(Converters.readable_elapsed(Stats.getTimeRunning(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis())));
        biking.setText(Converters.readable_elapsed(Stats.getTimeBiking(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis())));
        driving.setText(Converters.readable_elapsed(Stats.getTimeVehicle(context.getContentResolver(), mCalendar.getTimeInMillis(), System.currentTimeMillis())));

        //Return the card to AWARE/apps
        return card;
    }
}
