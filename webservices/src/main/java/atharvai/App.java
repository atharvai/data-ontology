package atharvai;

import atharvai.webresources.InfoResource;
import atharvai.webresources.healthcheck.HealthCheck;
import atharvai.webresources.EdgeResource;
import atharvai.webresources.EngineResource;
import atharvai.webresources.VertexResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfig>
{
    public static void main( String[] args ) throws Exception
    {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        AppConfig conf = AppConfig.getInstance();
        conf.setValues(appConfig);
        environment.jersey().register(appConfig);

        environment.jersey().packages("atharvai.webresources");
        final HealthCheck healthCheck = new HealthCheck();
        environment.healthChecks().register("webservices",healthCheck);
    }
}
