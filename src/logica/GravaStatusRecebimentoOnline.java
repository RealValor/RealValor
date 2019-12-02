package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.MovAtual;
import beans.Recibo;


import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import DAO.RecebimentoOnlineDAO;
import DAO.ReciboDAO;

public class GravaStatusRecebimentoOnline implements LogicaDeNegocio{

	//Alterar esta classe ...
	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession sessao = request.getSession();
		String incluiuRecebimentoTemp="";
		int retorno=0;
		
		String situPaga[] = {"A","Associado"};
		if(request.getAttribute("situPaga")!=null){
			situPaga = (String[])request.getAttribute("situPaga");
		}
		request.setAttribute("situPaga", situPaga);

		ArrayList<Recibo> listaDadosRecibo = (ArrayList<Recibo>)sessao.getAttribute("listaRecibo");
		if(sessao.getAttribute("listaRecibo")==null){
			sessao.removeAttribute("listaDados");
			sessao.removeAttribute("listaSocio");
		}

		if(sessao.getAttribute("listaRecibo")!=null){
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");
			
			HistoricoRecebimentoDAO objHistDAO = new HistoricoRecebimentoDAO();
			retorno = objHistDAO.verificarMudancaDeMes(objOperador.getNucleo().getCodigo());

			if(retorno>=0){ //---0 = mesmo mês; 1 = mudou mês

				MovAtual itemMov = new MovAtual();
				if(sessao.getAttribute("itemRecMov")==null){
					itemMov.setFechado("N");
					itemMov = MovAtualDAO.consultarMovAtual(itemMov);
					sessao.setAttribute("itemRecMov", itemMov);
				}
				
				itemMov = (MovAtual)sessao.getAttribute("itemRecMov");
				
				int totalRecibos = 0;
				if(request.getParameter("totalrecibos")!=null&&request.getParameter("totalrecibos")!="")
					totalRecibos = Integer.parseInt(request.getParameter("totalrecibos"));
				
				Calendar data = Calendar.getInstance();

				int numeroRecibo=0;

				if(totalRecibos>0){ //Inclusão de recibos reserva. 
					HistoricoRecebimentoDAO objData = new HistoricoRecebimentoDAO();
					Date recData = objData.buscarUltimaData(objOperador);

					Calendar cal = Calendar.getInstance();
					cal.setTime(recData);

					for (int i = 0; i < totalRecibos; i++) {
						//Gera os recibos reserva
						numeroRecibo = ReciboDAO.buscaNumeroRecibo(data.get(Calendar.YEAR),objOperador.getNucleo().getCodigo());
						//O método acima gera um novo numero de recibo e grava-o na tb_recibo_temp para garantir reserva da numeração.

						ReciboDAO.incluirReciboReserva(numeroRecibo, cal, objOperador);
						//Posteriormente, no fechamento do mês, GERAR uma forma de aproveitamento dos recibos reserva não utilizados.
					}
				}
				numeroRecibo = RecebimentoOnlineDAO.gravaRecebimentoOnline(data.get(Calendar.YEAR), objOperador.getUsuario(), objOperador.getNucleo().getCodigo());
				//Busca e grava NumeroRecibo pro RecebimentoOnlineTemp.

				RecebimentoOnlineDAO objRecebimentoOnline = new RecebimentoOnlineDAO();
				objRecebimentoOnline.incluiItensRecebimentoOnline(numeroRecibo, listaDadosRecibo, objOperador.getNucleo().getCodigo());
				//gravar detalhes do recibo na tb_itens_recebimento_online

				//Necssário fazer consulta do status para alimentação da tb_status_recebimento_online!!!
				
				//System.out.println("retornando: "+incluiuRecebimentoTemp);
				incluiuRecebimentoTemp=Integer.toString(data.get(Calendar.YEAR))+Integer.toString(numeroRecibo);
				retorno=Integer.parseInt(incluiuRecebimentoTemp);
				
				//-----------------------------------------------------
				sessao.removeAttribute("objMovIni");
				sessao.removeAttribute("objMovFin");
				
				MovAtual objMovI = new MovAtual();
				objMovI.setAno(data.get(Calendar.YEAR));
				objMovI.setMes(1); //janeiro
				
				MovAtual objMovF = new MovAtual();
				objMovF.setAno(data.get(Calendar.YEAR));
				objMovF.setMes(1); //janeiro
				
				sessao.setAttribute("objMovIni",objMovI);
				sessao.setAttribute("objMovFin",objMovF);
				//-----------------------------------------------------
				
				sessao.removeAttribute("listaRecibo");
				sessao.removeAttribute("listaSocio");
				sessao.removeAttribute("objPaga");
			}
			//---------------------------------------------------------
			
			StringBuffer xml = new StringBuffer(   
			"<?xml version='1.0' encoding='ISO-8859-1'?>");   
			
			xml.append("<recebimentoonline>");
				xml.append("<gravarecebimento>");
					xml.append("<situacao>");   
						xml.append(retorno);
					xml.append("</situacao>");   
				xml.append("</gravarecebimento>");
			xml.append("</recebimentoonline>");				
			
			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString());

		}
	}
}
