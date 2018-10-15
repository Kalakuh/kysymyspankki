package fi.kuha.kysymyspankki;

public class Aihe extends DaoObject {
    private int kurssiId;
    private String nimi;
    
    public void setNimi (String nimi) {
        this.nimi = nimi;
    }
    
    public String getNimi () {
        return this.nimi;
    }
    
    public int getKurssi () {
        return this.kurssiId;
    }
    
    public void setKurssi (int id) {
        this.kurssiId = id;
    }
}
