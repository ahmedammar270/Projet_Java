import java.util.List;
import java.util.ArrayList;

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

    public List<Couple> rechercher(List<Nom> liste1, List<Nom> liste2) {
        if (liste1 == null || liste2 == null) {
            System.out.println("Erreur: les listes ne peuvent pas être null");
            return new ArrayList<>();
        }

        if (generateur == null) {
            System.out.println("Erreur: aucun comparateur configuré");
            return new ArrayList<>();
        }

        SelecteurMatching selecteurEffectif = this.selecteur;
        if (selecteurEffectif == null) {
            System.out.println("Aucun sélecteur défini, utilisation du sélecteur simple par défaut.");
            selecteurEffectif = new SelecteurSimple();
        }

        System.out.println("Démarrage du moteur de recherche...");
        System.out.println("Nombre de noms dans liste 1 : " + liste1.size());
        System.out.println("Nombre de noms dans liste 2 : " + liste2.size());

        List<Nom> liste1Traitee = appliquerPretraitement(liste1);
        List<Nom> liste2Traitee = appliquerPretraitement(liste2);

        List<Couple> couples = generateur.generer(liste1Traitee, liste2Traitee);

        for (Couple c : couples) {
            int idx1 = liste1Traitee.indexOf(c.getNom1());
            int idx2 = liste2Traitee.indexOf(c.getNom2());
            if (idx1 >= 0 && idx2 >= 0) {
                c.setNom1(liste1.get(idx1));
                c.setNom2(liste2.get(idx2));
            }
        }

        System.out.println("Nombre de couples générés : " + couples.size());

        List<Couple> resultats = selecteurEffectif.selectionner(couples);
        System.out.println("Nombre de résultats après sélection : " + resultats.size());

        return resultats;
    }

    private List<Nom> appliquerPretraitement(List<Nom> liste) {
        List<Nom> resultat = new ArrayList<>();
        for (Nom nom : liste) {
            Nom nomTraite = nom;
            for (Pretraiteur pretraiteur : listePretraiteurs) {
                pretraiteur.pretraiter(nomTraite);
            }
            resultat.add(nomTraite);
        }
        return resultat;
    }

    public void ajouterPretraiteur(Pretraiteur pretraiteur) {
        if (pretraiteur == null) return;

        boolean dejaPresent = listePretraiteurs.stream()
                .anyMatch(p -> p.getClass().equals(pretraiteur.getClass()));

        if (!dejaPresent) {
            listePretraiteurs.add(pretraiteur);
        }
    }

    public void supprimerPretraiteur(Pretraiteur pretraiteur) {
        listePretraiteurs.remove(pretraiteur);
    }

    public void clearPretraiteurs() {
        listePretraiteurs.clear();
    }

    public void rechercherEtLivrer(List<Nom> liste1, List<Nom> liste2) {
        List<Couple> resultats = rechercher(liste1, liste2);
        livreur.livrerResultat(resultats);
    }

    public List<Pretraiteur> getListePretraiteurs() { return listePretraiteurs; }
    public void setGenerateur(GenerateurDeCandidats generateur) { this.generateur = generateur; }
    public GenerateurDeCandidats getGenerateur() { return generateur; }
    public void setSelecteur(SelecteurMatching selecteur) { this.selecteur = selecteur; }
    public SelecteurMatching getSelecteur() { return selecteur; }
    public void setConfiguration(Configuration configuration) { this.configuration = configuration; }
    public Configuration getConfiguration() { return configuration; }
    public void setLivreur(LivrerResultat livreur) { this.livreur = livreur; }
    public LivrerResultat getLivreur() { return livreur; }
}

