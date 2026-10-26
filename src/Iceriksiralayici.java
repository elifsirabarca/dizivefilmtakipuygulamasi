public class IcerikSiralayici {

    public static Icerik[] sirala(Icerik[] kaynak, SiralamaKriteri kriter) {
        return sirala(kaynak, kriter, true);
    }

    public static Icerik[] sirala(Icerik[] kaynak, SiralamaKriteri kriter, boolean artan) {
        int n = kaynak.length;
        Icerik[] dizi = new Icerik[n];
        for (int i = 0; i < n; i++) dizi[i] = kaynak[i];   // kopya al, kaynak bozulmasin

        Icerik[] gecici = new Icerik[n];
        mergeSort(dizi, gecici, 0, n - 1, kriter);

        if (!artan) tersCevir(dizi);
        return dizi;
    }

    // a < b ise negatif, esitse 0, a > b ise pozitiftir
    static int karsilastir(Icerik a, Icerik b, SiralamaKriteri kriter) {
        switch (kriter) {
            case AD:
                return a.getAd().compareToIgnoreCase(b.getAd());
            case SURE:
                return Integer.compare(a.toplamSure(), b.toplamSure());
            case TAMAMLANMA:
                return Double.compare(a.tamamlanmaOrani(), b.tamamlanmaOrani());
            default:
                throw new IllegalArgumentException("Bilinmeyen kriter: " + kriter);
        }
    }

    private static void mergeSort(Icerik[] d, Icerik[] gecici, int sol, int sag, SiralamaKriteri k) {
        if (sol >= sag) return;
        int orta = (sol + sag) / 2;
        mergeSort(d, gecici, sol, orta, k);
        mergeSort(d, gecici, orta + 1, sag, k);
        birlestir(d, gecici, sol, orta, sag, k);
    }

    private static void birlestir(Icerik[] d, Icerik[] gecici, int sol, int orta, int sag, SiralamaKriteri k) {
        int i = sol, j = orta + 1, t = sol;
        while (i <= orta && j <= sag) {
            if (karsilastir(d[i], d[j], k) <= 0) gecici[t++] = d[i++];   // <= : kararlilik
            else gecici[t++] = d[j++];
        }
        while (i <= orta) gecici[t++] = d[i++];
        while (j <= sag) gecici[t++] = d[j++];
        for (int x = sol; x <= sag; x++) d[x] = gecici[x];
    }

    private static void tersCevir(Icerik[] d) {
        for (int bas = 0, son = d.length - 1; bas < son; bas++, son--) {
            Icerik tmp = d[bas];
            d[bas] = d[son];
            d[son] = tmp;
        }
    }
}
