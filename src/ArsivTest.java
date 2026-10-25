//1.temel medya (içerik) sınıfı
class Media {
  private int id; //ID
  private String ad;
  private String tur;
  private int sure;
  private boolean izlendiMi;
  private double puan;

  public Media (int id, String ad, String tur, int sure) {
    this.id=id;
    this.ad=ad;
    this.tur=tur;
    this.sure=sure;
    this.izlendiMi=false;
    this.puan=0.0;
  }
  public int getId() {return id;}
  public String getAd() {return ad;}
  public String getTur() {return tur;}
  public int getSure() {return sure;}
  public boolean isIzlendiMi() {return izlendiMi;}
  public void setIzlendiMi(boolean izlendiMi) {this.izlendiMi=izlendiMi;}
  public double getPuan() {return puan;}
  public void setPuan(double puan) {this.puan=puan;}

  @Override
  public String toString() {
    return "id: " + id + " | ad: " + ad + " | tur: " + tur + " | sure: " + sure + " dk | izlendi: " + (izlendiMi ? "evet" : "hayir") + puan;
  }
}
 //2.film sınıfı (medyadan türer)
class Movie extends Media {
  private String yonetmen;

  public Movie(int id, String ad, String tur, int sure, String yonetmen) {
    super(id, ad, tur, sure);
    this.yonetmen=yonetmen;
  }
  public String getYonetmen() {return yonetmen;}
  public void setYonetmen(String yonetmen) {this.yonetmen=yonetmen;}

  @Override
  public String toString() {
    return super.toString() + " | yonetmen: " + yonetmen;
  }
}
//3.dizi sınıfı (medyadan türer)
class Series extends Media {
  private int toplamBolum;
  private int izlenenBolum;

  public Series(int id, String ad, String tur, int sure, int toplamBolum) {
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
    if (izlenenBolum<toplamBolum) {
      izlenenBolum++;
      if (izlenenBolum==toplamBolum) {
        setIzlendiMi(true);
      }
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return super.toString() + " | bolum: " + izlenenBolum + "/" + toplamBolum;
  }
}
//4.çift yönlü bağlı liste düğümü (node)
class Node {
  public Media veri;
  public Node onceki;
  public Node sonraki;
  public Node(Media veri) {
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
  public void ekle(Media media) {
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
  public Media idIleBul(int id) {
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
    Movie film1=new Movie(1, "interstellar", "bilim kurgu", 169, "christopher nolan");
    Series dizi1=new Series(2, "dark", "gerilim", 60, 26);

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
      
      
  
