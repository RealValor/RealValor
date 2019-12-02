package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import beans.Login;
import beans.Recibo;

public class ExcluiItensRecebimento implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Para consultar mensalidades inclua um nome válido";
		String pgJsp = "/incluiRecebimento.jsp";
		
		String posicoes = "";
		if(request.getParameter("pos")!=null&&request.getParameter("pos")!="")
			posicoes = request.getParameter("pos");
		
		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		int mudouMes;
		HistoricoRecebimentoDAO objHistDAO = new HistoricoRecebimentoDAO();

		mudouMes = objHistDAO.verificarMudancaDeMes(objOperador.getNucleo().getCodigo());

		ArrayList<Recibo> listaDadosRecibo = (ArrayList<Recibo>)sessao.getAttribute("listaRecibo");
		
		if(sessao.getAttribute("listaRecibo")==null){
			listaDadosRecibo = new ArrayList<Recibo>();
			//System.out.println("Atenção listaRecibo é nulo");
		}else{
			
			ArrayList<Recibo> listaDadosReciboAuxiliar = new ArrayList<Recibo>();
			
			if(posicoes!=""){
				
				int indice = 0;
				int numero;
				int anterior = 0;
				int separador = -1;
				int tamStr = posicoes.length();
				for (int i = 0;i < tamStr; i++) {
					
					if(posicoes.charAt(i)==';'){
						anterior = separador;
						separador = i;
						
						numero = Integer.parseInt(posicoes.substring(anterior+1,i));
						listaDadosReciboAuxiliar.add(indice, listaDadosRecibo.get(numero));
						
						indice++;
					}
				}
				
				for (Recibo recibo : listaDadosReciboAuxiliar) {
					listaDadosRecibo.remove(recibo);
				}
				
			}else{
				sessao.removeAttribute("listDiv");
			}
		}

		sessao.removeAttribute("listaRecibo");
		
		sessao.setAttribute("listaRecibo", listaDadosRecibo);
		sessao.setAttribute("consultaRecibo", 0);
		request.setAttribute("mudouMes", mudouMes);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
