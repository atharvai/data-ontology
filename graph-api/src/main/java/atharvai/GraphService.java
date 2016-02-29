package atharvai;

import atharvai.webresources.BaseResource;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/graph")
@Produces(MediaType.APPLICATION_JSON)
public class GraphService extends BaseResource {

    GraphService(GraphConfig cfg) {
        super(cfg);
    }

    @GET
    @Path("/info")
    public String getGraph() throws JsonProcessingException, org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException {
        return mapper.writeValueAsString(graph.variables().asMap());
    }

    @GET
    @Path("/save")
    public String saveGraph() {
        Boolean b = gi.saveGraph();
        return b.toString();
    }

}
