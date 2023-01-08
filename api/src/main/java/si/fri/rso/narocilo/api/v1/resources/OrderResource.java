package si.fri.rso.narocilo.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
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

@ApplicationScoped
@Path("/narocilo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, HEAD, DELETE, OPTIONS")
public class OrderResource {

    @Inject
    private OrderBean orderBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getOrder() {

        List<Order> order = orderBean.getOrdersFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(order).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") Integer id) {

        si.fri.rso.narocilo.lib.Order order = orderBean.getOrders(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(order).build();
    }

    @POST
    public Response createOrder(Order order) {

        if (order.getClient() == null || order.getProvider() == null || order.getItems() == null || order.getCost() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            order = orderBean.createOrder(order);
        }

        return Response.status(Response.Status.CONFLICT).entity(order).build();
    }

    @PUT
    @Path("{id}")
    public Response putOrder(@PathParam("id") Integer id, Order order) {

        order = orderBean.putOrder(id, order);

        if (order == null) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.status(Response.Status.OK).entity(order).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") Integer id, Order order) {

        boolean deleted = orderBean.deleteOrder(id);

        if (deleted) {
            return  Response.status(Response.Status.OK).build();
        } else return Response.status(Response.Status.NOT_FOUND).entity(order).build();
    }
}

