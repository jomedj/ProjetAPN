package com.example.johann.projetapn;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

public class FicheEnfant extends AppCompatActivity {
    SharedPreferences preferences;
    TextView Prenom;
    ListeEnfant enfant;
    CheckBox Sage;
    CheckBox Lettre;
    CheckBox Cadeau;
    Button boutonChercher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_enfant);

        preferences = getApplicationContext().getSharedPreferences("pref",0);

        Sage = (CheckBox)findViewById(R.id.Sage);
        Lettre = (CheckBox)findViewById(R.id.Lettre);
        Cadeau = (CheckBox)findViewById(R.id.Cadeaux);
        boutonChercher = (Button)findViewById(R.id.Valide);


        Prenom = (TextView)findViewById(R.id.Prenom);
        final Intent Donnee = getIntent();
        enfant = (ListeEnfant) getIntent().getSerializableExtra("enfant");

        //Si enfant sage alors on change l'etat des checkbox a true
        if(enfant.isSage()){
            Sage.setChecked(true);
        }
        if(enfant.isCadeau()){
            Cadeau.setChecked(true);
        }
        if(enfant.isLettre()){
            Lettre.setChecked(true);
        }

        Cadeau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enfant.setCadeau(!enfant.isCadeau());
            }
        });

        Sage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enfant.setSage(!enfant.isSage());
            }
        });

        Lettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enfant.setLettre(!enfant.isLettre());
            }
        });


        String prenom = enfant.getPrenom();
        Prenom.setText(prenom);


        //Bouton validation
        boutonChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrer(enfant);
            }
        });

    }

    //Enregistrement de l'enfant dans les sharedPreferences
    public void enregistrer(ListeEnfant enfant){
        SharedPreferences.Editor edit = preferences.edit();
        if(enfant.isLettre() || enfant.isCadeau() ||enfant.isSage()){
            Gson gson = new Gson();
            String json = gson.toJson(enfant);
            edit.putString(enfant.id(),json);
            edit.commit();
        }else{
            edit.remove(enfant.id());
            edit.apply();
        }
        Intent AccueilActivity = new Intent(FicheEnfant.this,Accueil.class );
        startActivity(AccueilActivity);

    }
}
