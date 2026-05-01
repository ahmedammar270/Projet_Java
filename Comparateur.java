public abstract class Comparateur {}


class ComparateurExact extends Comparateur {
    
    public boolean comparer(Nom nom1, Nom nom2) {
        return nom1.getNomPretraite().equals(nom2.getNomPretraite());
    }
    
}
class Distance extends Comparateur {
   public double calculerDistance(Nom nom1, Nom nom2) {
    String s1 = nom1.getNomPretraite().isEmpty() ? "" : nom1.getNomPretraite().get(0);
    String s2 = nom2.getNomPretraite().isEmpty() ? "" : nom2.getNomPretraite().get(0);

    if (s1.isEmpty()) return s2.length();
    if (s2.isEmpty()) return s1.length();

    int len1 = s1.length();
    int len2 = s2.length();

    int[][] dp = new int[len1 + 1][len2 + 1];

    for (int i = 0; i <= len1; i++) dp[i][0] = i;
    for (int j = 0; j <= len2; j++) dp[0][j] = j;

    for (int i = 1; i <= len1; i++) {
        for (int j = 1; j <= len2; j++) {
            int cout = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
            dp[i][j] = Math.min(
                Math.min(dp[i - 1][j] + 1,      // suppression
                         dp[i][j - 1] + 1),      // insertion
                         dp[i - 1][j - 1] + cout // substitution
            );
        }
    }

    return (double) dp[len1][len2];
}
}

class Similarite extends Comparateur {
}
