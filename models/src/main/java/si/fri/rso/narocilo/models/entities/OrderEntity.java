package si.fri.rso.narocilo.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "narocilo")
@NamedQueries(value =
        {
                @NamedQuery(name = "OrderEntity.getAll", query = "SELECT order FROM OrderEntity order"),
                @NamedQuery(name = "OrderEntity.getById", query = "SELECT order FROM OrderEntity order WHERE order.id=:id")
        })
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client")
    private Integer client;

    @Column(name = "provider")
    private Integer provider;

    @Column(name = "items")
    private String items;
    @Column(name = "cost")
    private Float cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getProvider() {
        return provider;
    }

    public void setProvider(Integer provider) {
        this.provider = provider;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String firstName) {
        this.items = items;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float distance) {
        this.cost = distance;
    }
}
