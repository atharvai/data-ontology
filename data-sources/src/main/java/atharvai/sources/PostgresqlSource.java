package atharvai.sources;

import atharvai.AppConfig;
import atharvai.DataSourceConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PostgresqlSource implements SourceInterface {
    private Connection conn = null;

    PostgresqlSource() {
        init();
    }
    public void init()  {
        AppConfig appConfig = AppConfig.getInstance();
        DataSourceConfig cfg = appConfig.getDataSourceConfig();
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

    public List<Map<String, String>> getTableColumnList() {
        return null;
    }

    public List<Map<String, String>> getTableColumnComments() {
        return null;
    }

    public void setTableComment(List<Map<String, String>> comments) {

    }

    public void setColumnComment(List<Map<String, String>> comments) {

    }
}
