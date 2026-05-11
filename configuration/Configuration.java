import java.util.List;
import java.util.ArrayList;

public class Configuration {

    private double seuil;
    private double pourcentage;
    private int nombrePremiers;
    private List<String> typesDeCouples;
    private double toleranceGenerateur;

    public Configuration() {
        seuil = 0.8;
        pourcentage = 50.0;
        nombrePremiers = 10;
        typesDeCouples = new ArrayList<>();
        toleranceGenerateur = 0.5;
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

    public int getNombrePremiers() {
        return nombrePremiers;
    }

    public void setNombrePremiers(int nouvelleValeur) {
        nombrePremiers = nouvelleValeur;
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
        System.out.println("Nombre résultats: " + nombrePremiers);
        System.out.println("Tolérance générateur: " + (toleranceGenerateur * 100) + "%");
        System.out.println("Types de couples: " + typesDeCouples);
    }
}
