package fi.kuha.kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VastausvaihtoehtoDao extends AbstractDao<Vastausvaihtoehto> {

    public VastausvaihtoehtoDao(Database db) {
        super("Vastausvaihtoehto", db);
    }

    @Override
    public PreparedStatement insertStatementFromObject(Connection conn, Vastausvaihtoehto object) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO Vastausvaihtoehto (kysymys_id, teksti, oikein) VALUES (?, ?, ?);");
            stmt.setInt(1, object.getKysymysId());
            stmt.setString(2, object.getTeksti());
            stmt.setBoolean(3, object.getOikein());
        } catch (SQLException ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }
    
    @Override
    public Vastausvaihtoehto createFromRow(ResultSet results) {
        try {
            Vastausvaihtoehto vaihtoehto = new Vastausvaihtoehto();
            vaihtoehto.setId(results.getInt("id"));
            vaihtoehto.setTeksti(results.getString("teksti"));
            vaihtoehto.setKysymysId(results.getInt("kysymys_id"));
            vaihtoehto.setOikein(results.getBoolean("oikein"));
            
            return vaihtoehto;
        } catch (SQLException ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
