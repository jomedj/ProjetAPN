package com.example.johann.projetapn;

import java.io.Serializable;

public class ListeEnfant implements Serializable {
    private String sexe;
    private String prenom;
    private String annee_naissance;

    private boolean sage;
    private boolean cadeau;
    private boolean lettre;


    public ListeEnfant(String sexe, String prenom, String annee_naissance ) {
        this.sexe = sexe;
        this.prenom = prenom;
        this.annee_naissance = annee_naissance;
        this.cadeau = false;
        this.lettre = false;
        this.sage = false;
    }

    public String id(){
        return this.prenom.toUpperCase()+this.annee_naissance.toUpperCase();
    }
    public void setSage(boolean sage) {
        this.sage = sage;
    }

    public void setCadeau(boolean cadeau) {
        this.cadeau = cadeau;
    }

    public void setLettre(boolean lettre) {
        this.lettre = lettre;
    }

    public String getSexe() {
        return sexe;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAnnee_naissance() {
        return annee_naissance;
    }

    public boolean isSage() {
        return sage;
    }

    public boolean isCadeau() {
        return cadeau;
    }

    public boolean isLettre() {
        return lettre;
    }

    @Override
    public String toString() {
        return  prenom+"   Née en "+annee_naissance;
    }
}
