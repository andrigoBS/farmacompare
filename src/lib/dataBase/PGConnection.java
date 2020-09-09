package lib.dataBase;

import org.postgresql.util.PSQLException;
import java.sql.*;
import java.util.HashMap;

public class PGConnection {
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:postgresql://localhost:5432/FarmaCompare";
    private final String entityName;
    private Connection connection;

    public PGConnection(String entityName) throws SQLException, ClassNotFoundException {
        this.entityName = entityName;
        reconnect();
    }

    public void reconnect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        connection.setAutoCommit(false);
    }

    public void commitAndClose() throws SQLException{
        connection.commit();
        connection.close();
    }

    public ResultSet execQuery(String sql) throws SQLException{
        var stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    /*
        Executa uma alteração no banco e taz de volta o id do elemento editado
     */
    public int execUpdate(String sql) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.executeUpdate();

        ResultSet rsID = stmt.getGeneratedKeys();

        int id = -1;
        try {
            if(rsID.next()) {
                id = rsID.getInt(idField());
            }
        }catch (PSQLException ignore){}

        stmt.close();

        return id;
    }

    public void update(String field, String value, Integer id) throws SQLException{
        execUpdate(new SqlBuilder(entityName)
                .update(field, value)
                .where(idField(), id.toString())
                .toString());
    }

    public void incrementWhereId(String field, Integer id) throws SQLException{
        var rs = execQuery(new SqlBuilder(entityName)
                            .selectField(field)
                            .where(idField(), id.toString()).toString());
        if(rs.next()){
            int value = rs.getInt(field);
            update(field, String.valueOf(value + 1), id);
        }
    }

    public void deleteById(Integer id) throws SQLException {
        execUpdate(new SqlBuilder(entityName)
                .delete()
                .where(idField(), id.toString())
                .toString());
    }

    public void deleteByField(String field, String value) throws SQLException {
        execUpdate(new SqlBuilder(entityName)
                .delete()
                .where(field, value)
                .toString());
    }

    /*
        Faz um insert e traz o id que foi gerado pelo banco para o novo elemento
     */
    public int insert(IsEntityInDB obj) throws SQLException {
        if (!obj.getEntityName().equals(entityName)) {
            throw new IllegalArgumentException("Entity name and entity no match");
        }
        HashMap<String, String> fields = obj.getNotNullFields();
        return execUpdate(new SqlBuilder(entityName)
                            .insert(fields.keySet().toArray(new String[0]),
                                    fields.values().toArray(new String[0]))
                            .toString());
    }

    private String idField(){
        return entityName+"_id";
    }
}
