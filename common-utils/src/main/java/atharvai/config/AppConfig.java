package atharvai.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class AppConfig extends Configuration {
    private static AppConfig instance = new AppConfig();
    private AppConfig() {}

    public static AppConfig getInstance() {
//        if (instance == null) {
//            instance = new AppConfig();
//        }
        return instance;
    }

    public void setValues(AppConfig acfg) {
        this.setGraphConfig(acfg.getGraphConfig());
        this.setDataSourceConfig(acfg.getDataSourceConfig());
    }

    private GraphConfig graphConfig;

    @JsonProperty("graph")
    public GraphConfig getGraphConfig() {
        return this.graphConfig;
    }

    @JsonProperty("graph")
    public void setGraphConfig(GraphConfig cfg) {
        this.graphConfig = cfg;
    }

    @JsonProperty("datasource")
    public List<DataSourceConfig> getDataSourceConfig() {
        return dataSourceConfig;
    }

    @JsonProperty("datasource")
    public void setDataSourceConfig(List<DataSourceConfig> dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private List<DataSourceConfig> dataSourceConfig = new ArrayList<DataSourceConfig>();
}
