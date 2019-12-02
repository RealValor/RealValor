package logica;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ReciboDAO;
import beans.Login;
import beans.Recibo;

public class AlteraDataRecibo implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/consultaReciboMudaData.jsp";
		
		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		String dataRecibo = request.getParameter("data_recibo");

		int numRecibo=0;
		if(request.getParameter("numero_recibo")!=null&&request.getParameter("numero_recibo")!="")
			numRecibo = Integer.parseInt(request.getParameter("numero_recibo"));

		int numAno=0;
		if(request.getParameter("ano_paga")!=null&&Integer.parseInt(request.getParameter("ano_paga"))>0){
			numAno=Integer.parseInt(request.getParameter("ano_paga"));
		}

		if(numRecibo>0&&numAno>0){	
			
			Recibo recibo = new Recibo();
			
			recibo.setNumero(numRecibo);
			recibo.setAno(numAno);
			recibo.setNucleo(objOperador.getNucleo().getCodigo());

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(formato.parse(dataRecibo));
			
			if(dataRecibo!=null&&!dataRecibo.equals("")){	
				recibo.setData(cal);
			}

			ReciboDAO objRecibo = new ReciboDAO();
			objRecibo.alterarDataRecibo(recibo);

			mens = "Data de recibo alterada";
			
			sessao.setAttribute("recibo", recibo);

		}

		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
