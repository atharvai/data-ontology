package atharvai.webresources;

import atharvai.GraphConfig;
import atharvai.GraphInstance;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerVertexProperty;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;
import org.apache.tinkerpop.shaded.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/vertex")
@Produces(MediaType.APPLICATION_JSON)
public class VertexResource {
    private ObjectMapper mapper = null;
    private Graph graph = null;
    private GraphTraversalSource g = null;

    public VertexResource(GraphConfig cfg) {
        GraphInstance gi = GraphInstance.getInstance(cfg);
        this.graph = gi.getGraph();
        this.g = gi.getTraveralSource();
        this.mapper = gi.getMapper();
    }

    @GET
    public String getVextex(@QueryParam("id") UUID id) throws JsonProcessingException, org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        if (id == null) {
            return mapper.writeValueAsString(IteratorUtils.asList(this.graph.vertices()));
        } else {
            return mapper.writeValueAsString(IteratorUtils.asList(this.g.V(id)));
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String addVertex(Map<String,Object> vertexData) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        String vertexLabel = vertexData.get("label").toString();
        Vertex vertex = graph.addVertex(vertexLabel);
        Map<String,Object> properties = (Map<String, Object>) vertexData.get("properties");
        for (String prop : properties.keySet()) {
            vertex.property(VertexProperty.Cardinality.single,prop,properties.get(prop));
        }
        return mapper.writeValueAsString(vertex);
    }

    @POST
    public String upsertVertex(@QueryParam("id") UUID id, Map<String,Object> vertexData) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        if (id == null) {
            return "{\"error\":\"Must provide a valid id as a query parameter\"}";
        } else {
            List vertices = IteratorUtils.asList(g.V(id).hasLabel(vertexData.get("label").toString()));
            if (vertices.size() == 1) {
                List<TinkerVertexProperty> properties = IteratorUtils.asList(((Vertex) vertices.get(0)).properties());
                Map<String,Object> updProperties = (Map<String,Object>)vertexData.get("properties");
                for (String key : updProperties.keySet()){
                    ((Vertex)vertices.get(0)).property(key,updProperties.get(key));
                }
            }
            return mapper.writeValueAsString(vertices);
        }
    }
/*
    @DELETE
    public String deleteVertex(@DefaultValue("false") @QueryParam("purge") boolean purge) {

    }*/
}
