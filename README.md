# Data Ontology

This project aims to provide a service to allow recording of all concepts that an organisation may want to collate. The eventual aim is to provide direct link back to databases and co-locate the data dictionary with the data.

## Running the service
Execute the following command:
`java -jar data-ontogoly.jar server config.yml`

This will execute two services: application and admin service on 8080 and 8081 respectively. The ports can be changed in the `config.yml` following the [DropWizard template](https://dropwizard.github.io/dropwizard/0.9.2/docs/manual/configuration.html "DropWizard Configuration Reference").

