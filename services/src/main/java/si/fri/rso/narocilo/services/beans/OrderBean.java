package si.fri.rso.narocilo.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.narocilo.lib.Order;
import si.fri.rso.narocilo.models.converters.OrderConverter;
import si.fri.rso.narocilo.models.entities.OrderEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class OrderBean {

    @Inject
    private EntityManager em;

    public List<Order> getOrders() {
        TypedQuery<OrderEntity> query = em.createNamedQuery("OrderEntity.getAll", OrderEntity.class);

        List<OrderEntity> resultList = query.getResultList();

        return resultList.stream().map(OrderConverter::toDto).collect(Collectors.toList());
    }

    public List<Order> getOrdersFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();

        return JPAUtils.queryEntities(em, si.fri.rso.narocilo.models.entities.OrderEntity.class, queryParameters).stream()
                .map(si.fri.rso.narocilo.models.converters.OrderConverter::toDto).collect(Collectors.toList());
    }

    public Order getOrders(Integer id) {

        si.fri.rso.narocilo.models.entities.OrderEntity orderEntity = em.find(si.fri.rso.narocilo.models.entities.OrderEntity.class, id);

        if (orderEntity == null) {
            throw new NotFoundException();
        }

        Order order = si.fri.rso.narocilo.models.converters.OrderConverter.toDto(orderEntity);

        return order;
    }

    public Order createOrder(Order order) {

        si.fri.rso.narocilo.models.entities.OrderEntity orderEntity = si.fri.rso.narocilo.models.converters.OrderConverter.toEntity(order);

        try {
            beginTx();
            em.persist(orderEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (orderEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return si.fri.rso.narocilo.models.converters.OrderConverter.toDto(orderEntity);
    }

    public Order putOrder(Integer id, Order order) {

        OrderEntity o = em.find(si.fri.rso.narocilo.models.entities.OrderEntity.class, id);

        if (o == null) {
            return null;
        }

        OrderEntity updatedOrderEntity = OrderConverter.toEntity(order);

        try {
            beginTx();
            updatedOrderEntity.setId(o.getId());
            updatedOrderEntity = em.merge(updatedOrderEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return si.fri.rso.narocilo.models.converters.OrderConverter.toDto(updatedOrderEntity);
    }

    public boolean deleteOrder(Integer id) {

        OrderEntity order = em.find(si.fri.rso.narocilo.models.entities.OrderEntity.class, id);

        if (order != null) {
            try {
                beginTx();
                em.remove(order);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}
