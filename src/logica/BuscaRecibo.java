package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ReciboDAO;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;

public class BuscaRecibo implements LogicaDeNegocio{

	//@SuppressWarnings("static-access")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String pgJsp = "/consultaRecibo.jsp";
		String nomePaga = request.getParameter("consulta");
		String codPaga  = request.getParameter("cod_paga");
		String numMes = request.getParameter("mesini_paga");
		String numAno   = request.getParameter("ano_paga");
		int cancelaRecibo = 0;
		
		HttpSession sessao = request.getSession();

		sessao.removeAttribute("listRecibo");
		
		String controle = "";
		if(request.getParameter("controle")!=null&&request.getParameter("controle")!="")
			controle = request.getParameter("controle");
		
		if (controle.equalsIgnoreCase("cancelar")){
			pgJsp = "/cancelaRecibo.jsp";
			cancelaRecibo = 1;			
		}

		String[] situPaga = new String[2];
        situPaga[0] = "A";
        situPaga[1] = "Sócio";
        
		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!=""){
			situPaga[0] = request.getParameter("situ_paga");
			situPaga[1] = situPaga[0]=="N"?"Não Sócio":"Sócio"; //situPaga[0].equalsIgnoreCase("N")?"Sócio":"Não Sócio";
		}
		
		int pSoc=Integer.parseInt(codPaga);
		int pMes=Integer.parseInt(numMes);
		int pAno=Integer.parseInt(numAno);

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		
		MovAtual objMovAtual = (MovAtual)sessao.getAttribute("objMovIni");
		objMovAtual.setMes(pMes);
		objMovAtual.setAno(pAno);
		
		ArrayList<HistoricoRecebimento> listRecibo = new ArrayList<HistoricoRecebimento>();
		listRecibo = ReciboDAO.listarRecibo(pSoc,objOperador.getNucleo().getCodigo(), objMovAtual, cancelaRecibo, situPaga[0]);
		
		//System.out.println("listRecibo.get(0).getRecibo(): "+listRecibo.get(0).getRecibo());
		
		String mens = "Não há recibos neste nome para o ano e mes informado";

		if (controle.equalsIgnoreCase("cancelar")){
			mens = "Não há recibos a cancelar neste nome para o ano e mes informado";
		}

		if(!listRecibo.isEmpty()){
			mens = "";
			//System.out.println("listRecibo.get(0).getRecibo(): "+listRecibo.get(0).getRecibo());

			//sessao.setAttribute("numerorecibo", listRecibo.get(0).getRecibo());
		}
		sessao.setAttribute("listRecibo", listRecibo);
		
		Login itemPaga = new Login();

		itemPaga.setUsuario(pSoc);		
		if(nomePaga!=null&&nomePaga!="")	
			itemPaga.setNome(nomePaga);
		
		sessao.setAttribute("objPaga", itemPaga);
		sessao.setAttribute("objMovIni",objMovAtual);

		request.setAttribute("situPaga", situPaga);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
