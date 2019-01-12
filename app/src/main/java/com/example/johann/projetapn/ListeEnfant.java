package com.example.johann.projetapn;

import java.io.Serializable;

public class ListeEnfant implements Serializable {
    private String sexe;
    private String prenom;
    private String annee_naissance;

    public ListeEnfant(String sexe, String prenom, String annee_naissance) {
        this.sexe = sexe;
        this.prenom = prenom;
        this.annee_naissance = annee_naissance;
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

    @Override
    public String toString() {
        return  prenom;
    }
}
