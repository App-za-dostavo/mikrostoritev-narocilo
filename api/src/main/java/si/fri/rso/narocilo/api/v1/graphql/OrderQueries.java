package si.fri.rso.narocilo.api.v1.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.narocilo.lib.Order;
import si.fri.rso.narocilo.services.beans.OrderBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class OrderQueries {

    @Inject
    private OrderBean orderBean;

    @GraphQLQuery
    public PaginationWrapper<Order> allOrders(@GraphQLArgument(name = "pagination") Pagination pagination,
                                              @GraphQLArgument(name = "sort") Sort sort,
                                              @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(orderBean.getOrders(), pagination, sort, filter);
    }

    @GraphQLQuery
    public Order getOrders(@GraphQLArgument(name = "id") Integer id) {
        return orderBean.getOrders(id);
    }

}