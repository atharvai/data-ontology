package atharvai.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Singleton;

@Singleton
public class GraphConfig {
    private static GraphConfig instance = new GraphConfig();
    private GraphConfig(){}

    public static GraphConfig getInstance() {
        return instance;
    }

    @JsonProperty
    public String getGraphName() {
        return graphName;
    }

    @JsonProperty
    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    @JsonProperty
    public String getGraphsonPath() {
        return graphsonPath;
    }

    @JsonProperty
    public void setGraphsonPath(String graphsonPath) {
        this.graphsonPath = graphsonPath;
    }

    private String graphName;
    private String graphsonPath;


}
