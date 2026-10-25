import java.util.ArrayList;
import java.util.List;

public class SiralamaTest{
  public statik void main(String[]args){
      SiralamaSorgulamaServisi servis = new SiralamaSorgulamaServis();
    List<String> testListesi= new ArrayList<>();
    testListesi.add("Avatar");

    System.out.println("----SİSTEM TESTİ BAŞLIYOR--");
    servis.İsmeGoreSirala(testListesi);
    servis.kelimeIleSorgula("Avatar");
    System.out.println("✅ Tüm iskelet kodlar hatasız çalışıyor!");
  }
}

  
