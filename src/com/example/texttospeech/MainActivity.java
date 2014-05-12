package com.example.texttospeech;
 
import java.util.Locale;
 
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
 
public class MainActivity extends Activity implements
        TextToSpeech.OnInitListener {
    /** Called when the activity is first created. */
 
    private TextToSpeech tts;
    private Button btnSpeak;
   
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        tts = new TextToSpeech(this, this);
 
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
 
       
 
        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                startTimer();
            }
 
        });
    }
 
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}


	@Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
 
    @Override
    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
 
            int result = tts.setLanguage(Locale.US);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
               
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
    }
 
   
    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();

    private void startTimer(){
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        //TODO
                    	
                    	 String text = "PLEASE STOP, OBSTACLE AHEAD";
                         
                         try {
                         	tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                             Thread.sleep(1000);
                         } catch(InterruptedException ex) {
                             Thread.currentThread().interrupt();
                         
                         }
                    }
                });
            }
        };

        mTimer1.schedule(mTt1, 1, 5000);
    }
}