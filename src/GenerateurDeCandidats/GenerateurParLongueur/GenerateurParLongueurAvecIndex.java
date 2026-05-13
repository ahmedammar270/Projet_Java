package GenerateurDeCandidats.GenerateurParLongueur;

import GenerateurDeCandidats.GenerateurDeCandidats;
import java.util.List;
import nom.Nom;
import java.util.HashMap;
import java.util.ArrayList;
import configuration.Configuration;

public class GenerateurParLongueurAvecIndex implements GenerateurDeCandidats {

    private Configuration config;

    /** Constructeur par défaut : utilise la configuration standard */
    public GenerateurParLongueurAvecIndex() {
        this.config = new Configuration();
    }

    /** Constructeur avec configuration externe (permet d'adapter la tolérance) */
    public GenerateurParLongueurAvecIndex(Configuration config) {
        this.config = config;
    }

    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
        HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();

        // Construction de l'index : longueur -> liste de noms
        HashMap<Integer, List<Nom>> listeIndexee = new HashMap<>();
        for (Nom nomNoir : listeNoir) {
            int longueur = nomNoir.getNomPretraite().get(0).length();
            if (!listeIndexee.containsKey(longueur)) {
                listeIndexee.put(longueur, new ArrayList<>());
            }
            listeIndexee.get(longueur).add(nomNoir);
        }

        for (Nom nomClient : listeClients) {
            int longNomClient = nomClient.getNomPretraite().get(0).length();
            List<Nom> nomsPotentiels = new ArrayList<>();

            int limiteInferieure;
            int limiteSuperieure;

            if (config.toleranceGenerateurestEntiere()) {
                int tolerance = config.getToleranceEntiere();
                limiteInferieure = longNomClient - tolerance;
                limiteSuperieure = longNomClient + tolerance;
            } else {
                double tolerance = config.getTolerancePourcentage();
                double toleranceLongueur = longNomClient * tolerance;
                limiteInferieure = (int) Math.round(longNomClient - toleranceLongueur);
                limiteSuperieure = (int) Math.round(longNomClient + toleranceLongueur);
            }

            // S'assurer que la limite inférieure est au moins 1
            limiteInferieure = Math.max(1, limiteInferieure);

            for (int i = limiteInferieure; i <= limiteSuperieure; i++) {
                List<Nom> liste = listeIndexee.get(i);
                if (liste != null) {
                    nomsPotentiels.addAll(liste);
                }
            }

            if (!nomsPotentiels.isEmpty()) {
                if (!listeNoirOptimisee.containsKey(nomClient)) {
                    listeNoirOptimisee.put(nomClient, new ArrayList<>());
                }
                listeNoirOptimisee.get(nomClient).addAll(nomsPotentiels);
            }
        }
        return listeNoirOptimisee;
    }
}