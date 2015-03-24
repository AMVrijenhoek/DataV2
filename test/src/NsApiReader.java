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
    String DepartureTrack;

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
        JSONObject tempName = XML.toJSONObject(receivedXML);

        departTimes = webResource.get(String.class);
        Stringrestucture();

        //todo write to database

        System.out.println(tempName);

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
        //departTimes=departTimes.replaceAll("\n\n","\n");
        //departTimes=departTimes.replaceAll("\n\n","\n");
        //departTimes=departTimes.replaceAll("<VertrekSpoor wijziging=\"false\">","false\n");
        //departTimes=departTimes.replaceAll("<VertrekSpoor wijziging=\"true\">","true\n");
        departTimes=departTimes.replaceAll("<VertrekVertraging>","==========");
        departTimes=departTimes.replaceAll("</.*>","");
        //departTimes=departTimes.replaceAll("<.*>","");
        //departTimes=departTimes.replaceAll("\n\n","\n;");
        /*
        departTimes=departTimes.replaceAll("/","");
        departTimes=departTimes.replaceAll("<RitNummer>","");
        departTimes=departTimes.replaceAll("<VertrekTijd>","");
        departTimes=departTimes.replaceAll("<VertrekVertraging>","&");
        departTimes=departTimes.replaceAll("<VertrekVertragingTekst>","");
        departTimes=departTimes.replaceAll("<EindBestemming>","");
        departTimes=departTimes.replaceAll("<TreinSoort>","");
        departTimes=departTimes.replaceAll("<RouteTekst>","");
        departTimes=departTimes.replaceAll("<Vervoerder>","");
        departTimes=departTimes.replaceAll("<VertrekSpoor wijziging=\"false\">","false\n");
        departTimes=departTimes.replaceAll("<VertrekSpoor wijziging=\"true\">","true\n");
        departTimes=departTimes.replaceAll("<VertrekSpoor>","");
        departTimes=departTimes.replaceAll("<VertrekkendeTrein>\n<VertrekkendeTrein>",";");
        departTimes=departTimes.replaceAll("<ReisTip>","");
        departTimes=departTimes.replaceAll("<ActueleVertrekTijden>","");
        departTimes=departTimes.replaceAll("<VertrekkendeTrein>","");
        */
    }
}
