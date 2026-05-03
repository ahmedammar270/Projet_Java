import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class DecomposeurSylabe extends Pretraiteur{
      public void pretraiter(Nom nom) {

        String mot = nom.getNomPretraite().get(0);
        List<Character> voyelles = Arrays.asList('a','e','i','o','u');

        List<String> syllabes = new ArrayList<>();
        String courant = "";

        boolean voyelle = voyelles.contains(mot.charAt(0));

        for (char c : mot.toCharArray()) {
            courant += c;

            if (voyelles.contains(c) != voyelle) {
                syllabes.add(courant);
                courant = "";
                voyelle = !voyelle;
            }
        }

        if (!courant.isEmpty()) {
            syllabes.add(courant);
        }

        nom.setNomPretraite(syllabes);
    } 
}
