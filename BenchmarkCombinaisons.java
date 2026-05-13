import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import nom.Nom;
import nom.Couple;
import pretraiteurs.*;
import comparateurs.*;
import configuration.Configuration;
import GenerateurDeCandidats.GenerateurDeCandidats;
import GenerateurDeCandidats.GenerateurParLongueur.*;

public class BenchmarkCombinaisons {

    // Noms de test connus presents dans les fichiers PEPs
    static String[] NOMS_TEST = {
        "Yaya Sangaré",
        "abubaker mohamed",
        "Vladimir Putin",
        "Kim Jong Un"
    };

    // Fichiers CSV a tester (tailles croissantes)
    static String[] FICHIERS = {
        "peps_names_100.csv",
        "peps_names_1k.csv",
        "peps_names_4k.csv",
        "peps_names_8k.csv",
        "peps_names_16k.csv",
        "peps_names_32k.csv",
        "peps_names_64k.csv",
        "peps_names_128k.csv",
        "peps_names_256k.csv",
        "peps_names_512k.csv",
        "peps_names_658k.csv"
    };

    public static void main(String[] args) throws Exception {
        String dirPath = "names_matching_peps-20260512T200543Z-3-001/names_matching_peps";
        java.io.File pepsDir = new java.io.File(dirPath);
        if (!pepsDir.exists()) pepsDir = new java.io.File("../" + dirPath);

        System.out.println("=======================================================");
        System.out.println("   BENCHMARK DE TOUTES LES COMBINAISONS");
        System.out.println("=======================================================\n");

        // --- PARTIE 1 : Qualite (sur 4k) ---
        String csv4k = new java.io.File(pepsDir, "peps_names_4k.csv").getAbsolutePath();
        List<Nom> noms4k = lireNomsDepuisCsv(csv4k);

        String[][] combos = {
            {"decouperNom",        "JaroWinkler",   "LongueurSansIndex"},
            {"decouperNom",        "JaroWinkler",   "LongueurAvecIndex"},
            {"decouperNom",        "Levenshtein",   "LongueurSansIndex"},
            {"decouperNom",        "Levenshtein",   "LongueurAvecIndex"},
            {"decouperNom",        "ComparateurDeMots", "LongueurSansIndex"},
            {"decouperNom",        "ComparateurDeMots", "LongueurAvecIndex"},
            {"decouperNom",        "DeCaracteres",  "LongueurSansIndex"},
            {"decouperNom",        "DeCaracteres",  "LongueurAvecIndex"},
            {"DecomposeurNgrams2", "NGram",         "LongueurSansIndex"},
            {"DecomposeurNgrams2", "NGram",         "LongueurAvecIndex"},
            {"DecomposeurNgrams3", "NGram",         "LongueurSansIndex"},
            {"DecomposeurNgrams3", "NGram",         "LongueurAvecIndex"},
        };

        System.out.println("--- PARTIE 1 : TEST DE QUALITE (4000 noms) ---\n");

        // CSV output for quality
        StringBuilder csvQualite = new StringBuilder();
        csvQualite.append("Decomposeur,Comparateur,Generateur,NomRecherche,Top1_Resultat,Top1_Score,Temps_ms\n");

        for (String[] combo : combos) {
            String decomp = combo[0];
            String comp = combo[1];
            String gen = combo[2];
            String label = decomp + " + " + comp + " + " + gen;

            System.out.println(">> Combinaison: " + label);

            for (String nomTest : NOMS_TEST) {
                // Recharger les noms frais (les decomposeurs modifient en place)
                List<Nom> listePeps = copierNoms(noms4k);
                List<Nom> listeQuery = new ArrayList<>();
                listeQuery.add(new Nom(nomTest, "Query"));

                // Pretraitement
                for (Nom n : listePeps) appliquerPretraitement(n);
                for (Nom n : listeQuery) appliquerPretraitement(n);

                long t1 = System.currentTimeMillis();

                // Generateur
                GenerateurDeCandidats generateur = creerGenerateur(gen);
                HashMap<Nom, List<Nom>> candidats = generateur.genererCandidats(listePeps, listeQuery);

                // Decomposition sur query
                for (Nom n : listeQuery) appliquerDecomposition(n, decomp);

                // Comparaison
                List<Couple> couples = new ArrayList<>();
                for (Map.Entry<Nom, List<Nom>> entry : candidats.entrySet()) {
                    Nom nomClient = entry.getKey();
                    appliquerDecomposition(nomClient, decomp);
                    for (Nom candidat : entry.getValue()) {
                        double score = appliquerComparateur(nomClient, candidat, comp);
                        couples.add(new Couple(nomClient, candidat, score));
                    }
                }

                couples.sort(Comparator.comparingDouble(Couple::getScore).reversed());
                long t2 = System.currentTimeMillis();

                String top1Nom = couples.isEmpty() ? "AUCUN" : couples.get(0).getNom2().getName();
                double top1Score = couples.isEmpty() ? 0.0 : couples.get(0).getScore();
                int nbCandidats = couples.size();

                System.out.printf("   %s -> Top1: %s (%.4f) | %d candidats | %d ms\n",
                    nomTest, top1Nom, top1Score, nbCandidats, (t2-t1));

                csvQualite.append(String.format("%s,%s,%s,%s,%s,%.4f,%d\n",
                    decomp, comp, gen, nomTest, top1Nom, top1Score, (t2-t1)));
            }
            System.out.println();
        }

        // --- PARTIE 2 : Performance (toutes tailles, meilleure combo) ---
        System.out.println("\n--- PARTIE 2 : TEST DE PERFORMANCE (toutes tailles) ---");
        System.out.println("Combinaison utilisee: decouperNom + JaroWinkler + LongueurAvecIndex\n");

        StringBuilder csvPerf = new StringBuilder();
        csvPerf.append("Nombre_Noms,Temps_ms\n");

        for (String fichier : FICHIERS) {
            java.io.File f = new java.io.File(pepsDir, fichier);
            if (!f.exists()) continue;

            List<Nom> listePeps = lireNomsDepuisCsv(f.getAbsolutePath());
            List<Nom> listeQuery = new ArrayList<>();
            listeQuery.add(new Nom("abubaker mohamed", "Query"));

            for (Nom n : listePeps) appliquerPretraitement(n);
            for (Nom n : listeQuery) appliquerPretraitement(n);

            long t1 = System.currentTimeMillis();

            GenerateurDeCandidats generateur = new GenerateurParLongueurAvecIndex();
            HashMap<Nom, List<Nom>> candidats = generateur.genererCandidats(listePeps, listeQuery);

            for (Nom n : listeQuery) new decouperNom().pretraiter(n);

            List<Couple> couples = new ArrayList<>();
            ComparateurJaroWinkler comparateur = new ComparateurJaroWinkler();
            for (Map.Entry<Nom, List<Nom>> entry : candidats.entrySet()) {
                Nom nomClient = entry.getKey();
                new decouperNom().pretraiter(nomClient);
                for (Nom candidat : entry.getValue()) {
                    double score = comparateur.comparer(nomClient, candidat);
                    couples.add(new Couple(nomClient, candidat, score));
                }
            }

            long t2 = System.currentTimeMillis();
            System.out.printf("   %s : %d noms -> %d ms\n", fichier, listePeps.size(), (t2-t1));
            csvPerf.append(String.format("%d,%d\n", listePeps.size(), (t2-t1)));
        }

        // Sauvegarder les resultats
        Files.write(Paths.get("benchmark_qualite.csv"), csvQualite.toString().getBytes());
        Files.write(Paths.get("benchmark_performance.csv"), csvPerf.toString().getBytes());

        System.out.println("\nResultats sauvegardes dans benchmark_qualite.csv et benchmark_performance.csv");
    }

    // --- Methodes utilitaires ---

    static void appliquerPretraitement(Nom nom) {
        new SupprimerAccents().pretraiter(nom);
        new SupprimerEspaces().pretraiter(nom);
        new SupprimerPonctuation().pretraiter(nom);
        new SupprimerPrefixes().pretraiter(nom);
        new TransformerEnMinuscule().pretraiter(nom);
        new Trimer().pretraiter(nom);
    }

    static void appliquerDecomposition(Nom nom, String type) {
        switch (type) {
            case "decouperNom": new decouperNom().pretraiter(nom); break;
            case "DecomposeurNgrams2": new DecomposeurNgrams(2).pretraiter(nom); break;
            case "DecomposeurNgrams3": new DecomposeurNgrams(3).pretraiter(nom); break;
        }
    }

    static double appliquerComparateur(Nom n1, Nom n2, String type) {
        switch (type) {
            case "JaroWinkler": return new ComparateurJaroWinkler().comparer(n1, n2);
            case "Levenshtein": return new ComparateurLevenshtein().comparer(n1, n2);
            case "NGram": return new ComparateurNGram().comparer(n1, n2);
            case "ComparateurDeMots": return new ComparateurDeMots().comparer(n1, n2);
            case "DeCaracteres": return new ComparateurDeCaracteres().comparer(n1, n2);
            default: return 0.0;
        }
    }

    static GenerateurDeCandidats creerGenerateur(String type) {
        switch (type) {
            case "LongueurSansIndex": return new GenerateurParLongueurSansIndex();
            case "LongueurAvecIndex": return new GenerateurParLongueurAvecIndex();
            default: return new GenerateurParLongueurSansIndex();
        }
    }

    static List<Nom> copierNoms(List<Nom> originaux) {
        List<Nom> copie = new ArrayList<>();
        for (Nom n : originaux) {
            copie.add(new Nom(n.getName(), n.getId()));
        }
        return copie;
    }

    static List<Nom> lireNomsDepuisCsv(String csvPath) throws IOException {
        List<Nom> noms = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(csvPath));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || (i == 0 && line.toLowerCase().startsWith("id,"))) continue;
            String[] parts = line.split(",", 2);
            if (parts.length < 2) continue;
            noms.add(new Nom(parts[1].trim(), parts[0].trim()));
        }
        return noms;
    }
}
