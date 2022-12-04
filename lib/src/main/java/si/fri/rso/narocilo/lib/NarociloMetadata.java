package si.fri.rso.narocilo.lib;

public class NarociloMetadata {

    private Integer id;
    private Integer client;
    private Integer provider;
    private String contents;
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
        this.client = id;
    }

    public Integer getProvider() {
        return provider;
    }

    public void setProvider(Integer id) {
        this.provider = id;
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
