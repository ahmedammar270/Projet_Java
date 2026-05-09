package nom;
public class Couple {
    private Nom nom1;
    private Nom nom2;
    private double score;

    public Couple(Nom nom1, Nom nom2, double score) {
        this.nom1 = nom1;
        this.nom2 = nom2;
        this.score = score;
    }

    public Nom getNom1() {
        return nom1;
    }

    public void setNom1(Nom nom1) {
        this.nom1 = nom1;
    }

    public Nom getNom2() {
        return nom2;
    }

    public void setNom2(Nom nom2) {
        this.nom2 = nom2;
    }

    public double getScore() {
        return score;
    }

    
    public String toString() {
        return nom1.getName() + " - " + nom2.getName() + " (score: " + score + ")";
    }
}
