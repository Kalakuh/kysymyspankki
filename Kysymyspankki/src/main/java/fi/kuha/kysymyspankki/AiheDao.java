package fi.kuha.kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AiheDao extends AbstractDao<Aihe> {

    public AiheDao(Database db) {
        super("Aihe", db);
    }

    @Override
    public PreparedStatement insertStatementFromObject(Connection conn, Aihe object) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO Aihe (kurssi_id, nimi) VALUES (?, ?);");
            stmt.setInt(1, object.getKurssi());
            stmt.setString(2, object.getNimi());
        } catch (SQLException ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }

    @Override
    public Aihe createFromRow(ResultSet results) {
        try {
            Aihe aihe = new Aihe();
            aihe.setId(results.getInt("id"));
            aihe.setKurssi(results.getInt("kurssi_id"));
            aihe.setNimi(results.getString("nimi"));
            return aihe;
        } catch (SQLException ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
