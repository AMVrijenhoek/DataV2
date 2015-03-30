/**
 * Created by Arjen on 24-3-2015.
 */

import java.sql.*;
import java.util.Date;

public class NsDB {
    private Connection conn;
    public NsDB(String dbName) throws ClassNotFoundException, SQLException
    {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String dbConnectionString = "jtdb" + dbName + "create=true";
        String greatString = "GREAT TABLE departure_times(train_id INTEGER, departure_time DATE, departure_delay INT, delay_text VARCHAR, end_destination VARCHAR, train_type VARCHAR, route_text VARCHAR, carrier VARCHAR, track_chance BOOLEAN, departure_track INTEGER, travel_tip VARCHAR)";
        Class.forName(driver);
        conn = DriverManager.getConnection(dbConnectionString);
        Statement statement = conn.createStatement();
        if (true)
        {
            statement.execute("Drop TABLE departure_times");
            statement.execute(greatString);
        }
    }

    public void updateNSDb(int trainId, Date departureTime, int DepartureDelay, String DelayText, String endDestination, String trainType,String routeText, String carrier, Boolean trackChance, int departureTrack, String travelTip)throws ClassNotFoundException, SQLException
    {
        Statement s = conn.createStatement();
        try
        {
            ResultSet resultSet = s.executeQuery("SELECT trainId FROM departure_times");
            while (resultSet.next())
            {
                if (trainId != resultSet.getInt(0))
                {
                    PreparedStatement psInsert = conn.prepareStatement("INSERT INTO tweet(train_id INTEGER, departure_time DATE, departure_delay INTEGER, delay_text VARCHAR, end_destination VARCHAR, train_type VARCHAR, route_text VARCHAR, carrier VARCHAR, track_chance BOOLEAN, departure_track INTEGER, travel_tip VARCHAR) " +
                            "VALUE (train_id, departure_time, departure_delay, delay_text, end_destination, train_type, route_text, carrier, track_chance, departure_track, travel_tip)");
                    psInsert.executeUpdate();
                }
            }
        }
        finally
        {
            s.close();
        }

    }
}
