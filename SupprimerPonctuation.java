import java.util.ArrayList;
import java.util.List;

public class SupprimerPonctuation extends Pretraiteur {
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0);
        nomAPretraiter = nomAPretraiter.replaceAll("[.,!?;:\"'()\\[\\]{}]", "");
        nom.getNomPretraite().set(0, nomAPretraiter);
    }
}