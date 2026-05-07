public class SupprimerAccents implements Pretraiteur{
    public void pretraiter(Nom nom) {
        String nomAPretraiter = nom.getNomPretraite().get(0);
        nomAPretraiter = nomAPretraiter.replaceAll("[éèêë]", "e")
                                       .replaceAll("[àâä]", "a")
                                       .replaceAll("[îï]", "i")
                                       .replaceAll("[ôö]", "o")
                                       .replaceAll("[ùûü]", "u");
        nom.getNomPretraite().set(0, nomAPretraiter);
    }   
    
}
