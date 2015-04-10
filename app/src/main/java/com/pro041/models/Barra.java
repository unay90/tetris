package com.pro041.models;

import com.pro041.pintado.GameView;
import com.pro041_tetris.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Barra extends Pieza {
	
	private GameView gameView;
	private Bitmap azul;
	private int[] posBloque1, posBloque2, posBloque3, posBloque4;
	private int pos;
	
	public Barra(GameView gv, int lado){
		gameView=gv;
		azul=BitmapFactory.decodeResource(gameView.getResources(), R.drawable.azul);
		azul=redimensionarImagen(azul, lado, lado);
		posBloque1=new int[2];
		posBloque2=new int[2];//pos[0]filas pos[1]columnas
		posBloque3=new int[2];
		posBloque4=new int[2];
		pos=1;
	}

	@Override
	public boolean puedoColocarInicio(Bitmap[][] arrayPiezas) {
		if(arrayPiezas[15][3]==null && arrayPiezas[15][4]==null && arrayPiezas[15][5]==null && arrayPiezas[15][6]==null){
			return true;
		}
		return false;
	}

	@Override
	public void colocarInicio(Bitmap[][] arrayPiezas) {
		posBloque1[0]=15;
		posBloque1[1]=3;
		posBloque2[0]=15;
		posBloque2[1]=4;
		posBloque3[0]=15;
		posBloque3[1]=5;
		posBloque4[0]=15;
		posBloque4[1]=6;
		arrayPiezas[posBloque1[0]][posBloque1[1]]=azul;
		arrayPiezas[posBloque2[0]][posBloque2[1]]=azul;
		arrayPiezas[posBloque3[0]][posBloque3[1]]=azul;
		arrayPiezas[posBloque4[0]][posBloque4[1]]=azul;
	}

	@Override
	public void moverPiezaAbajo(Bitmap[][] arrayPiezas) {
		if(pos==1){
			arrayPiezas[posBloque1[0]-1][posBloque1[1]]=arrayPiezas[posBloque1[0]][posBloque1[1]];
			arrayPiezas[posBloque2[0]-1][posBloque2[1]]=arrayPiezas[posBloque2[0]][posBloque2[1]];
			arrayPiezas[posBloque3[0]-1][posBloque3[1]]=arrayPiezas[posBloque3[0]][posBloque3[1]];
			arrayPiezas[posBloque4[0]-1][posBloque4[1]]=arrayPiezas[posBloque4[0]][posBloque4[1]];
			arrayPiezas[posBloque1[0]][posBloque1[1]]=null;
			arrayPiezas[posBloque2[0]][posBloque2[1]]=null;
			arrayPiezas[posBloque3[0]][posBloque3[1]]=null;
			arrayPiezas[posBloque4[0]][posBloque4[1]]=null;
		}else if(pos==2){
			arrayPiezas[posBloque4[0]-1][posBloque4[1]]=arrayPiezas[posBloque4[0]][posBloque4[1]];
			arrayPiezas[posBloque3[0]-1][posBloque3[1]]=arrayPiezas[posBloque3[0]][posBloque3[1]];
			arrayPiezas[posBloque2[0]-1][posBloque2[1]]=arrayPiezas[posBloque2[0]][posBloque2[1]];
			arrayPiezas[posBloque1[0]-1][posBloque1[1]]=arrayPiezas[posBloque1[0]][posBloque1[1]];
			arrayPiezas[posBloque1[0]][posBloque1[1]]=null;
		}
		posBloque1[0]--;
		posBloque2[0]--;
		posBloque3[0]--;
		posBloque4[0]--;
	}

	@Override
	public void girarPieza(Bitmap[][] arrayPiezas) {
		if(pos==1){
			arrayPiezas[posBloque2[0]-1][posBloque2[1]-1]=arrayPiezas[posBloque2[0]][posBloque2[1]];
			arrayPiezas[posBloque3[0]-2][posBloque3[1]-2]=arrayPiezas[posBloque3[0]][posBloque3[1]];
			arrayPiezas[posBloque4[0]-3][posBloque4[1]-3]=arrayPiezas[posBloque4[0]][posBloque4[1]];
			arrayPiezas[posBloque2[0]][posBloque2[1]]=null;
			arrayPiezas[posBloque3[0]][posBloque3[1]]=null;
			arrayPiezas[posBloque4[0]][posBloque4[1]]=null;
			posBloque2[0]--;
			posBloque2[1]--;
			posBloque3[0]=posBloque3[0]-2;
			posBloque3[1]=posBloque3[1]-2;
			posBloque4[0]=posBloque4[0]-3;
			posBloque4[1]=posBloque4[1]-3;
			pos=2;
		}else if(pos==2){
			arrayPiezas[posBloque2[0]+1][posBloque2[1]+1]=arrayPiezas[posBloque2[0]][posBloque2[1]];
			arrayPiezas[posBloque3[0]+2][posBloque3[1]+2]=arrayPiezas[posBloque3[0]][posBloque3[1]];
			arrayPiezas[posBloque4[0]+3][posBloque4[1]+3]=arrayPiezas[posBloque4[0]][posBloque4[1]];
			arrayPiezas[posBloque2[0]][posBloque2[1]]=null;
			arrayPiezas[posBloque3[0]][posBloque3[1]]=null;
			arrayPiezas[posBloque4[0]][posBloque4[1]]=null;
			posBloque2[0]++;
			posBloque2[1]++;
			posBloque3[0]=posBloque3[0]+2;
			posBloque3[1]=posBloque3[1]+2;
			posBloque4[0]=posBloque4[0]+3;
			posBloque4[1]=posBloque4[1]+3;
			pos=1;
		}
	}

	@Override
	public boolean puedoGirarPieza(Bitmap[][] arrayPiezas) {
		if(pos==1){
			if(posBloque4[1]>1 && arrayPiezas[posBloque2[0]-1][posBloque2[1]-1]==null && arrayPiezas[posBloque3[0]-2][posBloque3[1]-2]==null
					&& arrayPiezas[posBloque4[0]-3][posBloque4[1]-3]==null){
				return true;
			}
		}else if(pos==2){
			if(posBloque4[0]<9 && arrayPiezas[posBloque2[0]+1][posBloque2[1]+1]==null && arrayPiezas[posBloque3[0]+2][posBloque3[1]+2]==null
					&& arrayPiezas[posBloque4[0]+3][posBloque4[1]+3]==null){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean puedoMoverAbajo(Bitmap[][] arrayPiezas) {
		if(pos==1){
			if(posBloque1[0]>1 && posBloque2[0]>1 && posBloque3[0]>1 && posBloque4[0]>1
					&& arrayPiezas[posBloque1[0]-1][posBloque1[1]]==null && arrayPiezas[posBloque2[0]-1][posBloque2[1]]== null
					&& arrayPiezas[posBloque3[0]-1][posBloque3[1]]==null && arrayPiezas[posBloque4[0]-1][posBloque4[1]]==null){
				return true;
			}
		}else if(pos==2){
			if(posBloque4[0]>1 && arrayPiezas[posBloque4[0]-1][posBloque4[1]]==null){
				return true;
			}
		}
		return false;
	}

	@Override
	public int[] getPosBloque1() {
		return posBloque1;
	}

	@Override
	public int[] getPosBloque2() {
		return posBloque2;
	}

	@Override
	public int[] getPosBloque3() {
		return posBloque3;
	}

	@Override
	public int[] getPosBloque4() {
		return posBloque4;
	}

	@Override
	public boolean puedoMoverDer(Bitmap[][] arrayPiezas) {
		if(pos==1){
			if(posBloque4[1]<9 && arrayPiezas[posBloque4[0]][posBloque4[1]+1]==null){
				return true;
			}
		}else if(pos==2){
			if(posBloque1[1]<9 && posBloque2[1]<9 && posBloque3[1]<9 && posBloque4[1]<9 
					&& arrayPiezas[posBloque1[0]][posBloque1[1]+1]==null && arrayPiezas[posBloque2[0]][posBloque2[1]+1]==null 
					&& arrayPiezas[posBloque3[0]][posBloque3[1]+1]==null && arrayPiezas[posBloque4[0]][posBloque4[1]+1]==null){
				return true;
			}
		}
		return false;
	}

	@Override
	public void moverPiezaDer(Bitmap[][] arrayPiezas) {
		if(pos==1){
			arrayPiezas[posBloque4[0]][posBloque4[1]+1]=arrayPiezas[posBloque4[0]][posBloque4[1]];
			arrayPiezas[posBloque1[0]][posBloque1[1]]=null;
		}else if(pos==2){
			arrayPiezas[posBloque1[0]][posBloque1[1]+1]=arrayPiezas[posBloque1[0]][posBloque1[1]];
			arrayPiezas[posBloque2[0]][posBloque2[1]+1]=arrayPiezas[posBloque2[0]][posBloque2[1]];
			arrayPiezas[posBloque3[0]][posBloque3[1]+1]=arrayPiezas[posBloque3[0]][posBloque3[1]];
			arrayPiezas[posBloque4[0]][posBloque4[1]+1]=arrayPiezas[posBloque4[0]][posBloque4[1]];
			arrayPiezas[posBloque1[0]][posBloque1[1]]=null;
			arrayPiezas[posBloque2[0]][posBloque2[1]]=null;
			arrayPiezas[posBloque3[0]][posBloque3[1]]=null;
			arrayPiezas[posBloque4[0]][posBloque4[1]]=null;
		}
		posBloque1[1]++;
		posBloque2[1]++;
		posBloque3[1]++;
		posBloque4[1]++;
	}

	@Override
	public boolean puedoMoverIzq(Bitmap[][] arrayPiezas) {
		if(pos==1){
			if(posBloque1[1]>0 && arrayPiezas[posBloque1[0]][posBloque1[1]-1]==null){
				return true;
			}
		}else if(pos==2){
			if(posBloque1[1]>0 && arrayPiezas[posBloque1[0]][posBloque1[1]-1]==null && arrayPiezas[posBloque2[0]][posBloque2[1]-1]==null 
					&& arrayPiezas[posBloque3[0]][posBloque3[1]-1]==null && arrayPiezas[posBloque4[0]][posBloque4[1]-1]==null){
				return true;
			}
		}
		return false;
	}

	@Override
	public void moverPiezaIzq(Bitmap[][] arrayPiezas) {
		if(pos==1){
			arrayPiezas[posBloque1[0]][posBloque1[1]-1]=arrayPiezas[posBloque1[0]][posBloque1[1]];
			arrayPiezas[posBloque4[0]][posBloque4[1]]=null;
		}else if(pos==2){
			arrayPiezas[posBloque1[0]][posBloque1[1]-1]=arrayPiezas[posBloque1[0]][posBloque1[1]];
			arrayPiezas[posBloque2[0]][posBloque2[1]-1]=arrayPiezas[posBloque2[0]][posBloque2[1]];
			arrayPiezas[posBloque3[0]][posBloque3[1]-1]=arrayPiezas[posBloque3[0]][posBloque3[1]];
			arrayPiezas[posBloque4[0]][posBloque4[1]-1]=arrayPiezas[posBloque4[0]][posBloque4[1]];
			arrayPiezas[posBloque1[0]][posBloque1[1]]=null;
			arrayPiezas[posBloque2[0]][posBloque2[1]]=null;
			arrayPiezas[posBloque3[0]][posBloque3[1]]=null;
			arrayPiezas[posBloque4[0]][posBloque4[1]]=null;
		}
		posBloque1[1]--;
		posBloque2[1]--;
		posBloque3[1]--;
		posBloque4[1]--;
	}

}
