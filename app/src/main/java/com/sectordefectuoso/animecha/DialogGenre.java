package com.sectordefectuoso.animecha;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sectordefectuoso.animecha.entities.Genre;

import static com.sectordefectuoso.animecha.MainActivity.ref2;

public class DialogGenre extends AppCompatDialogFragment {
    LinearLayout llGenre;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_genres,null);
        llGenre = view.findViewById(R.id.layoutGenres);
        final AlertDialog alertGenres = new AlertDialog.Builder(getContext()).setView(view).setNegativeButton("Cerrar",null).create();
        alertGenres.setCanceledOnTouchOutside(false);
        alertGenres.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button btnClose = alertGenres.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogInterface.dismiss();
                    }
                });
            }
        });
        return alertGenres;
    }

    @Override
    public void onStart() {
        super.onStart();
        final String[] listGenres = AddAnimeActivity.txtGenre.getText().toString().equals("") ? null : AddAnimeActivity.txtGenre.getText().toString().split(",");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot genreSnapshot : dataSnapshot.getChildren()){
                    final CheckBox ch = new CheckBox(getActivity());
                    ch.setId(Integer.parseInt(genreSnapshot.getKey()));
                    ch.setText(genreSnapshot.getValue(Genre.class).getName());
                    String name = genreSnapshot.getValue(Genre.class).getName();
                    if(listGenres != null){
                        for(int i = 0; i < listGenres.length; i++){
                            if(listGenres[i].trim().equals(name)){
                                ch.setChecked(true);
                            }
                        }
                    }
                    llGenre.addView(ch);

                    ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            Genre genre = new Genre();
                            genre.setName(ch.getText().toString());
                            if (ch.isChecked()){
                                AddAnimeActivity.selectedGenre.add(genre.getName());
                            }
                            else{
                                AddAnimeActivity.selectedGenre.remove(genre.getName());
                            }
                            String strGenre = "";
                            AddAnimeActivity.txtGenre.setText("");
                            for(int i = 0; i < AddAnimeActivity.selectedGenre.size(); i++){
                                strGenre += AddAnimeActivity.selectedGenre.get(i);
                                if(i < AddAnimeActivity.selectedGenre.size() - 1){
                                    strGenre += ", ";
                                }
                            }
                            AddAnimeActivity.txtGenre.setText(strGenre);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
