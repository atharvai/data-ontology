package atharvai;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class WebConfig extends Configuration {
    GraphConfig cfg = new GraphConfig();

    @JsonProperty("graph")
    public GraphConfig getGraphConfig() {
        return this.cfg;
    }

    @JsonProperty("graph")
    public void setGraphConfig(GraphConfig cfg) {
        this.cfg = cfg;
    }
}
