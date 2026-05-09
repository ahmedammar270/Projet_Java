package pretraiteurs;
import nom.Nom;
public class SupprimerPonctuation implements Pretraiteur {
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0);
        nomAPretraiter = nomAPretraiter.replaceAll("[.,!?;:\"'()\\[\\]{}]", "");
        nom.getNomPretraite().set(0, nomAPretraiter);
    }
}
