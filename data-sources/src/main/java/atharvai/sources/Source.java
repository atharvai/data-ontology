package atharvai.sources;


import atharvai.config.DataSourceConfig;
import atharvai.datamodel.TableColumn;
import atharvai.datamodel.TableColumnComment;

import java.sql.SQLException;
import java.util.List;

public interface Source {
    void init(DataSourceConfig cfg) throws ClassNotFoundException;
    void closeConnection() throws SQLException;
    Boolean getConnectionState() throws SQLException;
    List<TableColumn> getTableColumnList() throws SQLException;
    List<TableColumnComment> getTableColumnComments() throws SQLException;
    void setTableComment(List<TableColumnComment> comments) throws SQLException;
    void setColumnComment(List<TableColumnComment> comments) throws SQLException;
}
