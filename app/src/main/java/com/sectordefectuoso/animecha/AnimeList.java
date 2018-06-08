package com.sectordefectuoso.animecha;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.sectordefectuoso.animecha.Anime;
import com.sectordefectuoso.animecha.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.anime_adapter_element, null);
        final TextView txtTitleList;
        Button btnEdit,btnDelete;

        txtTitleList = convertView.findViewById(R.id.txtTitleList);
        btnEdit = convertView.findViewById(R.id.btnEditAnime);
        btnDelete = convertView.findViewById(R.id.btnDeleteAnime);

        Anime anime = animes.get(position);
        txtTitleList.setText(anime.getTitle()+"");


        return convertView;
    }
}
