package GenerateurDeCandidats.GenerateurParMots;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import nom.Nom;


public class GenerateurParMotsAvecTri extends GenerateurParMots {

    public GenerateurParMotsAvecTri() {
    }

    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClient, List<Nom> listeNoire) {
        HashMap<Nom, List<Nom>> resultat = new HashMap<>();

        if (listeClient == null || listeNoire == null || listeClient.isEmpty() || listeNoire.isEmpty()) {
            return resultat;
        }

        for (Nom client : listeClient) {
            if (client == null) continue;
            
            List<Nom> noirsCorrespondants = new ArrayList<>();
            Map<Nom, Integer> nbMotsCommuns = new HashMap<>();
            
            for (Nom noir : listeNoire) {
                if (noir == null) continue;
                int nbCommuns = compterMotsEnCommun(client, noir);
                if (nbCommuns > 0) {
                    noirsCorrespondants.add(noir);
                    nbMotsCommuns.put(noir, nbCommuns);
                }
            }
            
            if (!noirsCorrespondants.isEmpty()) {
                trierParNbMotsCommuns(noirsCorrespondants, nbMotsCommuns);
                resultat.put(client, noirsCorrespondants);
            }
        }

        return resultat;
    }

    private int compterMotsEnCommun(Nom nom1, Nom nom2) {
        if (nom1 == null || nom2 == null) return 0;

        List<String> mots1 = nom1.getNomPretraite();
        List<String> mots2 = nom2.getNomPretraite();

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

    private void trierParNbMotsCommuns(List<Nom> noirsCorrespondants, Map<Nom, Integer> nbMotsCommuns) {
        Collections.sort(noirsCorrespondants, new Comparator<Nom>() {
            
            public int compare(Nom n1, Nom n2) {
                int nb1 = nbMotsCommuns.getOrDefault(n1, 0);
                int nb2 = nbMotsCommuns.getOrDefault(n2, 0);
                return Integer.compare(nb2, nb1);
            }
        });
    }
}