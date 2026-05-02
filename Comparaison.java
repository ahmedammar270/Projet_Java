public class Comparaison {
    private Nom nom1;
    private Nom nom2;
    private double score;

    public Comparaison(Nom nom1, Nom nom2, double score) {
        this.nom1 = nom1;
        this.nom2 = nom2;
        this.score = score;
    }

    public Nom getNom1() {
        return nom1;
    }

    public Nom getNom2() {
        return nom2;
    }

    public double getScore() {
        return score;
    }

    public String toString() {
        return "Comparaison{nom1=" + nom1.getName() + ", nom2=" + nom2.getName() + ", score=" + score + '}';
    }
}