package pretraiteurs;
import nom.Nom;
public class SupprimerEspaces implements Pretraiteur {
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0);
        nomAPretraiter = nomAPretraiter.replaceAll("\\s+", " ").trim();
        nom.getNomPretraite().set(0, nomAPretraiter);
    }
    
}
