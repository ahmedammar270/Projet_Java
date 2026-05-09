import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Nom> liste1 = new ArrayList<>();
        liste1.add(new Nom("Jean", "1"));
        liste1.add(new Nom("Marie", "2"));
        liste1.add(new Nom("naej", "5"));

        List<Nom> liste2 = new ArrayList<>();
        liste2.add(new Nom("Jean", "3"));
        liste2.add(new Nom("Pierre", "4"));

        ComparateurDeChaines comparateur = new ComparateurNGram(1);
        MoteurDeRecherche moteur = new MoteurDeRecherche(comparateur);
        
        moteur.ajouterPretraiteur(new TransformerEnMinuscule());
        moteur.ajouterPretraiteur(new SupprimerAccents());

        List<Couple> resultats = moteur.rechercher(liste1, liste2);
        
        System.out.println("=== Résultats ===" );
        for (Couple couple : resultats) {
            System.out.println(couple);
        }
    }
}
