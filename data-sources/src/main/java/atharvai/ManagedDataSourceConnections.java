package atharvai;

import atharvai.sources.DataSourceFactory;
import atharvai.sources.SourceInterface;
import io.dropwizard.lifecycle.Managed;

import java.util.Map;

public class ManagedDataSourceConnections implements Managed{
    public void start() throws Exception {

    }

    public void stop() throws Exception {
        Map<String, SourceInterface> allConnections = DataSourceFactory.getFactory().getAllConnections();
        for (SourceInterface source : allConnections.values()) {
            source.closeConnection();
        }
    }
}
