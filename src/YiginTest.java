public class YiginTest {
        private String baslik;
        private int toplamBolum;
        private int izlenenBolum;
        private boolean izlendiMi;
        private int planSirasi;

        private Durum tepe;

        public static class Durum {
          private String baslik;
          private int toplamBolum;
          private int izlenenBolum;
          private boolean izlendiMi;
          private int planSirasi;      
          private Durum onceki;

          private Durum (String baslik, int toplamBolum, int izlenenBolum, boolean izlendiMi, int planSirasi, Durum onceki) {
            this.baslik = baslik;
            this.toplamBolum = toplamBolum;
            this.izlenenBolum = izlenenBolum;
            this.izlendiMi = izlendiMi;
            this.planSirasi = planSirasi;      
            this.onceki = onceki;
            
          }
        }
        public YiginTest (String baslik, int toplamBolum){
          if(baslik == null || baslik.trim().isEmpty()){
            throw new IllegalArgumentException("Başlık boş olamaz.");
          }
          if(toplamBolum < 0){
            throw new IllegalArgumentException(
              "Toplam bölüm sayısı negatif olamaz."
              );
          }
          this.baslik = baslik;
          this.toplamBolum = toplamBolum;
          this.izlenenBolum = 0;
          this.izlendiMi = false;
          this.planSirasi = 0;
          this.tepe = null;
        }
        private void durumuKaydet(){
          tepe = new Durum(
                  baslik,
                  toplamBolum,
                  izlenenBolum, 
                  izlendiMi, 
                  planSirasi, 
                  tepe);
        }
        public boolean bolumİzle(){
          if(izlenenBolum >= toplamBolum){
            return false;
          }
          durumuKaydet();
          izlenenBolum++;
          return true;
        }
        public void izlendiDurumunuDegistir(){
          durumuKaydet();
          izlendiMi = !izlendiMi;
        }
        public void planSirasiniDegistir(int yeniSira) {
                if (yeniSira < 0) {
                        throw new IllegalArgumentException("Plan sırası negatif olamaz.");
                }
                if (yeniSira == planSirasi) {
                        return;
                }    
                durumuKaydet();
                planSirasi = yeniSira;
        }
        public void izlenenBolumSayisiniGuncelle(int yeniSayi) {
                if (yeniSayi < 0 || yeniSayi > toplamBolum) {
                        throw new IllegalArgumentException(
                                "İzlenen bölüm sayısı 0 ile toplam bölüm sayısı arasında olmalı." 
                        );
                }

                if (yeniSayi == izlenenBolum) {
                        return;
                }

                durumuKaydet();
                izlenenBolum = yeniSayi;
        }
        public void icerikBilgileriniGuncelle(
                String yeniBaslik, int yeniToplamBolum) {
           if (yeniBaslik == null || yeniBaslik.trim().isEmpty()) {
                   throw new IllegalArgumentException("Başlık boş olamaz.");
           }
                if (yeniToplamBolum < 0) {
                        throw new IllegalArgumentException(
                                "Toplam bölüm sayısı negatif olamaz."
                                );
                }
                if (yeniToplamBolum < izlenenBolum) {
                        throw new IllegalArgumentException(
                                "Toplam bölüm sayısı izlenen bölüm sayısından az olamaz."
                                );
                }

                if (yeniBaslik.equals(baslik)
                    && yeniToplamBolum == toplamBolum) {
                        return;
                }

                durumuKaydet();
                baslik = yeniBaslik;
                toplamBolum = yeniToplamBolum;
        }
        
        public boolean geriAl(){
          if(tepe == null){
            return false;
          }

          baslik = tepe.baslik;
          toplamBolum = tepe.toplamBolum;
          izlenenBolum = tepe.izlenenBolum;
          izlendiMi = tepe.izlendiMi;
          planSirasi = tepe.planSirasi;
          tepe = tepe.onceki;      

          return true;
        }
        @Override
        public String toString(){
          return baslik + ": " + izlenenBolum + "/" +toplamBolum
            + " bölüm, izlendi mi: " +izlendiMi
            + ", plan sırası: " + planSirasi;      
        }
}
