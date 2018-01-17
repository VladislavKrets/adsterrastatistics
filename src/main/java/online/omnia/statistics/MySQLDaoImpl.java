package online.omnia.statistics;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lollipop on 26.09.2017.
 */
public class MySQLDaoImpl {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static MySQLDaoImpl instance;

    static {
        configuration = new Configuration()
                .addAnnotatedClass(AccountsEntity.class)
                .addAnnotatedClass(SourceStatisticsEntity.class)
                .addAnnotatedClass(AffiliatesEntity.class)
                .addAnnotatedClass(AdsetEntity.class)
                .configure("/hibernate.cfg.xml");
        Map<String, String> properties = Utils.iniFileReader();
        configuration.setProperty("hibernate.connection.password", properties.get("password"));
        configuration.setProperty("hibernate.connection.username", properties.get("username"));
        String jdbcURL = (properties.get("url")
                .startsWith("jdbc:mysql://") ? properties.get("url") : "jdbc:mysql://" + properties.get("url"));
        String url = (!jdbcURL.endsWith("/") ? jdbcURL + "/" : jdbcURL) + properties.get("dbname");
        configuration.setProperty("hibernate.connection.url", url);

        while (true) {
            try {
                sessionFactory = configuration.buildSessionFactory();
                break;
            } catch (PersistenceException e) {

                try {
                    System.out.println("Can't connect to db");
                    System.out.println("Waiting for 30 seconds");
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    public synchronized void updateTodayAdset(AdsetEntity adsetEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update AdsetEntity set campaignId=:campaignId, CTR=:ctr, date=:date, impressions=:impressions, spent=:spent, clicks=:clicks, CR=:cr, CPC=:cpc, CPM=:cpm, conversions=:conversions, CPI=:cpi, time=:time where adset_id=:adsetId and account_id=:accountId")
                .setParameter("ctr", adsetEntity.getCtr())
                .setParameter("date", adsetEntity.getDate())
                .setParameter("impressions", adsetEntity.getImpressions())
                .setParameter("spent", adsetEntity.getSpent())
                .setParameter("clicks", adsetEntity.getClicks())
                .setParameter("cr", adsetEntity.getCr())
                .setParameter("cpc", adsetEntity.getCpc())
                .setParameter("cpm", adsetEntity.getCpm())
                .setParameter("conversions", adsetEntity.getConversions())
                .setParameter("cpi", adsetEntity.getCpi())
                .setParameter("adsetId", adsetEntity.getAdsetId())
                .setParameter("accountId", adsetEntity.getAccountId())
                .setParameter("campaignId", adsetEntity.getCampaignId())
                .setParameter("time", adsetEntity.getTime())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public AdsetEntity isDateInTodayAdsets(java.util.Date date, int accountId, String campaignId) {
        System.out.println(date);
        Session session = sessionFactory.openSession();
        AdsetEntity adsetEntity = null;
        try {
            adsetEntity = session.createQuery("from AdsetEntity where date=:date and account_id=:accountId and campaign_id=:campaignId", AdsetEntity.class)
                    .setParameter("date", date)
                    .setParameter("accountId", accountId)
                    .setParameter("campaignId", campaignId)
                    .getSingleResult();
            session.close();
            return adsetEntity;
        } catch (NoResultException e) {
            session.close();
            System.out.println("No adset with this date in db");
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return adsetEntity;
    }

    public synchronized void addTodayAdset(AdsetEntity adsetEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(adsetEntity);
        session.getTransaction().commit();
        session.close();
    }
    public void updateSourceStatistics(SourceStatisticsEntity sourceStatisticsEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(sourceStatisticsEntity);
        session.getTransaction().commit();
        session.close();
    }
    public SourceStatisticsEntity getSourceStatistics(int accountId, String name, Date date) {
        Session session = sessionFactory.openSession();
        SourceStatisticsEntity sourceStatisticsEntity = null;
        try {
            sourceStatisticsEntity = session.createQuery("from SourceStatisticsEntity where account_id=:accountId and campaign_name=:campaignName and date=:date", SourceStatisticsEntity.class)
                    .setParameter("accountId", accountId)
                    .setParameter("campaignName", name)
                    .setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException e) {
            sourceStatisticsEntity = null;
        }
        session.close();
        return sourceStatisticsEntity;
    }
    public void addSourceStatistics(SourceStatisticsEntity sourceStatisticsEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(sourceStatisticsEntity);
        session.getTransaction().commit();
        session.close();
    }
    public AffiliatesEntity getAffiliateByAfid(int afid) {
        Session session = sessionFactory.openSession();
        AffiliatesEntity affiliatesEntity = null;
        try {
            affiliatesEntity = session.createQuery("from AffiliatesEntity where afid=:afid", AffiliatesEntity.class)
                    .setParameter("afid", afid)
                    .getSingleResult();
        } catch (NoResultException e) {
            affiliatesEntity = null;
        }
        session.close();
        return affiliatesEntity;
    }

    public List<AccountsEntity> getAccountsEntities(String type) {
        Session session = sessionFactory.openSession();
        List<AccountsEntity> accountsEntities;
        while (true) {
            try {
                accountsEntities = session.createQuery("from AccountsEntity acc where acc.type like:accType", AccountsEntity.class)
                        .setParameter("accType", type)
                        .getResultList();
                break;
            } catch (PersistenceException e) {
                try {
                    System.out.println("Can't connect to db");
                    System.out.println("Waiting for 30 seconds");
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        session.close();
        return accountsEntities;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static MySQLDaoImpl getInstance() {
        if (instance == null) instance = new MySQLDaoImpl();
        return instance;
    }
}
