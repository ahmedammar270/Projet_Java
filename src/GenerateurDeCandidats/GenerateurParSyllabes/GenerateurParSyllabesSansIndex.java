package GenerateurDeCandidats.GenerateurParSyllabes;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import configuration.Configuration;
import nom.Nom;

import pretraiteurs.DecomposeurSylabe;
import GenerateurDeCandidats.GenerateurDeCandidats;


public class GenerateurParSyllabesSansIndex implements GenerateurDeCandidats {
    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
        HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();
        Configuration config = new Configuration();
        DecomposeurSylabe decomposeur = new DecomposeurSylabe();
        for (Nom nomClient : listeClients) {
            List<Nom> nomsPotentiels = new ArrayList<>();
            decomposeur.pretraiter(nomClient);
            List<String> syllabesClient = nomClient.getNomPretraite();
            int nbSyllabesClient = syllabesClient.size();
            for (Nom nomNoir : listeNoir) {
                decomposeur.pretraiter(nomNoir);
                List<String> syllabesNomNoir = nomNoir.getNomPretraite();
                int communes = syllabesCommunes(syllabesClient, syllabesNomNoir);
                boolean accepte = false;
                if (config.toleranceGenerateurestEntiere()) {
                    int tolerance = config.getToleranceEntiere();
                    if (communes >= tolerance) {
                        accepte = true;
                    }
                }
                if (config.toleranceGenerateurestPercentage()) {
                    double tolerance = config.getTolerancePourcentage();
                    double pourcentage = (double) communes / nbSyllabesClient;
                    if (pourcentage >= tolerance) {
                        accepte = true;
                    }
                }
                if (accepte) {
                    nomsPotentiels.add(nomNoir);
                }
            }
            if (!nomsPotentiels.isEmpty()) {
                listeNoirOptimisee.put(nomClient, nomsPotentiels);
            }
        }
        return listeNoirOptimisee;
    }
    private int syllabesCommunes(
            List<String> s1,
            List<String> s2) {

        int nb = 0;

        for (String syl : s1) {

            if (s2.contains(syl)) {

                nb++;
            }
        }
        return nb;
    }
}