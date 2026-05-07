import java.util.List;

public class LivrerResultat {

    public void livrerResultat(List<Couple> resultats) {
        System.out.println("Résultats de la recherche:");
        for (Couple couple : resultats) {
            System.out.println(couple);
        }
    }
}
