package com.example.johann.projetapn;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Accueil extends AppCompatActivity {
    SharedPreferences preferences;
    ListView listeEnfant;
    Button boutonChercher;
    EditText champPrenom;
    Spinner listeAnnee;
    CheckBox CheckGarcon;
    CheckBox CheckFille;
    String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";
    ArrayAdapter adapterListeEnfant;
    ArrayAdapter adapterListeAnnee;
    ArrayList<ListeEnfant> listeEnfantNoel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        preferences = getApplicationContext().getSharedPreferences("pref",0);
        preferences.edit();

        //Récuperation des différents élements
        listeEnfant = (ListView) findViewById(R.id.listeEnfant);
        boutonChercher = (Button)findViewById(R.id.boutonChercher);
        champPrenom = (EditText)findViewById(R.id.champPrenom);
        listeAnnee = (Spinner)findViewById(R.id.listeAnnee);
        CheckGarcon = (CheckBox)findViewById(R.id.Garcon);
        CheckFille = (CheckBox)findViewById(R.id.Fille);


        //Adapter de notre liste d'enfant

        adapterListeEnfant = new ArrayAdapter<ListeEnfant>(Accueil.this,android.R.layout.simple_list_item_1,listeEnfantNoel);
        listeEnfant.setAdapter(adapterListeEnfant);

        //Adapter du spinner pour l'annee
        final ArrayList<String> listeAnne = new ArrayList<>();
        for(int i = 2001;i<=2020;i++){
            listeAnne.add(Integer.toString(i));
        }
        adapterListeAnnee = new ArrayAdapter<>(Accueil.this,android.R.layout.simple_spinner_item,listeAnne);
        listeAnnee.setAdapter(adapterListeAnnee);
        majListeEnfant(url);

        //Bouton rechercher
        boutonChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prenom = null;
                String annee_naissance = null;
                try{
                    prenom = champPrenom.getText().toString();
                    annee_naissance = listeAnnee.getSelectedItem().toString();
                }catch(Exception e){}
                    boolean estGarcon = CheckGarcon.isChecked();
                    boolean estFille = CheckFille.isChecked();
                    String url = Filtre(prenom,annee_naissance,estGarcon,estFille);
                    majListeEnfant(url);
            }
        });


        //Passage dans une autre activité
        listeEnfant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent FicheEnfantActivity = new Intent(Accueil.this,FicheEnfant.class );
                ListeEnfant enfant = (ListeEnfant)parent.getAdapter().getItem(position);
                FicheEnfantActivity.putExtra("enfant",enfant);
                startActivity(FicheEnfantActivity);
                finish();
            }
        });
    }

    protected String Filtre(String prenom, String anneeEnfant, boolean garcon, boolean fille){
        String Url = url;
        if(prenom != null && !prenom.equals("")){
            Url += "&refine.prenom="+prenom.toUpperCase();
        }
        if(anneeEnfant != null){
            Url += "&refine.annee_naissance="+anneeEnfant.toUpperCase();
        }
        if(garcon && !fille){
            Url+= "&refine.sexe=GARCON";
        }
        if(fille && !garcon){
            Url += "&refine.sexe=FILLE";
        }
        return Url;
    }


    //MAJ de la liste d'enfant
    protected void majListeEnfant(String url){

        Ion.with(this).load(url).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                try{
                    JSONObject jsonObject = new JSONObject(String.valueOf(result));
                    JSONArray jsonArray = jsonObject.getJSONArray("records");
                    listeEnfantNoel.clear();
                    for(int i =0; i<jsonArray.length();i++){
                        Object object = jsonArray.getJSONObject(i).getJSONObject("fields");
                        String prenom = String.valueOf(((JSONObject)object).get("prenom"));
                        String annee = String.valueOf(((JSONObject)object).get("annee_naissance"));
                        String sexe = String.valueOf(((JSONObject)object).get("sexe"));
                        ListeEnfant enfant = new ListeEnfant(sexe,prenom,annee);
                        listeEnfantNoel.add(enfant);
                    }
                    listeEnfantNoel = fusion(listeEnfantNoel);
                    adapterListeEnfant = new ArrayAdapter<ListeEnfant>(Accueil.this,android.R.layout.simple_list_item_1,listeEnfantNoel);
                    listeEnfant.setAdapter(adapterListeEnfant);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public ArrayList<ListeEnfant> fusion(ArrayList<ListeEnfant> lEnfant){
        ArrayList<ListeEnfant> newLEnfant = lEnfant;
        Gson gson = new Gson();
        for (int i = 0; i<newLEnfant.size();i++){
            ListeEnfant enfant = newLEnfant.get(i);
            if(!preferences.getString(enfant.id(),"default").equals("default")){
                String json = preferences.getString(enfant.id(),"default");
                ListeEnfant kid = gson.fromJson(json,ListeEnfant.class);
                if(enfant.id().equals(kid.id())){
                    newLEnfant.set(i,kid);
                }
            }
        }
        return newLEnfant;
    }

}
