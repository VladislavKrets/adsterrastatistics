package online.omnia.statistics;

/**
 * Created by lollipop on 03.12.2017.
 */
public class JsonCampaignEntity {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "JsonCampaignEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
