package selecteurs;
public class SelecteurPercentagePremiers implements SelecteurMatching {

    private double pourcentage;

    public SelecteurPercentagePremiers(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public List<Couple> selectionner(List<Couple> couples) {
        int nombreASelectionner = (int) Math.ceil(couples.size() * pourcentage);
        return couples.subList(0, Math.min(nombreASelectionner, couples.size()));
    }       
    
}
