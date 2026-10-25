public class YiginTest {
        private String baslik;
        private int toplamBolum;
        private int izlenenBolum;
        private boolean izlendiMi;

        private Durum tepe;

        public static class Durum {
          private int izlenenBolum;
          private boolean izlendiMi;
          private Durum onceki;

          private Durum (int izlenenBolum, boolean izlendiMi, Durum onceki) {
            this.izlenenBolum = izlenenBolum;
            this.izlendiMi = izlendiMi;
            this.onceki = onceki;
            
          }
        }
        public YiginTakip (String baslik, int toplamBolum){
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
          this.tepe = null;
        }
        private void durumuKaydet(){
          tepe = new Durum(izlenenBolum, izlendiMi, tepe);
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
        public boolean geriAl(){
          if(tepe == null){
            return false;
          }

          izlenenBolum = tepe.izlenenBolum;
          izlendiMi = tepe.izlendiMi;
          tepe = tepe.onceki;

          return true;
        }
        @Override
        public String toString(){
          return baslik + ": " + izlenenBolum + "/" +toplamBolum
            + "bölüm, izlendi mi: " +izlendiMi;
        }
}
