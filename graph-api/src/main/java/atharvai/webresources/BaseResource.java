package atharvai.webresources;

import atharvai.GraphConfig;
import atharvai.GraphInstance;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.shaded.jackson.databind.ObjectMapper;

public class BaseResource {
    public ObjectMapper mapper = null;
    public Graph graph = null;
    public GraphTraversalSource g = null;
    public GraphInstance gi = null;

    public BaseResource(GraphConfig cfg) {
        gi = GraphInstance.getInstance(cfg);
        this.graph = gi.getGraph();
        this.g = gi.getTraveralSource();
        this.mapper = gi.getMapper();
    }

}
