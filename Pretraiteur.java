import java.util.*;
public class Pretraiteur {
    
}

class TransformerMiniscule extends Pretraiteur {
    public String transformer(Nom nom) {
        String pretraite = nom.getName().toLowerCase();
    
        return pretraite;
    }
}

// class supprimerEspace extends Pretraiteur {
//     public String transformer(Nom nom) {
//         String pretraite = nom.getName().replaceAll("\\s+", "");
//         return pretraite;
//     }
// }

class supprimerAccent extends Pretraiteur {
    public String transformer(Nom nom) {
        String pretraite = nom.getName().replaceAll("[éèêë]", "e")
                                        .replaceAll("[àâä]", "a")
                                        .replaceAll("[îï]", "i")
                                        .replaceAll("[ôö]", "o")
                                        .replaceAll("[ùûü]", "u")
                                        .replaceAll("[ç]", "c");

        return pretraite;
    }
}

class decouperNom extends Pretraiteur {

    public List<String> transformer(Nom nom) {
        List<String> res = new ArrayList<>();

        String[] t = nom.getName().trim().split("\\s+");

        String prenom = t[0];
        String nomFamille = "";

        if (t.length > 1) {
            for (int i = 1; i < t.length; i++) {
                nomFamille += t[i] + " ";
            }
            nomFamille = nomFamille.trim();
        }

        res.add(prenom);

        if (!nomFamille.isEmpty()) {
            res.add(nomFamille);
            res.add(prenom + " " + nomFamille);
            res.add(nomFamille + " " + prenom);
        }

        return res;
    }
}

class SupprimerPonctuation extends Pretraiteur {
    public String transformer(Nom nom) {
        String pretraite = nom.getName().replaceAll("[.!?;:,]", "");
        return pretraite;
    }
}