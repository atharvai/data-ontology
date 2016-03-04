package atharvai;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/datasource")
public class DataSourcesService {
    @GET
    public Response getInfo(){
        return Response.ok().build();
    }
}
