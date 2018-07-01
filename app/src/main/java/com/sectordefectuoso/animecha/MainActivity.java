package com.sectordefectuoso.animecha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Anime> animes;
    static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    static DatabaseReference ref = database.child("Anime");
    static GridView mainGrid;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                temp.remove(0);
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
