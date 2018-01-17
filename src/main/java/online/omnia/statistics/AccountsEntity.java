package online.omnia.statistics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lollipop on 12.10.2017.
 */
@Entity
@Table(name = "accounts")
public class AccountsEntity {
    @Id
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "buyer_id")
    private int buyerId;
    @Column(name = "type")
    private String type;
    @Column(name = "api_key")
    private String apiKey;
    @Column(name = "api_url")
    private String apiUrl;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "actual")
    private int actual;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public String toString() {
        return "AccountsEntity{" +
                "accountId=" + accountId +
                ", type='" + type + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                '}';
    }

    public int getBuyerId() {
        return buyerId;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }
}
