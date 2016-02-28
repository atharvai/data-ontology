package atharvai;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONMapper;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.apache.tinkerpop.shaded.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GraphInstance {
    private static Graph _graph = null;
    private static GraphTraversalSource _g = null;
    private static GraphConfig _cfg = null;
    private static GraphInstance _instance = null;
    private ObjectMapper mapper = null;

    GraphInstance() {
    }

    public static GraphInstance getInstance(GraphConfig cfg) {
        if (_instance == null) {
            _cfg = cfg;
            if (_graph == null) {
                try {
                    Configuration graphConfig = new BaseConfiguration();
                    graphConfig.setProperty("gremlin.tinkergraph.vertexIdManager","UUID");
                    graphConfig.setProperty("gremlin.tinkergraph.vertexPropertyIdManager","LONG");
                    graphConfig.setProperty("gremlin.tinkergraph.edgeIdManager","UUID");
                    graphConfig.setProperty("gremlin.tinkergraph.graphFormat","graphson");
                    graphConfig.setProperty("gremlin.tinkergraph.graphLocation",_cfg.getGraphsonPath());
                    _graph = TinkerGraph.open(graphConfig);
                    _graph.variables().set("name", _cfg.getGraphName());
                    _g = _graph.traversal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            _instance = new GraphInstance();
        }
        return _instance;
    }

    public Graph getGraph() {

        return _graph;
    }

    public GraphTraversalSource getTraveralSource() {
        return _g;
    }

    public boolean saveGraph() {
        try {
            _graph.io(IoCore.graphson()).writeGraph(_cfg.getGraphsonPath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = _graph.io(IoCore.graphson()).mapper().normalize(true).create().createMapper();
        }
        return mapper;
    }

    public void closeGraph() {
        if (_graph != null) {
            try {
                _graph.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
