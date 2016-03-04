package atharvai.sources;

import atharvai.AppConfig;
import atharvai.DataSourceConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceFactory {
    Map<String,Class> connectionMap = new HashMap<String, Class>();
    Map<String,SourceInterface> sourceMap = new HashMap<String, SourceInterface>();
    private static DataSourceFactory instance = new DataSourceFactory();

    private DataSourceFactory() {}

    public static DataSourceFactory getFactory() {
        return instance;
    }

    public SourceInterface getDataSource(String connType) {
        connType = connType.toLowerCase();
        AppConfig appConfig = AppConfig.getInstance();
        List<DataSourceConfig> dataSourceConfigList = appConfig.getDataSourceConfig();
        DataSourceConfig dataSourceConfig = null;
        if (sourceMap.keySet().contains(connType)) {
            return sourceMap.get(connType);
        }
        if (dataSourceConfigList.size() > 0 ) {
            for (DataSourceConfig cfg : dataSourceConfigList) {
                if (cfg.getType().equalsIgnoreCase(connType)) {
                    dataSourceConfig = cfg;
                    break;
                }
            }
        }
        if (dataSourceConfig != null){
            PostgresqlSource connectionObj = new PostgresqlSource(dataSourceConfig);
            sourceMap.put(connType,connectionObj);
            return connectionObj;
        }

        return null;
    }

    public Map<String,SourceInterface> getAllConnections() {
        return sourceMap;
    }
}
