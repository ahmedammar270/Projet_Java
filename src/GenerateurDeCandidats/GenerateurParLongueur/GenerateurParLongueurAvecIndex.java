package GenerateurDeCandidats.GenerateurParLongueur;

import GenerateurDeCandidats.GenerateurDeCandidats;
import java.util.List;
import nom.Nom;
import java.util.HashMap;
import java.util.ArrayList;
import configuration.Configuration;

public class GenerateurParLongueurAvecIndex implements GenerateurDeCandidats {
    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
        Configuration config = new Configuration();
        HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();
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

            if (config.toleranceGenerateurestEntiere()) {
                int tolerance = config.getToleranceEntiere();
                for (int i = longNomClient - tolerance; i <= longNomClient + tolerance; i++) {
                    List<Nom> liste = listeIndexee.get(i);
                    if (liste != null) {
                        nomsPotentiels.addAll(liste);
                    }
                }
            }
            if (config.toleranceGenerateurestPercentage()) {
                double tolerance = config.getTolerancePourcentage();
                double toleranceLongueur = longNomClient * tolerance;
                int limiteInferieure = (int) Math.round(longNomClient - toleranceLongueur);
                int limiteSuperieure = (int) Math.round(longNomClient + toleranceLongueur);
                for (int i = limiteInferieure; i <= limiteSuperieure; i++) {
                    List<Nom> liste = listeIndexee.get(i);
                    if (liste != null) {
                        nomsPotentiels.addAll(liste);
                    }
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

