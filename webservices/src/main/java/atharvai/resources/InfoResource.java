package atharvai.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
public class InfoResource {
    @GET
    @Timed
    public String getInfo() {
        return "{\"info\":\"You've reached the info page\"}";
    }
}
