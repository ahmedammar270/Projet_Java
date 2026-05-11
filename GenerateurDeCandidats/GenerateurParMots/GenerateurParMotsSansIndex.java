package GenerateurDeCandidats.GenerateurParMots;

import java.util.List;
import java.util.ArrayList;

public class GenerateurParMotsSansIndex extends GenerateurParMots {

    @Override
    public List<String[]> genererCouples(List<String> listeNoms) {
        List<String[]> couples = new ArrayList<>();

        if (listeNoms == null || listeNoms.isEmpty()) {
            return couples;
        }

        for (int i = 0; i < listeNoms.size(); i++) {
            for (int j = i + 1; j < listeNoms.size(); j++) {
                String nom1 = listeNoms.get(i);
                String nom2 = listeNoms.get(j);

                if (aUnMotEnCommun(nom1, nom2)) {
                    String[] couple = new String[2];
                    couple[0] = nom1;
                    couple[1] = nom2;
                    couples.add(couple);
                }
            }
        }

        return couples;
    }

    private boolean aUnMotEnCommun(String nom1, String nom2) {
        List<String> mots1 = decomposerEnMots(nom1);
        List<String> mots2 = decomposerEnMots(nom2);

        for (String mot1 : mots1) {
            for (String mot2 : mots2) {
                if (mot1.equalsIgnoreCase(mot2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void afficherStatistiques(List<String[]> couples) {
        System.out.println("=== Statistiques du générateur ===");
        System.out.println("Nombre de couples générés: " + couples.size());
    }
}