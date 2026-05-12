import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import nom.Nom;
import nom.Couple;
import pretraiteurs.TransformerEnMinuscule;
import pretraiteurs.SupprimerAccents;
import comparateurs.ComparateurNGram;
import comparateurs.ComparateurDeChaines;

public class Main {
    public static void main(String[] args) {
        String csvPath = args.length > 0 ? args[0] : "peps_names_100.csv";

        List<Nom> liste1;
        try {
            liste1 = lireNomsDepuisCsv(csvPath);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            return;
        }
        

        List<Nom> liste2 = new ArrayList<>();
        liste2.add(new Nom("Jean", "3"));
        liste2.add(new Nom("Pierre", "4"));

        ComparateurDeChaines comparateur = new ComparateurNGram();
        MoteurDeRecherche moteur = new MoteurDeRecherche(comparateur);
        
        moteur.ajouterPretraiteur(new TransformerEnMinuscule());
        moteur.ajouterPretraiteur(new SupprimerAccents());

        List<Couple> resultats = moteur.rechercher(liste1, liste2);
        
        System.out.println("=== Résultats ===");
        for (Couple couple : resultats) {
            System.out.println(couple);
        }
    }

    private static List<Nom> lireNomsDepuisCsv(String csvPath) throws IOException {
        Path path = Paths.get(csvPath);
        List<Nom> noms = new ArrayList<>();

        List<String> lines = Files.readAllLines(path);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || i == 0 && line.toLowerCase().startsWith("id,")) {
                continue;
            }

            String[] parts = line.split(",", 2);
            if (parts.length < 2) {
                continue;
            }

            String id = parts[0].trim();
            String name = parts[1].trim();
            noms.add(new Nom(name, id));
        }

        System.out.println("Chargement de " + noms.size() + " noms depuis " + csvPath);
        return noms;
    }
}
