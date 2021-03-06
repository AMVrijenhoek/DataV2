/**
 * Created by Arjen on 24-3-2015.
 */

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static java.sql.DriverManager.*;

public class TwitterDB
    {
    private String userName = "root";
    private String password = "";
    private String driver = "com.mysql.jdbc.Driver";
    Connection conn = null;
    Statement statement = null;

    public TwitterDB(String dbName)
    {
        try
        {
            String dbConnectionString = "jdbc:mysql://localhost:3306/"+dbName;
            String creatString = "CREATE TABLE Tweet(tweetId INT, tweetUser VARCHAR, tweetText VARCHAR, tweetRetweetCount INT, tweetFavoriteCount INT, tweetCreatedAt DATE, tweetAttitude VARCHAR)";
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(dbConnectionString, userName, password);
            System.out.println("PING");
            statement = conn.createStatement();
            if (true)//todo replace true for condition
            {
                statement.execute(creatString);
                System.out.print("db greated");
            }
        }
        catch (Exception e)
        {
        System.out.println("Ping");
            System.out.print(e);
        }
    }



    public void updatedb(Long tweetId, String tweetUser, String tweetText, int tweetRetweetCount, int tweetFavoriteCount, Date tweetCreatedAt, String tweetAttitude) throws ClassNotFoundException, SQLException
        {
<<<<<<< HEAD
        statement = conn.createStatement();
        try
            {

            ResultSet resultSet = statement.executeQuery("SELECT tweetId FROM Tweet");
=======
            ResultSet resultSet = s.executeQuery("SELECT tweetId FROM Tweet");
>>>>>>> 72c034a882f8d3e6a5cc4fca64ec35bd39042633
            while (resultSet.next())
                {
                if (tweetId != resultSet.getLong(0))
                    {
                    PreparedStatement psInsert = conn.prepareStatement("INSERT INTO tweet(tweetId INT, tweetUser VARCHAR, tweetText VARCHAR, tweetRetweetCount INT, tweetFavoriteCount INT, tweetCreatedAt DATE, tweetAttitude VARCHAR) " +
                            "VALUE (tweetId, tweetUser, tweetText, tweetRetweetCount, tweetFavoriteCount, tweetCreatedAt, tweetAttitude)");
                    psInsert.executeUpdate();
                    }
                }
            } finally
            {
            statement.close();
            }
        }



    public void shutDownDB() throws ClassNotFoundException, SQLException
    {
        if (driver.equals("oracle.jdbc.driver.OracleDriver"))
        {
            boolean gotSQLExc = false;
            try
            {
                getConnection("jdbc:derby:;shutdown=true");
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

