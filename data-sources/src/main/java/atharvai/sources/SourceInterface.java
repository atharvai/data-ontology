package atharvai.sources;


import atharvai.DataSourceConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SourceInterface {
    void init(DataSourceConfig cfg) throws ClassNotFoundException;
    void closeConnection() throws SQLException;
    Boolean getConnectionState() throws SQLException;
    List<Map<String,String>> getTableColumnList();
    List<Map<String,String>> getTableColumnComments();
    void setTableComment(List<Map<String,String>> comments);
    void setColumnComment(List<Map<String,String>> comments);
}
