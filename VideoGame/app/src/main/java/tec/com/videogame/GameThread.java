package tec.com.videogame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

import static android.graphics.Paint.Style.FILL;

/**
 * Created by Froylan on 09/11/2016.
 */


public class GameThread extends Thread {
    GameThread gameThread;
    SurfaceHolder mSurfaceHolder=null;
    Handler mHandler=null;
    Context mContext=null;
    boolean izq,der,arriba,abajo;
    float velocidad=2;
    Resources resources;
    int segundos=0;
    float x=50,y=100;
    int auxX=20,auxY=20;
    int fantX=200,fantY=200,moradoX=375,moradoY=100;
    private boolean dx=false,mx=false;

    private boolean mRun=false;
    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {

        mSurfaceHolder = surfaceHolder;
        mHandler = handler;
        mContext = context;

    }


    @Override
    public void run() {
        //empieza el ciclo principal del juego
        while (mRun) {
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
    }
    private void update() {

        if(fantX>22&&dx==false){
            fantX-=2;
            if(fantX==22||fantX==21){
                dx=true;
            }

        }else if(fantX<774&&dx==true){
            fantX+=2;
            if(fantX==774){
                dx=false;
            }
        }

        if(moradoY>22&&mx==false){
            moradoY-=2;

            if(moradoY==22){
                mx=true;
            }
        }
        if(moradoY<330&&mx==true){
            moradoY+=2;
            if(moradoY==330){
                mx=false;
            }
        }
    Log.i("infoo",""+fantX);

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
        final Thread tiempo= new Thread(){
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
        tiempo.start();


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
                if(segundos==4||segundos==8 ){
                    Random grandom = new Random();
                    auxX=22+grandom.nextInt(774);
                    auxY=22+grandom.nextInt(330);






                }

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



        
// dibuja tus componentes aquí
    }

    public void setRunning(boolean b) {
        mRun = b;
    }


}
