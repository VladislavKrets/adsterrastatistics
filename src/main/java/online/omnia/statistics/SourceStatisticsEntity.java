package online.omnia.statistics;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by lollipop on 13.10.2017.
 */
@Entity
@Table(name = "source_statistics")
public class SourceStatisticsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "campaign_id")
    private String campaignId;
    @Column(name = "afid")
    private int afid;
    @Column(name = "buyer_id")
    private int buyerId;
    @Column(name = "spent")
    private double spent;
    @Column(name = "conversions")
    private int conversions;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "account_id")
    private int account_id;
    @Column(name = "CR")
    private double cr;
    @Column(name = "date")
    private Date date;
    @Column(name = "campaign_name")
    private String campaignName;
    @Column(name = "CPC")
    private double cpc;
    @Column(name = "CPM")
    private double cpm;
    @Column(name = "CTR")
    private double ctr;
    @Column(name = "clicks")
    private int clicks;
    @Column(name = "impressions")
    private int impressions;
    @Column(name = "time")
    private Time time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public double getCpc() {
        return cpc;
    }

    public void setCpc(double cpc) {
        this.cpc = cpc;
    }

    public double getCpm() {
        return cpm;
    }

    public void setCpm(double cpm) {
        this.cpm = cpm;
    }

    public double getCtr() {
        return ctr;
    }

    public void setCtr(double ctr) {
        this.ctr = ctr;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public int getConversions() {
        return conversions;
    }

    public void setConversions(int conversions) {
        this.conversions = conversions;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SourceStatisticsEntity{" +
                "id=" + id +
                ", campaignId='" + campaignId + '\'' +
                ", buyerId=" + buyerId +
                ", spent=" + spent +
                ", conversions=" + conversions +
                ", receiver='" + receiver + '\'' +
                ", account_id=" + account_id +
                ", date=" + date +
                ", campaignName='" + campaignName + '\'' +
                ", cpc=" + cpc +
                ", cpm=" + cpm +
                ", ctr=" + ctr +
                ", clicks=" + clicks +
                ", time=" + time +
                '}';
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getAfid() {
        return afid;
    }

    public void setAfid(int afid) {
        this.afid = afid;
    }

    public double getCr() {
        return cr;
    }

    public void setCr(double cr) {
        this.cr = cr;
    }
}
