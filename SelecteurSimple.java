import java.util.ArrayList;
import java.util.List;

public class SelecteurSimple implements SelecteurMatching {

    public List<Couple> selectionner(List<Couple> couples) {
        List<Couple> resultats = new ArrayList<>();
        for (Couple couple : couples) {
            if (couple.getScore() > 0.5) {
                resultats.add(couple);
            }
        }
        return resultats;
    }
}
