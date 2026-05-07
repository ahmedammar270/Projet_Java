import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Nom> liste1 = new ArrayList<>();
        liste1.add(new Nom("Jean", "1"));
        liste1.add(new Nom("Marie", "2"));

        List<Nom> liste2 = new ArrayList<>();
        liste2.add(new Nom("Jean", "3"));
        liste2.add(new Nom("Pierre", "4"));

        ComparateursDeNoms comparateur = new ComparateurEgaliteExacte();
        SelecteurMatching selecteur = new SelecteurSimple();

        MoteurDeRecherche moteur = new MoteurDeRecherche(comparateur, selecteur);
        moteur.ajouterPretraiteur(new TransformerEnMinuscule());
        moteur.ajouterPretraiteur(new SupprimerAccents());

        moteur.rechercherEtLivrer(liste1, liste2);
    }
}
