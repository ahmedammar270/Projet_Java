package selecteurs;
import java.util.List;
import java.util.ArrayList;
import nom.Couple;
import configuration.Configuration;

public class SelecteurNPremiers implements SelecteurMatching {
    private int n;

    public SelecteurNPremiers(Configuration config) {
        this.n = config.getNPremiers();
    }

    public List<Couple> selectionner(List<Couple> couples) {
        List<Couple> resultats = new ArrayList<>();
        if (couples == null || couples.isEmpty()) {
            return new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            resultats.add(couples.get(i));
        }
        return resultats;
    }
}