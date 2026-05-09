package pretraiteurs;
public class TransformerEnMinuscule implements Pretraiteur {   
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0);
        nomAPretraiter = nomAPretraiter.toLowerCase();
        nom.getNomPretraite().set(0, nomAPretraiter);
    }
    
}
