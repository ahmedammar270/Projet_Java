import java.util.List;
import nom.Couple;

public class LivrerResultat {

    public void livrerResultat(List<Couple> resultats) {
        System.out.println("Résultats de la recherche:");
        for (Couple couple : resultats) {
            System.out.println(couple);
        }
    }
}
