package comparateurs;
import java.util.ArrayList;
import java.util.List;


public class ComparateurDeCaracteres extends ComparateursDeNoms {
    public Comparaison comparer(Nom nom1, Nom nom2) {
        String nom1Pretraite = String.join(" ", nom1.getNomPretraite());
        String nom2Pretraite = String.join(" ", nom2.getNomPretraite());
        int caracteresCommuns = 0;
        List<Character> copie = new ArrayList<>();
        for (char c : nom2Pretraite.toCharArray())
            copie.add(c);

        for (char c1 : nom1Pretraite.toCharArray()) {
            if (copie.remove(Character.valueOf(c1))) {
                caracteresCommuns++;
            }
        }
        int totalCaracteres = nom1Pretraite.length() + nom2Pretraite.length() - caracteresCommuns;
        double score = (double) caracteresCommuns / totalCaracteres;
        return new Comparaison(nom1, nom2, score);  
    }
    
}
