package atharvai.sources;

import atharvai.AppConfig;
import atharvai.DataSourceConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class DataSourceFactory {
    Map<String,Class> connectionMap = new HashMap<String, Class>();

    DataSourceFactory() {
        connectionMap.put("postgres",PostgresqlSource.class);
    }
    public Connection getConnection(String connType) {
        AppConfig appConfig = AppConfig.getInstance();
        DataSourceConfig dataSourceConfig = appConfig.getDataSourceConfig();
        try {
            Class<?> cls = Class.forName("atharvai.sources."+connType+"Source");
            Constructor<?> constructor = cls.getConstructor();
            Object connectionObj = constructor.newInstance();
            return (Connection) connectionObj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
