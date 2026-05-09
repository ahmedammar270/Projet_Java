package comparateurs;
import nom.Nom;
import nom.Couple;
public class ComparateurEgaliteExacte extends ComparateurDeChaines {
    public double comparer(Nom nom1, Nom nom2) {
        String nom1Pretraite = String.join(" ", nom1.getNomPretraite());
        String nom2Pretraite = String.join(" ", nom2.getNomPretraite());
        
        if (nom1Pretraite.equals(nom2Pretraite)) {
            return 1.0;
        } else {
            return 0.0;
        }
    }   
}
