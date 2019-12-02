package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import DAO.ReciboDAO;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;

public class ConsultaRecibo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Para consultar recibos inclua um nome válido";
		String pgJsp = "/consultaRecibo.jsp";
		int cancelaRecibo = 0;
		
		String controle = "";
		if(request.getParameter("controle")!=null&&request.getParameter("controle")!=""){
			controle = request.getParameter("controle");

			if (controle.equalsIgnoreCase("cancelar")){
				pgJsp = "/cancelaRecibo.jsp";
				cancelaRecibo = 1;
			}
		}
		
		String nomePaga = "";
		if(request.getParameter("consulta")!=null&&request.getParameter("consulta")!="")
			nomePaga = request.getParameter("consulta");

		int codSocio = 0;
		if(request.getParameter("cod_paga")!=null&&request.getParameter("cod_paga")!="")
			codSocio = Integer.parseInt(request.getParameter("cod_paga"));

		HttpSession sessao = request.getSession();
		Login socioPag = new Login();

		sessao.removeAttribute("objMovIni");
		sessao.removeAttribute("excluido");

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		MovAtual itemRecMov = new MovAtual();

		Calendar data = Calendar.getInstance();
		itemRecMov.setAno(data.get(Calendar.YEAR));
		itemRecMov.setMes(data.get(Calendar.MONTH)+1);
		
		sessao.setAttribute("cargarecibo", 0); //para carga automática do recibo pesquisado 
		
		if(request.getParameter("ctrl")==null||!request.getParameter("ctrl").equals("limpar")){
			if(request.getParameter("mesini_paga")!=null&&request.getParameter("mesini_paga")!="")
				itemRecMov.setMes(Integer.parseInt(request.getParameter("mesini_paga")));
			
			if(request.getParameter("ano_paga")!=null&&request.getParameter("ano_paga")!="")
				itemRecMov.setAno(Integer.parseInt(request.getParameter("ano_paga")));
		}
		
		int numReceb = 0;

		if(request.getParameter("ctrl")!=null&&request.getParameter("ctrl").equals("limpar")){			
			sessao.removeAttribute("listRecibo");
			
			sessao.removeAttribute("anoRecibo");
			sessao.removeAttribute("listaSoc");
			sessao.removeAttribute("listaRec");
			sessao.removeAttribute("numeroRecibo");
		}

		String[] situPaga = new String[2];
		situPaga[0] = "A";
		situPaga[1] = "Associado";

		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!=""){
			situPaga[0] = request.getParameter("situ_paga");
			if(request.getParameter("situ_paga").equalsIgnoreCase("N")){
				situPaga[1] = "Não Associado";  
			}else if(request.getParameter("situ_paga").equalsIgnoreCase("S")){
				situPaga[1] = "Associado";
			}
	    }
		
		int anoReceb = 0;
		ArrayList<HistoricoRecebimento> listRecibo=null;
		if(codSocio!=0){
			anoReceb = itemRecMov.getAno();
			
			if(numReceb==0){
				
				listRecibo = ReciboDAO.listarRecibo(codSocio, objOperador.getNucleo().getCodigo(), itemRecMov, cancelaRecibo, situPaga[0]);

				mens = "Não há recibos neste nome para o ano e mes informado";
				
				if (controle.equalsIgnoreCase("cancelar")){
					mens = "Não há recibos a cancelar neste nome para o ano e mes informado";
				}
				if(!listRecibo.isEmpty()){
					mens = "";
					Calendar dataLista = listRecibo.get(0).getData();
					
					numReceb = listRecibo.get(0).getRecibo();
					anoReceb = dataLista.get(Calendar.YEAR);
					sessao.setAttribute("listRecibo", listRecibo);
					
					sessao.setAttribute("cargarecibo", 1); //para carga automática do recibo pesquisado 
				}
				
			}
			
			Login itemPaga = new Login();

			itemPaga.setUsuario(codSocio);
			
			if(nomePaga!=null&&nomePaga!="")	
				itemPaga.setNome(nomePaga);
			
			sessao.setAttribute("objPaga", itemPaga);
			
			sessao.setAttribute("numeroRecibo", numReceb);
			sessao.setAttribute("anoRecibo", anoReceb);

		}

		if(sessao.getAttribute("listaRec")!=null)
			sessao.removeAttribute("listaRec");

		if(codSocio!=0){
			socioPag.setNome(nomePaga);

			ArrayList<HistoricoRecebimento> listHitRec = new ArrayList<HistoricoRecebimento>();
			listHitRec = HistoricoRecebimentoDAO.listarHistoricoRecebimento(numReceb, objOperador.getNucleo(), anoReceb, cancelaRecibo); //anoReceb
			
			if(listHitRec==null){
				sessao.removeAttribute("listRecibo");
				mens = "Recibo não encontrado para o mês: "+itemRecMov.getMes()+"/"+anoReceb;
			}
		}else{
			sessao.removeAttribute("objPaga");
			
			sessao.removeAttribute("listRecibo");
			
			sessao.removeAttribute("anoRecibo");
			sessao.removeAttribute("listaSoc");
			sessao.removeAttribute("listaRec");
			sessao.removeAttribute("numeroRecibo");

		}

		sessao.setAttribute("objMovIni", itemRecMov);
		sessao.setAttribute("objMovFin", itemRecMov);
		sessao.setAttribute("numeroRecibo", numReceb);
		
		request.setAttribute("situPaga", situPaga);
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}