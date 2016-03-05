package atharvai.webresources;

import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;
import org.apache.tinkerpop.shaded.jackson.core.JsonProcessingException;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/script")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class EngineResource extends BaseResource {

    @POST
    public Response executeScript(String script) throws ScriptException, JsonProcessingException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.getBindings(ScriptContext.ENGINE_SCOPE).put("graph",this.graph);
        Object result = engine.eval(script,engine.getBindings(ScriptContext.ENGINE_SCOPE));
        return Response.ok(this.mapper.writeValueAsString(IteratorUtils.asList(result))).build();
    }
}
