package pretraiteurs;
public class decouperNom implements Pretraiteur {
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0);
        String[] mots = nomAPretraiter.split(" ");
        nom.getNomPretraite().clear();  
        for (String mot : mots) {
            nom.getNomPretraite().add(mot);
        }


    
    }
}
