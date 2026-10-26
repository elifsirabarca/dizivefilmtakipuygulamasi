// Siralama ve sorgulama icin kucuk test. Calistir
public class SiralamaSorguTest {
    private static int gecen = 0;
    private static int kalan = 0;

    private static void kontrol(boolean sart, String mesaj) {
        if (sart) { gecen++; System.out.println("[GECTI] " + mesaj); }
        else      { kalan++; System.out.println("[KALDI] " + mesaj); }
    }

    private static String adlar(Icerik[] dizi) {
        String s = "";
        for (int i = 0; i < dizi.length; i++) {
            if (i > 0) s += ",";
            s += dizi[i].getAd();
        }
        return s;
    }

    private static Icerik[] ornekVeri() {
        Film inception = new Film(1, "Inception", "Bilim kurgu", 148);
        inception.izlendiIsaretle(true);                         

        Dizi dark = new Dizi(2, "Dark", "Gerilim", 26, 55);      
        dark.bolumIzle(10);                                      

        Film avatar = new Film(3, "Avatar", "Bilim kurgu", 162); 

        Dizi friends = new Dizi(4, "Friends", "Komedi", 10, 22); 
        friends.bolumIzle(5);                                    

        return new Icerik[] { inception, dark, avatar, friends };
    }

    public static void main(String[] args) {
        Icerik[] veri = ornekVeri();

        // Siralama
        kontrol(adlar(IcerikSiralayici.sirala(veri, SiralamaKriteri.AD)).equals("Avatar,Dark,Friends,Inception"),
                "AD'a gore artan siralama");
        kontrol(adlar(IcerikSiralayici.sirala(veri, SiralamaKriteri.SURE)).equals("Inception,Avatar,Friends,Dark"),
                "Toplam sureye gore artan siralama");
        kontrol(adlar(IcerikSiralayici.sirala(veri, SiralamaKriteri.TAMAMLANMA)).equals("Avatar,Dark,Friends,Inception"),
                "Tamamlanma oranina gore artan siralama");
        kontrol(adlar(IcerikSiralayici.sirala(veri, SiralamaKriteri.SURE, false)).equals("Dark,Friends,Avatar,Inception"),
                "Toplam sureye gore azalan siralama");
        kontrol(adlar(veri).equals("Inception,Dark,Avatar,Friends"),
                "Siralama kaynak diziyi degistirmez");
        kontrol(IcerikSiralayici.sirala(new Icerik[0], SiralamaKriteri.AD).length == 0,
                "Bos dizi siralanabilir");

        // Sorgulama
        kontrol(adlar(IcerikSorgulayici.sorgula(veri, new SorguFiltresi().turEsit("bilim kurgu"))).equals("Inception,Avatar"),
                "Ture gore arama (buyuk/kucuk harf fark etmez)");
        kontrol(adlar(IcerikSorgulayici.sorgula(veri, new SorguFiltresi().durumEsit(IzlemeDurumu.IZLENIYOR))).equals("Dark,Friends"),
                "Izleme durumuna gore arama");
        kontrol(adlar(IcerikSorgulayici.sorgula(veri, new SorguFiltresi().adIcerir("dar"))).equals("Dark"),
                "Ad icinde gecen metne gore arama");
        kontrol(adlar(IcerikSorgulayici.sorgula(veri, new SorguFiltresi().maxKalanSure(200))).equals("Inception,Avatar,Friends"),
                "Ayrilan sureye (200 dk) uyan icerikler");
        kontrol(adlar(IcerikSorgulayici.sorgula(veri,
                new SorguFiltresi().turEsit("Bilim kurgu").maxKalanSure(100))).equals("Inception"),
                "Birden fazla kriter birlikte");
        kontrol(IcerikSorgulayici.sorgula(veri, null).length == 4,
                "Filtre null ise hepsi doner");

      
        Icerik[] sirali = IcerikSiralayici.sirala(veri, SiralamaKriteri.AD);
        kontrol(IcerikSorgulayici.adaGoreIkiliArama(sirali, "friends") == 2, "Ikili arama: var olan icerik");
        kontrol(IcerikSorgulayici.adaGoreIkiliArama(sirali, "Yok") == -1, "Ikili arama: olmayan icerik");

        System.out.println();
        System.out.println("Sonuc: " + gecen + " gecti, " + kalan + " kaldi.");
        if (kalan > 0) System.exit(1);
    }
}
