import java.util.List;
import java.util.ArrayList;

public class GenerateurSimple implements GenerateurDeCandidats {
    private ComparateursDeNoms comparateur;

    public GenerateurSimple(ComparateursDeNoms comparateur) {
        this.comparateur = comparateur;
    }

    @Override
    public List<Couple> generer(List<Nom> liste1, List<Nom> liste2) {
        List<Couple> result = new ArrayList<>();
        for (Nom nom1 : liste1) {
            for (Nom nom2 : liste2) {
                Comparaison comp = comparateur.comparer(nom1, nom2);
                result.add(new Couple(nom1, nom2, comp.getScore()));
            }
        }
        return result;
    }
}