package atharvai.webresources;

import atharvai.datamodel.TableColumn;
import atharvai.sources.DataSourceFactory;
import atharvai.sources.Source;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/datasource/tablecolumn")
@Produces(MediaType.APPLICATION_JSON)
public class TableColumnResource {
    ObjectMapper mapper = new ObjectMapper();
    @GET
    public Response getTableColumns() {
        Source postgresql = DataSourceFactory.getFactory().getDataSource("postgresql");
        try {
            List<TableColumn> tableColumnList = postgresql.getTableColumnList();
            return Response.ok(tableColumnList).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
