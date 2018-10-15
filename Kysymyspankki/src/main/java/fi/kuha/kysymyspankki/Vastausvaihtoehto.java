package fi.kuha.kysymyspankki;


public class Vastausvaihtoehto extends DaoObject {
    private String teksti;
    private Integer kysymysId;
    private Boolean oikein;
    
    public void setOikein (Boolean b) {
        this.oikein = b;
    }
    
    public Boolean getOikein () {
        return this.oikein;
    }
    
    public void setTeksti (String s) {
        this.teksti = s;
    }
    
    public String getTeksti () {
        return this.teksti;
    }
    
    public void setKysymysId (Integer s) {
        this.kysymysId = s;
    }
    
    public Integer getKysymysId () {
        return this.kysymysId;
    }
}
