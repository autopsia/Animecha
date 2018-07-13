package com.sectordefectuoso.animecha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sectordefectuoso.animecha.entities.Anime;

import java.util.ArrayList;

import static com.sectordefectuoso.animecha.MainActivity.database;

public class AnimeList extends ArrayAdapter{

    private Context context;
    private ArrayList<Anime> animes;


    public AnimeList(Context context, ArrayList<Anime> animes) {
        super(context, 0,animes);
        this.context = context;
        this.animes = animes;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.anime_adapter_element, null);
        final TextView txtTitleList;
        Button btnEdit,btnDelete;
        ImageView imgPoster;
        txtTitleList = convertView.findViewById(R.id.txtTitleList);
        btnEdit = convertView.findViewById(R.id.btnEditAnime);
        btnDelete = convertView.findViewById(R.id.btnDeleteAnime);
        imgPoster = convertView.findViewById(R.id.imgPoster);

        Anime anime = animes.get(position);
        txtTitleList.setText(anime.getTitle());

        Glide.with(context).load(anime.getPoster()).into(imgPoster);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyref = animes.get(position).getId();
                database.child("Anime").child(keyref).getKey();
                Intent i = new Intent(context, AddAnimeActivity.class);
                i.putExtra("keyref", keyref);
                context.startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cuadro de confirmacion para borrar
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Borrar Anime")
                            .setMessage("¿Estas seguro de BORRAR este anime?\nEsta operacion es permanente.")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String keyref = animes.get(position).getId();
                                    database.child("Anime").child(keyref).removeValue();
                                    Toast.makeText(context, "Anime "+ animes.get(position).getTitle()+" Eliminado", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }



        });
        return convertView;
    }
}
