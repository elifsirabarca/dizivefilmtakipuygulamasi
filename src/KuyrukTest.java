

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

public IzlemePlaniKuyrugu(){
  this.bas = null;
  this.son= null;
}
  public boolean bosMu(){
    return bas == null;
  }
  public void sirayaEkle(Icerik icerik){
    
    Dugum yeniDugum= new Dugum(icerik);
      if  (bosMu()) {
      bas =yeniDugum;
      son = yeniDugum;
    }
    else{
      son.sonraki= yeniDugum;
      son = yeniDugum;
    }
    System.out.println("-> \"" +icerik.getAd() + "\" izleme planına eklendi.");
      }

  public void siradanCikar(){
    if (bosMu()){
      System.out.println("uyarı: izleme planı zaten boş!");
      return;
    }
    System.out.println("<- \"" +bas.veri.getAd() + "\" izlendi/ plandan çıkarıldı ve sıradan düşürüldü");
    bas = bas.sonraki;
    if(bas== null){
      son= null;
    }
  }
  public void siradakiniGoster(){
    if (bosMu()){
      System.out.println("izleme planında şu anda hiç içerik yok.");
    } 
    else{
      System.out.println("sıradaki izlenecek içerik: "+bas.veri.getAd() +" ( " +bas.veri.getTur() +")");
    }
  }
    public void planiGoster(){
      if(!bosMu()){
        System.out.println("\n ~~ Güncel İzleme Planı (Kuyruk) ~~");
        Dugum gecici =bas;
        int sira =1;
        while (gecici != null){
          System.out.println(sira +"." +gecici.veri.getAd() +"(" +gecici.veri.getTur() +")");
          gecici =gecici.sonraki;
          sira ++;
        }
        System.out.println("-----------");
          
      }
    }
    public class KuyrukTest {
      public static void main (String[] args) {
        IzlemePlaniKuyrugu izlemePlani = new IzlemePlaniKuyrugu();

        System.out.println(" ~~ İZLEME PLANI (KUYRUK) TESTİ~~");
        
        izlemePlani.sirayaEkle(new Film(1, "Inception " ,"Film", 148, "Christopher Nolan"));
        izlemePlani.sirayaEkle(new Dizi(2, "Breaking Bad " ,"Dizi", 47, 62));
        izlemePlani.sirayaEkle(new Film(3, "Interstellar " ,"Film", 169, "Christopher Nolan"));

        
        izlemePlani.planiGoster();
        izlemePlani.siradakiniGoster();
        izlemePlani.siradanCikar();
        izlemePlani.planiGoster();
        izlemePlani.siradakiniGoster();
      }
    }}
        
