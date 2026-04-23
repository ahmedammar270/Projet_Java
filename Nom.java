public class Nom {
    private String name;
    private String id;
    private String nomPretraite;

    
    public Nom(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setNomPretraite(String nomPretraite) {
        this.nomPretraite = nomPretraite;
    }
    public String getNomPretraite() {
        return nomPretraite;
    }   
}

