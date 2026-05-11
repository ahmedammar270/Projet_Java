package comparateurs;
import nom.Nom;

import java.util.List;
import java.util.ArrayList;
public class ComparateurDeMots extends ComparateursDeNoms {
    public double comparer(Nom nom1, Nom nom2) {
        List<String> nom1Pretraite = nom1.getNomPretraite();
        List<String> nom2Pretraite = nom2.getNomPretraite();
        int motsCommuns = 0;
        List<String> copie = new ArrayList<>(nom2Pretraite);
        
        for (String mot1 : nom1Pretraite) {
            if (copie.remove(mot1)) {
                motsCommuns++;
            }
        }
        int totalMots = nom1Pretraite.size() + nom2Pretraite.size() - motsCommuns;
        double score = (double) motsCommuns / totalMots;
        return score;  
    }
}
