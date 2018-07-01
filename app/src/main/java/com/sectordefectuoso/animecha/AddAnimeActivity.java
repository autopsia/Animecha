package com.sectordefectuoso.animecha;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.sectordefectuoso.animecha.MainActivity.database;

public class AddAnimeActivity extends AppCompatActivity {
    EditText txtTitle, txtDescription, txtGenre, txtEpisodes, txtEpisodeDuration, txtStudio, txtPoster, txtYear;
    Button btnAddAnime;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Anime");
    String keyref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anime);
        //evitar error nullpointerexemption checkeando si los extras son nulos
        if(getIntent() != null && getIntent().getExtras() != null)
            keyref = getIntent().getExtras().getString("keyref");
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtGenre = findViewById(R.id.txtGenre);
        txtEpisodes = findViewById(R.id.txtEpisodes);
        txtEpisodeDuration = findViewById(R.id.txtEpisodeDuration);
        txtStudio = findViewById(R.id.txtStudio);
        txtPoster = findViewById(R.id.txtPoster);
        txtYear = findViewById(R.id.txtYear);
        btnAddAnime = findViewById(R.id.btnAddAnime);


        if (keyref != null) {
            ValueEventListener valueEventListener = ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //loop compara y devuelve valores dependiendo de key padre
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Anime anime = dataSnapshot1.getValue(Anime.class);
                        if (dataSnapshot1.getKey().equals(keyref)) {
                            txtTitle.setText(anime.getTitle());
                            txtDescription.setText(anime.getDescription());
                            txtEpisodeDuration.setText(anime.getEpisodeDuration());
                            txtEpisodes.setText(anime.getEpisodes());
                            txtGenre.setText(anime.getGenre());
                            txtStudio.setText(anime.getStudio());
                            txtYear.setText(anime.getYear());
                            txtPoster.setText(anime.getPoster());

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });


        } else {

        }
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
                    String Id;
                    if (keyref != null) {
                        Id = keyref;
                    }else {
                        Id = ref.push().getKey();
                    }
                    Anime animes = new Anime();
                    ref.child(Id).child("Title").setValue(Title);
                    ref.child(Id).child("Description").setValue(Description);
                    ref.child(Id).child("Genre").setValue(Genre);
                    ref.child(Id).child("Episodes").setValue(Episodes);
                    ref.child(Id).child("EpisodeDuration").setValue(EpisodeDuration);
                    ref.child(Id).child("Studio").setValue(Studio);
                    ref.child(Id).child("Poster").setValue(Poster);
                    ref.child(Id).child("Year").setValue(Year);

                    Toast.makeText(AddAnimeActivity.this, ref+"Datos Agregados", Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }
            }
        });




    }
}
