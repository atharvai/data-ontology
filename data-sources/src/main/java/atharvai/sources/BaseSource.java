package atharvai.sources;

import atharvai.config.DataSourceConfig;

public abstract class BaseSource implements Source {
    DataSourceConfig sourceConfig;

    public DataSourceConfig getConfig() {
        return this.sourceConfig;
    }
}
