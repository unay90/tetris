package com.pro041.models;

import com.pro041.pintado.GameView;
import com.pro041_tetris.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Cuadrado extends Pieza {
	
	private GameView gameView;
	private Bitmap amarillo;
	private int[] posBloque1, posBloque2, posBloque3, posBloque4;

	public Cuadrado(GameView gv, int lado){
		gameView=gv;
		amarillo=BitmapFactory.decodeResource(gameView.getResources(), R.drawable.amarillo);
		amarillo=redimensionarImagen(amarillo, lado, lado);
		posBloque1=new int[2];
		posBloque2=new int[2];//pos[0]filas pos[1]columnas
		posBloque3=new int[2];
		posBloque4=new int[2];
	}
	
	@Override
	public boolean puedoColocarInicio(Bitmap[][] arrayPiezas) {
		if(arrayPiezas[15][4]==null && arrayPiezas[15][5]==null && arrayPiezas[14][4]==null && arrayPiezas[14][5]==null){
			return true;
		}
		return false;
	}
	
	public void colocarInicio(Bitmap[][] arrayPiezas){
		posBloque1[0]=15;//arriba a la izq
		posBloque1[1]=4;
		posBloque2[0]=15;//arriba a la der
		posBloque2[1]=5;
		posBloque3[0]=14;//abajo a la izq
		posBloque3[1]=4;
		posBloque4[0]=14;//abajo a la der
		posBloque4[1]=5;
		arrayPiezas[posBloque1[0]][posBloque1[1]]=amarillo;
		arrayPiezas[posBloque2[0]][posBloque2[1]]=amarillo;
		arrayPiezas[posBloque3[0]][posBloque3[1]]=amarillo;
		arrayPiezas[posBloque4[0]][posBloque4[1]]=amarillo;
	}
	
	public boolean puedoMoverAbajo(Bitmap[][] arrayPiezas){
		if(arrayPiezas[posBloque3[0]-1][posBloque3[1]]==null && arrayPiezas[posBloque4[0]-1][posBloque4[1]]==null && posBloque3[0]>1 && posBloque4[0]>1){
			return true;//para saber si esta dentro de la pantalla hay ke poner >1, si fuese 0 
		}				//kedaria fuera de la pantalla por la posicion 0,0
		return false;
	}
	
	public void moverPiezaAbajo(Bitmap[][] arrayPiezas){
		//muevo la pieza en el array. Primero muevo los de abajo
		arrayPiezas[posBloque3[0]-1][posBloque3[1]]=arrayPiezas[posBloque3[0]][posBloque3[1]];
		arrayPiezas[posBloque4[0]-1][posBloque4[1]]=arrayPiezas[posBloque4[0]][posBloque4[1]];
		arrayPiezas[posBloque1[0]-1][posBloque1[1]]=arrayPiezas[posBloque1[0]][posBloque1[1]];
		arrayPiezas[posBloque2[0]-1][posBloque2[1]]=arrayPiezas[posBloque2[0]][posBloque2[1]];
		//pongo los dos cuadros superiores a null
		arrayPiezas[posBloque1[0]][posBloque1[1]]=null;
		arrayPiezas[posBloque2[0]][posBloque2[1]]=null;
		posBloque1[0]--;
		posBloque2[0]--;
		posBloque3[0]--;
		posBloque4[0]--;
	}

	@Override
	public void girarPieza(Bitmap[][] arrayPiezas) {
	}

	@Override
	public boolean puedoGirarPieza(Bitmap[][] arrayPiezas) {
		return false;
	}

	public int[] getPosBloque1() {
		return posBloque1;
	}

	public int[] getPosBloque2() {
		return posBloque2;
	}

	public int[] getPosBloque3() {
		return posBloque3;
	}

	public int[] getPosBloque4() {
		return posBloque4;
	}

	@Override
	public boolean puedoMoverDer(Bitmap[][] arrayPiezas) {
		//primero se comprueba si estoy dentro de la pantalla en vez de saber si la siguiente posicion es null
		//ya qu eme da error de que me salgo del array. En el caso del movimiento hacia abajo da igual porque
		//la posicion 0 esta fuera de la pantalla
		if(posBloque2[1]<9 && posBloque4[1]<9 && arrayPiezas[posBloque2[0]][posBloque2[1]+1]==null && arrayPiezas[posBloque4[0]][posBloque4[1]+1]==null){
			return true;
		}
		return false;
	}

	@Override
	public void moverPiezaDer(Bitmap[][] arrayPiezas) {
		//muevo la pieza en el array. Primero muevo los de la der
		arrayPiezas[posBloque2[0]][posBloque2[1]+1]=arrayPiezas[posBloque2[0]][posBloque2[1]];
		arrayPiezas[posBloque4[0]][posBloque4[1]+1]=arrayPiezas[posBloque4[0]][posBloque4[1]];
		arrayPiezas[posBloque1[0]][posBloque1[1]+1]=arrayPiezas[posBloque1[0]][posBloque1[1]];
		arrayPiezas[posBloque3[0]][posBloque3[1]+1]=arrayPiezas[posBloque3[0]][posBloque3[1]];
		//pongo los 2 cuadros de la izq a null
		arrayPiezas[posBloque1[0]][posBloque1[1]]=null;
		arrayPiezas[posBloque3[0]][posBloque3[1]]=null;
		//actualizo la posicion de los bloques
		posBloque1[1]++;
		posBloque2[1]++;
		posBloque3[1]++;
		posBloque4[1]++;
	}

	@Override
	public boolean puedoMoverIzq(Bitmap[][] arrayPiezas) {
		if(posBloque1[1]>0 && posBloque3[1]>0 && arrayPiezas[posBloque1[0]][posBloque1[1]-1]==null && arrayPiezas[posBloque3[0]][posBloque3[1]-1]==null){
			return true;
		}
		return false;
	}

	@Override
	public void moverPiezaIzq(Bitmap[][] arrayPiezas) {
		//muevo la pieza en el array. Primero muevo los de la izq
		arrayPiezas[posBloque1[0]][posBloque1[1]-1]=arrayPiezas[posBloque1[0]][posBloque1[1]];
		arrayPiezas[posBloque3[0]][posBloque3[1]-1]=arrayPiezas[posBloque3[0]][posBloque3[1]];
		arrayPiezas[posBloque2[0]][posBloque2[1]-1]=arrayPiezas[posBloque2[0]][posBloque2[1]];
		arrayPiezas[posBloque4[0]][posBloque4[1]-1]=arrayPiezas[posBloque4[0]][posBloque4[1]];
		//pongo los 2 cuadros de la der a null
		arrayPiezas[posBloque2[0]][posBloque2[1]]=null;
		arrayPiezas[posBloque4[0]][posBloque4[1]]=null;
		//actualizo la posicion de los bloques
		posBloque1[1]--;
		posBloque2[1]--;
		posBloque3[1]--;
		posBloque4[1]--;
	}
}
