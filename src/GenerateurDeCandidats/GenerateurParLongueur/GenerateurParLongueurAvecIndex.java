package GenerateurDeCandidats.GenerateurParLongueur;
import java.util.List;
import nom.Nom;
import java.util.HashMap;
import java.util.ArrayList;


public class GenerateurParLongueurAvecIndex implements GenerateurDeCandidats{
    public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
        HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();
        HashMap<Integer, List<Nom>> listeIndexee = new HashMap<>();
        for (Nom nomNoir : listeNoir) {
            int longueur = nomNoir.getName().length();
            if (!listeIndexee.containsKey(longueur)) {
                listeIndexee.put(longueur, new ArrayList<>());
            }
            listeIndexee.get(longueur).add(nomNoir);
        }
        for (Nom nomClient : listeClients) {
            boolean nomNoirAccepte = false;
            int longNomClient = nomClient.getName().length();
            
            

            
    
    
}
