package selecteurs;
import java.util.List;
import java.util.ArrayList;
public class SelecteurParSeuil implements SelecteurMatching {
    private double seuil;

    public SelecteurParSeuil(double seuil) {
        this.seuil = seuil;
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
