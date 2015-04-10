package com.pro041.models;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public abstract class Pieza {	
	
	public Bitmap redimensionarImagen(Bitmap mBitmap, float newWidth, float newHeigth){
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
	
	public abstract boolean puedoColocarInicio(Bitmap[][] arrayPiezas);
	public abstract void colocarInicio(Bitmap[][] arrayPiezas);
	public abstract void moverPiezaAbajo(Bitmap[][] arrayPiezas);
	public abstract void girarPieza(Bitmap[][] arrayPiezas);
	public abstract boolean puedoGirarPieza(Bitmap[][] arrayPiezas);
	public abstract boolean puedoMoverAbajo(Bitmap[][] arrayPiezas);
	public abstract int[] getPosBloque1();
	public abstract int[] getPosBloque2();
	public abstract int[] getPosBloque3();
	public abstract int[] getPosBloque4();
	public abstract boolean puedoMoverDer(Bitmap[][] arrayPiezas);
	public abstract void moverPiezaDer(Bitmap[][] arrayPiezas);
	public abstract boolean puedoMoverIzq(Bitmap[][] arrayPiezas);
	public abstract void moverPiezaIzq(Bitmap[][] arrayPiezas);
}
