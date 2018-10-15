package fi.kuha.kysymyspankki;

import java.util.ArrayList;
import java.util.List;

public class Aihe extends DaoObject {
    private int kurssiId;
    private String nimi;
    private List<Kysymys> kysymykset;
    
    public Aihe () {
        this.kysymykset = new ArrayList<>();
    }
    
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
    
    public List<Kysymys> getKysymykset () {
        return this.kysymykset;
    }
}
