package fi.kuha.kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        
        stmt.close();
        conn.close();
        return ts;
    }

    @Override
    public T saveOrUpdate(T object) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.type);
        
        if (findOne(object.getId() == null)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.type);
        }
        
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
    
    public abstract void setQueryParameters(PreparedStatement stmt, T object);
    public abstract T createFromRow(ResultSet results);
}
