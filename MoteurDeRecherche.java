import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;

import comparateurs.ComparateurDeMots;
import comparateurs.ComparateurLevenshtein;
import configuration.Configuration;
import nom.Couple;
import nom.Nom;
import pretraiteurs.DecomposeurSylabe;
import pretraiteurs.Pretraiteur;
import pretraiteurs.SupprimerAccents;
import pretraiteurs.SupprimerEspaces;
import pretraiteurs.SupprimerPonctuation;
import pretraiteurs.SupprimerPrefixes;
import pretraiteurs.TransformerEnMinuscule;
import pretraiteurs.Trimer;
import pretraiteurs.decouperNom;
import selecteurs.SelecteurMatching;
import selecteurs.SelecteurNPremiers;

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
        return (new GenerateurParCaracteresSansIndex()).genererCandidat(liste1, liste2);
    }

    private double appliquerComparateurDeNoms(Nom nom1, Nom nom2) {
        return new ComparateurLevenshtein().comparer(nom1, nom2);
    }

    private List<Couple> appliquerSelecteurMatching(List<Couple> couples, int n) {
        return new SelecteurNPremiers(n).selectionner(couples);
    }

    private void appliquerLivraison(List<Couple> couples) {
        new LivrerResultat().livrerResultat(couples);
    }

<<<<<<< HEAD
    public List<Couple> rechercher(List<Nom> liste1, List<Nom> liste2) {
=======
    public void  rechercher(List<Nom> liste1 , List<Nom> liste2)  {
>>>>>>> a7054270f545b5014e7f5df97e415b2e1d1e2125
        List<Couple> couples = new ArrayList<>();
        for (Nom nom1 : liste1) {
            appliquerPretraitement(nom1);
        }
        for (Nom nom2 : liste2) {
            appliquerPretraitement(nom2);
        }
        HashMap<Nom, List<Nom>> candidats = appliquerGenerateurDeCondidats(liste1, liste2);

        for (Nom nom1 : liste1) {
            appliquerDecomposition(nom1);
        }

        for (Map.Entry<Nom, List<Nom>> entry : candidats.entrySet()) {
            Nom nom1 = entry.getKey();
            List<Nom> candidatsPourNom1 = entry.getValue();
            for (Nom candidat : candidatsPourNom1) {
                appliquerDecomposition(candidat);
                double score = appliquerComparateurDeNoms(nom1, candidat);
                couples.add(new Couple(nom1, candidat, score));
            }
        }

        couples.sort(Comparator.comparingDouble(Couple::getScore).reversed());
        List<Couple> resultat = appliquerSelecteurMatching(couples, new Configuration().getNPremiers());
        appliquerLivraison(resultat);

    }
}
