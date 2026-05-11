package selecteurs;
import java.util.List;
import java.util.ArrayList;
import nom.Couple;
import Configuration;

public class SelecteurPercentagePremiers implements SelecteurMatching {

    private double pourcentage;

    public SelecteurPercentagePremiers(Configuration config) {
        this.pourcentage = config.getPourcentage();
    }

    @Override
    public List<Couple> selectionner(List<Couple> couples) {
        List<Couple> trier = new ArrayList<>(couples);
        trier.sort((c1, c2) -> Double.compare(c2.getScore(), c1.getScore()));
        
        List<Couple> resultats = new ArrayList<>();
        int nombreASelectionner = (int) Math.ceil(trier.size() * pourcentage);
        
        for (int i = 0; i < trier.size(); i++) {
            if (i < nombreASelectionner) {
                resultats.add(trier.get(i));
            }
        }
        return resultats;
    
}

