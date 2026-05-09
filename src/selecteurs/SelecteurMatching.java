package selecteurs;
import java.util.List;
import nom.Couple;

public interface SelecteurMatching {
    abstract List<Couple> selectionner(List<Couple> couples);
}
