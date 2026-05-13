package comparateurs;

import java.util.List;

import nom.Nom;

public class ComparateurJaroWinkler extends ComparateurDeChaines {
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
                double scoreActuel = jaroWinkler(mot1, mot2);

                if (scoreActuel > scoreMaxPourCeMot) {
                    scoreMaxPourCeMot = scoreActuel;
                }
            }

            sommeScoresMaximaux += scoreMaxPourCeMot;
        }

        return sommeScoresMaximaux / liste1.size();
    }

    private double jaroWinkler(String s1, String s2) {

        int m = 0;
        int t = 0;
        int s1Len = s1.length();
        int s2Len = s2.length();
        int maxDist = Math.max(s1Len, s2Len) / 2 - 1;
        boolean[] s1Matches = new boolean[s1Len];
        boolean[] s2Matches = new boolean[s2Len];
        for (int i = 0; i < s1Len; i++) {
            int start = Math.max(0, i - maxDist);
            int end = Math.min(i + maxDist + 1, s2Len);
            for (int j = start; j < end; j++) {
                if (s2Matches[j])
                    continue;
                if (s1.charAt(i) != s2.charAt(j))
                    continue;
                s1Matches[i] = true;
                s2Matches[j] = true;
                m++;
                break;
            }
        }
        if (m == 0)
            return 0.0;
        int k = 0;
        for (int i = 0; i < s1Len; i++) {
            if (!s1Matches[i])
                continue;
            while (!s2Matches[k])
                k++;
            if (s1.charAt(i) != s2.charAt(k))
                t++;
            k++;
        }
        t /= 2;
        double jaro = ((double) m / s1Len + (double) m / s2Len + (double) (m - t) / m) / 3.0;
        int l = 0;
        for (int i = 0; i < Math.min(4, Math.min(s1Len, s2Len)); i++) {
            if (s1.charAt(i) == s2.charAt(i))
                l++;
            else
                break;
        }
        double winkler = jaro + l * 0.1 * (1 - jaro);
        return winkler;

    }
}
