package GenerateurDeCandidats.GenerateurParSyllabes;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import configuration.Configuration;
import nom.Nom;
import pretraiteurs.DecomposeurSylabe;
import GenerateurDeCandidats.GenerateurDeCandidats;

public class GenerateurParSyllabesAvecTri implements GenerateurDeCandidats {
    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
        HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();
        Configuration config = new Configuration();
        DecomposeurSylabe decomposeur = new DecomposeurSylabe();

        for (Nom nomNoir : listeNoir) {
            decomposeur.pretraiter(nomNoir);
        }
        Collections.sort(listeNoir,(n1, n2) ->nbSyllabes(n1)- nbSyllabes(n2));
        for (Nom client : listeClients) {
            List<Nom> nomsPotentiels = new ArrayList<>();
            decomposeur.pretraiter(client);
            List<String> syllabesClient = client.getNomPretraite();
            int nbSyllabesClient = syllabesClient.size();
            int limiteInferieure;
            int limiteSuperieure;
            if (config.toleranceGenerateurestEntiere()) {
                int tolerance = config.getToleranceEntiere();
                limiteInferieure = nbSyllabesClient - tolerance;
                limiteSuperieure = nbSyllabesClient + tolerance;
            } 
            else {
                double tolerance = config.getTolerancePourcentage();
                double ecart = nbSyllabesClient * tolerance;
                limiteInferieure = (int) Math.round(nbSyllabesClient - ecart);
                limiteSuperieure = (int) Math.round(nbSyllabesClient + ecart);
            }

            for (Nom nomNoir : listeNoir) {
                List<String> syllabesNomNoir = nomNoir.getNomPretraite();
                int nbSyllabesNomNoir = syllabesNomNoir.size();

                if (nbSyllabesNomNoir > limiteSuperieure) {
                    break;
                }
                if (nbSyllabesNomNoir >= limiteInferieure) {
                    int communes = syllabesCommunes(syllabesClient, syllabesNomNoir);
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
                        nomsPotentiels.add(nomNoir);
                    }
                }
            }
            if (!nomsPotentiels.isEmpty()) {
                listeNoirOptimisee.put(client, nomsPotentiels);
            }
        }
        return listeNoirOptimisee;
    }
    private int nbSyllabes(Nom nom) {
        return nom.getNomPretraite().size();
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
