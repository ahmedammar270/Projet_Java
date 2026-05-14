import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireFichiers {
    private String dirPath = "names_matching_peps-20260512T200543Z-3-001/names_matching_peps";

    public GestionnaireFichiers() {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir = new File("../" + dirPath);
            if (dir.exists()) {
                dirPath = dir.getPath();
            }
        }
    }

    public void ajouterFiche(String nomFichier, String id, String nom) throws IOException {
        File file = new File(dirPath, nomFichier);
        boolean estNouveau = !file.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (estNouveau) {
                bw.write("id,name");
                bw.newLine();
            }
            bw.write(id + "," + nom);
            bw.newLine();
        }
    }

    public List<String> listerFiches() {
        List<String> fiches = new ArrayList<>();
        File dir = new File(dirPath);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".csv"));
            if (files != null) {
                for (File f : files) {
                    fiches.add(f.getName());
                }
            }
        }
        return fiches;
    }

    public boolean supprimerFiche(String nomFichier) {
        File file = new File(dirPath, nomFichier);
        return file.exists() && file.delete();
    }

    public static void main(String[] args) {
        // Exemple d'utilisation simple
        GestionnaireFichiers gf = new GestionnaireFichiers();
        System.out.println("Fiches actuelles : " + gf.listerFiches());

        try {
            String testFile = "peps_supplement.csv";
            gf.ajouterFiche(testFile, "NEW-001", "Nouvel Entrant");
            System.out.println("Ajout réussi dans " + testFile);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }
}
