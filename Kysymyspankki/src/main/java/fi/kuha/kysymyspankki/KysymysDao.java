package fi.kuha.kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KysymysDao extends AbstractDao<Kysymys> {

    public KysymysDao(Database db) {
        super("Kysymys", db);
    }

    @Override
    public PreparedStatement insertStatementFromObject(Connection conn, Kysymys object) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO Kysymys (aihe_id, teksti) VALUES (?, ?);");
            stmt.setInt(1, object.getAiheId());
            stmt.setString(2, object.getTeksti());
        } catch (SQLException ex) {
            Logger.getLogger(KysymysDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }

    @Override
    public Kysymys createFromRow(ResultSet results) {
        try {
            Kysymys kysymys = new Kysymys();
            kysymys.setId(results.getInt("id"));
            kysymys.setTeksti(results.getString("teksti"));
            kysymys.setAiheId(results.getInt("aihe_id"));
            return kysymys;
        } catch (SQLException ex) {
            Logger.getLogger(KurssiDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
