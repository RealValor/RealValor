package logica;

import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.MovAtual;
import beans.Saida;


import DAO.MovAtualDAO;
import DAO.SaidaDAO;

public class ConsultaSaida implements LogicaDeNegocio{ //Socio(Principal)

	@SuppressWarnings("null")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiPagamento.jsp";
		String numSai  = request.getParameter("cod_saida");
		

		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		Saida itemSai = new Saida();
		
		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		itemMov.setNucleo(objOperador.getNucleo());
		
		MovAtual itemRecMov = new MovAtual();
		itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		
		if(itemRecMov == null){
			Calendar data = Calendar.getInstance();
			itemRecMov.setAno(data.get(Calendar.YEAR)); //obsrvar nulidade
			itemRecMov.setMes(data.get(Calendar.MONTH)+1);
		}		


		String[] situPaga = new String[2];

		if(numSai!=null&&numSai!=""){
			
			
			itemSai.setNumero(Integer.parseInt(numSai));
			itemSai.setNucleo(objOperador.getNucleo());
			itemSai.setMes(itemRecMov.getMes());
			itemSai.setAno(itemRecMov.getAno());
			
			SaidaDAO daoRecF = new SaidaDAO();			
			Saida itemRecF = daoRecF.consultarSaida(itemSai);
			
			mens = "Saída não encontrada";  
			
			if(itemRecF!=null){				
				mens = "Saida localizada";
				situPaga[0] = itemRecF.getFlSocio();
				situPaga[1] = "Não Sócio";
				if(!situPaga[0].equalsIgnoreCase("N")){
					situPaga[1] = "Sócio";
				}
			}
		
			request.setAttribute("objSaida", itemRecF);
			request.setAttribute("situPaga", situPaga);

			//sessao.setAttribute("objSaida", itemRecF);
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
