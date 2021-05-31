package com.example.minimo2ejemplo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusquedaActivity extends AppCompatActivity {

    TextView repositories;
    TextView following;
    ImageView imagen;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Followers> followers;
    MyAdapter myAdapter;
    TextView username;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String usuario = bundle.getString("username");
        setContentView(R.layout.activity_busqueda);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        repositories=findViewById(R.id.repositories);
        following=findViewById(R.id.following);
        username = findViewById(R.id.username);
        imagen = findViewById(R.id.imageView2);
        progressBar=findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        context = getApplicationContext();
        getUser(usuario);
        getFollowers(usuario);
    }

    private void getUser(String user) {
        progressBar.setVisibility(View.VISIBLE);
        Call<User> call = APIClient.getUserService().getInfo(user);
        Intent intent = new Intent(this, MainActivity.class);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                repositories.setText(String.valueOf(user.getRepos()));
                following.setText(String.valueOf(user.getFollowing()));
                String URL = user.getAvatar();
                Glide.with(context).load(URL).into(imagen);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void getFollowers(String user) {

        Call<List<Followers>> call = APIClient.getUserService().getFollower(user);
        Intent intent = new Intent(this, MainActivity.class);
        call.enqueue(new Callback<List<Followers>>() {
            @Override
            public void onResponse(Call<List<Followers>> call, Response<List<Followers>> response) {
                if(response.code() == 200) {
                    List<Followers> followers = response.body();
                    progressBar.setVisibility(View.INVISIBLE);
                    myAdapter = new MyAdapter();
                    myAdapter.setData(followers);
                    recyclerView.setAdapter(myAdapter);
                }
                else if(response.code() == 404)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(BusquedaActivity.this).create();

                    alertDialog.setMessage("No se ha encontrado al usuario");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    SharedPreferences.Editor editor = getSharedPreferences("Credenciales", MODE_PRIVATE).edit();
                                    editor.clear().apply();
                                    startActivity(intent);
                                }
                            });
                }
            }



            @Override
            public void onFailure(Call<List<Followers>> call, Throwable t) {

            }
        });

    }

}