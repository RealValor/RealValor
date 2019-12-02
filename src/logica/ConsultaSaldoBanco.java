package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BancoDAO;
import DAO.SaldoBancoDAO;
import beans.Banco;
import beans.Login;
import beans.SaldoBanco;

public class ConsultaSaldoBanco implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiSaldoBanco.jsp";
		String codBan  = request.getParameter("banco");

		HttpSession sessao = request.getSession();
		SaldoBanco itemSaldo = new SaldoBanco();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		
		if(codBan!=null&&codBan!=""){

			Banco itemBanco = new Banco();
			itemBanco.setCodigo(Integer.parseInt(codBan));
			itemBanco = BancoDAO.consultarBanco(itemBanco);
			
			itemSaldo.setBanco(itemBanco);
			int numAno;
			int numMes;
			double numSal;

			itemSaldo.setNucleo(objOperador.getNucleo());
			
			if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""){
				numAno = Integer.parseInt(request.getParameter("ano"));
				itemSaldo.setAno(numAno);
			}
			
			if(request.getParameter("mes")!=null&&request.getParameter("mes")!=""){
				numMes = Integer.parseInt(request.getParameter("mes"));
				itemSaldo.setMes(numMes);
			}
			
			if(request.getParameter("saldo")!=null&&request.getParameter("saldo")!=""){
				numSal = Double.parseDouble(request.getParameter("saldo"));
				itemSaldo.setValor(numSal);
			}
			SaldoBanco itemRecSal = SaldoBancoDAO.consultarSaldoBanco(itemSaldo);

			if(itemRecSal!=null){
				itemRecSal.setBanco(BancoDAO.consultarBanco(itemRecSal.getBanco()));				
				mens = " Saldo localizado: "+itemRecSal.getBanco().getDescricao();
			}

			//----------------------------------------
			itemSaldo.setBanco(itemBanco);
			
			ArrayList<SaldoBanco> listSal = SaldoBancoDAO.listarSaldoBanco(itemSaldo);
			if(listSal != null){
				request.setAttribute("listSal", listSal);		                
			}
			//----------------------------------------

			request.setAttribute("objSal", itemRecSal);
			sessao.setAttribute("objSal", itemRecSal);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
