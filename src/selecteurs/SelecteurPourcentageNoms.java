public class SelecteurPourcentageNoms implements SelecteurMatching {

    private double pourcentage;

    public SelecteurPourcentageNoms(double pourcentage) {
        this.pourcentage = pourcentage;
        if (this.pourcentage < 0) this.pourcentage = 0;
        if (this.pourcentage > 100) this.pourcentage = 100;
    }

    @Override
    public java.util.List<Couple> selectionner(java.util.List<Couple> couples) {
        java.util.List<Couple> resultat = new java.util.ArrayList<>();

        couples.sort((c1, c2) -> Double.compare(c2.getScore(), c1.getScore()));

        int nombreSelection = (int) Math.ceil(couples.size() * (this.pourcentage / 100.0));

        for (int i = 0; i < nombreSelection && i < couples.size(); i++) {
            resultat.add(couples.get(i));
        }

        return resultat;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
        if (this.pourcentage < 0) this.pourcentage = 0;
        if (this.pourcentage > 100) this.pourcentage = 100;
    }
}
