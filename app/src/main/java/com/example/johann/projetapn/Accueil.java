package com.example.johann.projetapn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Accueil extends AppCompatActivity {

    ListView listeEnfant;
    Button boutonChercher;
    EditText champPrenom;
    Spinner listeAnnee;
    Spinner listeSexe;
    String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";
    ArrayAdapter adapterListeEnfant;
    ArrayAdapter adapterListeAnnee;
    ArrayAdapter adapterListeSexe;
    String prenoms = "BAMBI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //Récuperation des différents élements
        listeEnfant = (ListView) findViewById(R.id.listeEnfant);
        boutonChercher = (Button)findViewById(R.id.boutonChercher);
        champPrenom = (EditText)findViewById(R.id.champPrenom);
        listeAnnee = (Spinner)findViewById(R.id.listeAnnee);
        listeSexe = (Spinner)findViewById(R.id.listeSexe);

        //Adapter de notre liste d'enfant
        final ArrayList<ListeEnfant> listeEnfantNoel = new ArrayList<>();
        adapterListeEnfant = new ArrayAdapter<ListeEnfant>(Accueil.this,android.R.layout.simple_list_item_1,listeEnfantNoel);
        listeEnfant.setAdapter(adapterListeEnfant);

        //Adapter du spinner pour l'annee
        final ArrayList<String> listeAnne = new ArrayList<>();
        for(int i = 2001;i<=2030;i++){
            listeAnne.add(Integer.toString(i));
        }
        adapterListeAnnee = new ArrayAdapter<>(Accueil.this,android.R.layout.simple_spinner_item,listeAnne);
        listeAnnee.setAdapter(adapterListeAnnee);

        //Adapter du spinner pour le sexe
        final ArrayList<String> ListeSexe = new ArrayList<>();
        ListeSexe.add("GARCON");
        ListeSexe.add("FILLE");
        adapterListeSexe = new ArrayAdapter<>(Accueil.this,android.R.layout.simple_spinner_item,ListeSexe);
        listeSexe.setAdapter(adapterListeSexe);
        majListeEnfant(url);

        //Passage dans une autre activité
        listeEnfant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent FicheEnfantActivity = new Intent(Accueil.this,FicheEnfant.class );
                FicheEnfantActivity.putExtra("prenom",prenoms);
                startActivity(FicheEnfantActivity);
                finish();
            }
        });


    }
    //MAJ de la liste d'enfant
    protected void majListeEnfant(String url){
        Ion.with(this).load(url).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                try{
                    JSONObject jsonObject = new JSONObject(String.valueOf(result));
                    JSONArray jsonArray = jsonObject.getJSONArray("records");
                    adapterListeEnfant.clear();
                    for(int i =0; i<jsonArray.length();i++){
                        Object object = jsonArray.getJSONObject(i).getJSONObject("fields");
                        String prenom = String.valueOf(((JSONObject)object).get("prenom"));
                        String annee = String.valueOf(((JSONObject)object).get("annee_naissance"));
                        String sexe = String.valueOf(((JSONObject)object).get("sexe"));
                        ListeEnfant enfant = new ListeEnfant(sexe,prenom,annee);
                        adapterListeEnfant.add(enfant);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
