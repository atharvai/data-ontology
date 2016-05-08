# Data Ontology

This project aims to provide a service to allow recording of all concepts that an organisation may want to collate. The eventual aim is to provide direct link back to databases and co-locate the data dictionary with the data.

This is a work in progress and far from being usable or complete. However any ideas or insights would be appreciated.

## Running the service
Execute the following command:

`java -jar data-ontology.jar server config.yml`

This will execute two services: application and admin service on 8080 and 8081 respectively. The ports can be changed in the `config.yml` following the [DropWizard template](https://dropwizard.github.io/dropwizard/0.9.2/docs/manual/configuration.html "DropWizard Configuration Reference").

### Configuration
The configuration file is a YAML formatted file. Server related properties are inherited from DropWizard.
Graph related custom properties are defined under the `graph` section:
```yaml
graph:
    graphName: test
    graphsonPath: test.graphson

datasource:
  - type: Postgresql
    connectionUrl:
    username:
    password:
```
