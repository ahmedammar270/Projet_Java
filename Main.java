import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import nom.Nom;
import nom.Couple;

public class Main {
    public static void main(String[] args) {
        String dirPath = "names_matching_peps-20260512T200543Z-3-001/names_matching_peps";
        File pepsDir = new File(dirPath);
        if (!pepsDir.exists() || !pepsDir.isDirectory()) {
            pepsDir = new File("../" + dirPath);
        }

        String csvPath = new File(pepsDir, "peps_names_512k.csv").getAbsolutePath();

        List<Nom> liste1;
        try {
            liste1 = lireNomsDepuisCsv(csvPath);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + csvPath);
            System.err.println(
                    "Assurez-vous que le dossier 'names_matching_peps-20260512T200543Z-3-001' se trouve bien à côté de votre projet, ou passez le chemin complet du fichier en argument.");
            System.err.println("Détails de l'erreur : " + e.getMessage());
            return;
        }

        // Configuration de la sortie console en UTF-8
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("Erreur: UTF-8 non supporté pour la sortie.");
        }

        System.out.print("Veuillez saisir un nom à rechercher : ");
        String nomSaisi = "Jean Dupont";
        
        try {
            // Utilisation d'un BufferedReader avec encodage explicite UTF-8
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, "UTF-8")
            );
            
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                nomSaisi = line.trim();
                // Nettoyage optionnel du BOM (Byte Order Mark) si présent (fréquent avec les pipes PowerShell)
                if (nomSaisi.startsWith("\uFEFF")) {
                    nomSaisi = nomSaisi.substring(1);
                }
            }
        } catch (IOException e) {
            System.out.println("\n(Erreur de lecture. Utilisation de la valeur par défaut : " + nomSaisi + ")");
        }

        List<Nom> liste2 = new ArrayList<>();
        liste2.add(new Nom(nomSaisi, "SaisieUtilisateur"));

        MoteurDeRecherche moteur = new MoteurDeRecherche();

        System.out.println("Recherche en cours pour le nom: " + nomSaisi + "...");
        List<Couple> resultats = moteur.rechercher(liste1, liste2);

        // Les résultats sont déjà affichés par le LivrerResultat dans le
        // MoteurDeRecherche,
        // mais nous pouvons aussi les afficher ici si besoin.
        System.out.println("=== Fin de la recherche ===");
    }

    private static List<Nom> lireNomsDepuisCsv(String csvPath) throws IOException {
        Path path = Paths.get(csvPath);
        List<Nom> noms = new ArrayList<>();

        // Spécifier explicitement l'encodage UTF-8
        List<String> lines = Files.readAllLines(path, java.nio.charset.StandardCharsets.UTF_8);
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
