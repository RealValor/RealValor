package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.Recibo;
import beans.Socio;
import beans.SocioValor;
import beans.TipoEntrada;


import DAO.HistoricoRecebimentoDAO;
import DAO.SocioDAO;
import DAO.SocioValorDAO;
import DAO.TipoEntradaDAO;

public class EstatisticaInadimplencia implements LogicaDeNegocio{ //Modificar esta classe. Resposta muito lenta!

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/estatisticaInadimplencia.jsp";
		String codAno=null;
		String totMeses=null;
		
		if(request.getParameter("ano_paga")!=null&&request.getParameter("ano_paga")!="")
			codAno   = request.getParameter("ano_paga");

		if(request.getParameter("tot_meses")!=null&&request.getParameter("tot_meses")!="")
			totMeses = request.getParameter("tot_meses");

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
		
		if(codAno!=null){
			itemMovIni.setAno(Integer.parseInt(codAno)); 
		}
		
		sessao.setAttribute("objMovIni", itemMovIni);
		request.setAttribute("totMeses", 0);
		
		if(totMeses!=null){	
			int totalMeses = Integer.parseInt(totMeses); //quantidade mínima de meses em aberto
			
			Socio itemDeve = new Socio();
			
			itemDeve.setCpf(0);
			itemDeve.setNome(null);

			sessao.removeAttribute("listRecI");
			Login objLogin = new Login();

			int ateEsteMes = 0; //EXCLUIR ESTA LINHA E SUAS CONSEQUENCIAS!
			if( ateEsteMes - totalMeses >= 0 ){

				Login objOperador = new Login();
				objOperador = (Login)sessao.getAttribute("objUsu");

				String pTipo = "ativo";
				ArrayList<Socio> listObjSoc = new ArrayList<Socio>();
				listObjSoc = SocioDAO.listarSocio(objOperador.getNucleo().getCodigo(), itemDeve, pTipo,0);

				ArrayList<HistoricoRecebimento> listRecF=null;
				ArrayList<Recibo> listaDadosRecibo = new ArrayList<Recibo>();

				int mesIniCont=(ateEsteMes - (totalMeses-1));
				
				int totalSociosAtivos = SocioDAO.buscarTotalSocioAtivo(objOperador.getNucleo().getCodigo());
				
				listRecF = HistoricoRecebimentoDAO.listarHistoricoOrdenadoPorGrau(objOperador.getNucleo(), null,2,Integer.parseInt(codAno));
				//baseado em F.P.S.G. - tipo: ,2,
				
				int iniciouPagamento=0;
				for (Socio socio : listObjSoc) { //for para todos os sócios

					int meses[] = null;
					Recibo fpsgRecibo = null;
					meses = new int[12];
					boolean devedor=true;
					int indice=-1;
					
					for (int j = 0; j <= 11; j++){
						meses[j]=0; // 0 == atraso 
					}
					
					//procura o sócio na lista
					for (HistoricoRecebimento historico : listRecF) {
						
						iniciouPagamento = -1;
						
						if(socio.getCodigo()==historico.getSocioDevedor().getCodigo()){
							indice = listRecF.indexOf(historico);
							iniciouPagamento = 0; //identifica que o socio pagaou pelo menos um mes
							break; //encontra o sócio, memoriza o índice e quebra o loop
						}
					}
					
					objLogin.setUsuario(socio.getCodigo());
					if(iniciouPagamento < 0 && HistoricoRecebimentoDAO.listarHistoricoRecebimento(objLogin,2,Integer.parseInt(codAno)-1)!=null)
						iniciouPagamento = 0; //condição verifica existencia do socio no ano anterior, se nada pagou no ano atual.
					
					if(indice==-1){//<------ não encontrou o sócio

						if(iniciouPagamento!=0){
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

						SocioValor socVal = new SocioValor();
						socVal = SocioValorDAO.consultarVal(socio.getCodigo(),1);

						if(socVal!=null){
							fpsgRecibo.setValorMensalidade(socVal.getValor());
						}else{
							fpsgRecibo.setValorMensalidade(TipoEntradaDAO.consultarTipoEntrada(objEntrada).getValor());
						}
						objEntrada.setCodigo(2);
						fpsgRecibo.setValor(TipoEntradaDAO.consultarTipoEntrada(objEntrada).getValor());
						
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
							if(meses[j]>0){
								break;
							}else{
								meses[j]=-1; //-1 == ainda não associado no mes
							}
						}
						
						TipoEntrada objEntrada = new TipoEntrada();
						objEntrada.setCodigo(1);

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

						SocioValor socVal = new SocioValor();
						socVal = SocioValorDAO.consultarVal(socio.getCodigo(),1);

						if(socVal!=null){
							fpsgRecibo.setValorMensalidade(socVal.getValor());
						}else{
							fpsgRecibo.setValorMensalidade(TipoEntradaDAO.consultarTipoEntrada(objEntrada).getValor());
						}
						for (int j = mesIniCont; j <= ateEsteMes; j++){
							if(meses[j-1]>0){ //meses[j-1]!=0
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
			}
			request.setAttribute("totMeses", totMeses+1);
			mens = "Listagem de inadimplentes";
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
