class Icerik{
  String ad;
  String tur;

  public Icerik(String ad, String tur) {
    this.ad = ad;
    this.tur =tur;}
}

class Dugum{
  Icerik veri;
  Dugum sonraki;

  public Dugum(Icerik veri){
    this.veri = veri;
    this.sonraki= null;
  }
}


class IzlemePlaniKuyrugu{
  private Dugum bas;
  private Dugum son;

public IzlemePLanKuyrugu(){
  this.bas = null;
  this.son= null;
}
  public boolean bosMu(){
    return bas == null;
  }
  public void sirayaEkle(string ad,String tur){
    Icerik yeniIcerik = new Icerik(ad,tur);
    Dugum yeniDugum= new Dugum(yeniIcerik);
      if  (bosMu()) {
      bas =yeniDugum;
      son = yeniDugum;
    }
    else{
      son.sonraki= yeniDugum;
      son = yeniDugum;
    }
    System.out.println("-> \"" +ad + "\" izleme planına eklendi.");
      }

  public void SiradanCikar(){
    if (bosMu()){
      System.out.println("uyarı: izleme planı zaten boş!");
      return;
    }
    System.out.println(<- \"" +bas.veri.ad + "\" izlendi/ plandan çıkarıldı ve sıradan düşürüldü");
    bas = bas.sonraki;
    if(bas== null){
      son= null;
    }
  }
  public void siradakinigoster(){
    if (bosMu()){
      System.out.println("izleme planında şu anda hiç içerik yok.");
    } 
    else{
      System.out.println("sıradaki izlenecek içerik: "+bas.veri.ad +" ( " +bas.veri.tur +")");
    }
    public void planiGoster(){
      if(bosMu()){
        System.out.println("\n ~~ Güncel İzleme Planı (Kuyruk) ~~");
        Dugum gecici =bas;
        int sira =1;
        while (gecici != null){
          System.out.priintln(sira +"." +gecici.veri.ad +"(" +gecici.veri.tur +")");
          gecici =gecici.sonraki;
          sira ++;
        }
        System.out.println("-----------");
          
      }
    }
    public class Main {
      public static void main (String[] args) {
        IzlemePlaniKuyrugu izlemPLani = new izlemePlaniKuyrugu();

        System.out.println(" ~~ İZLEME PLANI (KUYRUK) TESTİ~~");
        
        izlemePLani.sirayaEkle("Inception " ,"Film");
        izlemePLani.sirayaEkle("Breaking Bad " ,"dizi");
        izlemePLani.sirayaEkle("Interstellar " ,"Film");

        
        izlemePlani.planiGoster();
        izlemePlani.siradakiniGoster();
        izlemePlani.siradanCikar();
        izlemePlani.planiGoster();
        izlemePlani.siradakiniGoster();
      }
    }}
        
