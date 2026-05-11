import java.util.List ; 
import java.util.ArrayList ;

public class Configuration {
    private double seuil ; 
    private double pourcentage ; 
    private int nombrePremiers ; 
    private List<String> typesCouple ;  

    public Configuration() {
        this.seuil = 0.8;
        this.pourcentage = 50.0;
        this.nombrePremiers = 10;
        this.typesCouple = new ArrayList<>();
    }

    public double getSeuil() {
        return seuil;
    }

    public void setSeuil(double seuil) {
        this.seuil = seuil;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public int getNombrePremiers() {
        return nombrePremiers;
    }

    public void setNombrePremiers(int nombrePremiers) {
        this.nombrePremiers = nombrePremiers;
    }

    public List<String> getTypesCouple() {
        return typesCouple;
    }

    public void setTypesCouple(List<String> typesCouple) {
        this.typesCouple = typesCouple;
    }

    public void ajouterTypeCouple(String type) {
        if (!this.typesCouple.contains(type)) {
            this.typesCouple.add(type);
        }
    }
}

