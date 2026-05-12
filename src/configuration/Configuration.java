package configuration;
import java.util.List;
import java.util.ArrayList;


public class Configuration {

    private double seuil;
    private double pourcentage;
    private int nPremiers;
    private List<String> typesDeCouples;
    private double toleranceGenerateur;
    private boolean estEntiere;
    private int     toleranceEntiere;
    private double  tolerancePourcentage;

    public Configuration() {
        seuil = 0.8;
        pourcentage = 50.0;
        nPremiers = 10;
        typesDeCouples = new ArrayList<>();
        toleranceGenerateur = 0.5;
        estEntiere = true;
        toleranceEntiere = 3;
        tolerancePourcentage = 0.30;

    }
    public boolean toleranceGenerateurestEntiere()    { return estEntiere; }
    public boolean toleranceGenerateurestPercentage() { return !estEntiere; }
    public int getToleranceEntiere() {
        return toleranceEntiere;
    }
    public double getTolerancePourcentage() {
        return tolerancePourcentage;
    }
    public void ToleranceEntiere(int t) {
        this.toleranceEntiere = t;
        this.estEntiere = true;
    }

    public void TolerancePourcentage(double t) {
        this.tolerancePourcentage = t;
        this.estEntiere = false;
    }

    public double getSeuil() {
        return seuil;
    }

    public void setSeuil(double nouvelleValeur) {
        seuil = nouvelleValeur;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double nouvelleValeur) {
        pourcentage = nouvelleValeur;
    }

    public int getNPremiers() {
        return nPremiers;
    }

    public void setNombrePremiers(int nouvelleValeur) {
        nPremiers = nouvelleValeur;
    }

    public List<String> getTypesDeCouples() {
        return typesDeCouples;
    }

    public void setTypesDeCouples(List<String> nouvelleListe) {
        typesDeCouples = nouvelleListe;
    }

    public void ajouterType(String type) {
        if (!typesDeCouples.contains(type)) {
            typesDeCouples.add(type);
        }
    }

    public double getToleranceGenerateur() {
        return toleranceGenerateur;
    }

    public void setToleranceGenerateur(double nouvelleTolerance) {
        if (nouvelleTolerance < 0) {
            toleranceGenerateur = 0;
        } else if (nouvelleTolerance > 1) {
            toleranceGenerateur = 1;
        } else {
            toleranceGenerateur = nouvelleTolerance;
        }
    }

    public void augmenterTolerance(double augmentation) {
        setToleranceGenerateur(toleranceGenerateur + augmentation);
    }

    public void diminuerTolerance(double reduction) {
        setToleranceGenerateur(toleranceGenerateur - reduction);
    }

    public boolean estAccepte(double score) {
        return score >= toleranceGenerateur;
    }

    public void afficherParametres() {
        System.out.println("=== Paramètres actuels ===");
        System.out.println("Seuil: " + seuil);
        System.out.println("Pourcentage: " + pourcentage + "%");
        System.out.println("Nombre résultats: " + nPremiers);
        System.out.println("Tolérance générateur: " + (toleranceGenerateur * 100) + "%");
        System.out.println("Types de couples: " + typesDeCouples);
    }
}
