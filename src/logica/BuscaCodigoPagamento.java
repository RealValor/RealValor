package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class BuscaCodigoPagamento implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//HttpSession sessao = request.getSession();
		//Nucleo objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		String serverUrl = "https://ws.sandbox.pagseguro.uol.com.br/v2/checkout/";
		//Chamar uma pagina local de teste e verificar o que está recebendo em email e token.
		
	    StringBuilder sb = new StringBuilder();
		
	    HttpURLConnection urlConnection = null;
	    try {
	        URL url = new URL(serverUrl);
	        urlConnection = (HttpURLConnection) url.openConnection();
	        urlConnection.setDoOutput(true);
	        urlConnection.setRequestMethod("POST");
	        urlConnection.setUseCaches(false);
	        urlConnection.setConnectTimeout(50000);
	        urlConnection.setReadTimeout(50000);
	        //urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        //urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	        urlConnection.setDoInput(true);
	        urlConnection.setDoOutput(true); //fala que voce vai enviar algo

	        //urlConnection.setRequestProperty("Host", host);
	        
	        //You Can also Create JSONObject here
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("email", "mailfranca@gmail.com"); //objNucleo.getEmail()
			jsonObject.put("token", "1A1C6552D077407F817B6BFD699BF120");
			jsonObject.put("currency", "BRL");		
			jsonObject.put("itemId1", "0001");		
			jsonObject.put("itemQuantity1", "1");
			jsonObject.put("itemDescription1", "Pagamento de Mensalidade");
			jsonObject.put("itemAmount1", "56,00");		
			jsonObject.put("itemWeight1", "1");		

	        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
	        out.write(jsonObject.toJSONString());// .toString() here i sent the parameter
	        
	        //out.write("email=mailfranca@gmail.com&token=1A1C6552D077407F817B6BFD699BF120&currency=BRL&itemId1=0001&itemQuantity1=1&itemDescription1=Pagamento_de_Mensalidade&itemAmount1=5600&itemWeight1=1");
	        out.flush();
	        out.close();
	        
	        urlConnection.connect();

	        int HttpResult = urlConnection.getResponseCode();
	        if (HttpResult == HttpURLConnection.HTTP_OK) {
	            
	        	BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
	            
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                sb.append(line + "\n");

		            //System.out.println(sb.toString());
	            }
	            br.close();
	            //Log.e("new Test", "" + sb.toString());
	            //return sb.toString();
	            
	            //System.out.println(sb.toString());
	            
	        } else {
	        	//System.out.println("erro: "+urlConnection.getResponseMessage());
	            //Log.e(" ", "" + urlConnection.getResponseMessage());
	        }
	        //*/
	        
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (urlConnection != null)
	            urlConnection.disconnect();
	    }
	    //return null;
	}
}
