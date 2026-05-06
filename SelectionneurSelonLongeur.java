import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SelectionneurSelonLongeur extends SelectionneurDeCanditat {
    public List<String> selectionner(String fichier,Nom nom){
        String nomOriginale = nom.getName();
        int longueur = nomOriginale.length();
        List<String> resultats = new ArrayList<>();

        
        double tolerance = 0.3; 

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {

            String ligne;

            while ((ligne = br.readLine()) != null) {

                String[] v = ligne.split(",");
                if (v.length < 2) continue;

                String name = v[1];

                int lenNom = name.length();

            
                if (Math.abs(lenNom - longueur) <= tolerance * longueur || Math.abs(lenNom - longueur) >= tolerance * longueur) {
                    resultats.add(name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultats;
    }
}


