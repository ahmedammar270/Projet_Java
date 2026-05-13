package GenerateurDeCandidats.GenerateurParMots;

import java.util.ArrayList;
import java.util.List;

import GenerateurDeCandidats.GenerateurDeCandidats;

public abstract class GenerateurParMots implements GenerateurDeCandidats {

    protected List<String> genererCandidats(String nomComplet) {
        List<String> mots = new ArrayList<>();
        if (nomComplet == null || nomComplet.isEmpty()) {
            return mots;
        }

        for (String partie : nomComplet.split("[\\s,;:!?.-]+")) {
            if (!partie.trim().isEmpty()) {
                mots.add(partie.trim());
            }
        }

        return mots;
    }

    protected int compterMotsCommuns(List<String> mots1, List<String> mots2) {
        if (mots1 == null || mots2 == null || mots1.isEmpty() || mots2.isEmpty()) {
            return 0;
        }

        int compteur = 0;
        for (String mot1 : mots1) {
            for (String mot2 : mots2) {
                if (mot1.equalsIgnoreCase(mot2)) {
                    compteur++;
                    break;
                }
            }
        }

        return compteur;
    }
}
