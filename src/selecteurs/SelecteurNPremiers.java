package selecteurs;
import java.util.List;
import java.util.ArrayList;
import nom.Couple;
public class SelecteurNPremiers implements SelecteurMatching {
    private int n;

    public SelecteurNPremiers(int n) {
        this.n = n;
    }
    public List<Couple> selectionner(List<Couple> couples) {
        List<Couple> resultats = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            resultats.add(couples.get(i));
        }
        return resultats;
    }
}
