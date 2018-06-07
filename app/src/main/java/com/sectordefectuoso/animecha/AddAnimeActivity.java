package com.sectordefectuoso.animecha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddAnimeActivity extends MainActivity {
    EditText txtTitle, txtDescription, txtGenre, txtEpisodes, txtEpisodeDuration, txtStudio, txtPoster, txtYear;
    Button btnAddAnime;
    ArrayList<Anime> animes = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Anime");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anime);

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtGenre = findViewById(R.id.txtGenre);
        txtEpisodes = findViewById(R.id.txtEpisodes);
        txtEpisodeDuration = findViewById(R.id.txtEpisodeDuration);
        txtStudio = findViewById(R.id.txtStudio);
        txtPoster = findViewById(R.id.txtPoster);
        txtYear = findViewById(R.id.txtYear);
        btnAddAnime = findViewById(R.id.btnAddAnime);

        btnAddAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAnime();
            }

            private void AddAnime(){
                //coje los datos de los campos y los envia a Firebase
                String Title = txtTitle.getText().toString();
                String Description = txtDescription.getText().toString();
                String Genre = txtGenre.getText().toString();
                String Episodes = txtEpisodes.getText().toString();
                String EpisodeDuration = txtEpisodeDuration.getText().toString();
                String Studio = txtStudio.getText().toString();
                String Poster = txtPoster.getText().toString();
                String Year = txtYear.getText().toString();
                //Revisar que se hayan ingresado todos los datos
                if (TextUtils.isEmpty(Title)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa el Titulo", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Description)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa una Descripcion", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Genre)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa un Genero", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Episodes)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa el numero de episodios", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(EpisodeDuration)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa la duracion aproximada de cada episodio", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Studio)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa el Estudio", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Poster)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa una direccion de la imagen del poster", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Year)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa el Ano de Estreno", Toast.LENGTH_SHORT).show();
                }else {
                    String id = ref.push().getKey();
                    Anime animes = new Anime();
                    ref.child(id).child("Title").setValue(Title);
                    ref.child(id).child("Description").setValue(Description);
                    ref.child(id).child("Genre").setValue(Genre);
                    ref.child(id).child("Episodes").setValue(Episodes);
                    ref.child(id).child("EpisodeDuration").setValue(EpisodeDuration);
                    ref.child(id).child("Studio").setValue(Studio);
                    ref.child(id).child("Poster").setValue(Poster);
                    ref.child(id).child("Year").setValue(Year);

                    Toast.makeText(AddAnimeActivity.this, "Datos Agregados", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
