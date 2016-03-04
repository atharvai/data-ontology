package atharvai;

import atharvai.webresources.BaseResource;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/graph")
@Produces(MediaType.APPLICATION_JSON)
public class GraphService extends BaseResource {
    @GET
    @Path("/info")
    public Response getGraph() throws JsonProcessingException, org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        return Response.ok(graph.variables().asMap()).build();
    }

    @GET
    @Path("/save")
    public Response saveGraph() {
        Boolean b = gi.saveGraph();
        return Response.accepted().entity(b).build();
    }

}
