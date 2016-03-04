package atharvai.sources;

import atharvai.AppConfig;
import atharvai.DataSourceConfig;

public abstract class BaseSource implements SourceInterface{
    DataSourceConfig sourceConfig;

    public DataSourceConfig getConfig() {
        return this.sourceConfig;
    }
}
