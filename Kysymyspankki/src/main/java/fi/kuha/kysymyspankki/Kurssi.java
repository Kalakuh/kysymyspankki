package fi.kuha.kysymyspankki;

import java.util.ArrayList;
import java.util.List;

public class Kurssi extends DaoObject {
    private String nimi;
    private List<Aihe> aiheet;
    
    public Kurssi () {
        aiheet = new ArrayList<>();
    }
    
    public void setNimi (String nimi) {
        this.nimi = nimi;
    }
    
    public String getNimi () {
        return this.nimi;
    }
    
    public List<Aihe> getAiheet () {
        return this.aiheet;
    }
}
