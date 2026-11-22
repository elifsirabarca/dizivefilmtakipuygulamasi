public class YiginTest {
    private final Icerik icerik;

    private Durum tepe;

    private static class Durum {
        private final String ad;
        private final String tur;
        private final int sure;
        private final boolean izlendiMi;
        private final double puan;
        private final IzlemeDurumu izlemeDurumu;
        private final Integer toplamBolum;
        private final Integer izlenenBolum;
        private final Durum onceki;

        private Durum(Icerik icerik, Durum onceki) {
            this.ad = icerik.getAd();
            this.tur = icerik.getTur();
            this.sure = icerik.getSure();
            this.izlendiMi = icerik.isIzlendiMi();
            this.puan = icerik.getPuan();
            this.izlemeDurumu = icerik.getDurum();

            if (icerik instanceof Dizi) {
                Dizi dizi = (Dizi) icerik;
                this.toplamBolum = dizi.getToplamBolum();
                this.izlenenBolum = dizi.getIzlenenBolum();
            } else {
                this.toplamBolum = null;
                this.izlenenBolum = null;
            }

            this.onceki = onceki;
        }
    }

    public YiginTest(Icerik icerik) {
        if (icerik == null) {
            throw new IllegalArgumentException("İçerik null olamaz.");
        }

        this.icerik = icerik;
        this.tepe = null;
    }

    public void durumuKaydet() {
        tepe = new Durum(icerik, tepe);
    }

    public boolean geriAl() {
        if (tepe == null) {
            return false;
        }

        icerik.durumuGeriYukle(
                tepe.ad,
                tepe.tur,
                tepe.sure,
                tepe.izlendiMi,
                tepe.puan,
                tepe.izlemeDurumu
        );

        if (icerik instanceof Dizi && tepe.toplamBolum != null) {
            ((Dizi) icerik).bolumDurumunuGeriYukle(
                    tepe.toplamBolum,
                    tepe.izlenenBolum
            );
        }

        tepe = tepe.onceki;
        return true;
    }

    public void gecmisiYazdir() {
        if (tepe == null) {
            System.out.println("Geri alınacak değişiklik yok.");
            return;
        }

        Durum gecici = tepe;
        int sira = 1;

        System.out.println("Değişiklik geçmişi (en son değişiklik önce):");

        while (gecici != null) {
            String bolumBilgisi = "";

            if (gecici.izlenenBolum != null) {
                bolumBilgisi = ", bölüm: "
                        + gecici.izlenenBolum
                        + "/"
                        + gecici.toplamBolum;
            }

            System.out.println(
                    sira + ". " + gecici.ad
                    + ", izlendi: " + gecici.izlendiMi
                    + ", durum: " + gecici.izlemeDurumu
                    + ", puan: " + gecici.puan
                    + bolumBilgisi
            );

            gecici = gecici.onceki;
            sira++;
        }
    }
}
