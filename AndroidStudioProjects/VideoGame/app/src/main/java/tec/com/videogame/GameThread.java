package tec.com.videogame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.util.Random;

import static android.graphics.Paint.Style.FILL;

/**
 * Created by Froylan on 09/11/2016.
 */


public class GameThread extends Thread {
        public   Thread tiempos;
    SurfaceHolder mSurfaceHolder=null;
    Handler mHandler=null;
    Context mContext=null;
    boolean izq,der,arriba,abajo,banderabomba;
    float velocidad=2;
    Resources resources;
    int segundos=0;
    float x=50,y=100;
    int auxX=20,auxY=20;
    int fantX=200,fantY=200,moradoX=375,moradoY=100;
    private boolean dx=false,mx=false;
    public int score=0;
    boolean bandera1,bandera2,bandera3;
    boolean detiene=false,musica=false;
    MediaPlayer medplayer;
    int velociFantasmas=2;




    GameThread gameThread;


    private boolean mRun=false;
    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {

        mSurfaceHolder = surfaceHolder;
        mHandler = handler;
        mContext = context;


    }


    @Override
    public void run() {
        Looper.prepare();


        //empieza el ciclo principal del juego
        while (mRun) {
            Log.i("siii","si");
            Canvas c = null;
            try {

//obtenemos una instancia al canvas de la superficie
                c = mSurfaceHolder.lockCanvas(null);
//nos aseguramos de que solo un hilo a la vez manipule la superficie
                synchronized (mSurfaceHolder) {

                        update();//actualizamos la lógica del juego
                        doDraw(c);//dibujamos sobre el canvas

                }
            } finally {
// do this in a finally so that if an exception is thrown
// during the above, we don't leave the Surface in an
// inconsistent state
                if (c != null) {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
        Looper.loop();

    }
    private void update() {
        if(detiene==false&&musica==false) {
            medplayer = MediaPlayer.create(mContext, R.raw.aud);
            medplayer.start();
            musica=true;
        }



        if(fantX>22&&dx==false){
            fantX-=velociFantasmas;
            if(fantX==22||fantX==21){
                dx=true;
            }

        }else if(fantX<774&&dx==true){
            fantX+=velociFantasmas;
            if(fantX==774){
                dx=false;
            }
        }

        if(moradoY>22&&mx==false){
            moradoY-=velociFantasmas;

            if(moradoY==22){
                mx=true;
            }
        }
        if(moradoY<330&&mx==true){
            moradoY+=velociFantasmas;
            if(moradoY==330){
                mx=false;
            }
        }

        if(izq&&x>=22){

            x-=velocidad;
        }
        if(der&&x<=774){
         //   Log.i("infoo",x+"");
            x+=velocidad;
        }
        if(arriba&&y>=22){
            y-=velocidad;
        }
        if(abajo&&y<=330){
            y+=velocidad;
        }

    }

    public void bombas(){
         tiempos= new Thread(){
          public void run(){
              try {
                  while(segundos<100) {
                      handler.post(proceso);
                      Thread.sleep(1000);
                      segundos++;


                  }
              }catch (InterruptedException e){;}
          }
        };
        tiempos.start();
    }
    Handler handler=new Handler();
    Runnable proceso= new Runnable() {
        @Override
        public void run() {
            try{
            if(segundos==8){
                velocidad+=1;


                segundos=0;
            }
                Random();


            }catch (Exception e){;}


        }
    };




    private void doDraw(Canvas canvas) {

        Bitmap bitmap=BitmapFactory.decodeResource(resources,R.drawable.bomberman);
        canvas.drawColor(Color.rgb(0,51,0));
        Paint paint= new Paint();
        paint.setColor(Color.rgb(191,191,191));

        canvas.drawRect(20,407,0,0,paint);
        canvas.drawRect(852,20,20,0,paint);
        canvas.drawRect(832,407,852,20,paint);
        canvas.drawRect(852,387,20,407,paint);
        canvas.drawBitmap(bitmap,x,y,null);


        Bitmap bomba = BitmapFactory.decodeResource(resources, R.drawable.bomba);
        canvas.drawBitmap(bomba, auxX, auxY, null);

        Bitmap fantasma=BitmapFactory.decodeResource(resources,R.drawable.fantasma);
        canvas.drawBitmap(fantasma,fantX,fantY,null);

        Bitmap morado = BitmapFactory.decodeResource(resources,R.drawable.fantasmamorado);
        canvas.drawBitmap(morado,moradoX,moradoY,null);


        int bh = bitmap.getHeight();
        int bw = bitmap.getWidth();

        int mh = morado.getHeight();
        int mw = morado.getWidth();


        if (x-15 + bh < moradoX) {
            bandera1 = false;

        }
        else if ( y+20 + bw < moradoY) {
           bandera1 =false;

        }
        else if (x+15 > moradoX + mw) {
            bandera1 =false;

        }
        else if (y-15 > moradoY + mh) {
            bandera1=false;

        }else{
            bandera1=true;


        }

        if(bandera1== true){
           alerta2();
        }

        if (x-15 + bh < fantX) {
            bandera2 = false;

        }
        else if ( y-10 + bw < fantY) {
            bandera2 =false;

        }
        else if (x+15 > fantX + mw) {
            bandera2 =false;
        }
        else if (y+15 > fantY + mh) {
            bandera2=false;

        }else{
            bandera2=true;
        }

        if(bandera2== true){

            alerta2();
        }

//bomba
        if (x + bh < auxX) {
            bandera3 = false;
            banderabomba=false;

        }
        else if ( y + bw < auxY) {
            bandera3 =false;
            banderabomba=false;

        }
        else if (x > auxX + mw) {
            bandera3 =false;
            banderabomba=false;

        }
        else if (y > auxY + mh) {
            bandera3=false;
            banderabomba=false;

        }else{
            bandera3=true;
        }

        if(bandera3== true && banderabomba==false){
           score+=1;
           Log.i("score",score+"");
            bandera3=false;
            banderabomba=true;
            Random grandom = new Random();
            auxX=22+grandom.nextInt(774);
            auxY=22+grandom.nextInt(330);
        }

        
// dibuja tus componentes aquí
    }
public void  Random( ){
    if(segundos==4||segundos==8 ){
        Random grandom = new Random();
        auxX=22+grandom.nextInt(774);
        auxY=22+grandom.nextInt(330);






    }
}

    public void alerta2 (){

        medplayer.pause();
        tiempos.interrupt();
        mRun=false;


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setMessage("GAME OVER!!!!!!  Scrore:"+score).setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gameThread.interrupt();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void setRunning(boolean b) {
        mRun = b;
    }



}


