package atharvai.webresources;

import atharvai.datamodel.TableColumnComment;
import atharvai.sources.DataSourceFactory;
import atharvai.sources.Source;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import java.sql.SQLException;
import java.util.List;

@Path("/datasource/tablecomment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TableCommentResource {
    @GET
    public Response getTableColumns() {
        Source postgresql = DataSourceFactory.getFactory().getDataSource("postgresql");
        try {
            List<TableColumnComment> tableColumnComments = postgresql.getTableColumnComments();
            return Response.ok(tableColumnComments).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @POST
    public Response addTableComments(List<TableColumnComment> comments, @QueryParam("action") String action) {
        Source postgresql = DataSourceFactory.getFactory().getDataSource("postgresql");
        try {
            if (action == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else if (action.equalsIgnoreCase("table")) {
                postgresql.setTableComment(comments);
            } else if (action.equalsIgnoreCase("column")) {
                postgresql.setColumnComment(comments);
            }
            return Response.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.notModified().build();
        }
    }
}
