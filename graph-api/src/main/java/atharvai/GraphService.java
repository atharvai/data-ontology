package atharvai;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.structure.Graph;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/graph")
@Produces(MediaType.APPLICATION_JSON)
public class GraphService {
    GraphConfig cfg = null;
    ObjectMapper mapper = new ObjectMapper();

    GraphService(GraphConfig cfg) {
        this.cfg=cfg;
    }

    @GET
    @Path("/info")
    public String getGraph() throws JsonProcessingException {
        GraphInstance gi = GraphInstance.getInstance(this.cfg);
        Graph graph = gi.getGraph();
        return mapper.writeValueAsString(graph.variables().asMap());
    }

    @GET
    @Path("/save")
    public String saveGraph() {
        GraphInstance gi = GraphInstance.getInstance(this.cfg);
        Boolean b = gi.saveGraph();
        return b.toString();
    }

}
