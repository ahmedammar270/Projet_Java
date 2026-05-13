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
        List<Couple> trier = new ArrayList<>(couples);
        trier.sort((c1, c2) -> Double.compare(c2.getScore(), c1.getScore()));
        
        List<Couple> resultats = new ArrayList<>();
        for (int i = 0; i < trier.size(); i++) {
            if (i < n) {
                resultats.add(trier.get(i));
            }
        }
        return resultats;
    }
}