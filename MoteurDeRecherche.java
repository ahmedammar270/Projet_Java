import java.util.List;
import java.util.Map;

import GenerateurDeCandidats.GenerateurParLongueur.GenerateurParLongueurSansIndex;
import GenerateurDeCandidats.GenerateurParLongueur.GenerateurParLongueurAvecIndex;
import GenerateurDeCandidats.GenerateurParSyllabes.GenerateurParSyllabesAvecIndex;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;

import comparateurs.ComparateurDeMots;
import comparateurs.ComparateurJaroWinkler;
import comparateurs.ComparateurLevenshtein;
import comparateurs.ComparateurNGram;
import configuration.Configuration;
import nom.Couple;
import nom.Nom;
import pretraiteurs.DecomposeurNgrams;
import pretraiteurs.DecomposeurSylabe;
import pretraiteurs.Pretraiteur;
import pretraiteurs.SupprimerAccents;
import pretraiteurs.SupprimerEspaces;
import pretraiteurs.SupprimerPonctuation;
import pretraiteurs.SupprimerPrefixes;
import pretraiteurs.TransformerEnMinuscule;
import pretraiteurs.Trimer;
import pretraiteurs.decouperNom;
import GenerateurDeCandidats.GenerateurDeCandidats;
import selecteurs.SelecteurMatching;
import selecteurs.SelecteurNPremiers;
import selecteurs.SelecteurParSeuil;

public class MoteurDeRecherche {

    private List<Pretraiteur> listePretraiteurs;
    private GenerateurDeCandidats generateur;
    private SelecteurMatching selecteur;
    private Configuration configuration;
    private LivrerResultat livreur;

    private void appliquerPretraitement(Nom nom) {

        if (nom == null) {
            return;
        }
        new SupprimerAccents().pretraiter(nom);
        new SupprimerEspaces().pretraiter(nom);
        new SupprimerPonctuation().pretraiter(nom);
        new SupprimerPrefixes().pretraiter(nom);
        new TransformerEnMinuscule().pretraiter(nom);
        new Trimer().pretraiter(nom);
    }

    private void appliquerDecomposition(Nom nom) {
        if (nom == null) {
            return;
        }
        new decouperNom().pretraiter(nom);
    }

    private HashMap<Nom, List<Nom>> appliquerGenerateurDeCondidats(List<Nom> liste1, List<Nom> liste2) {
        if (liste1 == null || liste2 == null) {
            return new HashMap<>();
        }
        return (new GenerateurParLongueurAvecIndex()).genererCandidats(liste1, liste2);
    }

    private double appliquerComparateurDeNoms(Nom nom1, Nom nom2) {
        return new ComparateurJaroWinkler().comparer(nom1, nom2);
    }

    private List<Couple> appliquerSelecteurMatching(List<Couple> couples) {
        return new SelecteurNPremiers(new Configuration()).selectionner(couples);
    }

    private void appliquerLivraison(List<Couple> couples) {
        new LivrerResultat().livrerResultat(couples);
    }

    public List<Couple> rechercher(List<Nom> liste1, List<Nom> liste2) {
        List<Couple> couples = new ArrayList<>();
        for (Nom nom1 : liste1) {
            appliquerPretraitement(nom1);
        }
        for (Nom nom2 : liste2) {
            appliquerPretraitement(nom2);
        }
        HashMap<Nom, List<Nom>> candidats = appliquerGenerateurDeCondidats(liste1, liste2);

        for (Nom nom2 : liste2) {
            appliquerDecomposition(nom2);
        }

        for (Map.Entry<Nom, List<Nom>> entry : candidats.entrySet()) {
            Nom nom1 = entry.getKey();
            appliquerDecomposition(nom1);
            List<Nom> candidatsPourNom1 = entry.getValue();
            for (Nom candidat : candidatsPourNom1) {
                double score = appliquerComparateurDeNoms(nom1, candidat);
                couples.add(new Couple(nom1, candidat, score));
            }
        }

        couples.sort(Comparator.comparingDouble(Couple::getScore).reversed());
        List<Couple> resultat = appliquerSelecteurMatching(couples);
        appliquerLivraison(resultat);
        return resultat;

    }
}