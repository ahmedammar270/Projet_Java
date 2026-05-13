package GenerateurDeCandidats.GenerateurParMots;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import nom.Nom;

public class GenerateurParMotsSansIndex extends GenerateurParMots {
    @Override
    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClient, List<Nom> listeNoire) {
        HashMap<Nom, List<Nom>> resultat = new HashMap<>();

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
        if (nom1 == null || nom2 == null) return false;

        
        List<String> mots1 = nom1.getNomPretraite();
        List<String> mots2 = nom2.getNomPretraite();

        if (mots1 == null || mots2 == null || mots1.isEmpty() || mots2.isEmpty()) {
            return false;
        }

        for (String mot1 : mots1) {
            for (String mot2 : mots2) {
                if (mot1.equalsIgnoreCase(mot2)) {
                    return true;
                }
            }
        }
        return false;
    }
}