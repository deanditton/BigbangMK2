package com.example.dean.bigbangmk2;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;


public class SingleplayerActivity extends ActionBarActivity  implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    public boolean switchCase = false;
    public int counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_singleplayer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;
        if (GameHub.getUserChoice() == GameHub.NO_SELECTION){
            return;
        }else {
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long localStamp = System.currentTimeMillis();
                float x = sensorEvent.values[1];
                if (x >= 8.0) {
                    switchCase = true;
                    // Log.d("THE X VALUE:", Float.toString(x));

                }else if (x < 1.5 & switchCase){
                    counter += 1;
                    Toast.makeText(this, "SHAKE: " + Integer.toString(counter), Toast.LENGTH_SHORT).show();
                    if (counter == 3){
                        Log.d("GAME PLAYED", Integer.toString(counter));
                       counter = 0;
                    }
                    switchCase = false;
                }

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void setPlayerChoice(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.Rock:
                if (checked)
                    GameHub.setUserChoice(GameHub.ROCK);
                break;
            case R.id.Paper:
                if (checked)
                    GameHub.setUserChoice(GameHub.PAPER);
                break;
            case R.id.Scissors:
                if (checked)
                    GameHub.setUserChoice(GameHub.SICSSOR);
                break;
            case R.id.Lizard:
                if (checked)
                    GameHub.setUserChoice(GameHub.SICSSOR);
                break;
            case R.id.Spock:
                if (checked)
                    GameHub.setUserChoice(GameHub.SICSSOR);
                break;
        }

    }


    public void playGame(View view) {
        if (GameHub.getUserChoice() == GameHub.NO_SELECTION) {
            Toast.makeText(this, "Please Guess", Toast.LENGTH_LONG).show();
        } else {
            gameChoice();

        }

    }

    public void gameChoice() {
        GameHub.compare(GameHub.AiGuess());
        Toast.makeText(this, "Computer is thinking", Toast.LENGTH_SHORT).show();

        switch (GameHub.RESULT) {
            case GameHub.WIN:
                Toast.makeText(this, "YOU ARE WINNER!!!!!!", Toast.LENGTH_SHORT).show();
                break;
            case GameHub.TIE:
                Toast.makeText(this, "YOU TIE WITH COMPUTER", Toast.LENGTH_SHORT).show();
            default:
                Toast.makeText(this, "COMPUTER BEAT YOU!!!!!", Toast.LENGTH_SHORT).show();
        }
    }


}
