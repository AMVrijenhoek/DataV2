/**
 * Created by Arjen on 24-3-2015.
 */

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.json.JSONObject;
import org.json.XML;

public class NsApiReader {
    String departTimes;
    String ritNumber;
    String departureTime;
    String delay;
    String delayText;
    String endDestination;
    String trainType;
    String routeText;
    String carrier;
    String trackChanced;
    String departureTrack;
    String travelTip;

    public void getNsApiData()
    {
        final String USERNAME = "christianlangejan@hotmail.com";
        final String PASSWORD = "APREZyc2aQ0I1viyFEMmhsD6-ciFxzNGXgA5NTLCkj2bq_aITYjxdQ";

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

        client.addFilter(new HTTPBasicAuthFilter(USERNAME, PASSWORD));

        // Get the protected web page:
        WebResource webResource = client.resource("http://webservices.ns.nl/ns-api-avt?station=RTD");
        String receivedXML = webResource.accept(MediaType.APPLICATION_XML).get(String.class);
        //JSONObject tempName = XML.toJSONObject(receivedXML);

        departTimes = webResource.get(String.class);
        Stringrestucture();

        System.out.println(departTimes);
        System.out.println(departTimes.length());

        writeToDatabase();

        //todo: make a NsDB (new class) see twitterDB class for example.

        //Old code that did not work properly for unknown reasons remove when done with program

        //ClientConfig config = new DefaultClientConfig();
        //Client client = Client.create(config);
        //WebResource service = client.resource(UriBuilder.fromUri("http://christianlangejan%40hotmail.com:APREZyc2aQ0I1viyFEMmhsD6-ciFxzNGXgA5NTLCkj2bq_aITYjxdQ@webservices.ns.nl/ns-api-avt?station=RTD").build());
        //String receivedXML = service.accept(MediaType.APPLICATION_XML).get(String.class);
        //JSONObject soapDatainJsonObject = XML.toJSONObject(receivedXML);
        //System.out.println(service);
        //System.out.println(soapDatainJsonObject.getJSONObject().getJSONObject().getString());

    }

    private void Stringrestucture()
    {
        //remove irrelevant parts of string and reorganize(leaves only about 1/4 of the sting in the end)
        departTimes=departTimes.replaceAll("\t","");
        departTimes=departTimes.replaceAll("\n\n","\n");
        departTimes=departTimes.replaceAll("\n\n","\n");
        departTimes=departTimes.replaceAll("</VertrekTijd>\n<EindBestemming>","\n\n\n");
        departTimes=departTimes.replaceAll("</TreinSoort>\n<Vervoerder>","\n\n");
        departTimes=departTimes.replaceAll("<VertrekSpoor wijziging=\"false\">","false\n");
        departTimes=departTimes.replaceAll("<VertrekSpoor wijziging=\"true\">","true\n");
        departTimes=departTimes.replaceAll("</VertrekSpoor>\n</VertrekkendeTrein>","\n");
        departTimes=departTimes.replaceAll("</.*>","");
        departTimes=departTimes.replaceAll("<VertrekkendeTrein>\n",";");
        departTimes=departTimes.replaceAll("<.*>","");

    }
    private void writeToDatabase()
    {
        int a=0;
        int part=1;
        while(true)
        {
            if(Character.toString(departTimes.charAt(a)).equals(";"))
            {
                break;
            }
            else
            {
                a++;
            }
        }
        for(int i=a;i<departTimes.length();i++)
        {
            if(Character.toString(departTimes.charAt(i)).equals(";"))
            {
                part=1;
                ritNumber="";
                departureTime="";
                delay="";
                delayText="";
                endDestination="";
                trainType="";
                routeText="";
                carrier="";
                trackChanced="";
                departureTrack="";
                travelTip="";
            }
            else if(Character.toString(departTimes.charAt(i)).equals("\n"))
            {
                part++;
            }
            else
            {
                switch (part)
                {
                    case 1: ritNumber=ritNumber+Character.toString(departTimes.charAt(i)); break;
                    case 2: departureTime=departureTime+Character.toString(departTimes.charAt(i));break;
                    case 3: delay=delay+Character.toString(departTimes.charAt(i));break;
                    case 4: delayText=delayText+Character.toString(departTimes.charAt(i)); break;
                    case 5: endDestination=endDestination+Character.toString(departTimes.charAt(i));break;
                    case 6: trainType=trainType+Character.toString(departTimes.charAt(i));break;
                    case 7: routeText=routeText+Character.toString(departTimes.charAt(i));break;
                    case 8: carrier=carrier+Character.toString(departTimes.charAt(i));break;
                    case 9: trackChanced=trackChanced+Character.toString(departTimes.charAt(i));break;
                    case 10: departureTrack=departureTrack+Character.toString(departTimes.charAt(i));break;
                    case 11: travelTip=travelTip+Character.toString(departTimes.charAt(i));break;
                }
            }
        }
    }

}
