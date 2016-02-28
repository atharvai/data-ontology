package atharvai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GraphConfig {
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
