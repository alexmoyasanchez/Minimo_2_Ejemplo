package com.example.minimo2ejemplo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText user;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.username);
        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("repositorio","error");

        if (usuario != "error") {
            //CargarSharedPreferences();
        }
    }

    public void buscar (View view){

        String username = user.getText().toString();
        Intent intent = new Intent(this,BusquedaActivity.class);
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",username);
        editor.commit();
        bundle.putString("username",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void cargarSharedPreference(){
        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("repositorio","error");

        Intent intent = new Intent(this, BusquedaActivity.class);

        bundle.putString("username", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}