package com.pro041.pintado;

import java.util.Calendar;
import java.util.Random;

import com.pro041.models.Barra;
import com.pro041.models.Cuadrado;
import com.pro041.models.Ele;
import com.pro041.models.Ese;
import com.pro041.models.Jota;
import com.pro041.models.Pieza;
import com.pro041.models.Triangulo;
import com.pro041.models.Zeta;
import com.pro041_tetris.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	private HiloPantalla hiloPantalla;
	private Pieza pieza;
	private Bitmap[][] arrayPiezas;
	private int ladoBloque;
	private int nPieza;
	private Random r;
	private int[] posBloque1, posBloque2, posBloque3, posBloque4;
	private int puntos, y, x, alto;
	private Paint p;
	private Bitmap bmpCuadrado, bmpBarra, bmpTriangulo, bmpEse, bmpZeta, bmpJota, bmpEle;
    private static final int MAX_CLICK_DURATION = 125;
    private long startClickTime;
    private boolean auto=false;

	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		ladoBloque=getWidth()*10/100;
		bmpCuadrado=BitmapFactory.decodeResource(getResources(), R.drawable.cuadrado);
		bmpCuadrado=redimensionarImagen(bmpCuadrado, ladoBloque*2, ladoBloque*2);
		bmpBarra=BitmapFactory.decodeResource(getResources(), R.drawable.barra);
		bmpBarra=redimensionarImagen(bmpBarra, ladoBloque*4, ladoBloque);
		bmpTriangulo=BitmapFactory.decodeResource(getResources(), R.drawable.triangulo);
		bmpTriangulo=redimensionarImagen(bmpTriangulo, ladoBloque*3, ladoBloque*2);
		bmpEse=BitmapFactory.decodeResource(getResources(), R.drawable.ese);
		bmpEse=redimensionarImagen(bmpEse, ladoBloque*3, ladoBloque*2);
		bmpZeta=BitmapFactory.decodeResource(getResources(), R.drawable.zeta);
		bmpZeta=redimensionarImagen(bmpZeta, ladoBloque*3, ladoBloque*2);
		bmpEle=BitmapFactory.decodeResource(getResources(), R.drawable.ele);
		bmpEle=redimensionarImagen(bmpEle, ladoBloque*3, ladoBloque*2);
		bmpJota=BitmapFactory.decodeResource(getResources(), R.drawable.jota);
		bmpJota=redimensionarImagen(bmpJota, ladoBloque*3, ladoBloque*2);
		puntos=0;
		p=new Paint();
		p.setTextSize(getHeight()*5/100);
		r=new Random();
		arrayPiezas=new Bitmap[16][10];//16 filas 10 columnas
		siguientePieza();
		if(puedoSacarPieza()){
			sacarPieza();
		}
		siguientePieza();
		hiloPantalla=new HiloPantalla(this);
		hiloPantalla.setRunning(true);
		hiloPantalla.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry=true;
		while(retry){
			try{
				hiloPantalla.setRunning(false);
				hiloPantalla.join();
				retry=false;
			}catch(InterruptedException e){
			}
		}
	}
	
	public void onDraw(Canvas canvas){
		if(hiloPantalla.isRunning()){
			alto=canvas.getHeight();
			int ancho=canvas.getWidth();
			canvas.drawRGB(255, 255, 255);
			switch (nPieza) {
			case 0:
				canvas.drawBitmap(bmpCuadrado, ancho-bmpCuadrado.getWidth(), 0, null);
				break;
			case 1:
				canvas.drawBitmap(bmpBarra, ancho-bmpBarra.getWidth(), 0, null);
				break;
			case 2:
				canvas.drawBitmap(bmpTriangulo, ancho-bmpTriangulo.getWidth(), 0, null);
				break;
			case 3:
				canvas.drawBitmap(bmpEse, ancho-bmpEse.getWidth(), 0, null);
				break;
			case 4:
				canvas.drawBitmap(bmpZeta, ancho-bmpZeta.getWidth(), 0, null);
				break;
			case 5:
				canvas.drawBitmap(bmpEle, ancho-bmpEle.getWidth(), 0, null);
				break;
			case 6:
				canvas.drawBitmap(bmpJota, ancho-bmpJota.getWidth(), 0, null);
				break;
			}
            if (auto){
                if (hiloPantalla.getnFPS()%5 == 0){
                    flujo();
                }
            }else {
                if (hiloPantalla.getnFPS() == 0 || hiloPantalla.getnFPS() == 10 || hiloPantalla.getnFPS() == 20 || hiloPantalla.getnFPS() == 30) {
                    flujo();
                }
            }

			canvas.drawText(Integer.toString(puntos), 5, getHeight()*6/100, p);
			synchronized (arrayPiezas) {
				for(int f=0; f<16; f++){
					for(int c=0; c<10; c++){
						if(arrayPiezas[f][c]!=null){
							canvas.drawBitmap(arrayPiezas[f][c], c*ladoBloque, alto-(f*ladoBloque), null);
						}
					}
				}
			}
		}
	}

    private void flujo(){
        if (pieza.puedoMoverAbajo(arrayPiezas)) {
            pieza.moverPiezaAbajo(arrayPiezas);
        } else if (puedoSacarPieza()) {
            sacarPieza();
            siguientePieza();
        } else {
            finJuego();
        }
    }
	
	private void finJuego() {
		for(int f=0; f<16; f++){
			for(int c=0; c<10; c++){
				arrayPiezas[f][c]=null;
			}
		}
		if(puedoSacarPieza()){
			sacarPieza();
			siguientePieza();
		}
		hiloPantalla.setFinJuego(true);
	}

	public boolean onTouchEvent(MotionEvent event){
        int action=event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startClickTime = Calendar.getInstance().getTimeInMillis();
                y = (int) event.getY();
                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (!auto) {
                    long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                    if (clickDuration < MAX_CLICK_DURATION) {
                        posBloque1 = pieza.getPosBloque1();
                        posBloque2 = pieza.getPosBloque2();
                        posBloque3 = pieza.getPosBloque3();
                        posBloque4 = pieza.getPosBloque4();
                        //cojo la posicion en la que deberia estar cada bloque en la pantalla
                        Rect bloque1 = new Rect(posBloque1[1] * ladoBloque, getHeight() - (posBloque1[0] * ladoBloque), (posBloque1[1] * ladoBloque) + ladoBloque, (getHeight() - (posBloque1[0] * ladoBloque) + ladoBloque));
                        Rect bloque2 = new Rect(posBloque2[1] * ladoBloque, getHeight() - (posBloque2[0] * ladoBloque), (posBloque2[1] * ladoBloque) + ladoBloque, (getHeight() - (posBloque2[0] * ladoBloque) + ladoBloque));
                        Rect bloque3 = new Rect(posBloque3[1] * ladoBloque, getHeight() - (posBloque3[0] * ladoBloque), (posBloque3[1] * ladoBloque) + ladoBloque, (getHeight() - (posBloque3[0] * ladoBloque) + ladoBloque));
                        Rect bloque4 = new Rect(posBloque4[1] * ladoBloque, getHeight() - (posBloque4[0] * ladoBloque), (posBloque4[1] * ladoBloque) + ladoBloque, (getHeight() - (posBloque4[0] * ladoBloque) + ladoBloque));
                        if (bloque1.contains(x, y) || bloque2.contains(x, y) || bloque3.contains(x, y) || bloque4.contains(x, y)) {
                            synchronized (arrayPiezas) {
                                if (pieza.puedoGirarPieza(arrayPiezas)) {
                                    pieza.girarPieza(arrayPiezas);
                                }
                            }
                        } else if (x >= getWidth() * 50 / 100) {
                            synchronized (arrayPiezas) {
                                if (pieza.puedoMoverDer(arrayPiezas)) {
                                    pieza.moverPiezaDer(arrayPiezas);
                                }
                            }
                        } else if (x < getWidth() * 50 / 100) {
                            synchronized (arrayPiezas) {
                                if (pieza.puedoMoverIzq(arrayPiezas)) {
                                    pieza.moverPiezaIzq(arrayPiezas);
                                }
                            }
                        }
                    } else {
                        int espacio = alto / 100 * 5;
                        if ((event.getY() - y) >= espacio) {
                            auto=true;
                        }
                    }
                }
                break;
        }
		return true;
	}

	private void sacarPieza(){
		switch (nPieza) {
		case 0:
			pieza.colocarInicio(arrayPiezas);
			break;
		case 1:
			pieza.colocarInicio(arrayPiezas);	
			break;
		case 2:
			pieza.colocarInicio(arrayPiezas);
			break;
		case 3:
			pieza.colocarInicio(arrayPiezas);
			break;
		case 4:
			pieza.colocarInicio(arrayPiezas);
			break;
		case 5:
			pieza.colocarInicio(arrayPiezas);
			break;
		case 6:
			pieza.colocarInicio(arrayPiezas);	
			break;
		}
	}
	
	public boolean puedoSacarPieza(){
        auto=false;
		limpiarLineas();
		switch (nPieza) {
		case 0:
			pieza=new Cuadrado(this, ladoBloque);
			break;
		case 1:
			pieza=new Barra(this, ladoBloque);
			break;
		case 2:
			pieza=new Triangulo(this, ladoBloque);
			break;
		case 3:
			pieza=new Ese(this, ladoBloque);
			break;
		case 4:
			pieza=new Zeta(this, ladoBloque);
			break;
		case 5:
			pieza=new Ele(this, ladoBloque);
			break;
		case 6:
			pieza=new Jota(this, ladoBloque);		
			break;
		}
		if(pieza.puedoColocarInicio(arrayPiezas)){
			return true;
		}
		return false;
	}

	private void limpiarLineas() {
        int nLineas=0;
		for(int f=1; f<15; f++){//no hago la ultima para ke no de error al igualarlo con una fila superior
			if(arrayPiezas[f][0]!=null && arrayPiezas[f][1]!=null && arrayPiezas[f][2]!=null && arrayPiezas[f][3]!=null && arrayPiezas[f][4]!=null && arrayPiezas[f][5]!=null &&
					arrayPiezas[f][6]!=null && arrayPiezas[f][7]!=null && arrayPiezas[f][8]!=null && arrayPiezas[f][9]!=null){
				for(int fila=f; fila<15; fila++){
					for(int c=0; c<10; c++){
						arrayPiezas[fila][c]=arrayPiezas[fila+1][c];
					}
				}
				nLineas++;
				f--;
			}
		}
        switch (nLineas){
            case 1:
                puntos=puntos+10;
                break;
            case 2:
                puntos=puntos+25;
                break;
            case 3:
                puntos=puntos+40;
                break;
            case 4:
                puntos=puntos+60;
                break;
        }
	}
	
	private void siguientePieza(){
		nPieza=r.nextInt(7);
	}
	
	private Bitmap redimensionarImagen(Bitmap mBitmap, float newWidth, float newHeigth){
		//Redimensionamos    
		int width = mBitmap.getWidth();    
		int height = mBitmap.getHeight();   
		float scaleWidth = ((float) newWidth) / width;    
		float scaleHeight = ((float) newHeigth) / height;    
		// create a matrix for the manipulation    
		Matrix matrix = new Matrix();    
		// resize the bit map    
		matrix.postScale(scaleWidth, scaleHeight);    
		// recreate the new Bitmap    
		return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false); 
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	public int getPuntos(){
		return puntos;
	}

}
