package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import DAO.SocioDAO;
import DAO.TipoEntradaDAO;
import beans.DebitoSocio;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.Recibo;
import beans.Socio;
import beans.TipoEntrada;

public class ConsultaInadimplente implements LogicaDeNegocio{ //Modificar esta classe. Resposta muito lenta!

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Listagem de inadimplentes";
		String pgJsp = "/consultaInadimplente.jsp";
		int mesPaga=0;
		int codAno=0;
		int totMeses=0;
		
		if(request.getParameter("mesfin_paga")!=null&&request.getParameter("mesfin_paga")!="")
			mesPaga  = Integer.parseInt(request.getParameter("mesfin_paga"));

		if(request.getParameter("ano_paga")!=null&&request.getParameter("ano_paga")!="")
			codAno   = Integer.parseInt(request.getParameter("ano_paga"));

		if(request.getParameter("tot_meses")!=null&&request.getParameter("tot_meses")!="")
			totMeses = Integer.parseInt(request.getParameter("tot_meses"));

		HttpSession sessao = request.getSession();
	
		MovAtual itemMovIni = new MovAtual();
		
		Calendar data = Calendar.getInstance();
		itemMovIni.setAno(data.get(Calendar.YEAR));
		itemMovIni.setMes(data.get(Calendar.MONTH)+1);

		if(data.get(Calendar.DAY_OF_MONTH)<10){
			itemMovIni.setMes(data.get(Calendar.MONTH));
		}
		
		ArrayList<MovAtual> listObjMov = new ArrayList<MovAtual>();		
		for (int i = 0; i < itemMovIni.getMes(); i++) {
			MovAtual itemMovAux = new MovAtual();
			itemMovAux.setMes(itemMovIni.getMes()-i);
			listObjMov.add(itemMovAux);
		}
		sessao.setAttribute("listObjMov", listObjMov);

		if(codAno!=0){
			if(itemMovIni.getAno()>codAno){
				mesPaga=12;
				totMeses=1;
			}
			itemMovIni.setAno(codAno); 
		}		
		
		if(mesPaga!=0){
			itemMovIni.setMes(mesPaga);
		}

		int anoPrimeiroPagamento;
		HistoricoRecebimentoDAO histRecDAO = new HistoricoRecebimentoDAO();

		sessao.setAttribute("objMovIni", itemMovIni);
		request.setAttribute("totMeses", 0);
		
		if(totMeses!=0){	
			int totalMeses = totMeses; //quantidade mínima de meses em aberto
			
			Socio itemDeve = new Socio();
			
			itemDeve.setCodigo(0);
			itemDeve.setCpf(0);
			itemDeve.setNome(null);

			sessao.removeAttribute("listRecI");

			int ateEsteMes = mesPaga;
			if( (ateEsteMes - totalMeses) >= 0 ){

				Login objOperador = new Login();
				objOperador = (Login)sessao.getAttribute("objUsu");

				
				//String pTipo = "ativo";
				ArrayList<Socio> listObjSoc = new ArrayList<Socio>();
				listObjSoc = SocioDAO.listarSocioAtivoNaoIsento(objOperador.getNucleo().getCodigo()); //,itemDeve.getCodigo()

				ArrayList<HistoricoRecebimento> listRecF=null;
				ArrayList<DebitoSocio> listDebitoSocio=null;
				ArrayList<Recibo> listaDadosRecibo = new ArrayList<Recibo>();

				int mesIniCont=(ateEsteMes - (totalMeses-1));
				
				int totalSociosAtivos = SocioDAO.buscarTotalSocioAtivo(objOperador.getNucleo().getCodigo());

				//System.out.println("aqui 1");
				listRecF = HistoricoRecebimentoDAO.listarHistoricoOrdenadoPorGrau(objOperador.getNucleo(),null,0,codAno);
				//baseado em F.P.S.G. - tipo: 2 ou Mensalidade - Tipo 1

				//System.out.println("aqui 2");


				listDebitoSocio = HistoricoRecebimentoDAO.listarDebitoSocio(objOperador.getNucleo(),null);

				if(listRecF==null){
					mens = "Não existem recebimentos!";
				}else{

					int iniciouPagamento = 0;
					int debitoAnterior = 0;

					for (Socio socio : listObjSoc) { //Pra todos os sócios
						int meses[] = null;
						Recibo fpsgRecibo = null;
						meses = new int[12];
						boolean devedor=true;
						int indice=-1;
						
						for (int j = 0; j <= 11; j++){
							meses[j]=0; // 0 == atraso 
						}
						
						anoPrimeiroPagamento = histRecDAO.buscarAnoPrimeiroPagamento(socio.getCodigo());

						//procura o sócio na lista
						for (HistoricoRecebimento historico : listRecF) {
							
							iniciouPagamento = -1;

							if(socio.getCodigo()==historico.getSocioDevedor().getCodigo()){
								indice = listRecF.indexOf(historico);
								iniciouPagamento = 0; //identifica que o socio pagaou pelo menos um mes,
								break; 				  //encontra o sócio, memoriza o índice e quebra o loop
							}
						}

						if(iniciouPagamento < 0 && anoPrimeiroPagamento < codAno){
							iniciouPagamento = 0;
							debitoAnterior = 1;
						}
						
						if(indice==-1){//<------ não encontrou o sócio

							if(anoPrimeiroPagamento <= codAno){
								
								if(iniciouPagamento < 0){
									for(int j = 0; j <= 11; j++)meses[j]=-1; //-1 == ainda não associado no mes
								}
								
								TipoEntrada objEntrada = new TipoEntrada();
								objEntrada.setCodigo(1);
								objEntrada.setNucleo(objOperador.getNucleo());
								
								objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);
								
								fpsgRecibo = new Recibo();
								fpsgRecibo.setEntrada(objEntrada);
								
								fpsgRecibo.setNumero(0); //iniciouPagamento
								
								fpsgRecibo.setMes(itemMovIni.getMes());
								fpsgRecibo.setAno(itemMovIni.getAno());
								fpsgRecibo.setData(data);
								fpsgRecibo.setFlSocio("S");
								
								fpsgRecibo.setSocioDevedor(socio);
								
								fpsgRecibo.setMeses(meses);
								
								for (DebitoSocio obfDebSocio : listDebitoSocio) {
									if(obfDebSocio.getSocio().getCodigo()==fpsgRecibo.getSocioDevedor().getCodigo()){
										fpsgRecibo.setValorMensalidade(obfDebSocio.getValormensal());
										fpsgRecibo.setValor(obfDebSocio.getValoroutros());
									}
								}
							}
						}else{ //sócio encontrado na lista
							
							for (HistoricoRecebimento historico : listRecF) {
								
								if(socio.getCodigo()==historico.getSocioDevedor().getCodigo()){
									meses[historico.getMes()-1] = historico.getMes();
									//apenas para um mesmo sócio
								}else{
									if(meses[0]>0) break;
								}
							}
							
							for (int j = 0; j <= 11; j++){
								if(meses[j]>0){ //De 0 a 11 verifica se já se associou ...
									break;
								}else{
									meses[j]=-1; //-1 == ainda não associado no mes
								}
							}
							
							TipoEntrada objEntrada = new TipoEntrada();
							objEntrada.setCodigo(1);
							objEntrada.setNucleo(objOperador.getNucleo());
							

							objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);


							fpsgRecibo = new Recibo();
							fpsgRecibo.setEntrada(objEntrada);
							
							fpsgRecibo.setNumero(listRecF.get(indice).getRecibo());
							fpsgRecibo.setMes(listRecF.get(indice).getMes());
							fpsgRecibo.setAno(listRecF.get(indice).getAno());
							fpsgRecibo.setData(listRecF.get(indice).getData());
							fpsgRecibo.setFlSocio(listRecF.get(indice).getFlSocio());

							fpsgRecibo.setSocioDevedor(socio);

							fpsgRecibo.setMeses(meses);
							fpsgRecibo.setValor(listRecF.get(indice).getValor());

							for (DebitoSocio obfDebSocio : listDebitoSocio) {

								if(obfDebSocio.getSocio().getCodigo()==fpsgRecibo.getSocioDevedor().getCodigo()){
									fpsgRecibo.setValorMensalidade(obfDebSocio.getValormensal());
									fpsgRecibo.setValor(obfDebSocio.getValoroutros());
								}
							}

							for (int j = mesIniCont; j <= ateEsteMes; j++){
								if(meses[j-1]!=0){ 
									devedor=false;
								}
							}
						}
						
						if(devedor==true){
							listaDadosRecibo.add(fpsgRecibo);
						}
						sessao.setAttribute("recibo", fpsgRecibo);
						
					}
					sessao.setAttribute("totalSociosAtivos", totalSociosAtivos);
					sessao.setAttribute("listRecI", listaDadosRecibo);
					sessao.setAttribute("listDebitoSocio", listDebitoSocio);
					sessao.setAttribute("debitoAnterior", debitoAnterior);
				}
			}
			request.setAttribute("totMeses", totMeses+1);
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
