package GenerateurDeCandidats;
import java.util.List;
import nom.Nom;
import java.util.HashMap;

public interface GenerateurDeCandidats {
    HashMap<Nom, List<Nom>> genererCandidats(List<Nom> listeClients, List<Nom> listeNoir);
    
}