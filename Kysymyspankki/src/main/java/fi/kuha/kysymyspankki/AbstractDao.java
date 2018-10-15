package fi.kuha.kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDao<T extends DaoObject> implements Dao<T, Integer> {
    private String type;
    protected Database db;
    
    public AbstractDao(String type, Database db) {
        this.type = type;
        this.db = db;
    }
    
    @Override
    public T findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.type + " WHERE id=?");
        stmt.setInt(1, key);
        
        ResultSet results = stmt.executeQuery();
        
        T result = null;
        if (results.next()) {
            result = createFromRow(results);
        }
        
        stmt.close();
        conn.close();
        return result;
    }

    @Override
    public List<T> findAll() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.type);
        
        ResultSet results = stmt.executeQuery();
        
        List<T> ts = new ArrayList<>();
        while (results.next()) {
            ts.add(createFromRow(results));
        }
        results.close();
        stmt.close();
        conn.close();
        return ts;
    }

    public Integer findIdByColumnValue(String column, String target) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM " + this.type + " WHERE " + column + " = ?;");
        stmt.setString(1, target);
        ResultSet results = stmt.executeQuery();
        Integer rv = null;
        if (results.next()) {
            rv = results.getInt("id");
        }
        stmt.close();
        conn.close();
        return rv;
    }
    
    public boolean resultSetNotEmpty (PreparedStatement stmt) {
        try {
            ResultSet results = stmt.executeQuery();
            boolean ans = results.next();
            results.close();
            stmt.close();
            return ans;
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public void saveOrUpdate(T object) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = insertStatementFromObject(conn, object);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + this.type + " WHERE id=?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public abstract PreparedStatement insertStatementFromObject(Connection conn, T object);
    public abstract T createFromRow(ResultSet results);
}
