package selecteurs;
import java.util.List;
import java.util.ArrayList;
import nom.Couple;
import configuration.Configuration;

public class SelecteurParSeuil implements SelecteurMatching {
    private double seuil;

    public SelecteurParSeuil(Configuration config) {
        this.seuil = config.getSeuil();
    }
    public List<Couple> selectionner(List<Couple> couples) {
        List<Couple> resultats = new ArrayList<>();
        for (Couple couple : couples) {
            if (couple.getScore() >= seuil) {
                resultats.add(couple);
            }
        }
        return resultats;
    }

    
}

