package GenerateurDeCandidats.GenerateurParLongueur;
import GenerateurDeCandidats.GenerateurDeCandidats;
import java.util.List;
import nom.Nom;
import java.util.HashMap;
import java.util.ArrayList;

public class GenerateurParLongueurSansIndex implements GenerateurDeCandidats{
        public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
            HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();
            boolean nomNoirAccepte=false;
            for(Nom nomClient: listeClients){
                for(Nom nomNoir: listeNoir){
                    int longNomClient=nomClient.getName().length();
                    int longNomNoir=nomNoir.getName().length();
                    int diff=Math.abs(longNomClient-longNomNoir);
                    if (toleranceGenerateurestEntiere()){
                        int tolerance=Configuration.getToleranceGenerateur();
                        if (diff<=tolerance){
                            nomNoirAccepte=true;
                        }
                    }
                    if(toleranceGenerateurestPercentage()){
                        double tolerance=Configuration.getToleranceGenerateur();
                        double pourcentageDiff=(double)diff/longNomClient;
                        if (pourcentageDiff<=tolerance){
                            nomNoirAccepte=true;    
                        }
                    }
                    if (nomNoirAccepte){
                        if (!listeNoirOptimisee.containsKey(nomClient)) {
                            listeNoirOptimisee.put(nomClient, new ArrayList<>());
                        }
                        listeNoirOptimisee.get(nomClient).add(nomNoir);
                    }}}
        return listeNoirOptimisee;


                    

            }


            
        }
    


