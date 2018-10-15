package fi.kuha.kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KurssiDao extends AbstractDao<Kurssi> {

    public KurssiDao(Database db) {
        super("Kurssi", db);
    }

    @Override
    public PreparedStatement insertStatementFromObject(Connection conn, Kurssi object) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO Kurssi (nimi) VALUES (?);");
            stmt.setString(1, object.getNimi());
        } catch (SQLException ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }
    
    @Override
    public Kurssi createFromRow(ResultSet results) {
        try {
            Kurssi kurssi = new Kurssi();
            kurssi.setId(results.getInt("id"));
            kurssi.setNimi(results.getString("nimi"));
            return kurssi;
        } catch (SQLException ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
