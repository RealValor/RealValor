package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import DAO.ListaDAO;
import DAO.MovAtualDAO;
import DAO.ReciboDAO;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.Recibo;
import beans.TipoEntrada;

public class FechaRecibo implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiRecebimento.jsp";
		
		HttpSession sessao = request.getSession();

		String observacao = "";
		if(request.getParameter("observacao")!=null&&request.getParameter("observacao")!="")
			observacao = request.getParameter("observacao");

		String situPaga[] = {"A","Associado"};
		if(request.getAttribute("situPaga")!=null){
			situPaga = (String[])request.getAttribute("situPaga");
		}
		request.setAttribute("situPaga", situPaga);

		ArrayList<Recibo> listaDadosRecibo = (ArrayList<Recibo>)sessao.getAttribute("listaRecibo");

		sessao.removeAttribute("listaRec");

		TipoEntrada objTipoEntrada = new TipoEntrada();
		objTipoEntrada.setCodigo(0);

		if(sessao.getAttribute("listaRecibo")==null){
			sessao.removeAttribute("listaDados");
			sessao.removeAttribute("listaSocio");
			mens = "Não há recibo para fechamento";
		}
		
		if(sessao.getAttribute("listaRecibo")!=null){

			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");
			
			HistoricoRecebimentoDAO objHistDAO = new HistoricoRecebimentoDAO();

			int retorno = objHistDAO.verificarMudancaDeMes(objOperador.getNucleo().getCodigo());

			if(retorno<0){ //retorno!=0&&retorno!=1
				pgJsp = "/erroDataRecibo.jsp";
			}else{
				
				MovAtual itemMov = new MovAtual();
				if(sessao.getAttribute("itemRecMov")==null){
					itemMov.setFechado("N");
					itemMov.setNucleo(objOperador.getNucleo());
					itemMov = MovAtualDAO.consultarMovAtual(itemMov);
					sessao.setAttribute("itemRecMov", itemMov);
				}
				itemMov = (MovAtual)sessao.getAttribute("itemRecMov");

				int totalRecibos = 0;
				if(request.getParameter("totalrecibos")!=null&&request.getParameter("totalrecibos")!="")
					totalRecibos = Integer.parseInt(request.getParameter("totalrecibos"));

				Calendar data = Calendar.getInstance();

				int numeroRecibo=0;

				if(totalRecibos>0){ //Foi solicitado inclusão de recibos reserva.
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
						//Verificar a possibilidade de exclusão dos recibos reservados, liberando a numeração para utilização no mês seguinte.
						//Observando que isto dificulta gerar recibos reserva após fechamento do mês (diretamento no banco).
					}
				}
				
				//System.out.println("aqui 1");

				String reciboReserva = "0";
				if(request.getParameter("recibo_reserva")!=null&&request.getParameter("recibo_reserva")!="")
					reciboReserva = request.getParameter("recibo_reserva"); //numero+ano ex. 6272011
					//Condição para utilização de recibo reserva.

				if(!reciboReserva.equalsIgnoreCase("0")){
					numeroRecibo = Integer.parseInt(reciboReserva.substring(0,reciboReserva.length()-4));
					sessao.setAttribute("numeroRecibo", numeroRecibo);

					int ano = Integer.parseInt(reciboReserva.substring(reciboReserva.length()-4));

					ArrayList<HistoricoRecebimento> recibosReserva = (ArrayList<HistoricoRecebimento>) sessao.getAttribute("recibosReserva");
					Recibo recRes = new Recibo();

					for (HistoricoRecebimento recHist : recibosReserva) {
						if(recHist.getRecibo()==numeroRecibo && recHist.getAno()==ano){
							recRes.setData(recHist.getData());
							break;
						}
					}

					for (Recibo recibo : listaDadosRecibo) {
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setData(recRes.getData());
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setCpfOperador(objOperador.getCpf());
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setNucleo(objOperador.getNucleo().getCodigo());
						//-----------------------------------------------------------------------------
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setObservacao(observacao);
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setFechado("N");
					}

					ReciboDAO.utilizarReciboReserva(numeroRecibo, ano, listaDadosRecibo);

				}else{

					numeroRecibo = ReciboDAO.buscaNumeroRecibo(data.get(Calendar.YEAR),objOperador.getNucleo().getCodigo());
					sessao.setAttribute("numeroRecibo", numeroRecibo);

					for (Recibo recibo : listaDadosRecibo) {
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setCpfOperador(objOperador.getCpf());
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setNucleo(objOperador.getNucleo().getCodigo());
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setNumero(numeroRecibo);
						//-----------------------------------------------------------------------------
						listaDadosRecibo.get(listaDadosRecibo.indexOf(recibo)).setObservacao(observacao);
					}
					ReciboDAO objRecibo = new ReciboDAO();
					objRecibo.incluirRecibo(listaDadosRecibo);
				}

				ListaDAO objListaDAO = new ListaDAO();
				
				objListaDAO.gravaPagamentoLista(listaDadosRecibo);

				if(sessao.getAttribute("recibo")!=null)
					sessao.removeAttribute("recibo");

				sessao.setAttribute("recibo", listaDadosRecibo.get(0));

				sessao.setAttribute("listaRec", listaDadosRecibo);
				sessao.setAttribute("consultaRecibo", 1);

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
				request.setAttribute("tipoEntrada", objTipoEntrada);
				
				//-----------------------------------------------------
				
				sessao.removeAttribute("listaRecibo");
				sessao.removeAttribute("listaSocio");
				sessao.removeAttribute("objPaga");
			}
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
