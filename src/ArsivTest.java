enum IzlemeDurumu {
  IZLENECEK,
  IZLENIYOR,
  TAMAMLANDI
}
//1.temel medya (içerik) sınıfı
abstract class Icerik {
  private int id; //ID
  private String ad;
  private String tur;
  private int sure;
  private boolean izlendiMi;
  private double puan;
  private IzlemeDurumu durum;

  public Icerik (int id, String ad, String tur, int sure) {
    this.id=id;
    this.ad=ad;
    this.tur=tur;
    this.sure=sure;
    this.izlendiMi=false;
    this.puan=0.0;
    this.durum=IzlemeDurumu.IZLENECEK;
  }
  public int getId() {return id;}
  public String getAd() {return ad;}
  public String getTur() {return tur;}
  public int getSure() {return sure;}
  public boolean isIzlendiMi() {return izlendiMi;}
  public void setIzlendiMi(boolean izlendiMi) {
    this.izlendiMi=izlendiMi;
    this.durum=izlendiMi ? IzlemeDurumu.TAMAMLANDI : IzlemeDurumu.IZLENECEK;
  }
  public double getPuan() {return puan;}
  public void setPuan(double puan) {this.puan=puan;}
  public IzlemeDurumu getDurum() {return durum;}
  public void setDurum(IzlemeDurumu durum) {
    this.durum=durum;
    this.izlendiMi=(durum==IzlemeDurumu.TAMAMLANDI);
  }
   public abstract int toplamSure();
   public abstract int kalanSure();
   public abstract double tamamlanmaOrani();

  @Override
  public String toString() {
    return "id: " + id + " | ad: " + ad + " | tur: " + tur + " | sure: " + sure + " dk | izlendi: " + (izlendiMi ? "evet" : "hayir") + " | puan" + puan;
  }
}
 //2.film sınıfı (medyadan türer)
class Film extends Icerik {
  private String yonetmen;

  public Film(int id, String ad, String tur, int sure, String yonetmen) {
    super(id, ad, tur, sure);
    this.yonetmen=yonetmen;
  }
  public void izlendiIsaretle(boolean izlendi) {
    setIzlendiMi(izlendi);
  }
  public String getYonetmen() {return yonetmen;}
  public void setYonetmen(String yonetmen) {this.yonetmen=yonetmen;}

  @Override 
  public int toplamSure() {
    return getSure();
  }
  @Override
  public int kalanSure() {
    return isIzlendiMi() ? 0 : getSure();
  }
  @Override
  public double tamamlanmaOrani() {
    return isIzlendiMi() ? 100.0 : 0.0;
  }
  @Override
  public String toString() {
    return super.toString() + " | yonetmen: " + yonetmen;
  }
}
//3.dizi sınıfı (medyadan türer)
class Dizi extends Icerik {
  private int toplamBolum;
  private int izlenenBolum;

  public Dizi(int id, String ad, String tur, int sure, int toplamBolum) {
    super(id, ad, tur, sure);
    if (toplamBolum<0) {
      throw new IllegalArgumentException("toplam bolum sayisi negatif olamaz.");
    }
    this.toplamBolum=toplamBolum;
    this.izlenenBolum=0;
  }
  public int getToplamBolum() {return toplamBolum;}
  public int getIzlenenBolum() {return izlenenBolum;}
  public boolean bolumIlerlet() {
    return bolumIzle(1);
  }
  public boolean bolumIzle(int sayi) {
    if (izlenenBolum<toplamBolum) {
      izlenenBolum+=sayi;;
      if (izlenenBolum>=toplamBolum) {
        izlenenBolum=toplamBolum;
        setDurum(IzlemeDurumu.TAMAMLANDI);
      } else {
        setDurum(IzlemeDurumu.IZLENIYOR);
      }
      return true;
    }
    return false;
  }

  @Override
  public int toplamSure() {
    return toplamBolum*getSure();
  }
  @Override
  public int kalanSure() {
    return (toplamBolum-izlenenBolum)*getSure();
  }
  @Override
  public double tamamlanmaOrani() {
    if (toplamBolum==0) return 0.0;
    return((double) izlenenBolum/toplamBolum)*100.0;
  }
  
  @Override
  public String toString() {
    return super.toString() + " | bolum: " + izlenenBolum + "/" + toplamBolum;
  }
}
//4.çift yönlü bağlı liste düğümü (node)
class Node {
  public Icerik veri;
  public Node onceki;
  public Node sonraki;
  public Node(Icerik veri) {
    this.veri=veri;
    this.onceki=null;
    this.sonraki=null;
  }
}
//5.çift yönlü bağlı liste sınıfı (arşiv yönetimi)
class DoublyLinkedList {
  private Node bas;
  private Node son;
  private int boyut;
  public DoublyLinkedList() {
    this.bas=null;
    this.son=null;
    this.boyut=0;
  }
  public void ekle(Icerik media) {
    Node yeniDugum=new Node(media);
    if (bas==null) {
      bas=yeniDugum;
      son=yeniDugum;
    } else {
      son.sonraki=yeniDugum;
      yeniDugum.onceki=son;
      son=yeniDugum;
    }
    boyut++;
  }
  public Icerik idIleBul(int id) {
    Node guncel=bas;
    while (guncel!=null) {
      if (guncel.veri.getId()==id) {
        return guncel.veri;
      }
      guncel=guncel.sonraki;
    }
    return null;
  }
  public void bastanYazdir() {
    System.out.println("---film ve dizi arsivi (bastan sona) ---");
    Node guncel=bas;
    while (guncel!=null) {
      System.out.println(guncel.veri.toString());
      guncel=guncel.sonraki;
    }
  }
  public void sondanYazdir() {
    System.out.println("--- film ve dizi arsivi (sondan basa) ---");
    Node guncel=son;
    while (guncel!=null) {
      System.out.println(guncel.veri.toString());
      guncel=guncel.onceki;
    }
  }
  public int getBoyut() {return boyut;}
}
//6.çalıştırıcı ana sınıf (çalıştığını gösterecek test kodu)
public class ArsivTest {
  public static void main(String[] args) {
    DoublyLinkedList arsiv=new DoublyLinkedList();

    //örnek film ve dizi oluşturma (id'ler şimdilik el ile 1,2)
    Film film1=new Film(1, "saiyaara", "romantik/drama", 156, "mohit suri");
    Dizi dizi1=new Dizi(2, "zanjeerain", "romantik/drama", 35, 39);

    //bolum ilerletme testi
    dizi1.bolumIlerlet();

    //arşive ekleme
    arsiv.ekle(film1);
    arsiv.ekle(dizi1);

    //listeyi ekrana basma (çift yön kanıtı)
    arsiv.bastanYazdir();
    arsiv.sondanYazdir();
  }
}
      
      
  
