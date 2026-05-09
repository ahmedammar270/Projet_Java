package comparateurs;
import nom.Nom;
import nom.Couple;
public class ComparateurJaroWinkler extends ComparateurDeChaines  {
        public double comparer(Nom nom1, Nom nom2) {
        String nom1Pretraite = String.join(" ", nom1.getNomPretraite());
        String nom2Pretraite = String.join(" ", nom2.getNomPretraite());
        
            
            double score = jaroWinkler(nom1Pretraite, nom2Pretraite);
            
            return score;
        }
        
        private double jaroWinkler(String s1, String s2) {
            // Implémentation de l'algorithme Jaro-Winkler
            // Vous pouvez trouver des implémentations en ligne ou les coder vous-même
            // Voici une implémentation simple pour illustrer le concept
            
            int m = 0; // Nombre de caractères correspondants
            int t = 0; // Nombre de transpositions
            int s1Len = s1.length();
            int s2Len = s2.length();    
            int maxDist = Math.max(s1Len, s2Len) / 2 - 1;
            boolean[] s1Matches = new boolean[s1Len];
            boolean[] s2Matches = new boolean[s2Len];
            for (int i = 0; i < s1Len; i++) {
                int start = Math.max(0, i - maxDist);
                int end = Math.min(i + maxDist + 1, s2Len);
                for (int j = start; j < end; j++) {
                    if (s2Matches[j]) continue;
                    if (s1.charAt(i) != s2.charAt(j)) continue;
                    s1Matches[i] = true;
                    s2Matches[j] = true;
                    m++;
                    break;
                }
            }
            if (m == 0) return 0.0;
            int k = 0;  
            for (int i = 0; i < s1Len; i++) {
                if (!s1Matches[i]) continue;
                while (!s2Matches[k]) k++;
                if (s1.charAt(i) != s2.charAt(k)) t++;
                k++;
            }   
            t /= 2;
            double jaro = ((double) m / s1Len + (double) m / s2Len + (double) (m - t) / m) / 3.0;
            int l = 0;  
            for (int i = 0; i < Math.min(4, Math.min(s1Len, s2Len)); i++) {
                if (s1.charAt(i) == s2.charAt(i)) l++;
                else break;
            }   
            double winkler = jaro + l * 0.1 * (1 - jaro);   
            return winkler;

            

        }
}
