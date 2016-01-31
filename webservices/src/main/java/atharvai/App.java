package atharvai;

import atharvai.resources.InfoResource;
import atharvai.resources.healthcheck.HealthCheck;
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
        environment.jersey().register(infoResource);

        final HealthCheck healthCheck = new HealthCheck();
        environment.healthChecks().register("webservices",healthCheck);
    }
}
