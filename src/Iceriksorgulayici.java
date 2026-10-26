public class IcerikSorgulayici {

    public static Icerik[] sorgula(Icerik[] kaynak, SorguFiltresi filtre) {
        // 1. tur: kac tane uyuyor say
        int say = 0;
        for (int i = 0; i < kaynak.length; i++) {
            if (filtre == null || filtre.uyuyorMu(kaynak[i])) say++;
        }
        // 2. tur: tam boyutta dizi acip doldur
        Icerik[] sonuc = new Icerik[say];
        int k = 0;
        for (int i = 0; i < kaynak.length; i++) {
            if (filtre == null || filtre.uyuyorMu(kaynak[i])) sonuc[k++] = kaynak[i];
        }
        return sonuc;
    }

    public static int adaGoreIkiliArama(Icerik[] adaGoreSirali, String ad) {
        int sol = 0, sag = adaGoreSirali.length - 1;
        while (sol <= sag) {
            int orta = (sol + sag) / 2;
            int sonuc = adaGoreSirali[orta].getAd().compareToIgnoreCase(ad);
            if (sonuc == 0) return orta;
            if (sonuc < 0) sol = orta + 1;
            else sag = orta - 1;
        }
        return -1;
    }
}
