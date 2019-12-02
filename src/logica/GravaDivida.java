package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.DividaDAO;
import beans.Divida;

public class GravaDivida implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiDivida.jsp";
		int mes;
		HttpSession sessao = request.getSession();
		
		ArrayList<Divida> listaDadosDivida = (ArrayList<Divida>)sessao.getAttribute("listDiv");
		
		if(sessao.getAttribute("listDiv")==null){
			sessao.removeAttribute("listDiv");
			mens = "N�o h� d�ivida para grava��o";
		}else{
			
			Divida objDivida = new Divida();
			objDivida = listaDadosDivida.get(0);

			mes = objDivida.getMes();
			objDivida.setMes(0);  

			objDivida = DividaDAO.consultarDivida(objDivida);


			if(objDivida==null){
				listaDadosDivida.get(0).setMes(mes); //Atualiza mes para o objeto da lista
				
				DividaDAO objDivDAO = new DividaDAO();
				objDivDAO.incluirDivida(listaDadosDivida);
				
				mens = "D�vida incluida com sucesso";
			}else{
				mens = "ATEN��O! D�vida j� incluida para esta sa�da!";
			}
		}
		
		sessao.removeAttribute("listDiv");

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
