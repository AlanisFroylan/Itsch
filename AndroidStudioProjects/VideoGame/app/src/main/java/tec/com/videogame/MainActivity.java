package tec.com.videogame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private GameView mGameView = null;
    private GameThread mGameThread = null;
    Button izq,der,arriba,abajo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mGameView=(GameView)findViewById(R.id.gameView);
        mGameThread= mGameView.getThread();





        izq=(Button)findViewById(R.id.btn_izq);
        der=(Button)findViewById(R.id.btn_der);
        arriba=(Button)findViewById(R.id.btn_arriba);
        abajo=(Button)findViewById(R.id.btn_abajo);

        mGameThread.bombas();


        mGameThread.resources=getResources();





        izq.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mGameThread.izq=true;
                        der.setEnabled(false);
                        arriba.setEnabled(false);
                        abajo.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        mGameThread.izq=false;
                        der.setEnabled(true);
                        arriba.setEnabled(true);
                        abajo.setEnabled(true);

                        break;
                }
                return false;
            }
        });
        der.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mGameThread.der=true;
                        izq.setEnabled(false);
                        arriba.setEnabled(false);
                        abajo.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        mGameThread.der=false;
                        izq.setEnabled(true);
                        arriba.setEnabled(true);
                        abajo.setEnabled(true);
                        break;

                }
                return false;
            }
        });
        arriba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mGameThread.arriba=true;
                        izq.setEnabled(false);
                        der.setEnabled(false);
                        abajo.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        mGameThread.arriba=false;
                        der.setEnabled(true);
                        izq.setEnabled(true);
                        abajo.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        abajo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mGameThread.abajo=true;
                        der.setEnabled(false);
                        arriba.setEnabled(false);
                        izq.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        mGameThread.abajo=false;
                        der.setEnabled(true);
                        arriba.setEnabled(true);
                        izq.setEnabled(true);
                        break;
                }
                return false;
            }
        });

    }




}
