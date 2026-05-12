package GenerateurDeCandidats.GenerateurParSyllabes;

import GenerateurDeCandidats.GenerateurDeCandidats;
import configuration.Configuration;
import nom.Nom;
import pretraiteurs.DecomposeurSylabe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateurParSyllabesAvecIndex implements GenerateurDeCandidats {
    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
        Configuration config = new Configuration();
        DecomposeurSylabe decomposeur = new DecomposeurSylabe();
        HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();
        HashMap<Integer, List<Nom>> listeIndexee = new HashMap<>();
        for (Nom nomNoir : listeNoir) {
            decomposeur.pretraiter(nomNoir);
            int nbSyllabesPep = nomNoir.getNomPretraite().size();
            listeIndexee.computeIfAbsent(nbSyllabesPep, k -> new ArrayList<>()).add(nomNoir);
        }
        for (Nom client : listeClients) {
            decomposeur.pretraiter(client);
            List<String> syllabesClient = client.getNomPretraite();
            int nbSyllabesClient = syllabesClient.size();
            int limiteInferieure;
            int limiteSuperieure;
            if (config.toleranceGenerateurestEntiere()) {
                int tolerance = config.getToleranceEntiere();
                limiteInferieure = nbSyllabesClient - tolerance;
                limiteSuperieure = nbSyllabesClient + tolerance;
            } else {
                double tolerance = config.getTolerancePourcentage();
                double ecart = nbSyllabesClient * tolerance;
                limiteInferieure = (int) Math.round(nbSyllabesClient - ecart);
                limiteSuperieure = (int) Math.round(nbSyllabesClient + ecart);
            }
            List<Nom> nompot = new ArrayList<>();
            for (int i = Math.max(limiteInferieure, 0); i <= limiteSuperieure; i++) {
                List<Nom> liste = listeIndexee.get(i);
                if (liste == null) {
                    continue;
                }
                for (Nom nomNoir : liste) {
                    List<String> syllabesPep = nomNoir.getNomPretraite();
                    int communes = syllabesCommunes(syllabesClient, syllabesPep);
                    boolean accepte = false;

                    if (config.toleranceGenerateurestEntiere()) {
                        if (communes >= config.getToleranceEntiere()) {
                            accepte = true;
                        }
                    } else {
                        double pourcentage = (double) communes / nbSyllabesClient;
                        if (pourcentage >= config.getTolerancePourcentage()) {
                            accepte = true;
                        }
                    }
                    if (accepte) {
                        candidats.add(nomNoir);
                    }
                }
            }
            if (!candidats.isEmpty()) {
                listeNoirOptimisee.put(client, candidats);
            }
        }
        return listeNoirOptimisee;
    }
    private int syllabesCommunes(List<String> s1, List<String> s2) {
        int nb = 0;
        for (String syl : s1) {
            if (s2.contains(syl)) {
                nb++;
            }
        }
        return nb;
    }
}
