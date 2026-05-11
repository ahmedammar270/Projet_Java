 public class SlectionneurTaille {
     public void selectionner(Nom nom1, Nom nom2) {
         int taille1 = nom1.getNomPretraite().size();
         int taille2 = nom2.getNomPretraite().size();

         if (taille1 == taille2) {
             for (mot1 : nom1.getNomPretraite()) {
                 for (mot2 : nom2.getNomPretraite()) {
                     if (mot1.equals(mot2)) {
                         return; // Les deux noms sont de même taille et ont au moins un mot en commun
                    }
                 }
             }
         }
     }
 }
