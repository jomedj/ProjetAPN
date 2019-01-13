
package com.example.johann.projetapn;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ListeEnfant> {
    private ArrayList<ListeEnfant> dataEnfant;
    Context mContext;

    private static class ViewHolder{
        TextView textPrenom;
        TextView textSexe;
        TextView textAnnee;
        CheckBox cSage;
        CheckBox cCadeau;
        CheckBox cLettre;
    }

    public CustomAdapter(ArrayList<ListeEnfant> data, Context context){
        super(context, R.layout.customlistview, data);
        this.dataEnfant = data;
        this.mContext = context;
    }


    public void onClick(View view){

    }
}

