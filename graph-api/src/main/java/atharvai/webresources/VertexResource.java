package atharvai.webresources;

import atharvai.GraphConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerVertexProperty;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Path("/vertex")
@Produces(MediaType.APPLICATION_JSON)
public class VertexResource extends BaseResource {

    @GET
    public Response getVextex(@QueryParam("id") UUID id) throws JsonProcessingException, org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        if (id == null) {
            return Response.ok(mapper.writeValueAsString(IteratorUtils.asList(this.graph.vertices()))).build();
        } else {
            return Response.ok(mapper.writeValueAsString(IteratorUtils.asList(this.g.V(id)))).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addVertex(List<Map<String, Object>> vertexData) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        List<Vertex> vertices = new ArrayList<Vertex>();
        for (Map<String, Object> vertexDatum : vertexData) {
            Vertex vertex = createVertex(vertexDatum);
            vertices.add(vertex);
        }
        return Response.status(Response.Status.CREATED).entity(mapper.writeValueAsString(vertices)).build();
    }

    private Vertex createVertex(Map<String, Object> vertexData) {
        String vertexLabel = vertexData.get("label").toString();
        Vertex vertex = graph.addVertex(vertexLabel);
        Map<String, Object> properties = (Map<String, Object>) vertexData.get("properties");
        for (String prop : properties.keySet()) {
            vertex.property(VertexProperty.Cardinality.single, prop, properties.get(prop));
        }
        return vertex;
    }

    @POST
    public Response upsertVertex(@QueryParam("id") UUID id, Map<String, Object> vertexData) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        if (id != null) {
            List vertices = updateVertex(id, vertexData);
            return Response.ok(mapper.writeValueAsString(vertices)).build();
        }
        return Response.notModified().entity("{\"error\":\"Must provide a valid id as a query parameter\"}").build();
    }

    private List updateVertex(UUID id, Map<String, Object> vertexData) {
        List vertices = IteratorUtils.asList(g.V(id).hasLabel(vertexData.get("label").toString()));
        if (vertices.size() == 1) {
            List<TinkerVertexProperty> properties = IteratorUtils.asList(((Vertex) vertices.get(0)).properties());
            Map<String, Object> updProperties = (Map<String, Object>) vertexData.get("properties");
            for (String key : updProperties.keySet()) {
                ((Vertex) vertices.get(0)).property(key, updProperties.get(key));
            }
        }
        return vertices;
    }
/*
    @DELETE
    public String deleteVertex(@DefaultValue("false") @QueryParam("purge") boolean purge) {

    }*/

}
