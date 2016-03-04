package atharvai.sources;

import atharvai.DataSourceConfig;
import atharvai.datamodel.TableColumn;
import atharvai.datamodel.TableColumnComment;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PostgresqlSource extends BaseSource implements Source {
    private Connection conn = null;

    PostgresqlSource(DataSourceConfig cfg) {
        this.sourceConfig = cfg;
        init(this.sourceConfig);
    }

    public void init(DataSourceConfig cfg)  {
        try {
            Class.forName("org.postgresql.Driver");
            String url = cfg.getConnectionUrl();
            this.conn = DriverManager.getConnection(url,cfg.getUsername(),cfg.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws SQLException {
        if (!this.conn.isClosed())
            this.conn.close();
    }

    public Boolean getConnectionState() throws SQLException {
        return !this.conn.isClosed();
    }

    public Connection getConn(){
        return this.conn;
    }



    public List<TableColumn> getTableColumnList() throws SQLException {
        String sql = "SELECT table_schema as schema, table_name as table, column_name as column, data_type as datatype \n" +
                "FROM information_schema.columns \n" +
                "WHERE table_schema NOT IN ('pg_catalog','information_schema','pg_internal');";
        BeanListHandler<TableColumn> handler = new BeanListHandler<TableColumn>(TableColumn.class);
        QueryRunner runner = new QueryRunner();
        List<TableColumn> results = null;
        results = runner.query(this.conn, sql, handler);
        return results;
    }

    public List<TableColumnComment> getTableColumnComments() throws SQLException {
        String sql = "select pn.nspname as schema, pc.relname as table, pa.attname as column, pd.description \n" +
                "from pg_class pc \n" +
                "left join pg_attribute pa ON pc.oid = pa.attrelid \n" +
                "left join pg_description pd on pd.objoid = pc.oid and pd.objsubid=pa.attnum \n" +
                "inner join pg_namespace pn on pc.relnamespace=pn.oid \n" +
                "where pn.nspname NOT IN ('pg_catalog','information_schema','pg_internal') \n" +
                "union \n" +
                "select pn.nspname as schema, pc.relname as table, null as column, pd.description \n" +
                "from pg_class pc \n" +
                "left join pg_description pd on pd.objoid = pc.oid and pc.relkind = 'r' and objsubid=0 \n" +
                "inner join pg_namespace pn on pc.relnamespace=pn.oid \n" +
                "where pn.nspname NOT IN ('pg_catalog','information_schema','pg_internal') ;";
        BeanListHandler<TableColumnComment> handler = new BeanListHandler<TableColumnComment>(TableColumnComment.class);
        QueryRunner runner = new QueryRunner();
        List<TableColumnComment> results = null;
        results = runner.query(this.conn, sql, handler);
        return results;
    }

    public void setTableComment(List<TableColumnComment> comments) throws SQLException {
        String sql = "COMMENT ON TABLE %s IS ?;";
        try {
            this.conn.setAutoCommit(false);
            for (TableColumnComment comment : comments) {
                if (comment.getColumn() == null) {
                    String stmt = String.format(sql, comment.getTable());
                    PreparedStatement preparedStatement = this.conn.prepareStatement(stmt);
                    preparedStatement.setString(1,comment.getDescription());
                    preparedStatement.execute();
                }
            }
            this.conn.commit();
        } catch (SQLException ex) {
            this.conn.rollback();
            throw ex;
        }
    }

    public void setColumnComment(List<TableColumnComment> comments) throws SQLException {
        String sql = "COMMENT ON COLUMN %s.%s IS ?;";
        try {
            this.conn.setAutoCommit(false);
            for (TableColumnComment comment : comments) {
                if (comment.getColumn() != null) {
                    String stmt = String.format(sql, comment.getTable(),comment.getColumn());
                    PreparedStatement preparedStatement = this.conn.prepareStatement(stmt);
                    preparedStatement.setString(1,comment.getDescription());
                    preparedStatement.execute();
                }
            }
            this.conn.commit();
        } catch (SQLException ex) {
            this.conn.rollback();
            throw ex;
        }
    }
}
