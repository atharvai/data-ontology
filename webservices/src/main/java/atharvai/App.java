package atharvai;

import atharvai.resources.InfoResource;
import atharvai.resources.healthcheck.HealthCheck;
import atharvai.webresources.EdgeResource;
import atharvai.webresources.VertexResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<WebConfig>
{
    public static void main( String[] args ) throws Exception
    {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<WebConfig> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(WebConfig webConfig, Environment environment) throws Exception {
        final InfoResource infoResource = new InfoResource();
        final GraphService graphResource = new GraphService(webConfig.cfg);
        final VertexResource vertexResource = new VertexResource(webConfig.cfg);
        final EdgeResource edgeResource = new EdgeResource(webConfig.cfg);
        environment.jersey().register(infoResource);
        environment.jersey().register(graphResource);
        environment.jersey().register(vertexResource);
        environment.jersey().register(edgeResource);

        final HealthCheck healthCheck = new HealthCheck();
        environment.healthChecks().register("webservices",healthCheck);
    }
}
