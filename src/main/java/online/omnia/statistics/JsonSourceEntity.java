package online.omnia.statistics;

import java.sql.Date;

/**
 * Created by lollipop on 03.12.2017.
 */
public class JsonSourceEntity {
    private Date date;
    private int impressions;
    private int conversions;
    private int clicks;
    private double ctr;
    private double cpm;
    private double spent;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getConversions() {
        return conversions;
    }

    public void setConversions(int conversions) {
        this.conversions = conversions;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public double getCtr() {
        return ctr;
    }

    public void setCtr(double ctr) {
        this.ctr = ctr;
    }

    public double getCpm() {
        return cpm;
    }

    public void setCpm(double cpm) {
        this.cpm = cpm;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    @Override
    public String toString() {
        return "JsonSourceEntity{" +
                "date=" + date +
                ", impressions=" + impressions +
                ", conversions=" + conversions +
                ", clicks=" + clicks +
                ", ctr=" + ctr +
                ", cpm=" + cpm +
                ", spent=" + spent +
                '}';
    }
}
