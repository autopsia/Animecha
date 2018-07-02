package com.sectordefectuoso.animecha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.Year;
import java.util.ArrayList;

import static com.sectordefectuoso.animecha.MainActivity.database;

public class AddAnimeActivity extends AppCompatActivity {
    EditText txtTitle, txtDescription, txtGenre, txtEpisodes, txtEpisodeDuration, txtStudio, txtPoster, txtYear;
    Button btnAddAnime,btnAddImg;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Anime");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://animecha-f6b0c.appspot.com/posters");
    String keyref;
    ImageView imgView;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anime);
        //evitar error nullpointerexemption checkeando si los extras son nulos
        if (getIntent() != null && getIntent().getExtras() != null)
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
        btnAddImg = findViewById(R.id.btnAddImg);

        imgView = findViewById(R.id.imgView);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });



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
        if (keyref != null) {
            btnAddAnime.setText("Actualizar Anime");
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
                }else if (TextUtils.isEmpty(Year)){
                    Toast.makeText(AddAnimeActivity.this, "Ingresa el Ano de Estreno", Toast.LENGTH_SHORT).show();
                }else {
                    final String Id;
                    if (keyref != null) {
                        Id = keyref;
                    }else {
                        Id = ref.push().getKey();
                    }
                    ref.child(Id).child("Title").setValue(Title);
                    ref.child(Id).child("Description").setValue(Description);
                    ref.child(Id).child("Genre").setValue(Genre);
                    ref.child(Id).child("Episodes").setValue(Episodes);
                    ref.child(Id).child("EpisodeDuration").setValue(EpisodeDuration);
                    ref.child(Id).child("Studio").setValue(Studio);
                    ref.child(Id).child("Year").setValue(Year);
                    if (keyref != null) {
                        btnAddAnime.setText("Actualizar Anime");
                        Toast.makeText(AddAnimeActivity.this, Title+" Datos Actualizados", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddAnimeActivity.this, Title+" Datos Agregados", Toast.LENGTH_SHORT).show();
                    }

                    //sube imagenes usando el nombre de id
                        if (filePath != null) {
                            pd.show();

                            StorageReference childRef = storageRef.child(Id+".jpg");

                            //uploading the image
                            final UploadTask uploadTask = childRef.putFile(filePath);

                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pd.dismiss();
                                    Uri posterUri = taskSnapshot.getDownloadUrl();
                                    String posterURL = posterUri.toString();
                                    ref.child(Id).child("Poster").setValue(posterURL);
                                    Toast.makeText(AddAnimeActivity.this, "Poster subido", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(AddAnimeActivity.this, "Fallo la subida -> " + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                        ref.child(Id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.hasChild("Poster")){
                                    ref.child(Id).child("Poster").setValue("https://firebasestorage.googleapis.com/v0/b/animecha-f6b0c.appspot.com/o/posters%2Fnoposter.jpg?alt=media&token=4c178968-bb1b-4a8d-8113-12fb43a1c57b");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                            Toast.makeText(AddAnimeActivity.this, "Ningun poster agregado", Toast.LENGTH_SHORT).show();
                        }

                    onBackPressed();
                }
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
