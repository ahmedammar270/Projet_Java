package GenerateurDeCandidats.GenerateurParMots;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import nom.Nom;

public class GenerateurParMotsAvecIndex extends GenerateurParMots {

    private Map<String, List<Nom>> indexListeNoire;

    public GenerateurParMotsAvecIndex() {
        this.indexListeNoire = new HashMap<>();
    }

    public Map<Nom, List<Nom>> genererCorrespondances(List<Nom> listeClient, List<Nom> listeNoire) {
        Map<Nom, List<Nom>> resultat = new HashMap<>();

        if (listeClient == null || listeNoire == null || listeClient.isEmpty() || listeNoire.isEmpty()) {
            return resultat;
        }

        construireIndex(listeNoire);

        for (Nom client : listeClient) {
            if (client == null) continue;
            List<Nom> noirsCorrespondants = new ArrayList<>();
            for (String mot : client.getNomPretraite()) {
                String motCle = mot.toLowerCase();
                List<Nom> nomsAvecMot = indexListeNoire.get(motCle);
                if (nomsAvecMot != null) {
                    for (Nom noir : nomsAvecMot) {
                        if (!noirsCorrespondants.contains(noir)) {
                            noirsCorrespondants.add(noir);
                        }
                    }
                }
            }
            if (!noirsCorrespondants.isEmpty()) {
                resultat.put(client, noirsCorrespondants);
            }
        }

        return resultat;
    }

    private void construireIndex(List<Nom> listeNoire) {
        indexListeNoire.clear();
        for (Nom noir : listeNoire) {
            if (noir == null) continue;
            for (String mot : noir.getNomPretraite()) {
                String motCle = mot.toLowerCase();
                
                List<Nom> liste = indexListeNoire.get(motCle);
                if (liste == null) {
                    liste = new ArrayList<>();
                    indexListeNoire.put(motCle, liste);
                }
                liste.add(noir);
            }
        }
    }
}