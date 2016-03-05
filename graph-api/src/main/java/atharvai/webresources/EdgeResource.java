package atharvai.webresources;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/edge")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EdgeResource extends BaseResource{

    private List<Object> mapToVarargs(Map<String,Object> map) {
        List<Object> result = new ArrayList<Object>();
        for (String key : map.keySet()){
            result.add(key);
            result.add(map.get(key));
        }
        return result;
    }

    @GET
    public Response getEdge(@QueryParam("id") UUID id) throws JsonProcessingException, org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        if (id == null) {
            return Response.ok(mapper.writeValueAsString(IteratorUtils.asList(this.graph.edges()))).build();
        } else {
            return Response.ok(mapper.writeValueAsString(IteratorUtils.asList(this.g.E(id)))).build();
        }
    }

    @PUT
    public Response addEdges(Map<String, Object> data) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        if (data.get("targetVertex") instanceof List & data.get("sourceVertex") instanceof String) {
            return Response.status(Response.Status.CREATED).entity(addMultipleEdges(data)).build();
        } else if (data.get("targetVertex") instanceof String) {
            return Response.status(Response.Status.CREATED).entity(addEdge(data)).build();
        }
        return Response.notModified().entity("{\"error\":\"targetVertex is not a String or List of String\"}").build();
    }

    /***
     * Creates a new Edge between two vertices. Accepts a JSON object requiring edge label, source and target vertices.
     * @param edgeData JSON string
     * @return Edge as JSON
     * @throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException
     */
    private String addEdge(Map<String, Object> edgeData) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        String edgeLabel = edgeData.get("label").toString();
        Map<String, Object> properties = (Map<String, Object>) edgeData.get("properties");
        Vertex srcVertex = IteratorUtils.asList(g.V(edgeData.get("sourceVertex").toString())).size() > 0 ? g.V(edgeData.get("sourceVertex").toString()).next() : null;
        Vertex targetVertex = IteratorUtils.asList(g.V(edgeData.get("targetVertex").toString())).size() > 0 ? g.V(edgeData.get("targetVertex").toString()).next() : null;
        if (srcVertex == null) {
            return "{\"error\":\"sourceVertex does not exist\"}";
        } else if (targetVertex == null) {
            return "{\"error\":\"targetVertex does not exist\"}";
        }
        Edge edge = srcVertex.addEdge(edgeLabel.toUpperCase(),targetVertex,mapToVarargs(properties).toArray());
        return mapper.writeValueAsString(edge);
    }

    /***
     * Creates a new Edge between multiple vertices. Accepts a JSON object requiring edge label, source and target vertices.
     * @param edgeData JSON string
     * @return Edge as JSON
     * @throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException
     */
    private String addMultipleEdges(Map<String, Object> edgeData) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        String edgeLabel = edgeData.get("label").toString();
        Map<String, Object> properties = (Map<String, Object>) edgeData.get("properties");
        List<Edge> edges = new ArrayList<Edge>();

        GraphTraversal<Vertex, Vertex> srcVertices = g.V(edgeData.get("sourceVertex").toString());
        Vertex srcVertex = null;
        if (srcVertices.hasNext()){
            srcVertex = srcVertices.next();
        }
        GraphTraversal<Vertex, Vertex> targetVertices = g.V(edgeData.get("targetVertex"));
        if (srcVertex == null) {
            return "{\"error\":\"sourceVertex does not exist\"}";
        } else if (!targetVertices.hasNext()) {
            return "{\"error\":\"targetVertex does not exist\"}";
        }
        while(targetVertices.hasNext()){
            Vertex targetVertex = targetVertices.next();
            Edge edge = srcVertex.addEdge(edgeLabel.toUpperCase(),targetVertex,mapToVarargs(properties).toArray());
            edges.add(edge);
        }

        return mapper.writeValueAsString(edges);
    }

    @POST
    public Response upsertEdge(@QueryParam("id") UUID id, Map<String, Object> edgeData) throws org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        if (id == null) {
            return Response.notModified().entity("{\"error\":\"Must provide a valid id as a query parameter\"}").build();
        } else {
            String edgeLabel = edgeData.get("label").toString().toUpperCase();
            Vertex srcVertex = IteratorUtils.asList(g.V(edgeData.get("sourceVertex").toString())).size() > 0 ? g.V(edgeData.get("sourceVertex").toString()).next() : null;
            Vertex targetVertex = IteratorUtils.asList(g.V(edgeData.get("targetVertex").toString())).size() > 0 ? g.V(edgeData.get("targetVertex").toString()).next() : null;
            if (srcVertex == null) {
                return Response.notModified().entity("{\"error\":\"sourceVertex does not exist\"}").build();
            } else if (targetVertex == null) {
                return Response.notModified().entity("{\"error\":\"targetVertex does not exist\"}").build();
            }

            List edges = IteratorUtils.asList(g.E(id).hasLabel(edgeLabel));
            if (edges.size() == 1) {
                Map<String, Object> updProperties = (Map<String, Object>) edgeData.get("properties");
                for (String key : updProperties.keySet()) {
                    ((Edge) edges.get(0)).property(key, updProperties.get(key));
                }
                return Response.ok(mapper.writeValueAsString(edges)).build();
            } else {
                return Response.notModified().entity("{\"error\":\"Multiple Edges found with ID: " + id + "\"}").build();
            }

        }
    }
/*
    @DELETE
    public String deleteVertex(@DefaultValue("false") @QueryParam("purge") boolean purge) {

    }*/
}
