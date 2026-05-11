package GenerateurDeCandidats.GenerateurParMots;

import java.util.List;
import java.util.ArrayList;

public abstract class GenerateurParMots {

    public abstract List<String[]> genererCouples(List<String> listeNoms);

    protected List<String> decomposerEnMots(String nomComplet) {
        List<String> mots = new ArrayList<>();
        if (nomComplet == null || nomComplet.isEmpty()) return mots;
        for (String partie : nomComplet.split(" ")) {
            if (!partie.trim().isEmpty()) mots.add(partie.trim());
        }
        return mots;
    }

    protected int compterMotsCommuns(List<String> mots1, List<String> mots2) {
        int compteur = 0;
        for (String m1 : mots1)
            for (String m2 : mots2)
                if (m1.equalsIgnoreCase(m2)) { compteur++; break; }
        return compteur;
    }

    protected int getNombreMots(String nomComplet) {
        if (nomComplet == null || nomComplet.isEmpty()) return 0;
        return nomComplet.split("\\s+").length;
    }

    protected boolean aUnMotEnCommun(String nom1, String nom2) {
        return compterMotsCommuns(decomposerEnMots(nom1), decomposerEnMots(nom2)) > 0;
    }

    protected void afficherCouple(String[] couple) {
        if (couple != null && couple.length >= 2)
            System.out.println("Couple: " + couple[0] + " <-> " + couple[1]);
    }
}
