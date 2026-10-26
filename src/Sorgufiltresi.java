public class SorguFiltresi {
    private String ad;               
    private String tur;                
    private IzlemeDurumu durum;        
    private Integer maxKalanSure;      

    public SorguFiltresi adIcerir(String ad) { this.ad = ad; return this; }
    public SorguFiltresi turEsit(String tur) { this.tur = tur; return this; }
    public SorguFiltresi durumEsit(IzlemeDurumu durum) { this.durum = durum; return this; }
    public SorguFiltresi maxKalanSure(int dakika) { this.maxKalanSure = dakika; return this; }

    public boolean uyuyorMu(Icerik i) {
        if (ad != null && !i.getAd().toLowerCase().contains(ad.toLowerCase())) return false;
        if (tur != null && (i.getTur() == null || !i.getTur().equalsIgnoreCase(tur))) return false;
        if (durum != null && i.getDurum() != durum) return false;
        if (maxKalanSure != null && i.kalanSure() > maxKalanSure) return false;
        return true;
    }
}
