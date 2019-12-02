package logica;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LerDadosReuni {
	
	private static String streamToString(InputStream inputStream) {
		@SuppressWarnings("resource")
		String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
		return text;
	}

	public static String lerDadosReuni(String pURL) throws IOException{

		String sURL = pURL;
    	URL url = new URL(sURL);

    	String json = null;
        try {
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
          connection.setDoOutput(true);
          connection.setInstanceFollowRedirects(false);
          connection.setRequestMethod("GET");
          connection.setRequestProperty("Content-Type", "application/json");
          connection.setRequestProperty("charset", "utf-8");
          connection.connect();
          InputStream inStream = connection.getInputStream();
          json = streamToString(inStream);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        return json;
        
	}
}
