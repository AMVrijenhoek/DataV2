/**
 * Created by Arjen on 24-3-2015.
 */

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class TwitterDB {
    private Connection conn;
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public TwitterDB(String dbName) throws ClassNotFoundException, SQLException
    {
        String dbConnectionString = "jtdb" + dbName + "create=true";
        String greatString = "CREATE TABLE Tweet(tweetId INT, tweetUser VARCHAR, tweetText VARCHAR, tweetRetweetCount INT, tweetFavoriteCount INT, tweetCreatedAt DATE, tweetAttitude VARCHAR)";
        Class.forName(driver);
        conn = DriverManager.getConnection(dbConnectionString);
        Statement statement = conn.createStatement();
        if (true)
        {
            statement.execute("Drop TABLE Tweet");
            statement.execute(greatString);
        }
    }

    public void updatedb(Long tweetId, String tweetUser, String tweetText, int tweetRetweetCount, int tweetFavoriteCount, Date tweetCreatedAt, String tweetAttitude) throws ClassNotFoundException, SQLException
    {
        Statement s = conn.createStatement();
        try
        {
            ResultSet resultSet = s.executeQuery("SELECT tweetId FROM Tweet");
            while (resultSet.next())
            {
                if (tweetId != resultSet.getLong(0))
                {
                    PreparedStatement psInsert = conn.prepareStatement("INSERT INTO tweet(tweetId INT, tweetUser VARCHAR, tweetText VARCHAR, tweetRetweetCount INT, tweetFavoriteCount INT, tweetCreatedAt DATE, tweetAttitude VARCHAR) " +
                            "VALUE (tweetId, tweetUser, tweetText, tweetRetweetCount, tweetFavoriteCount, tweetCreatedAt, tweetAttitude)");
                    psInsert.executeUpdate();
                }
            }
        }
        finally
        {
            s.close();
        }
    }

    public void shutDownDB() throws ClassNotFoundException, SQLException
    {
        if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver"))
        {
            boolean gotSQLExc = false;
            try
            {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            }
            catch (SQLException se)
            {
                if ( se.getSQLState().equals("XJ015") )
                {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc)
            {
                System.out.println("Database did not shut down normally");
            }
            else
            {
                System.out.println("Database shut down normally");
            }
        }
    }
}
