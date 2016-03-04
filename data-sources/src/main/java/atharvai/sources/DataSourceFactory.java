package atharvai.sources;

import atharvai.AppConfig;
import atharvai.DataSourceConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceFactory {
    Map<String,Class> connectionMap = new HashMap<String, Class>();
    Map<String,Source> sourceMap = new HashMap<String, Source>();
    private static DataSourceFactory instance = new DataSourceFactory();

    private DataSourceFactory() {}

    public static DataSourceFactory getFactory() {
        return instance;
    }

    public Source getDataSource(String connType) {
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

    public Map<String,Source> getAllConnections() {
        return sourceMap;
    }
}
