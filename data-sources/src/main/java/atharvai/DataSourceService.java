package atharvai;

import atharvai.sources.DataSourceFactory;
import atharvai.sources.SourceInterface;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("/datasource")
public class DataSourceService {
    @GET
    public Response getInfo(){
        return Response.ok().build();
    }

    @GET
    @Path("/state")
    public Response testconnect(@QueryParam("type") String type) throws SQLException {
        SourceInterface conn = DataSourceFactory.getFactory().getDataSource(type.toLowerCase());
        Map<String,Boolean> state = new HashMap<String, Boolean>();
        state.put("state",conn.getConnectionState());
        return Response.ok(state).build();
    }
}
