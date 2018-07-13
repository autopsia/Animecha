package com.sectordefectuoso.animecha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sectordefectuoso.animecha.entities.Anime;

public class AnimeActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("Anime");
    TextView txtATitle, txtADescription, txtAGenre, txtAEpisodes, txtAYear, txtAStudio;
    ImageView imgAPoster;
    String keyref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        txtATitle = findViewById(R.id.txtATitle);
        txtADescription = findViewById(R.id.txtADescription);
        txtAGenre = findViewById(R.id.txtAGenre);
        txtAEpisodes = findViewById(R.id.txtAEpisodes);
        txtAYear = findViewById(R.id.txtAYear);
        txtAStudio = findViewById(R.id.txtAStudio);
        imgAPoster = findViewById(R.id.imgAPoster);

        //evitar error nullpointerexemption checkeando si los extras son nulos
        if(getIntent() != null && getIntent().getExtras() != null)
            keyref = getIntent().getExtras().getString("keyref");
        if (keyref != null) {

            ValueEventListener valueEventListener = ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //loop compara y devuelve valores dependiendo de key padre
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Anime anime = dataSnapshot1.getValue(Anime.class);
                        if (dataSnapshot1.getKey().equals(keyref)) {
                            txtATitle.setText(anime.getTitle());
                            txtADescription.setText(anime.getDescription());
                            txtAEpisodes.setText(anime.getEpisodes()+" episodios, "+anime.getEpisodeDuration()+" min c/u");
                            txtAGenre.setText(anime.getGenre());
                            txtAStudio.setText(anime.getStudio());
                            txtAYear.setText(anime.getYear());
                            if (anime != null){
                                Glide.with(getApplicationContext()).load(anime.getPoster()).into(imgAPoster);
                                //txtPoster.setText(anime.getPoster());
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {

        }


    }
}
