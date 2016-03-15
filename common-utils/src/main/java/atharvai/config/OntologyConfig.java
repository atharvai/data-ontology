package atharvai.config;

import atharvai.OntologyDefinition;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Singleton
public class OntologyConfig {
    private static final String ONTOLOGY_DEFINITIONS_JSON = "ontology_definitions.json";
    private static OntologyConfig instance = new OntologyConfig();

    private OntologyConfig() {
    }

    public static OntologyConfig getInstance() {
        return instance;
    }

    private ObjectMapper mapper = new ObjectMapper();
    private OntologyDefinition ontologyDefinition = null;


    public OntologyDefinition getDefinitions() throws IOException {
        if (ontologyDefinition == null) {
            if (Files.exists(Paths.get(ONTOLOGY_DEFINITIONS_JSON))) {
                InputStream inF = this.getClass().getClassLoader().getResourceAsStream(ONTOLOGY_DEFINITIONS_JSON);
                ontologyDefinition = mapper.readValue(inF, OntologyDefinition.class);
                inF.close();
            } else {
                ontologyDefinition = new OntologyDefinition();
            }
        }
        return ontologyDefinition;
    }

    public void saveDefinitions() throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(ONTOLOGY_DEFINITIONS_JSON));
        mapper.writeValue(bufferedWriter, ontologyDefinition);
    }
}
