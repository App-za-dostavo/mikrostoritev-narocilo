package si.fri.rso.narocilo.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.narocilo.lib.Order;
import si.fri.rso.narocilo.services.beans.OrderBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/narocilo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, HEAD, DELETE, OPTIONS")
public class OrderResource {

    private Logger log = Logger.getLogger(OrderResource.class.getName());

    @Inject
    private OrderBean orderBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all orders metadata.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of order metadata",
                    content = @Content(schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getOrder() {

        List<Order> order = orderBean.getOrdersFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(order).build();
    }

    @Operation(description = "Get metadata for 1 order.", summary = "Get metadata for 1 order by its id number.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order metadata",
                    content = @Content(
                            schema = @Schema(implementation = Order.class))
            )})
    @GET
    @Path("/{id}")
    public Response getOrder(@Parameter(description = "Metadata ID.", required = true)
                                 @PathParam("id") Integer id) {

        si.fri.rso.narocilo.lib.Order order = orderBean.getOrders(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(order).build();
    }

    @Operation(description = "Add order metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createOrder(@RequestBody(
            description = "DTO object with order metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = Order.class))) Order order) {

        if (order.getClient() == null || order.getProvider() == null || order.getItems() == null || order.getCost() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            order = orderBean.createOrder(order);
        }

        return Response.status(Response.Status.CONFLICT).entity(order).build();
    }

    @Operation(description = "Update metadata for 1 order.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{id}")
    public Response putOrder(@Parameter(description = "Metadata ID.", required = true)
                                 @PathParam("id") Integer id,
                             @RequestBody(
                                     description = "DTO object with order metadata.",
                                     required = true, content = @Content(
                                     schema = @Schema(implementation = Order.class))) Order order) {

        order = orderBean.putOrder(id, order);

        if (order == null) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.status(Response.Status.OK).entity(order).build();
    }

    @Operation(description = "Delete metadata for 1 order.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{id}")
    public Response deleteOrder(@Parameter(description = "Metadata ID.", required = true)
                                    @PathParam("id") Integer id) {

        boolean deleted = orderBean.deleteOrder(id);

        if (deleted) {
            return  Response.status(Response.Status.OK).build();
        } else return Response.status(Response.Status.NOT_FOUND).build();
    }
}

