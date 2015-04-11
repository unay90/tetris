package com.pro041_tetris;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static int puntuacion, fps=20;
	private static SharedPreferences preferencias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		preferencias=getSharedPreferences("tetris", Context.MODE_PRIVATE);
		puntuacion=preferencias.getInt("puntuacion", 0);
	}

	public void jugar(View v){
		Intent i =new Intent(this, TetrisJuego.class);
		this.startActivity(i);
	}
	
	public void salir(View v){
		System.exit(0);
	}
	
	public void mostrarPuntuacion(View v){
		puntuacion=preferencias.getInt("puntuacion", 0);
		Toast.makeText(getApplicationContext(), puntuacion+"", Toast.LENGTH_SHORT).show();
	}

    public void velocidad(View v){
        final CharSequence[] items = {"Lento", "Rapido"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Velocidad");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item){
                    case 0:
                        fps=20;
                        break;
                    case 1:
                        fps=30;
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	public static void guardarPuntuacion(int p){
		Editor editor=preferencias.edit();
		editor.putInt("puntuacion", p);
		editor.commit();
	}


}
