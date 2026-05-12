package GenerateurDeCandidats.GenerateurParLongueur;
import GenerateurDeCandidats.GenerateurDeCandidats;
import java.util.List;
import nom.Nom;
import java.util.HashMap;
import java.util.ArrayList;
import configuration.Configuration;

public class GenerateurParLongueurSansIndex implements GenerateurDeCandidats{
        public HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir) {
            HashMap<Nom, List<Nom>> listeNoirOptimisee = new HashMap<>();
            Configuration config = new Configuration();
            for(Nom nomClient: listeClients){
                for(Nom nomNoir: listeNoir){
                    boolean nomNoirAccepte=false;
                    int longNomClient=nomClient.getName().length();
                    int longNomNoir=nomNoir.getName().length();
                    int diff=Math.abs(longNomClient-longNomNoir);
                    if (config.toleranceGenerateurestEntiere()){
                        int tolerance=config.getToleranceEntiere();
                        if (diff<=tolerance){
                            nomNoirAccepte=true;
                        }
                    }
                    if(config.toleranceGenerateurestPercentage()){
                        double tolerance=config.getTolerancePourcentage();
                        double pourcentageDiff=(double)diff/longNomClient;
                        if (pourcentageDiff<=tolerance){
                            nomNoirAccepte=true;    
                        }
                    }
                        if (nomNoirAccepte) {
                            if (!listeNoirOptimisee.containsKey(nomClient)){
                                listeNoirOptimisee.put(nomClient, new ArrayList<>());
                        }
                        listeNoirOptimisee.get(nomClient).add(nomNoir);
                    }



                    

            }


            
        }  return listeNoirOptimisee;   
    }
}
    


