package citytracksaware.client;

import android.app.*;
import android.content.*;
import android.os.*;
import android.provider.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import java.io.*;
import java.util.*;

import citytracksaware.core.util.*;
import citytracksaware.core.trip.control.TripManager;

public class MainActivity extends FragmentActivity {

    protected JSONDAO<String> serverAddressDAO;

    // UI Widgets.
    protected Button startTripButton;
    protected Button finishTripButton;
    protected Button cancelTripButton;
    protected Button uploadDataButton;
    protected Button travelModeButton;
    protected Button homeButton;
    protected Button workButton;
    protected Button educationButton;
    protected Button shoppingButton;
    protected Button leisureButton;
    protected Button otherButton;
    protected Button serverAddressOkButton;
    protected Button travelModeOkButton;
    protected Button tripPurposeOkButton;
    protected TextView deviceIdTextView;
    protected TextView travelModeTextView;
    protected TextView tripPurposeTextView;
    protected EditText serverAddressEditText;
    protected EditText travelModeEditText;
    protected EditText tripPurposeEditText;
    private String selectedTravelMode;
    private String selectedTripPurpose;

    private String serverAddress;
    private TripManager tripManager;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        locateUIWidgets();
        serverAddressDAO = new JSONDAO<>(this, "serverAddress");
        loadServerAddress();
        tripManager = new TripManager(this, serverAddress);
        tripManager.restoreCurrentTripState();
        configureButtonEnabledStates();
    }

    @Override
    protected void onPause() {
        createNotification();
        saveApplicationState();
        super.onPause();
    }

    @Override
    protected void onStop() {
        createNotification();
        saveApplicationState();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        saveApplicationState();
        super.onDestroy();
    }

    private void loadServerAddress() {
        try {
            List<String> listString = null;
            listString = serverAddressDAO.retrieveAll(String.class);
            if(listString.size() == 1){
                serverAddress = listString.get(0);
            }else{
                serverAddress = "http://citytracks.intelliurb.org/citytracks-aware/";
            }
            serverAddressEditText.setText(serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNotification() {

        String modoDeTransporte = getString(R.string.selecionar);

        String propositoDeViagem = getString(R.string.selecionar);

        if(selectedTravelMode != null) {
            modoDeTransporte = selectedTravelMode.toString();
        }

        if(selectedTripPurpose != null){
            propositoDeViagem = selectedTripPurpose.toString();
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.modo) + ": " + modoDeTransporte + " - " + getString(R.string.proposito) + ": " + propositoDeViagem)
                        .setAutoCancel(true);

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void saveApplicationState() {
        persistServerAddress();
        tripManager.saveCurrentTripState();
        //TODO save selected buttons and text field data
    }

    public void setServerAddress(View view){
        serverAddressEditText.setVisibility(View.VISIBLE);
        serverAddressOkButton.setVisibility(View.VISIBLE);
    }

    public void saveServerAddress(View view){
        serverAddress = serverAddressEditText.getText().toString();
        persistServerAddress();
        tripManager.setServerURL(serverAddress);
        Toast.makeText(this, "Server address saved!", Toast.LENGTH_SHORT).show();
    }

    private void persistServerAddress() {
        serverAddressDAO.deleteAll(String.class);
        serverAddressDAO.create(serverAddress);
    }

    private void locateUIWidgets() {
        startTripButton = (Button) findViewById(R.id.iniciar_atualizacoes_button);
        finishTripButton = (Button) findViewById(R.id.parar_atualizacoes_button);
        cancelTripButton = (Button) findViewById(R.id.cancelar_viagem_button);
        uploadDataButton = (Button) findViewById(R.id.enviar_dados_coletados);
        homeButton = (Button) findViewById(R.id.proposito_de_viagem_casa);
        workButton = (Button) findViewById(R.id.proposito_de_viagem_trabalho);
        educationButton = (Button) findViewById(R.id.proposito_de_viagem_educacao);
        shoppingButton = (Button) findViewById(R.id.proposito_de_viagem_compras);
        leisureButton = (Button) findViewById(R.id.proposito_de_viagem_lazer);
        otherButton = (Button) findViewById(R.id.proposito_de_viagem_outro);
        deviceIdTextView = (TextView) findViewById(R.id.id_dispositivo);
        deviceIdTextView.setText(
                getString(R.string.id_dispositivo) + ": " + Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.ANDROID_ID
                )
        );
        travelModeTextView = (TextView) findViewById(R.id.selecionar_modo_de_transporte);
        tripPurposeTextView = (TextView) findViewById(R.id.selecionar_proposito_da_viagem);
        serverAddressEditText = (EditText) findViewById(R.id.endereco_servidor);
        serverAddressEditText.setVisibility(View.GONE);
        travelModeEditText = (EditText) findViewById(R.id.modo_de_transporte);
        travelModeEditText.setVisibility(View.GONE);
        tripPurposeEditText = (EditText) findViewById(R.id.proposito_da_viagem);
        tripPurposeEditText.setVisibility(View.GONE);
        serverAddressOkButton = (Button) findViewById(R.id.endereco_servidor_ok);
        serverAddressOkButton.setVisibility(View.GONE);
        tripPurposeOkButton = (Button) findViewById(R.id.proposito_da_viagem_ok);
        tripPurposeOkButton.setVisibility(View.GONE);
        travelModeOkButton = (Button) findViewById(R.id.modo_de_transporte_ok);
        travelModeOkButton.setVisibility(View.GONE);
    }

    public void startTrip(View view) {

        if(selectedTravelMode != null && selectedTripPurpose != null && serverAddress != null) {
            tripManager.startTrip(
                    selectedTravelMode.toString(),
                    selectedTripPurpose.toString()
            );
            configureButtonEnabledStates();
        }else{
            Toast.makeText(this,getString(R.string.mensagem_selecione),Toast.LENGTH_SHORT).show();
        }

    }

    public void finishTrip(View view) {
        if (tripManager.isTripActive()) {
            view.setEnabled(false);
            Toast.makeText(this, getString(R.string.mensagem_processando1), Toast.LENGTH_SHORT).show();
            tripManager.finishTrip();
            Toast.makeText(this, getString(R.string.mensagem_processando2), Toast.LENGTH_SHORT).show();
            persistServerAddress();
            configureButtonEnabledStates();
        }
    }

    public void cancelTrip(View view){
        if (tripManager.isTripActive()) {
            view.setEnabled(false);
            tripManager.cancelTrip();
            Toast.makeText(this, getString(R.string.mensagem_cancelar), Toast.LENGTH_SHORT).show();
            configureButtonEnabledStates();
        }
    }

    public void uploadDataToServer(View view){
        if (tripManager.uploadLocalDataToServer()){
            Toast.makeText(this, getString(R.string.enviando_dados), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.erro_enviando_dados), Toast.LENGTH_SHORT).show();
        }
    }

    public void clearTripPurpose(){
        homeButton.setEnabled(true);
        workButton.setEnabled(true);
        educationButton.setEnabled(true);
        shoppingButton.setEnabled(true);
        leisureButton.setEnabled(true);
        otherButton.setEnabled(true);
    }

    public void setTripPurpose(View view){
        Button button = (Button) view;
        clearTripPurpose();
        button.setEnabled(false);
        selectedTripPurpose = button.getText().toString();
        if(selectedTripPurpose.equals(getString(R.string.proposito_de_viagem_outro))){
            tripPurposeEditText.setVisibility(View.VISIBLE);
            tripPurposeOkButton.setVisibility(View.VISIBLE);
        }else{
            tripPurposeEditText.setVisibility(View.GONE);
            tripPurposeOkButton.setVisibility(View.GONE);
            Toast.makeText(this, getString(R.string.mudar_proposito) + ": " + selectedTripPurpose,Toast.LENGTH_SHORT).show();
        }
    }

    public void setTripPurposeOther(View view){
        selectedTripPurpose = tripPurposeEditText.getText().toString();
        Toast.makeText(this, getString(R.string.mudar_proposito) + ": " + selectedTripPurpose,Toast.LENGTH_SHORT).show();
    }

    public void setTravelModeOther(View view){
        selectedTravelMode = travelModeEditText.getText().toString();
        tripManager.changeTravelModeTo(selectedTravelMode.toString());
        Toast.makeText(this, getString(R.string.mudar_modo) + ": " + selectedTravelMode,Toast.LENGTH_SHORT).show();
    }

    public void setTravelMode(View view) {
        Button button = (Button) view;
        selectedTravelMode = button.getText().toString();
        if(selectedTravelMode.equals(getString(R.string.modo_de_transporte_outro))){
            travelModeEditText.setVisibility(View.VISIBLE);
            travelModeOkButton.setVisibility(View.VISIBLE);
        }else{
            travelModeEditText.setVisibility(View.GONE);
            travelModeOkButton.setVisibility(View.GONE);
            tripManager.changeTravelModeTo(selectedTravelMode.toString());
            Toast.makeText(this, getString(R.string.mudar_modo) + ": " + selectedTravelMode,Toast.LENGTH_SHORT).show();
        }

        if(travelModeButton != null) {
            travelModeButton.setEnabled(true);
        }
        button.setEnabled(false);
        travelModeButton = button;
    }

    private void configureButtonEnabledStates() {
        if (tripManager.isTripActive()) {
            startTripButton.setEnabled(false);
            finishTripButton.setEnabled(true);
            cancelTripButton.setEnabled(true);
            uploadDataButton.setEnabled(false);
            shoppingButton.setEnabled(false);
            homeButton.setEnabled(false);
            educationButton.setEnabled(false);
            leisureButton.setEnabled(false);
            otherButton.setEnabled(false);
            workButton.setEnabled(false);
            tripPurposeOkButton.setEnabled(false);
            tripPurposeEditText.setEnabled(false);
        } else {
            startTripButton.setEnabled(true);
            finishTripButton.setEnabled(false);
            cancelTripButton.setEnabled(false);
            uploadDataButton.setEnabled(true);
            shoppingButton.setEnabled(true);
            homeButton.setEnabled(true);
            educationButton.setEnabled(true);
            leisureButton.setEnabled(true);
            otherButton.setEnabled(true);
            workButton.setEnabled(true);
            tripPurposeOkButton.setEnabled(true);
            tripPurposeEditText.setEnabled(true);
        }
    }
}
