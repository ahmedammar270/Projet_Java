package pretraiteurs;

import java.util.ArrayList;
import java.util.List;

import nom.Nom;

public class DecomposeurNgrams implements Pretraiteur {
    private int n;

    public DecomposeurNgrams(int n) {
        this.n = n;
    }

    public void pretraiter(Nom nom) {
       String nom1 = nom.getNomPretraite().get(0);
        List<String> ngrams = new ArrayList<>();
        for (int i = 0; i <= nom1.length() - n; i++) {
            ngrams.add(nom1.substring(i, i + n));
        }
        nom.setNomPretraite(ngrams);
    }
}

    