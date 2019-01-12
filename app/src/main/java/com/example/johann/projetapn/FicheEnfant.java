package com.example.johann.projetapn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FicheEnfant extends AppCompatActivity {

    TextView Prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_enfant);

        Prenom = (TextView)findViewById(R.id.Prenom);
        Intent Donnee = getIntent();
        Accueil prenom = (Accueil)Donnee.getSerializableExtra("prenom");
        Prenom.setText((CharSequence) prenom);
    }
}
