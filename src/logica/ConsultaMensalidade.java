package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.DependenteDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.NaoSocioDAO;
import DAO.RecebimentoOnlineDAO;
import DAO.ReciboDAO;
import DAO.SocioDAO;
import DAO.TipoEntradaDAO;
import beans.Dependente;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.NaoSocio;
import beans.Recibo;
import beans.Socio;
import beans.TipoEntrada;

public class ConsultaMensalidade implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Historico não encontrado para "+request.getParameter("consulta");
		String pgJsp = "/incluiRecebimento.jsp";
		String nomePaga = request.getParameter("consulta");
		String tipoPaga = request.getParameter("tipo_entrada");
		String codAno   = request.getParameter("ano_paga");
		String sitPaga  = request.getParameter("situ_paga");
		int codPaga  = Integer.parseInt(request.getParameter("cod_paga"));

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		ArrayList<TipoEntrada> listTEnt = TipoEntradaDAO.listarTipoEntrada(objOperador.getNucleo().getCodigo(), (sitPaga.equals("N")?1:3));
		//0 = todos; 1 = mensal="N"; 2 = mensal="S"; 3 = ativo<>"N";
		
		listTEnt.get(0).setNucleo(objOperador.getNucleo());
		TipoEntrada objTipoEntrada = new TipoEntrada();
		
		if(Integer.parseInt(tipoPaga)>2){
			objTipoEntrada.setCodigo(Integer.parseInt(tipoPaga));
			objTipoEntrada.setNucleo(objOperador.getNucleo());
			
			objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
		}else{
			objTipoEntrada.setCodigo(0);
			if(Integer.parseInt(tipoPaga)==0){
				tipoPaga="1";
			}
		}

		//-------------------------------------------------------------------
		int codSocio = 0;
		if(codPaga!=0)
			codSocio = codPaga;

		Login itemPaga = new Login();
		
		itemPaga.setUsuario(((Login)sessao.getAttribute("objUsu")).getUsuario());
		itemPaga.setCpf(((Login)sessao.getAttribute("objUsu")).getCpf());
		itemPaga.setNucleo(((Login)sessao.getAttribute("objUsu")).getNucleo());
		itemPaga.setNome(((Login)sessao.getAttribute("objUsu")).getNome());
		itemPaga.setNivel(((Login)sessao.getAttribute("objUsu")).getNivel());
		itemPaga.setCargo(((Login)sessao.getAttribute("objUsu")).getCargo());
		itemPaga.setSenha(((Login)sessao.getAttribute("objUsu")).getSenha());
		itemPaga.setSituacao(((Login)sessao.getAttribute("objUsu")).getSituacao());

		if(itemPaga!=null&&itemPaga.getNivel()<2){
			//Tratamento para usuário comum (Nivel<2).
			pgJsp = "/incluiRecebimentoUsuario.jsp";
			codSocio=itemPaga.getUsuario();
			sessao.removeAttribute("consultaMensalidade");
			int consultaMensalidade=1; 
			sessao.setAttribute("consultaMensalidade", consultaMensalidade);
		}
		
		String[] situPaga = new String[2];
        situPaga[0] = "A";
        situPaga[1] = "Associado";

		if(sitPaga.equals("N")){
			situPaga[0] = sitPaga;
			situPaga[1] = sitPaga=="N"?"Associado":"Não Associado"; 
        }

		if(codSocio !=0){

			itemPaga.setUsuario(codSocio);
			itemPaga.setSituacao(sitPaga);
			
			if(sitPaga.equals("N")){
				NaoSocio objPagador = new NaoSocio();
				objPagador.setCodigo(codSocio);

				NaoSocioDAO naoSocioDAO = new NaoSocioDAO();
				itemPaga.setNome(naoSocioDAO.consultarNaoSoc(objPagador,objOperador.getRegiao().getCodigo()).getNome());
			}else{
				Socio objPagador = new Socio();
				objPagador.setCodigo(codSocio);

				SocioDAO socioDAO = new SocioDAO();
				objPagador = socioDAO.consultarSocio(objPagador, objOperador.getNucleo());
				
				request.setAttribute("isento", objPagador.getIsencao());
				
				itemPaga.setNome(objPagador.getNome());
				situPaga[0] = objPagador.getSituacao();
			}

			sessao.setAttribute("objPaga", itemPaga);
		}

		Calendar data = Calendar.getInstance();

		MovAtual itemMovIni = new MovAtual();
		itemMovIni.setAno(data.get(Calendar.YEAR));
		itemMovIni.setMes(data.get(Calendar.MONTH)+1);
		
		MovAtual itemMovFin = new MovAtual();
		itemMovFin.setAno(data.get(Calendar.YEAR));
		itemMovFin.setMes(data.get(Calendar.MONTH)+1);

		if(data.get(Calendar.DAY_OF_MONTH)<10){
			itemMovFin.setMes(data.get(Calendar.MONTH));
		}

		request.setAttribute("situPaga", situPaga);
		
		int mesAberto = 0; //para indentificar mes final em aberto

		//--------------------------------------------------------
		if(sitPaga.equals("A")){

			SocioDAO daoRecF = new SocioDAO();		
			Socio itemRecF = new Socio();
			itemRecF.setNome(nomePaga);
			itemRecF.setCodigo(0);
			
			itemRecF = daoRecF.consultarSocio(itemRecF, objOperador.getNucleo());
			if(itemRecF==null){
				mens = "Nome "+nomePaga+" inexistente";
				nomePaga=null;
				codPaga=0;
			}
		}
		//--------------------------------------------------------

		if((nomePaga!=null&&nomePaga!="")&&(codPaga!=0)){

			sessao.removeAttribute("listRecF");
			
			if(nomePaga!=null&&nomePaga!="")	
				itemPaga.setNome(nomePaga);
			
			if(codPaga!=0)
				itemPaga.setUsuario(codPaga);

			if(listTEnt != null){
				listTEnt = BuscaSocioValor.buscaSocioValor(codPaga, listTEnt);
				sessao.setAttribute("listTEnt", listTEnt);		                
			}

			//----------------------------------------------------------------
			//Este é o momento ideal para fazer a busca do status na página do pagSeguro.
			//e atualizar, caso exista alguma mudança.

			RecebimentoOnlineDAO objRecebimentoOnlineDAO = new RecebimentoOnlineDAO();
			int statusTransacao = objRecebimentoOnlineDAO.buscaStatusTransacao(itemPaga,Integer.parseInt(codAno),4,objOperador.getNucleo().getCodigo()); 

			if(statusTransacao==4){ //4 - GERA O RECIBO. //6, 7 ou 8 - VERMELHO
					
				int anoData = 0; 
				int reciboOnlineTemp=0;
				
				do {
					
					int meses[] = new int[ 12 ];
					for (int i = 0; i <= 11; i++) {
						meses[i]=0; //preenche todos os meses com zero
					}
					
					anoData = objRecebimentoOnlineDAO.buscaAnoDataTransacao(itemPaga,Integer.parseInt(codAno));
					reciboOnlineTemp = objRecebimentoOnlineDAO.buscaReciboTransacao(itemPaga,anoData, 4);
					
					ArrayList<Recibo> listaDadosRecibo = new ArrayList<Recibo>();
					ArrayList<Recibo> listaDadosOnlineRecibo = null;
					
					RecebimentoOnlineDAO objBuscaReciboOnlineDAO = new RecebimentoOnlineDAO();
					listaDadosOnlineRecibo = objBuscaReciboOnlineDAO.buscaDadosRecebimentoOnline(itemPaga,anoData,reciboOnlineTemp);

					int numeroRecibo = ReciboDAO.buscaNumeroRecibo(data.get(Calendar.YEAR),itemPaga.getNucleo().getCodigo());
					
					for (Recibo itemRecibo : listaDadosOnlineRecibo) {
						
						Recibo fpsgRecibo = new Recibo();

						fpsgRecibo.setNumero(numeroRecibo);
						fpsgRecibo.setEntrada(itemRecibo.getEntrada());
						fpsgRecibo.setNucleo(itemRecibo.getNucleo());
						fpsgRecibo.setMes(itemRecibo.getMes());
						fpsgRecibo.setAno(itemRecibo.getAno());
						fpsgRecibo.setData(Calendar.getInstance());

						fpsgRecibo.setCpfOperador(itemRecibo.getCpfOperador());
						fpsgRecibo.setFlSocio(itemRecibo.getFlSocio());
						fpsgRecibo.setSocioDevedor(itemRecibo.getSocioDevedor()); 

						fpsgRecibo.setSocioPagador(itemRecibo.getSocioPagador());
						
						meses[itemRecibo.getMes()-1]=itemRecibo.getMes(); // itemRecibo.getMes()-1 devido inicio em zero 
						fpsgRecibo.setMeses(meses);

						fpsgRecibo.setValor(itemRecibo.getEntrada().getValor());

						fpsgRecibo.setNumeroLista(0);
						
						listaDadosRecibo.add(fpsgRecibo);

					}

					ReciboDAO objRecibo = new ReciboDAO();
					objRecibo.incluirRecibo(listaDadosRecibo);
					
					//ListaDAO objListaDAO = new ListaDAO();
					//objListaDAO.gravaPagamentoLista(listaDadosRecibo);
					
					RecebimentoOnlineDAO objRecebimentoDAO = new RecebimentoOnlineDAO();
					objRecebimentoDAO.zeraReciboOnline(reciboOnlineTemp, anoData);
					//gravar status igual a 0 (zero) para finalizar o recibo temporário.
					statusTransacao = objRecebimentoOnlineDAO.buscaStatusTransacao(itemPaga,Integer.parseInt(codAno),4,objOperador.getNucleo().getCodigo());

				} while(statusTransacao==4);

			}
			//----------------------------------------------------------------

			ArrayList<HistoricoRecebimento> listRecF=null;
			
			int tipoPagamento = Integer.parseInt(tipoPaga);
			int dividaAnoAnterior = 0;

			if(itemPaga.getUsuario()!=0) {

				if(sitPaga.equals("N")||tipoPagamento>2){
					
					mens = "Histórico de pagamentos - "+itemPaga.getNome();
					if(sitPaga.equals("N")){
						tipoPagamento=0;
					}
					listRecF = HistoricoRecebimentoDAO.listarHistoricoRecebimento(itemPaga,tipoPagamento,Integer.parseInt(codAno));
				}else{
					
					mens = "Histórico de mensalidades do associado "+itemPaga.getNome();
					listRecF = HistoricoRecebimentoDAO.listarHistoricoRecebimento(itemPaga,2,Integer.parseInt(codAno)); //2 = F.P.S.G é o valor base para consulta
				}
			}


			ArrayList<HistoricoRecebimento> listRecAnt=null;

			int anoPrimeiroPagamento;
			
			HistoricoRecebimentoDAO histRecDAO = new HistoricoRecebimentoDAO(); 
			anoPrimeiroPagamento = histRecDAO.buscarAnoPrimeiroPagamento(itemPaga.getUsuario());
			

			if(itemPaga.getUsuario()!=0&&anoPrimeiroPagamento<Integer.parseInt(codAno)) {
				mens = "Histórico de mensalidades do associado "+itemPaga.getNome();
				
				listRecAnt = HistoricoRecebimentoDAO.listarHistoricoRecebimento(itemPaga,2,(Integer.parseInt(codAno)-1)); //2 = F.P.S.G
			}

			if(listRecF==null){

				mens = "Historico de mensalidades "+codAno+" não encontrado para "+itemPaga.getNome();
				itemMovFin.setAno(Integer.parseInt(codAno));

				itemMovIni.setMes(1);
				itemMovFin.setMes(1);
				
				if(!(sitPaga.equals("N")||tipoPagamento>2)){

					ArrayList<HistoricoRecebimento> listRecAux = new ArrayList<HistoricoRecebimento>();
					HistoricoRecebimento reciboVazio;
					
					for (int i = 0; i < 12; i++) {
						
						reciboVazio = new HistoricoRecebimento();
						
						reciboVazio.setAno(0);
						if(anoPrimeiroPagamento<Integer.parseInt(codAno)){
							reciboVazio.setAno(Integer.parseInt(codAno));
						}
						reciboVazio.setRecibo(0);
						reciboVazio.setData(null);
						reciboVazio.setMes(i+1);
						reciboVazio.setFlSocio("S");
						
						listRecAux.add(reciboVazio);
					}
					listRecF=listRecAux;
					mesAberto=data.get(Calendar.MONTH)+1;
				}
				
			}else{
				TipoEntrada objTipEnt = new TipoEntrada();
				boolean existeMes;
				boolean iniciaMes = false;
				int mesPagFin = 1; //último mes pago
				int mesPagAux = 0;
				mesAberto = 0;
				int controleMesInicial = 1;
				for (int i = 0; i < 12; i++) {
					HistoricoRecebimento hisRec = new HistoricoRecebimento();
					hisRec.setMes(i+1);
					hisRec.setAno(Integer.parseInt(codAno));
					existeMes = false;
					for (HistoricoRecebimento histRec : listRecF) {
						if(histRec.getMes()==(i+1)){
							existeMes = true;
							iniciaMes = true;
							if( histRec.getMes()>mesPagFin ) mesPagFin=histRec.getMes();
						}
					}
					if (!existeMes){
						if( hisRec.getAno()<itemMovIni.getAno()||(hisRec.getAno()==itemMovIni.getAno()&&hisRec.getMes()<=data.get(Calendar.MONTH)+1) ) mesAberto=hisRec.getMes();
						if( mesPagAux<1 )mesPagAux = i; //Linha adicionada para incluir mes não recebido intercalado entre meses recebidos.
						if(!iniciaMes){
							hisRec.setAno(0);
							controleMesInicial=0;
						}
						listRecF.add(i,hisRec);
					}
				}

				if(mesPagAux>0){
					mesPagFin=mesPagAux;
					//Condição que permite a inclusão de meses em aberto intercalados entre meses recebidos.
				}

				mesPagFin=controleMesInicial==0?1:mesPagFin+1;
				itemMovIni.setMes(mesPagFin);
				if(sitPaga.equals("N")||tipoPagamento>2){
					itemMovIni.setMes(1);
				}
				
				itemMovFin.setAno(Integer.parseInt(codAno));
				
				request.setAttribute("objTipEnt", objTipEnt);
			}

			if(objRecebimentoOnlineDAO.consultaRecebimentoOnlinePendente(itemPaga,Integer.parseInt(codAno))){

				ArrayList<HistoricoRecebimento> listRecOnline=null;
				listRecOnline = RecebimentoOnlineDAO.listarRecebimentoOnlinePendente(itemPaga,tipoPagamento,Integer.parseInt(codAno));
				
				for (HistoricoRecebimento recebimentoOnline : listRecOnline) {
					for (HistoricoRecebimento historicoRecebimento : listRecF) {
						if(historicoRecebimento.getMes()==recebimentoOnline.getMes()&&historicoRecebimento.getAno()==recebimentoOnline.getAno()){
							
							listRecF.get(listRecF.indexOf(historicoRecebimento)).setAno(-1);
							listRecF.get(listRecF.indexOf(historicoRecebimento)).setData(null);
							
							itemMovIni.setMes(listRecF.get(listRecF.indexOf(historicoRecebimento)).getMes()+1);
							mesAberto=itemMovIni.getMes();
						}
					}
				}
			}
			
			if(listRecAnt!=null&&sitPaga.equals("A")){

				int controleMesInicial = 1;
				boolean encontrouMesPago = false;
				for (int i = 0; i < 12; i++) {
					
					for (HistoricoRecebimento objRecebimento : listRecAnt) {
						if(objRecebimento.getMes()==(i+1)){
							encontrouMesPago = true;
							controleMesInicial = (i+1);
						}
					}

					if(encontrouMesPago&&(i+1)>controleMesInicial){
						dividaAnoAnterior = 1;
						//apenas para detectar parcela em aberto.
					}
				}
			}else{
				dividaAnoAnterior = 12;
				if(anoPrimeiroPagamento<Integer.parseInt(codAno)&&!sitPaga.equals("N")){
					dividaAnoAnterior = 1;	
				}
			}
			
			int mesAtual = data.get(Calendar.MONTH)+1;
			if(data.get(Calendar.DAY_OF_MONTH)<10){
				mesAtual = data.get(Calendar.MONTH);
			}

			if(mesAberto!=0&&!sitPaga.equals("N")){
				itemMovFin.setMes(mesAberto);
			}else{
				itemMovFin.setMes(itemMovIni.getMes());
			}

			Socio objSocio = new Socio();
			objSocio.setCodigo(codSocio);

			ArrayList<Dependente> listDependente = DependenteDAO.listarDependente(objSocio, objOperador.getNucleo());

			sessao.setAttribute("listDependente", listDependente);
			
			request.setAttribute("listRecF", listRecF); //não deve ser na sessao devido atualização de consulta
			request.setAttribute("dividaAnoAnterior",dividaAnoAnterior);

			request.setAttribute("consultaRecibo", sessao.getAttribute("consultaRecibo"));
			//sessao.setAttribute("consultaRecibo", 0);

			Recibo objRecibo = (Recibo) sessao.getAttribute("recibo");
			
			if(objRecibo == null){
				objRecibo = new Recibo();
				objRecibo.setCpfOperador(objOperador.getCpf());
				objRecibo.setNucleo(objOperador.getNucleo().getCodigo());
			}
			
			sessao.setAttribute("recibo", objRecibo);
			
			sessao.setAttribute("listMens", listRecF);		                
			sessao.setAttribute("objPaga", itemPaga);
			sessao.setAttribute("objMovIni", itemMovIni);
			sessao.setAttribute("objMovFin", itemMovFin);
			sessao.setAttribute("anoAtual", itemMovIni.getAno());
			sessao.setAttribute("mesAtual", mesAtual);
			sessao.setAttribute("tipoEntrada", objTipoEntrada);
		}
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
