/*
 * import java.io.File;
 * import java.io.FileWriter;
 * import java.io.IOException;
 * import java.nio.file.Files;
 * import java.nio.file.Path;
 * import java.nio.file.Paths;
 * import java.util.ArrayList;
 * import java.util.List;
 * import nom.Nom;
 * import nom.Couple;
 * 
 * public class Benchmark {
 * public static void main(String[] args) {
 * String dirPath =
 * "names_matching_peps-20260512T200543Z-3-001/names_matching_peps";
 * File pepsDir = new File(dirPath);
 * 
 * if (!pepsDir.exists() || !pepsDir.isDirectory()) {
 * pepsDir = new File("../" + dirPath);
 * }
 * 
 * if (!pepsDir.exists()) {
 * System.err.println("Directory not found!");
 * return;
 * }
 * 
 * File[] files = pepsDir.listFiles((dir, name) ->
 * name.toLowerCase().endsWith(".csv"));
 * if (files == null) return;
 * 
 * try (FileWriter writer = new FileWriter("benchmark_results.csv")) {
 * writer.write("ExecutionTime(ms),NumberOfNames\n");
 * 
 * for (File file : files) {
 * System.out.println("Benchmarking " + file.getName() + "...");
 * try {
 * List<Nom> list1 = lireNomsDepuisCsv(file.getAbsolutePath());
 * List<Nom> list2 = new ArrayList<>();
 * list2.add(new Nom("Yaya Sangaré", "Query"));
 * 
 * MoteurDeRecherche moteur = new MoteurDeRecherche();
 * 
 * long startTime = System.currentTimeMillis();
 * moteur.rechercher(list1, list2);
 * long endTime = System.currentTimeMillis();
 * 
 * long duration = endTime - startTime;
 * writer.write(duration + "," + list1.size() + "\n");
 * System.out.println("  Time: " + duration + " ms, Names: " + list1.size());
 * } catch (Exception e) {
 * System.err.println("Error benchmarking " + file.getName() + ": " +
 * e.getMessage());
 * }
 * }
 * System.out.println("Results saved to benchmark_results.csv");
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * }
 * 
 * private static List<Nom> lireNomsDepuisCsv(String csvPath) throws IOException
 * {
 * Path path = Paths.get(csvPath);
 * List<Nom> noms = new ArrayList<>();
 * List<String> lines = Files.readAllLines(path);
 * for (int i = 0; i < lines.size(); i++) {
 * String line = lines.get(i).trim();
 * if (line.isEmpty() || (i == 0 && line.toLowerCase().startsWith("id,")))
 * continue;
 * String[] parts = line.split(",", 2);
 * if (parts.length < 2) continue;
 * noms.add(new Nom(parts[1].trim(), parts[0].trim()));
 * }
 * return noms;
 * }
 * }
 */