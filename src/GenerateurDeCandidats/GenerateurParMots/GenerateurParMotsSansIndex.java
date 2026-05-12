package GenerateurDeCandidats.GenerateurParMots;

import java.util.List;
import java.util.ArrayList;
import nom.Nom;
import nom.Couple;

public class GenerateurParMotsSansIndex extends GenerateurParMots {

    @Override
    public List<Couple> genererCouples(List<Nom> liste1, List<Nom> liste2) {
        List<Couple> couples = new ArrayList<>();

        if (liste1 == null || liste2 == null || liste1.isEmpty() || liste2.isEmpty()) {
            return couples;
        }

        for (Nom nom1 : liste1) {
            for (Nom nom2 : liste2) {
                if (nom1 == null || nom2 == null) {
                    continue;
                }

                if (aUnMotEnCommun(nom1, nom2)) {
                    couples.add(new Couple(nom1, nom2, 0.0));
                }
            }
        }

        return couples;
    }

    private boolean aUnMotEnCommun(Nom nom1, Nom nom2) {
        if (nom1 == null || nom2 == null) {
            return false;
        }

        String texte1 = nom1.getNomComplet();
        String texte2 = nom2.getNomComplet();

        if (texte1 == null || texte2 == null || texte1.isEmpty() || texte2.isEmpty()) {
            return false;
        }

        List<String> mots1 = decomposerEnMots(texte1);
        List<String> mots2 = decomposerEnMots(texte2);

        for (String mot1 : mots1) {
            for (String mot2 : mots2) {
                if (mot1.equalsIgnoreCase(mot2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void afficherStatistiques(List<Couple> couples) {
        System.out.println("=== Statistiques du générateur ===");
        System.out.println("Nombre de couples générés: " + couples.size());
    }
}
