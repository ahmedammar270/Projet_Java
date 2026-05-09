package nom;
import java.util.ArrayList;
import java.util.List;

public class Nom {
    private String name;
    private String id;
    private List<String> nomPretraite = new ArrayList<>();

    
    public Nom(String name, String id) {
        this.name = name;
        this.id = id;
        this.nomPretraite.add(name);
    }

    public String getName() {
        return name;
    }
    public String getId(){
        return id;
    }
    public void setNomPretraite(List<String> nomPretraite) {
        this.nomPretraite = nomPretraite;
    }
    public List<String> getNomPretraite() {
        return nomPretraite;
    }   
}

