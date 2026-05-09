import java.util.ArrayList;
import java.util.List;

public class MainComparateurTest {
    public static void main(String[] args) {
        // Liste source
        List<Nom> listeSource = new ArrayList<>();
        listeSource.add(new Nom("Jean Dupont", "1"));
        listeSource.add(new Nom("Marie Curie", "2"));
        listeSource.add(new Nom("Pierre Martin", "3"));

        // Liste cible
        List<Nom> listeCible = new ArrayList<>();
        listeCible.add(new Nom("jean dupont", "A"));
        listeCible.add(new Nom("Marie Curie", "B"));
        listeCible.add(new Nom("Paul Durant", "C"));

        // Comparateur
        ComparateurDeChaines comparateur = new ComparateurNGram();

        // Moteur de recherche
        MoteurDeRecherche moteur = new MoteurDeRecherche(comparateur);
        
        // Optionnel : ajouter un prétraiteur
        // moteur.ajouterPretraiteur(new TransformerEnMinuscule());

        // Comparaison
        List<Couple> resultats = moteur.rechercher(listeSource, listeCible);
        
        // Affichage
        System.out.println("=== Comparaison avec " + comparateur.getClass().getSimpleName() + " ===");
        for (Couple couple : resultats) {
            System.out.println(couple);
        }
    }
}
