package atharvai.sources;

import atharvai.AppConfig;
import atharvai.DataSourceConfig;

public abstract class BaseSource implements SourceInterface{
    DataSourceConfig dataSourceConfig = AppConfig.getInstance().getDataSourceConfig();

}
