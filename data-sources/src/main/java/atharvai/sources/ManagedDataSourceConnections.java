package atharvai.sources;

import io.dropwizard.lifecycle.Managed;

import java.util.Map;

public class ManagedDataSourceConnections implements Managed {
    public void start() throws Exception {

    }

    public void stop() throws Exception {
        Map<String, Source> allConnections = DataSourceFactory.getFactory().getAllConnections();
        for (Source source : allConnections.values()) {
            source.closeConnection();
        }
    }
}
