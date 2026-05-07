// import java.io.*;
// import java.util.ArrayList;
// import java.util.List;

// public class MainTest {
//     public static void main(String[] args) {
//         String fichierCsv = "peps_names_100.csv";
        
//         // Étape 1: Lire le fichier CSV et charger les noms
//         List<Nom> nomsDB = chargerNomsDepuisCSV(fichierCsv);
        
//         if (nomsDB.isEmpty()) {
//             System.out.println("Erreur: Aucun nom chargé depuis le fichier CSV");
//             return;
//         }
        
//         // Étape 2: Prendre un nom qui existe déjà (par exemple le 2ème)
//         Nom nomARechercher = nomsDB.get(1); // "Marica Montemaggi"
//         System.out.println("=== TEST DE RECHERCHE ===");
//         System.out.println("Nom à rechercher: " + nomARechercher.getName());
//         System.out.println();
        
//         // Étape 3: Créer une liste de recherche avec ce nom
//         List<Nom> listeRecherche = new ArrayList<>();
//         listeRecherche.add(new Nom(nomARechercher.getName(), "TEST-001"));
        
//         // Étape 4: Configurer le moteur de recherche
//         ComparateursDeNoms comparateur = new ComparateurEgaliteExacte();
//         SelecteurMatching selecteur = new SelecteurSimple();
//         MoteurDeRecherche moteur = new MoteurDeRecherche(comparateur, selecteur);
        
//         // Ajouter des prétraitements
//         moteur.ajouterPretraiteur(new TransformerEnMinuscule());
//         moteur.ajouterPretraiteur(new SupprimerAccents());
//         moteur.ajouterPretraiteur(new SupprimerEspaces());
        
//         // Étape 5: Effectuer la recherche
//         System.out.println("=== RÉSULTATS DE LA RECHERCHE ===");
//         List<Couple> resultats = moteur.rechercher(listeRecherche, nomsDB);
        
//         if (resultats.isEmpty()) {
//             System.out.println("Aucun résultat trouvé");
//         } else {
//             System.out.println("Nombre de résultats: " + resultats.size());
//             for (Couple couple : resultats) {
//                 System.out.println("Nom trouvé: " + couple.getNom1().getName());
//                 System.out.println("Score: " + couple.getScore());
//                 System.out.println("---");
//             }
//         }
//     }
    
//     // Méthode utilitaire pour charger les noms depuis le CSV
//     private static List<Nom> chargerNomsDepuisCSV(String nomFichier) {
//         List<Nom> noms = new ArrayList<>();
//         try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
//             String ligne;
//             boolean firstLine = true;
//             while ((ligne = br.readLine()) != null) {
//                 if (firstLine) {
//                     firstLine = false; // Sauter l'en-tête
//                     continue;
//                 }
//                 String[] parts = ligne.split(",", 2);
//                 if (parts.length == 2) {
//                     String id = parts[0];
//                     String name = parts[1];
//                     noms.add(new Nom(name, id));
//                 }
//             }
//         } catch (IOException e) {
//             System.out.println("Erreur lors de la lecture du fichier: " + e.getMessage());
//         }
//         return noms;
//     }
// }
