package atharvai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OntologyDefinition {

    public List<String> getVertexLabels() {
        return vertexLabels;
    }

    public void setVertexLabels(List<String> vertexLabels) {
        this.vertexLabels = vertexLabels;
    }

    public List<String> getEdgeLabels() {
        return edgeLabels;
    }

    public void setEdgeLabels(List<String> edgeLabels) {
        this.edgeLabels = edgeLabels;
    }

    public List<String> getVertexProperties() {
        return vertexProperties;
    }

    public void setVertexProperties(List<String> vertexProperties) {
        this.vertexProperties = vertexProperties;
    }

    public List<String> getEdgeProperties() {
        return edgeProperties;
    }

    public void setEdgeProperties(List<String> edgeProperties) {
        this.edgeProperties = edgeProperties;
    }

    @JsonProperty
    private List<String> vertexLabels;
    @JsonProperty
    private List<String> edgeLabels;
    @JsonProperty
    private List<String> vertexProperties;
    @JsonProperty
    private List<String> edgeProperties;
}
