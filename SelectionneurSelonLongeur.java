import java.io.*;
import java.util.*;

public class SelectionneurSelonLongeur implements SelectionneurDeCanditat {

    public List<String> selectionner(String fichier, Nom nom) {

        String nomOriginale = nom.getName();
        int longueur = nomOriginale.length();

        List<String> resultats = new ArrayList<>();

        double tolerance = 0.3; 

        int min = (int) (longueur * (1 - tolerance));
        int max = (int) (longueur * (1 + tolerance));

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {

            String ligne;

            while ((ligne = br.readLine()) != null) {

                String[] v = ligne.split(",");
                if (v.length < 2) continue;

                String name = v[1];
                int lenNom = name.length();

                if (lenNom >= min && lenNom <= max) {
                    resultats.add(name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultats;
    }
}