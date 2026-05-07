
public class ComparateurEgaliteExacte extends ComparateurDeChaines {
    public Comparaison comparer(Nom nom1, Nom nom2) {
        String nom1Pretraite = String.join(" ", nom1.getNomPretraite());
        String nom2Pretraite = String.join(" ", nom2.getNomPretraite());
        
        if (nom1Pretraite.equals(nom2Pretraite)) {
            return new Comparaison(nom1, nom2, 1.0);
        } else {
            return new Comparaison(nom1, nom2, 0.0);
        }
    }   
}
