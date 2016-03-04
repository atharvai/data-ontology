package atharvai.sources;


import java.util.List;
import java.util.Map;

public interface SourceInterface {
    void init() throws ClassNotFoundException;
    List<Map<String,String>> getTableColumnList();
    List<Map<String,String>> getTableColumnComments();
    void setTableComment(List<Map<String,String>> comments);
    void setColumnComment(List<Map<String,String>> comments);
}
