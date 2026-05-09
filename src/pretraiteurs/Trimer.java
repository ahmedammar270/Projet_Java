package pretraiteurs;
public class Trimer implements Pretraiteur {
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0).trim();
        nom.getNomPretraite().clear();
        nom.getNomPretraite().add(nomAPretraiter);
    }
}
