package si.fri.rso.narocilo.api.v1.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import si.fri.rso.narocilo.lib.Order;
import si.fri.rso.narocilo.services.beans.OrderBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class OrderMutations {

    @Inject
    private OrderBean orderBean;

    @GraphQLMutation
    public Order addOrder(@GraphQLArgument(name = "order") Order order) {
        orderBean.createOrder(order);
        return order;
    }

    @GraphQLMutation
    public DeleteResponse deleteOrder(@GraphQLArgument(name = "id") Integer id) {
        return new DeleteResponse(orderBean.deleteOrder(id));
    }

}