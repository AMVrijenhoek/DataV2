import java.lang.reflect.Executable;

/**
 * Created by Arjen on 24-3-2015.
 */

public class main {


    public static void main(String[] args)throws Exception
    {
        NsApiReader nsApiReader = new NsApiReader();
        TwitterReader twitterReader = new TwitterReader();

        nsApiReader.getNsApiData();
        //twitterReader.twitterSearch();


    }
}
