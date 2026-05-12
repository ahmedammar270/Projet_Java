package comparateurs;
import nom.Nom;
import java.util.List;
public class ComparateurNGram extends ComparateurDeChaines {
    

    public double comparer(Nom nom1, Nom nom2) {
        List<String> ngrams1 = nom1.getNomPretraite();
        List<String> ngrams2 = nom2.getNomPretraite();

        int intersection = 0;
        for (String ngram : ngrams1) {
            if (ngrams2.contains(ngram)) {
                intersection++;
            }
        }

        int union = ngrams1.size() + ngrams2.size() - intersection;
        double score = (union == 0) ? 0.0 : (double) intersection / union;

        return score;
    }

    
    
    
}
