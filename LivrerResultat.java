import java.util.List;
import nom.Couple;

public class LivrerResultat {

    public void livrerResultat(List<Couple> resultats) {
        for (Couple couple : resultats) {
            System.out.println(couple);
        }
    }
}