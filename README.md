# dizivefilmtakipuygulamasi
veri yapıları dönem sonu projesi/dizivefilmtakipuygulamasi
 bu proje, kullanıcıların izledikleri ve izleyecekleri film ile dizileri kişisel bir arşivde düzenli şekilde tutmalarını, izleme planı oluşturmalarını, bölüm ilerlemelerini kaydetmelerini ve içerikleri filtreleyip sıralamalarını sağlayan bir masaüstü uygulamasıdır. dış katalog bağlantısı olmadan kullanıcı verileri doğrudan sisteme elle girilir.

## kullanılan teknolojiler
**programlama dili:** java
**arayüz:** javafx/swing

## ekip üyeleri, görev dağılımı ve veri yapıları
1.*Elif Şira BARCA (@elifsirabarca)*

   **kılavuzdaki rolü:** kaydetme ve arşiv yönetimi
   
   **kullandığı veri yapısı:** çift yönlü bağlı liste (doubly linked list)
   
   **tercih gerekçesi:** arşivdeki içerikler arasında hem ileriye hem geriye doğru rahatça gezinebilmek ve aradan bir film silindiğinde dizilerdeki gibi diğer elemanları tek tek kaaydırmak zorunda kalmamak
   
2.*Esmanur KALALI (@Esmanur)*

   **kılavuzdaki rolü:** silme ve izleme planı yönetimi
   
   **kullandığı veri yapısı:** kuyruk (custom queue)
   
   **tercih gerekçesi:** izleme planı mantıken bir sıradır. listeye ilk eklenen içeriğin ilk sırada izlenmesi kuralına (ilk giren ilk çıkar) tam uyduğu için kuyruk yapısı seçilmiştir.
   
3.*Filiz BAYIR (@filizbayir)*

   **kılavuzdaki rolü:** güncelleme ve durum takibi (geri alma)
   
   **kullandığı veri yapısı:** bağlı liste tabanlı yığın 
   
   **tercih gerekçesi:** kullanıcının yaptığı son değişikliği veya son izlediği içeriği en üstte tutmak, hatalı bir işlem yapıldığında ise son durumu kolayca geri alabilmek (son giren ilk çıkar) için yığın yapısı tercih edilmiştir.
   
4.*Leyla Nur KÖSE (@Leylanur)*

   **kılavuzdaki rolü:** sıralama ve sorgulama
   
   **kullandığı veri yapısı:** sıralama algoritması ve filtreleme
   
   **tercih gerekçesi:** arşivdeki içerikleri sürelerine, bölüm sayılarına veya ada göre düzenli dizmek ve ayrıca kullanıcının seçtiği türe göre listeyi filtreleyebilmek için temel sıralama ve arama yöntemleri kullanılacaktır.
   
## temel sınıf taslağı
 **Media(temel sınıf):** 'id', 'ad', 'tur', 'sure', 'izlendiMi', 'puan'
 
 **Film sınıfı:** Media sınıfından türer, 'yonetmen' bilgisi içerir.
 
 **Dizi sınıfı:** Media sınııfından türer, 'toplamBolum' ve 'izlenenBolum' bilgisi içerir.

## işleyiş ve kurallar
  *izlenen bölüm sayısı toplam bölüm sayısını kesinlikle aşamaz.
  
  *aynı içerik aktif izleme planına ikinci kez eklenemez.
  
  *sistemden bir içerik tamamen silinirken önce aktif planda aranır ve varsa plandan düşürülür, ardından ana arşivden kaldırılır.

  ```mermaid
flowchart TB
    %% STİLLER
    classDef domain fill:#e8f0fe,stroke:#4285f4,stroke-width:2px,color:#000
    classDef kisi1 fill:#e6f4ea,stroke:#34a853,stroke-width:2px,color:#000
    classDef kisi2 fill:#e0f7fa,stroke:#00acc1,stroke-width:2px,color:#000
    classDef kisi3 fill:#f3e8fd,stroke:#9c27b0,stroke-width:2px,color:#000
    classDef kisi4 fill:#fff8e1,stroke:#fbc02d,stroke-width:2px,color:#000

    %% 1. ORTAK ALTYAPI
    subgraph S1 ["1. Ortak Çekirdek Katmanı (Domain)"]
        direction TB
        Enum["enum IzlemeDurumu<br/>• IZLENECEK<br/>• IZLENIYOR<br/>• TAMAMLANDI"]:::domain
        Icerik["abstract class Icerik<br/>- id, ad, tur, sure, durum<br/>+ toplamSure()*<br/>+ kalanSure()*<br/>+ tamamlanmaOrani()*"]:::domain
        Film["class Film<br/>- yonetmen<br/>+ izlendiIsaretle()"]:::domain
        Dizi["class Dizi<br/>- toplamBolum, izlenenBolum<br/>+ bolumIzle()"]:::domain

        Icerik --> Enum
        Film -- miras alır --> Icerik
        Dizi -- miras alır --> Icerik
    end

    %% 2. VE 3. KİŞİ (SOL ALT)
    subgraph S2 ["1. Kişi: Arşiv (Bağlı Liste)"]
        direction TB
        DLL["DoublyLinkedList<br/>- bas, son, boyut<br/>+ ekle(Icerik)<br/>+ idIleBul(id)<br/>+ bastanYazdir()<br/>+ sondanYazdir()"]:::kisi1
        Node["Node<br/>+ veri: Icerik<br/>+ onceki, sonraki"]:::kisi1
        DLL --> Node
    end

    subgraph S3 ["2. Kişi: İzleme Planı (Kuyruk)"]
        direction TB
        Kuyruk["IzlemePlaniKuyrugu<br/>- bas, son<br/>+ sirayaEkle(Icerik)<br/>+ siradanCikar()<br/>+ planiGoster()"]:::kisi2
    end

    %% 4. VE 5. KİŞİ (SAĞ)
    subgraph S4 ["3. Kişi: İşlem Geçmişi (Yığın)"]
        direction TB
        Yigin["IslemGecmisiYigini<br/>- tepe, boyut<br/>+ islemiKaydet(aciklama, Icerik)<br/>+ sonIslemiGeriAl()"]:::kisi3
    end

    subgraph S5 ["4. Kişi: Sıralama ve Arama Servisi"]
        direction TB
        Kriter["enum SiralamaKriteri<br/>• AD, SURE, TAMAMLANMA"]:::kisi4
        Sorgu["SorguFiltresi<br/>- ad, tur, durum, maxSure<br/>+ uyuyorMu(Icerik)"]:::kisi4
        Sirala["IcerikSiralayici<br/>+ sirala(Icerik[], Kriter)<br/><i>Merge Sort O(n log n)</i>"]:::kisi4
        Ara["IcerikSorgulayici<br/>+ adaGoreIkiliArama()<br/><i>Binary Search O(log n)</i>"]:::kisi4

        Sirala --> Kriter
        Ara --> Sorgu
    end

    %% MODÜL BAĞLANTILARI
    Node -.->|tutar| Icerik
    Kuyruk -.->|kullanır| Icerik
    Yigin -.->|kaydeder| Icerik
    Sirala -.->|sıralar| Icerik
    Ara -.->|arar| Icerik
```

## yapay zeka kullanım beyanı
projenin planlama, mimari tasarım ve görev dağılımı aşamalarında yapay zeka araçlarından bir fikir danışmanı olarak yararlanılmıştır. tüm veri yapı sınıfları, algoritmalar ve test kodları ekip üyeleri tarafından bizzat yazılarak ve kavranarak projeye aktarılmaktadır.
