package GenerateurDeCandidats.GenerateurParMots;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import nom.Nom;

public class GenerateurParMotsSansIndex { // Plus d'extension

    public Map<Nom, List<Nom>> genererCorrespondances(List<Nom> listeClient, List<Nom> listeNoire) {
        Map<Nom, List<Nom>> resultat = new HashMap<>();

        if (listeClient == null || listeNoire == null || listeClient.isEmpty() || listeNoire.isEmpty()) {
            return resultat;
        }

        for (Nom client : listeClient) {
            if (client == null) continue;
            List<Nom> noirsCorrespondants = new ArrayList<>();
            for (Nom noir : listeNoire) {
                if (noir == null) continue;
                if (aUnMotEnCommun(client, noir)) {
                    noirsCorrespondants.add(noir);
                }
            }
            if (!noirsCorrespondants.isEmpty()) {
                resultat.put(client, noirsCorrespondants);
            }
        }

        return resultat;
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

    private List<String> decomposerEnMots(String texte) {
        List<String> mots = new ArrayList<>();
        // Découpage simple par espaces et ponctuation
        String[] parties = texte.split("[\\s,;:!?.-]+");
        for (String partie : parties) {
            if (!partie.isEmpty()) {
                mots.add(partie);
            }
        }
        return mots;
    }
}