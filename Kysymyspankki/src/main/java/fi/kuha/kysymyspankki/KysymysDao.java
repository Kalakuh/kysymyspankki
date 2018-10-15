package fi.kuha.kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KysymysDao extends AbstractDao<Kysymys> {

    public KysymysDao(Database db) {
        super("Kysymys", db);
    }

    @Override
    public PreparedStatement insertStatementFromObject(Connection conn, Kysymys object) {
        return null;
    }

    @Override
    public Kysymys createFromRow(ResultSet results) {
        return null;
    }
    
}
