package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.MovAtual;
import beans.Saida;


import DAO.MovAtualDAO;
import DAO.SaidaDAO;

public class ListaSaida implements LogicaDeNegocio{ //ListaSaida(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/listaSaida.jsp";

		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		itemMov.setNucleo(objOperador.getNucleo());
		
		MovAtual itemRecMov = new MovAtual();
		itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		
		Saida itemSai = new Saida();

		int codPaga = 0;
		if(request.getParameter("cod_paga")!=null&&request.getParameter("cod_paga")!="")
			codPaga = Integer.parseInt(request.getParameter("cod_paga"));

		String dataNota = null;
		if(request.getParameter("data_nota")!=null&&request.getParameter("data_nota")!="")
			dataNota = request.getParameter("data_nota");

		String numeroDoc = "";			
		if(request.getParameter("numero_doc")!=null&&request.getParameter("numero_doc")!="")
			numeroDoc = request.getParameter("numero_doc");

		double vlrPago = 0;
		if(request.getParameter("valor_doc")!=null&&request.getParameter("valor_doc")!="")
			vlrPago = Double.parseDouble(request.getParameter("valor_doc"));


		itemSai.setAno(itemRecMov.getAno());
		itemSai.setMes(itemRecMov.getMes());
		itemSai.setFornecedor(codPaga);
		itemSai.setNucleo(objOperador.getNucleo());
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		if(dataNota!=null){					
			itemSai.setDataDocumento((Date)formato.parse(dataNota));
		}

		itemSai.setDocumento(numeroDoc);
		itemSai.setValor(vlrPago);

		ArrayList<Saida> listSai = SaidaDAO.listarSaida(itemSai);

		if(listSai != null){
			request.setAttribute("listSai", listSai);		                
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
