package fi.kuha.kysymyspankki;

import java.util.ArrayList;
import java.util.List;

class Kysymys extends DaoObject {
    private String teksti;
    private Integer aiheId;
    private List<Vastausvaihtoehto> vaihtoehdot;
    
    public Kysymys () {
        vaihtoehdot = new ArrayList<>();
    }
    
    public void setTeksti (String s) {
        this.teksti = s;
    }
    
    public String getTeksti () {
        return this.teksti;
    }
    
    public void setAiheId (Integer s) {
        this.aiheId = s;
    }
    
    public Integer getAiheId () {
        return this.aiheId;
    }
    
    public List<Vastausvaihtoehto> getVaihtoehdot () {
        return this.vaihtoehdot;
    }
}
