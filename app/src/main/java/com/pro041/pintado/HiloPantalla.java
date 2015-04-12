package com.pro041.pintado;

import com.pro041_tetris.MainActivity;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

@SuppressLint("WrongCall")
public class HiloPantalla extends Thread {

	private GameView view;
	private boolean running=false;
	private int FPS=MainActivity.fps;
	private boolean finJuego;
	private int nFPS;
	
	public HiloPantalla(GameView view){
		this.view=view;
		finJuego=true;
		nFPS=1;
	}
	
	public void run(){
		long startTime;
		long sleepTime;
		while(running){
			long ticksPS=1000/FPS;
			Canvas c=null;
			startTime=System.currentTimeMillis();
			try{
				c=view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
				nFPS++;
				if(nFPS>=FPS){
					nFPS=1;
				}
			}finally{
				if(c!=null){
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime=ticksPS-(System.currentTimeMillis()-startTime);
			try{
				if(sleepTime>0){
					sleep(sleepTime);
				}
			}catch(Exception e){
			}
			if(finJuego){
				try {
					sleep(2000);
					comprobarPuntuacion();
					((GameView) view).setPuntos(0);
					finJuego=false;
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	private void comprobarPuntuacion(){
		if(MainActivity.puntuacion<view.getPuntos()){
			MainActivity.guardarPuntuacion(view.getPuntos());
		}
	}

	public void setFinJuego(boolean finJuego) {
		this.finJuego = finJuego;
	}

    public int getnFPS() {
        return nFPS;
    }

    public void setRunning(boolean run){
        running=run;
    }

    public boolean isRunning(){
        return running;
    }
}
