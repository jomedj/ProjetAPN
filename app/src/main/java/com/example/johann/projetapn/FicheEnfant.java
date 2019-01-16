package com.example.johann.projetapn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class FicheEnfant extends AppCompatActivity {

    TextView Prenom;
    ListeEnfant enfant;
    CheckBox Sage;
    CheckBox Lettre;
    CheckBox Cadeau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_enfant);

        Sage = (CheckBox)findViewById(R.id.Sage);
        Lettre = (CheckBox)findViewById(R.id.Lettre);
        Cadeau = (CheckBox)findViewById(R.id.Cadeaux);


        Prenom = (TextView)findViewById(R.id.Prenom);
        Intent Donnee = getIntent();
        enfant = (ListeEnfant) getIntent().getSerializableExtra("enfant");

        String prenom = enfant.getPrenom();
        Prenom.setText(prenom);

    }
}
