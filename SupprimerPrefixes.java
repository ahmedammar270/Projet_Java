
public class SupprimerPrefixes implements Pretraiteur {
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0);   
        String[] prefixes = {"Dr.", "Mr.", "Ms.", "Mrs.", "Prof."};
        for (String prefix : prefixes) {    
            if (nomAPretraiter.startsWith(prefix)) {
                nomAPretraiter = nomAPretraiter.substring(prefix.length());
                break;
            }
        }
        nom.getNomPretraite().set(0, nomAPretraiter);

    
}
}
