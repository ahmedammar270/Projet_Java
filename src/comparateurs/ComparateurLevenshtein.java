package comparateurs;
import java.util.List;

import nom.Nom;
public class ComparateurLevenshtein extends ComparateurDeChaines {
    public double comparer(Nom nom1, Nom nom2) {
        List<String> liste1 = nom1.getNomPretraite();
        List<String> liste2 = nom2.getNomPretraite();

        if (liste1.isEmpty() || liste2.isEmpty()) {
            return 0.0;
        }

        double sommeScoresMaximaux = 0.0;

        for (String mot1 : liste1) {
            double scoreMaxPourCeMot = 0.0;


            for (String mot2 : liste2) {
                int distance = calculerDistanceLevenshtein(mot1, mot2);
                

                int maxLength = Math.max(mot1.length(), mot2.length());
                double scoreActuel = (maxLength == 0) ? 1.0 : 1.0 - (double) distance / maxLength;


                if (scoreActuel > scoreMaxPourCeMot) {
                    scoreMaxPourCeMot = scoreActuel;
                }
            }

            sommeScoresMaximaux += scoreMaxPourCeMot;
        }

        return sommeScoresMaximaux / liste1.size();
    }
    private int calculerDistanceLevenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[s1.length()][s2.length()];
    }
    
}
