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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import nom.Couple;
import nom.Nom;

public class Main {

    private static final int NB_COEURS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {

        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setErr(new java.io.PrintStream(System.err, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("Avertissement: UTF-8 non supporté.");
        }

        System.out.println("[INFO] " + NB_COEURS + " coeurs CPU disponibles");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));

        String relPath = "names_matching_peps-20260512T200543Z-3-001/names_matching_peps";
        File pepsDir = new File(relPath);
        if (!pepsDir.exists())
            pepsDir = new File("Projet_Java/" + relPath);
        if (!pepsDir.exists())
            pepsDir = new File("../" + relPath);
        String csvPath = new File(pepsDir, "peps_names_512k.csv").getAbsolutePath();

        List<Nom> listePeps;
        try {
            listePeps = lireNomsDepuisCsv(csvPath);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier PEPs : " + csvPath);
            return;
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
            if (cheminCsv.isEmpty())
                return;
            try {
                listeQuery = lireNomsDepuisCsv(cheminCsv);
                System.out.println(listeQuery.size() + " noms chargés depuis " + cheminCsv);
            } catch (IOException e) {
                System.err.println("Erreur lecture CSV.");
                return;
            }
        } else {
            System.out.print("Veuillez saisir un nom à rechercher : ");
            String nomSaisi = lireLigne(reader, "Jean Dupont");
            listeQuery = new ArrayList<>();
            listeQuery.add(new Nom(nomSaisi, "SaisieUtilisateur"));
        }

        // --- Recherche ---
        if (listeQuery.size() == 1) {
            // Nom unique : recherche directe
            System.out.println("\nRecherche en cours pour : " + listeQuery.get(0).getName());
            MoteurDeRecherche moteur = new MoteurDeRecherche();
            moteur.rechercher(copierListe(listePeps), listeQuery);

        } else {
            // CSV : recherche parallèle, un thread par nom
            System.out.println("\nRecherche parallèle sur " + listeQuery.size()
                    + " noms avec " + NB_COEURS + " coeurs...\n");

            final String csvPathFinal = csvPath;
            ExecutorService pool = Executors.newFixedThreadPool(NB_COEURS);
            List<Future<?>> futures = new ArrayList<>();

            for (Nom nomQuery : listeQuery) {
                futures.add(pool.submit(() -> {
                    List<Nom> pepsFrais = copierListe(listePeps);
                    List<Nom> uneRequete = new ArrayList<>();
                    uneRequete.add(new Nom(nomQuery.getName(), nomQuery.getId()));

                    MoteurDeRecherche moteur = new MoteurDeRecherche();
                    synchronized (System.out) {
                        moteur.rechercher(pepsFrais, uneRequete);
                    }
                }));
            }

            for (Future<?> f : futures) {
                try {
                    f.get();
                } catch (Exception e) {
                    System.err.println("[ERREUR thread] " + e.getMessage());
                }
            }
            pool.shutdown();
            System.out.println("\n=== Fin de toutes les recherches ===");
        }
    }

    // --- Copie d'une liste de Nom ---
    private static List<Nom> copierListe(List<Nom> source) {
        List<Nom> copie = new ArrayList<>();
        for (Nom n : source)
            copie.add(new Nom(n.getName(), n.getId()));
        return copie;
    }

    private static String lireLigne(BufferedReader reader, String defaut) {
        try {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty())
                return defaut;
            return line.trim();
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
            if (line.isEmpty() || (i == 0 && line.toLowerCase().startsWith("id,")))
                continue;
            String[] parts = line.split(",", 2);
            if (parts.length < 2)
                continue;
            noms.add(new Nom(parts[1].trim(), parts[0].trim()));
        }
        return noms;
    }
}