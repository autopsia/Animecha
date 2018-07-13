package com.sectordefectuoso.animecha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sectordefectuoso.animecha.entities.Anime;
import com.sectordefectuoso.animecha.entities.Genre;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static ArrayList<Anime> animes;
    public static ArrayList<Genre> genreList;
    static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    static final DatabaseReference ref = database.child("Anime");
    static final DatabaseReference ref2 = database.child("Genre");
    Spinner spinnerGenre;
    int counter = 0;

    static GridView mainGrid;
    ArrayList<Anime> tmpSelectedAnime = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAnime();
            }
        });
        mainGrid = findViewById(R.id.mainGrid);
        spinnerGenre = findViewById(R.id.spinnerGenre);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(counter > 0){
                    final Genre genre = (Genre) spinnerGenre.getItemAtPosition(i);
                    final String CurrentGenre = genre.getName();
                    tmpSelectedAnime.clear();

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            tmpSelectedAnime.clear();
                            for (DataSnapshot animeSnapshot : dataSnapshot.getChildren()) {
                                if(genre.getGenreId().equals("0")){
                                    Anime anime = new Anime();
                                    anime.setId(animeSnapshot.getKey());
                                    anime.setTitle(animeSnapshot.getValue(Anime.class).getTitle());
                                    anime.setDescription(animeSnapshot.getValue(Anime.class).getDescription());
                                    anime.setGenre(animeSnapshot.getValue(Anime.class).getGenre());
                                    anime.setEpisodes(animeSnapshot.getValue(Anime.class).getEpisodes());
                                    anime.setEpisodeDuration(animeSnapshot.getValue(Anime.class).getEpisodeDuration());
                                    anime.setStudio(animeSnapshot.getValue(Anime.class).getStudio());
                                    anime.setPoster(animeSnapshot.getValue(Anime.class).getPoster());
                                    anime.setYear(animeSnapshot.getValue(Anime.class).getYear());
                                    tmpSelectedAnime.add(anime);
                                }
                                else{
                                    String tmpGenre = animeSnapshot.getValue(Anime.class).getGenre() == null ? "" : animeSnapshot.getValue(Anime.class).getGenre();
                                    String listGenres[] = tmpGenre != null ? tmpGenre.split(",") : null;
                                    tmpSelectedAnime.clear();
                                    for(int i = 0; i < listGenres.length; i++){
                                        if(listGenres != null){
                                            if(CurrentGenre.equals(listGenres[i].trim())){
                                                Anime anime = new Anime();
                                                anime.setId(animeSnapshot.getKey());
                                                anime.setTitle(animeSnapshot.getValue(Anime.class).getTitle());
                                                anime.setDescription(animeSnapshot.getValue(Anime.class).getDescription());
                                                anime.setGenre(animeSnapshot.getValue(Anime.class).getGenre());
                                                anime.setEpisodes(animeSnapshot.getValue(Anime.class).getEpisodes());
                                                anime.setEpisodeDuration(animeSnapshot.getValue(Anime.class).getEpisodeDuration());
                                                anime.setStudio(animeSnapshot.getValue(Anime.class).getStudio());
                                                anime.setPoster(animeSnapshot.getValue(Anime.class).getPoster());
                                                anime.setYear(animeSnapshot.getValue(Anime.class).getYear());
                                                tmpSelectedAnime.add(anime);
                                            }
                                        }
                                    }
                                }
                            }
                            AnimeList animeAdapter = new AnimeList(MainActivity.this, tmpSelectedAnime);
                            mainGrid.setAdapter(animeAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                counter++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        genreList = new ArrayList<>();
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                genreList.clear();
                Genre g = new Genre();
                g.setGenreId("0");
                g.setName("Todos los generos");
                genreList.add(g);
                for (DataSnapshot genreSnapshot : dataSnapshot.getChildren()){
                    Genre genre = new Genre();
                    genre.setGenreId(genreSnapshot.getKey());
                    genre.setName(genreSnapshot.getValue(Genre.class).getName());
                    genre.setDescription(genreSnapshot.getValue(Genre.class).getDescription());
                    genreList.add(genre);
                }
                ArrayAdapter<Genre> genreAdapter = new ArrayAdapter<>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,genreList);
                spinnerGenre.setAdapter(genreAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        animes = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                animes.clear();
                for (DataSnapshot animeSnapshot : dataSnapshot.getChildren()) {
                    Anime anime = new Anime();
                    anime.setId(animeSnapshot.getKey());
                    anime.setTitle(animeSnapshot.getValue(Anime.class).getTitle());
                    anime.setDescription(animeSnapshot.getValue(Anime.class).getDescription());
                    anime.setGenre(animeSnapshot.getValue(Anime.class).getGenre());
                    anime.setEpisodes(animeSnapshot.getValue(Anime.class).getEpisodes());
                    anime.setEpisodeDuration(animeSnapshot.getValue(Anime.class).getEpisodeDuration());
                    anime.setStudio(animeSnapshot.getValue(Anime.class).getStudio());
                    anime.setPoster(animeSnapshot.getValue(Anime.class).getPoster());
                    anime.setYear(animeSnapshot.getValue(Anime.class).getYear());
                    animes.add(anime);
                }
                ArrayList<Anime> temp = animes;
                AnimeList animeAdapter = new AnimeList(MainActivity.this, animes);
                mainGrid.setAdapter(animeAdapter);
                //da funcionalidad para al darle clic cambiar de actividad
                mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String keyref = animes.get(i).getId();
                        database.child("Anime").child(keyref).getKey();
                        Intent j = new Intent(MainActivity.this, AnimeActivity.class);
                        j.putExtra("keyref", keyref);
                        startActivity(j);
                        //Toast.makeText(MainActivity.this, i+""+keyref, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void AddAnime(){
        Intent i = new Intent(MainActivity.this, AddAnimeActivity.class);
        startActivity(i);
    }


}
