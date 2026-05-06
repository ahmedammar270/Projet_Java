import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class SelectionneurSelonSyllabes extends SelectionneurDeCanditat {
    public List<String> selectionner(String fichier, Nom nom) {
        List<String> resultats = new ArrayList<>();
        List<String> syllabesNom = nom.getNomPretraite();
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] v = ligne.split(",");
                if (v.length < 2) continue;

                String name = v[1];
                Nom tempNom = new Nom(v[0],name);
                List<String> syllabesName = tempNom.getNomPretraite();

            
                for (String syllabeNom : syllabesNom) {
                    for (String syllabeName : syllabesName) {
                        if (syllabeNom.equals(syllabeName)) {
                            resultats.add(name);
                            break;
                        }
                    }
                }
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultats;

    }

    
}
