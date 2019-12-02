package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.EntradaDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.ListaDAO;
import DAO.MovAtualDAO;
import DAO.RecebimentoOffLineDAO;
import DAO.ReciboDAO;
import beans.HistoricoDevedor;
import beans.Lista;
import beans.Login;
import beans.MovAtual;
import beans.Recibo;
import beans.Socio;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BuscaXML implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String pgJsp="/principal3.jsp";
		String mens = "Sincronização de dados off-line concluída";
		
		String diretorio = "//home//udvnmg//receive//";
		/*
		String diretorio = "C:\\RVoffline\\";
		*/

		HttpSession sessao = request.getSession();
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		ArrayList<HistoricoDevedor> listaHistoricoDevedorXML = new ArrayList<HistoricoDevedor>();
		ArrayList<Recibo> listRecibo = new ArrayList<Recibo>();

		String nomeXML = diretorio+"historicoDevedorLocal"+objOperador.getNucleo().getCodigo()+".xml";

		try {
			FileReader lerXML = new FileReader(nomeXML);
			
			XStream xstreamHistoricoDevedor = new XStream(new DomDriver());
			xstreamHistoricoDevedor.alias("HistoricoDevedor", HistoricoDevedor.class);
			xstreamHistoricoDevedor.registerConverter(new ConverteDataXML());
			
			listaHistoricoDevedorXML = (ArrayList<HistoricoDevedor>)xstreamHistoricoDevedor.fromXML(lerXML);

		}catch (FileNotFoundException e) {  
			mens = "Arquivo de sincronização não encontrado. Exporte o arquivo pelo módulo Off-line";
		}

		if(listaHistoricoDevedorXML.isEmpty()){
			mens = "Erro de sincronização. Cheque existência do arquivo: C:\\RVoffline\\historicoDevedorLocal"+objOperador.getNucleo().getCodigo()+".xml e refaça a exportação";
		}else{

			for (HistoricoDevedor historicoDevedor : listaHistoricoDevedorXML) {
				
				//System.out.println("historico tem conteúdo - recibo "+historicoDevedor.getRecibo());
				
				Recibo objRecibo = new Recibo();
				
				objRecibo.setNumero(historicoDevedor.getRecibo());
				objRecibo.setNucleo(historicoDevedor.getNucleo());
				objRecibo.setEntrada(historicoDevedor.getEntrada());
				objRecibo.setMes(historicoDevedor.getMes());
				objRecibo.setAno(historicoDevedor.getAno());
				objRecibo.setData(ConverteFormatoData.dateToCalendar(historicoDevedor.getData()));
				objRecibo.setCpfOperador(historicoDevedor.getCpfOperador());
				objRecibo.setSocioDevedor(historicoDevedor.getSocioDevedor());
				
				
				Socio objSocioPagador = new Socio();
				objSocioPagador.setCodigo(historicoDevedor.getSocioPagador());
				
				objRecibo.setSocioPagador(objSocioPagador);
				objRecibo.setFlSocio(historicoDevedor.getFlSocio());
				
				int meses[] = new int[ 12 ];
				for (int i = 0; i < 12; i++) {
					meses[i]=historicoDevedor.getMes()==(i+1)?historicoDevedor.getMes():0;
				}
				objRecibo.setMeses(meses);
				
				objRecibo.setValor(historicoDevedor.getValor());
				//private double valorMensalidade;
				//private String observacao;
				objRecibo.setFechado(historicoDevedor.getExcluido());
				objRecibo.setNumeroLista(historicoDevedor.getNumeroLista());
				
				listRecibo.add(objRecibo);
			}
		}

		if(!listRecibo.isEmpty()){

			MovAtual itemMov = new MovAtual();
			if(sessao.getAttribute("itemRecMov")==null){
				itemMov.setFechado("N");
				itemMov.setNucleo(objOperador.getNucleo());
				itemMov = MovAtualDAO.consultarMovAtual(itemMov);
				sessao.setAttribute("itemRecMov", itemMov);
			}
			itemMov = (MovAtual)sessao.getAttribute("itemRecMov");

			Calendar data = Calendar.getInstance();
			int numeroRecibo=0;

			HistoricoRecebimentoDAO objHistDAO = new HistoricoRecebimentoDAO();
			int retorno = objHistDAO.verificarMudancaDeMes(objOperador.getNucleo().getCodigo());

			if(retorno>0){ // 0 = mesmo mês; 1 = mudou mês
				
				int totalRecibos = 3;

				HistoricoRecebimentoDAO objData = new HistoricoRecebimentoDAO();
				Date recData = objData.buscarUltimaData(objOperador);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(recData);
				
				for (int i = 0; i < totalRecibos; i++) {
					//Gera os recibos reserva
					numeroRecibo = ReciboDAO.buscaNumeroRecibo(data.get(Calendar.YEAR),objOperador.getNucleo().getCodigo());
					//O método acima gera um novo numero de recibo e grava-o na tb_recibo_temp para garantir reserva da numeração.
					ReciboDAO.incluirReciboReserva(numeroRecibo, cal, objOperador);
				}
			}

			int numeroReciboAux = 0; 
			
			for (Recibo recibo : listRecibo) {
				listRecibo.get(listRecibo.indexOf(recibo)).setCpfOperador(objOperador.getCpf());
				listRecibo.get(listRecibo.indexOf(recibo)).setNucleo(objOperador.getNucleo().getCodigo());
				
				if(recibo.numero!=numeroReciboAux){
					numeroReciboAux = recibo.numero; 
					numeroRecibo = ReciboDAO.buscaNumeroRecibo(data.get(Calendar.YEAR),objOperador.getNucleo().getCodigo());
				}
				
				listRecibo.get(listRecibo.indexOf(recibo)).setNumero(numeroRecibo);
			}

			numeroReciboAux = listRecibo.get(0).getNumero();
			ArrayList<Recibo> listaInclusao = new ArrayList<Recibo>();
			for (Recibo recibo : listRecibo) {
				
				if(recibo.numero==numeroReciboAux){
					listaInclusao.add(recibo);
				}else{

					RecebimentoOffLineDAO objReciboOffLine = new RecebimentoOffLineDAO();
					objReciboOffLine.incluirReciboOffline(listaInclusao);
					
					ReciboDAO objRecibo = new ReciboDAO();
					objRecibo.incluirRecibo(listaInclusao);

					ListaDAO objListaDAO = new ListaDAO();
					objListaDAO.gravaPagamentoLista(listaInclusao);

					numeroReciboAux = recibo.numero;

					listaInclusao = new ArrayList<Recibo>();
					listaInclusao.add(recibo);
				}
			}
			
			RecebimentoOffLineDAO objReciboOffLine = new RecebimentoOffLineDAO();
			objReciboOffLine.incluirReciboOffline(listaInclusao);

			ReciboDAO objRecibo = new ReciboDAO();
			objRecibo.incluirRecibo(listaInclusao);
			
			ListaDAO objListaDAO = new ListaDAO();
			objListaDAO.gravaPagamentoLista(listaInclusao);

			int verificaRecibo=0;
			Lista objLista = new Lista();
			ListaDAO objListaNovaDAO = new ListaDAO();
			EntradaDAO objEntrDAO = new EntradaDAO();

			for (Recibo recibo : listRecibo) {
				
				if(recibo.getFechado().equalsIgnoreCase("S")&&recibo.numero!=verificaRecibo){
					
					objEntrDAO.excluirEntrada(recibo.getNucleo(), recibo.numero, recibo.getAno());
					
					objLista.setNumero(recibo.numero);
					objLista.setAno(recibo.getAno());
					
					objListaNovaDAO.revertePagamentoLista(recibo);
					verificaRecibo=recibo.numero;
				}
			}

			//------------------------------
			File arquivo = new File(diretorio+"historicoDevedorLocal"+objOperador.getNucleo().getCodigo()+".xml");
			if(arquivo.isFile()){
				arquivo.delete();
			}
		}
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}

