import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;

import comparateurs.ComparateurDeMots;
import comparateurs.ComparateurLevenshtein;
import comparateurs.ComparateurDeChaines;
import configuration.Configuration;
import nom.Couple;
import nom.Nom;
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
import GenerateurDeCandidats.GenerateurDeCandidats;
import GenerateurDeCandidats.GenerateurParLongueur.GenerateurParLongueurSansIndex;

public class MoteurDeRecherche {
    
    private List<Pretraiteur> listePretraiteurs;
    private GenerateurDeCandidats generateur;
    private SelecteurMatching selecteur;
    private Configuration configuration;
    private LivrerResultat livreur;
    private ComparateurDeChaines comparateur;

    public MoteurDeRecherche(ComparateurDeChaines comparateur) {
        this.comparateur = comparateur;
        this.listePretraiteurs = new ArrayList<>();
        this.generateur = new GenerateurParLongueurSansIndex(); // Valeur par défaut
        this.configuration = new Configuration();
        this.selecteur = new SelecteurNPremiers(this.configuration);
        this.livreur = new LivrerResultat();
    }

    public void ajouterPretraiteur(Pretraiteur p) {
        this.listePretraiteurs.add(p);
    }

    private void appliquerPretraitement(Nom nom) {
        if (nom == null) return;
        for (Pretraiteur p : listePretraiteurs) {
            p.pretraiter(nom);
        }
    }

    private void appliquerDecomposition(Nom nom) {
        if (nom == null) return;
        new decouperNom().pretraiter(nom);
    }

    private HashMap<Nom, List<Nom>> appliquerGenerateurDeCondidats(List<Nom> liste1 , List<Nom> liste2 ){
        if (liste1 == null || liste2 == null) return new HashMap<>();
        return generateur.genererCandidats(liste1, liste2);
    }

    private double appliquerComparateurDeNoms(Nom nom1, Nom nom2) {
        return comparateur.comparer(nom1, nom2);
    }

    private List<Couple> appliquerSelecteurMatching(List<Couple> couples) {
        return selecteur.selectionner(couples);
    }

    private void appliquerLivraison(List<Couple> couples) {
        livreur.livrerResultat(couples);
    }

    public List<Couple> rechercher(List<Nom> liste1 , List<Nom> liste2)  {
        // 1. Prétraitement des deux listes
        for (Nom nom1 : liste1) appliquerPretraitement(nom1);
        for (Nom nom2 : liste2) appliquerPretraitement(nom2);

        // 2. Génération des candidats
        HashMap<Nom, List<Nom>> candidats = appliquerGenerateurDeCondidats(liste1, liste2);

        // 3. Décomposition et Comparaison
        List<Couple> couples = new ArrayList<>();
        for (Nom nom1 : liste1) appliquerDecomposition(nom1);

        for (Map.Entry<Nom, List<Nom>> entry : candidats.entrySet()) {
            Nom nom1 = entry.getKey();
            List<Nom> candidatsPourNom1 = entry.getValue();
            for (Nom candidat : candidatsPourNom1) {
                appliquerDecomposition(candidat);
                double score = appliquerComparateurDeNoms(nom1, candidat);
                couples.add(new Couple(nom1, candidat, score));
            }
        }

        // 4. Tri et Sélection
        couples.sort(Comparator.comparingDouble(Couple::getScore).reversed());
        List<Couple> resultatsSelectionnes = appliquerSelecteurMatching(couples);

        // 5. Livraison
        appliquerLivraison(resultatsSelectionnes);

        return resultatsSelectionnes;
    }

    // Setters pour la flexibilité
    public void setGenerateur(GenerateurDeCandidats generateur) { this.generateur = generateur; }
    public void setSelecteur(SelecteurMatching selecteur) { this.selecteur = selecteur; }
    public void setLivreur(LivrerResultat livreur) { this.livreur = livreur; }
    public void setConfiguration(Configuration configuration) { this.configuration = configuration; }
}
