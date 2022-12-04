package si.fri.rso.narocilo.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "narocilo_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "NarociloMetadataEntity.getAll",
                        query = "SELECT narocilo FROM NarociloMetadataEntity narocilo"),
                @NamedQuery(name = "NarociloMetadataEntity.getById",
                        query = "SELECT narocilo FROM NarociloMetadataEntity narocilo WHERE narocilo.id=:id"),      
        })
public class NarociloMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client")
    private Integer client; //id of client

    @Column(name = "provider")
    private Integer provider; //id of provider

    @Column(name = "contents")
    private String contents;

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

    public void setClient(Integer id) {
        this.id = id;
    }
    
    public Integer getProvider() {
        return provider;
    }

    public void setProvider(Integer id) {
        this.id = id;
    }
    
     public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}