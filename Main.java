import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import nom.Couple;
import nom.Nom;

public class Main {
    private static int nPremiers = 10;

    public static void main(String[] args) {

        // --- Encodage UTF-8 console ---
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setErr(new java.io.PrintStream(System.err, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("Avertissement: UTF-8 non supporté.");
        }

        // --- Lecteur console UTF-8 ---
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8)
        );

        // --- Chargement de la liste PEPs ---
        String dirPath = "names_matching_peps-20260512T200543Z-3-001/names_matching_peps";
        File pepsDir = new File(dirPath);
        if (!pepsDir.exists() || !pepsDir.isDirectory()) {
            pepsDir = new File("../" + dirPath);
        }
        String csvPath = new File(pepsDir, "peps_names_512k.csv").getAbsolutePath();

        List<Nom> listePeps;
        try {
            listePeps = lireNomsDepuisCsv(csvPath);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier PEPs : " + csvPath);
            System.err.println("Détails : " + e.getMessage());
            return;
        }

        // --- Bienvenue ---
        System.out.println("=== Moteur de recherche PEPs ===");

        // --- Nombre de résultats ---
        System.out.print("Nombre de résultats à afficher [défaut: 10] : ");
        String inputN = lireLigne(reader, "");
        if (!inputN.isEmpty()) {
            try {
                nPremiers = Integer.parseInt(inputN);
                System.out.println("-> " + nPremiers + " résultats seront affichés.");
            } catch (NumberFormatException e) {
                System.out.println("-> Valeur invalide, 10 résultats par défaut.");
                nPremiers = 10;
            }
        }

        // --- Choix du mode ---
        System.out.println("\nChoisissez un mode :");
        System.out.println("  1 - Rechercher un seul nom");
        System.out.println("  2 - Rechercher depuis un fichier CSV");
        System.out.print("Votre choix (1 ou 2) : ");
        String choix = lireLigne(reader, "1");

        List<Nom> listeQuery;

        if (choix.equals("2")) {
            System.out.print("Entrez le chemin du fichier CSV : ");
            String cheminCsv = lireLigne(reader, "");
            if (cheminCsv.isEmpty()) {
                System.err.println("Chemin invalide. Arrêt.");
                return;
            }
            try {
                listeQuery = lireNomsDepuisCsv(cheminCsv);
                System.out.println(listeQuery.size() + " noms chargés depuis " + cheminCsv);
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture de " + cheminCsv);
                System.err.println("Détails : " + e.getMessage());
                return;
            }
        } else {
            System.out.print("Veuillez saisir un nom à rechercher : ");
            String nomSaisi = lireLigne(reader, "Jean Dupont");
            listeQuery = new ArrayList<>();
            listeQuery.add(new Nom(nomSaisi, "SaisieUtilisateur"));
        }

        // --- Recherche ---
        MoteurDeRecherche moteur = new MoteurDeRecherche();

        if (listeQuery.size() == 1) {
            System.out.println("\nRecherche en cours pour : " + listeQuery.get(0).getName());
            List<Couple> resultats = moteur.rechercher(listePeps, listeQuery);
            afficherResultats(resultats, nPremiers);
            System.out.println("=== Fin de la recherche ===");

        } else {
            System.out.println("\nRecherche en cours pour " + listeQuery.size() + " noms...");
            for (Nom nomQuery : listeQuery) {
                System.out.println("\n--- Recherche : " + nomQuery.getName() + " ---");

                List<Nom> listePepsFraiche;
                try {
                    listePepsFraiche = lireNomsDepuisCsv(csvPath);
                } catch (IOException e) {
                    System.err.println("Erreur rechargement PEPs : " + e.getMessage());
                    continue;
                }

                List<Nom> listeQueryUnique = new ArrayList<>();
                listeQueryUnique.add(new Nom(nomQuery.getName(), nomQuery.getId()));

                List<Couple> resultats = moteur.rechercher(listePepsFraiche, listeQueryUnique);
                afficherResultats(resultats, nPremiers);
            }
            System.out.println("\n=== Fin de toutes les recherches ===");
        }
    }

    private static void afficherResultats(List<Couple> resultats, int n) {
        System.out.println("Résultats (top " + n + ") :");
        int limite = Math.min(n, resultats.size());
        for (int i = 0; i < limite; i++) {
            System.out.println("  " + (i + 1) + ". " + resultats.get(i));
        }
        if (resultats.isEmpty()) {
            System.out.println("  Aucun résultat trouvé.");
        }
    }

    private static String lireLigne(BufferedReader reader, String defaut) {
        try {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) return defaut;
            line = line.trim();
            if (line.startsWith("\uFEFF")) line = line.substring(1);
            return line;
        } catch (IOException e) {
            return defaut;
        }
    }

    public static List<Nom> lireNomsDepuisCsv(String csvPath) throws IOException {
        Path path = Paths.get(csvPath);
        List<Nom> noms = new ArrayList<>();
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            if (i == 0 && line.toLowerCase().startsWith("id,")) continue;

            String[] parts = line.split(",", 2);
            if (parts.length < 2) continue;

            String id   = parts[0].trim();
            String name = parts[1].trim();
            if (!name.isEmpty()) {
                noms.add(new Nom(name, id));
            }
        }

        System.out.println("Chargement de " + noms.size() + " noms depuis " + csvPath);
        return noms;
    }
}