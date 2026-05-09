package comparateurs;
import java.util.List;
import java.util.ArrayList;
public class ComparateurNGram extends ComparateurDeChaines {
    private int n;

    public ComparateurNGram(int n) {
        this.n = n;
    }

    public Comparaison comparer(Nom nom1, Nom nom2) {
        List<String> ngrams1 = genererNgrams(String.join(" ", nom1.getNomPretraite()), n);
        List<String> ngrams2 = genererNgrams(String.join(" ", nom2.getNomPretraite()), n);

        int intersection = 0;
        for (String ngram : ngrams1) {
            if (ngrams2.contains(ngram)) {
                intersection++;
            }
        }

        int union = ngrams1.size() + ngrams2.size() - intersection;
        double score = (union == 0) ? 0.0 : (double) intersection / union;

        return new Comparaison(nom1, nom2, score);
    }

    private List<String> genererNgrams(String str, int n) {
        List<String> ngrams = new ArrayList<>();
        for (int i = 0; i <= str.length() - n; i++) {
            ngrams.add(str.substring(i, i + n));
        }
        return ngrams;
    }
    
    
}
