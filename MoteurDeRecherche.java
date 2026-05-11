import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;

import comparateurs.ComparateurDeMots;
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

    public MoteurDeRecherche() {
        this.listePretraiteurs = new ArrayList<>();
        this.configuration = new Configuration();
        this.livreur = new LivrerResultat();
    }

    public MoteurDeRecherche(ComparateursDeNoms comparateur, SelecteurMatching selecteur) {
        this();
        this.generateur = new GenerateurSimple(comparateur);
        this.selecteur = selecteur;
    }

    private HashMap<Nom, List<Nom>> appliquerGenerateurDeCondidats(List<Nom> liste1 , List<Nom> liste2 ){
        if (liste1 == null || liste2 == null) {
            return new HashMap<>();
        }
        return (new GenerateurParCaracteresSansIndex()).genererCandidat(liste1, liste2);
    }

    private double appliquerComparateurDeNoms(Nom nom1, Nom nom2) {
        return new ComparateurDeMots().comparer(nom1, nom2);
    }

    private List<Couple> appliquerSelecteurMatching(List<Couple> couples, int n) {
        return new SelecteurNPremiers(n).selectionner(couples);
    }

    private void appliquerLivraison(List<Couple> couples) {
        new LivrerResultat().livrerResultat(couples);
    }

    public List<Couple> rechercher(List<Nom> liste1 , List<Nom> liste2)  {
        List<Couple> couples = new ArrayList<>();
        HashMap<Nom, List<Nom>> candidats = appliquerGenerateurDeCondidats(liste1, liste2);

        for (Nom nom1 : liste1) {
            appliquerPretraitement(nom1);
            appliquerDecomposition(nom1);
        }
        for (Map.Entry<Nom, List<Nom>> entry : candidats.entrySet()) {
            Nom nom1 = entry.getKey();
            List<Nom> candidatsPourNom1 = entry.getValue();
            for (Nom candidat : candidatsPourNom1) {
                appliquerPretraitement(candidat);
                appliquerDecomposition(candidat);
                double score = appliquerComparateurDeNoms(nom1, candidat);
                couples.add(new Couple(nom1, candidat, score));
            }
        }

        couples.sort(Comparator.comparingDouble(Couple::getScore).reversed());
        return couples;
    }
}


