package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CargoDAO;
import DAO.DividaDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import DAO.SaidaDAO;
import DAO.SaldoBancoDAO;
import DAO.SaldoCaixaDAO;
import beans.Cargo;
import beans.HistoricoRecebimento;
import beans.MovAtual;
import beans.Nucleo;
import beans.Saida;
import beans.SaldoBanco;
import beans.SaldoCaixa;
import beans.Socio;

public class ConsultaBalancete implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaBalancetePrevio.jsp";
		int controle = 0;

		HttpSession sessao = request.getSession();

		sessao.removeAttribute("objMovFin");

		MovAtual itemMov = new MovAtual();
		MovAtual itemMovFim = new MovAtual();

		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
		itemMov.setNucleo(objNucleo);
		itemMov.setFechado("N");

		String pTipo="";
		if(request.getParameter("tipo")!=null&&request.getParameter("tipo")!="")
			pTipo = request.getParameter("tipo");

		int codAno = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""){
			codAno = Integer.parseInt(request.getParameter("ano"));
			itemMov.setAno(codAno);
		}

		int codMes = 0;

		if(request.getParameter("mes")!=null&&request.getParameter("mes")!=""){
			codMes = Integer.parseInt(request.getParameter("mes"));
		}
		
		if(request.getParameter("mesini")!=null&&request.getParameter("mesini")!=""){
			codMes = Integer.parseInt(request.getParameter("mesini"));
		}

		int codMesFim = 0;
		if(request.getParameter("mesfim")!=null&&request.getParameter("mesfim")!=""){
			codMesFim = Integer.parseInt(request.getParameter("mesfim"));
		}
		
		itemMov = MovAtualDAO.consultarMovAtual(itemMov);

		if(itemMov==null){
			itemMov = new MovAtual();

			itemMov.setNucleo(objNucleo);
			itemMov.setAno(codAno);
			itemMov.setMes(12);

			itemMov = MovAtualDAO.consultarMovAtual(itemMov);
		}

		//------------Data Entrada Inicial e final
		HistoricoRecebimento itemEntradaInicial = new HistoricoRecebimento();
		HistoricoRecebimento itemEntradaFinal 	= new HistoricoRecebimento();
		
		//--------dataEntradaInicial Ano mes e dia---------
		Calendar dataEntradaInicial = Calendar.getInstance();

		dataEntradaInicial.set(Calendar.YEAR, codAno);
		dataEntradaInicial.set(Calendar.MONTH, (codMes-1)); //Calendar.MONTH começa em zero
		dataEntradaInicial.set(Calendar.DAY_OF_MONTH, 1); //(dd/MM/yyyy) - between

		//-------itemEntradaInicial data e Núcleo---------
		itemEntradaInicial.setData(dataEntradaInicial);
		itemEntradaInicial.setNucleo(objNucleo);
		//-------------------------------------------------
		
		//--------dataEntradaFinal Ano mes e dia-----------
		Calendar dataEntradaFinal = Calendar.getInstance();
		
		//System.out.println("codMesFim=codMesFim==0?codMes:codMesFim - codMesFim: "+codMesFim+" codMes: "+codMes);
		
		codMesFim=codMesFim==0?codMes:codMesFim;
		
		dataEntradaFinal.set(Calendar.YEAR, codAno);
		dataEntradaFinal.set(Calendar.MONTH, (codMesFim-1));
		dataEntradaFinal.set(Calendar.DAY_OF_MONTH, dataEntradaFinal.getActualMaximum(Calendar.DAY_OF_MONTH)); //(dd/MM/yyyy) - between

		//-------itemEntradaFinal data e Núcleo---------
		itemEntradaFinal.setData(dataEntradaFinal); 
		itemEntradaFinal.setNucleo(objNucleo);
		//-------------------------------------------------

		HistoricoRecebimentoDAO objEntradaDAO = new HistoricoRecebimentoDAO();
		ArrayList<HistoricoRecebimento> listEntrada = objEntradaDAO.listarHistoricoPeriodo(itemEntradaInicial, itemEntradaFinal);	
		//----------------------------------------
		

		//------------Saída Inicial e final----------------
		Saida itemSaidaInicial = new Saida();
		Saida itemSaidaFinal = new Saida();
		
		//--------dataSaidaInicial Ano mes e dia-----------
		Calendar dataSaidaInicial = Calendar.getInstance();
		
		dataSaidaInicial.set(Calendar.YEAR, codAno);
		dataSaidaInicial.set(Calendar.MONTH, (codMes-1));
		dataSaidaInicial.set(Calendar.DAY_OF_MONTH, 1);

		//-------itemSaidaInicial data e Núcleo------------
		itemSaidaInicial.setData(dataSaidaInicial);
		itemSaidaInicial.setNucleo(objNucleo);
		//-------------------------------------------------

		//--------dataSaidaFinal Ano mes e dia-----------
		Calendar dataSaidaFinal = Calendar.getInstance();
		
		dataSaidaFinal.set(Calendar.YEAR, codAno);
		dataSaidaFinal.set(Calendar.MONTH, (codMesFim-1)); //Calendar.MONTH começa em zero
		dataSaidaFinal.set(Calendar.DAY_OF_MONTH, 1); 

		//-------itemSaidaInicial data e Núcleo------------
		itemSaidaFinal.setData(dataSaidaFinal); //itemEntradaFinal irá tratar data (dd/MM/yyyy) - between
		itemSaidaFinal.setNucleo(objNucleo);
		//-------------------------------------------------
		
		SaidaDAO objSaidaDAO = new SaidaDAO();
		ArrayList<Saida> listSaida = objSaidaDAO.listarSaidaPeriodoMensal(itemSaidaInicial,itemSaidaFinal);
		//-------------------------------------------------
		
		if(codMes!=0){
			sessao.setAttribute("listEntrada", listEntrada);
			sessao.setAttribute("listSaida", listSaida);
		}

		if (!pTipo.equalsIgnoreCase("previo")){ //finalizado

			itemMov.setMes(itemMov.getMes()-1); //exclui o mês ainda não finalizado

			if(itemMov.getMes()<=0){
				itemMov.setAno(itemMov.getAno()-1);
				itemMov.setMes(12);
			}
		}

		ArrayList<MovAtual> listObjMov = new ArrayList<MovAtual>();		

		int count = itemMov.getMes();
		
		if(pTipo.equalsIgnoreCase("periodo")){
			for (int i = 1; i <= count; i++) {
				MovAtual itemMovAux = new MovAtual();
				itemMovAux.setMes(i);
				listObjMov.add(itemMovAux);
			}
		}else{
			for (int i = count; i >= 1; i--) {
				MovAtual itemMovAux = new MovAtual();
				itemMovAux.setMes(i);
				listObjMov.add(itemMovAux);
			}
		}

		sessao.setAttribute("listObjMov", listObjMov);

		ArrayList<Cargo> listCargoPresidente = (ArrayList<Cargo>) sessao.getAttribute("listCargoPresidente");
		ArrayList<Cargo> listCargoTesoureiro = (ArrayList<Cargo>) sessao.getAttribute("listCargoTesoureiro");
		ArrayList<Cargo> listCargoPresFiscal = (ArrayList<Cargo>) sessao.getAttribute("listCargoPresFiscal");

		if(!pTipo.equalsIgnoreCase("previo")){

			if(pTipo.equalsIgnoreCase("periodo")){
				pgJsp = "/consultaBalancetePeriodo.jsp";
			}else{
				pgJsp = "/consultaBalanceteFinalizado.jsp";
			}

			if(listCargoPresidente == null){
				listCargoPresidente = CargoDAO.listarCargoFiltro("President"); 
			}

			if(listCargoTesoureiro == null){
				listCargoTesoureiro = CargoDAO.listarCargoFiltro("Tesoureir");
			}

			if(listCargoPresFiscal == null){
				listCargoPresFiscal = CargoDAO.listarCargoFiltro("Fiscal");
				for (Cargo cargo : listCargoPresFiscal) {
					for (int i = 0; i < listCargoPresidente.size(); i++) {
						if(listCargoPresidente.get(i).getDescricao().equalsIgnoreCase(cargo.getDescricao())){
							listCargoPresidente.remove(i);
						}
					}
				}
			}
		}
		
		if(codAno!=0&&codMes!=0){
			itemMov.setAno(codAno);
			itemMov.setMes(codMes);
		}

		String mes="";
		if(Integer.toString(itemMov.getMes()).length()<2) mes="0";

		mes=mes+Integer.toString(itemMov.getMes());
		String paramData = Integer.toString(itemMov.getAno())+"-"+mes+"-01";

		if(!pTipo.equalsIgnoreCase("previo")){

			//--------------Trata presidente---------------------------------------------
			Cargo objCargoPresidente = (Cargo) sessao.getAttribute("objCargoPresidente");
			if(objCargoPresidente == null){
				objCargoPresidente = new Cargo();
				objCargoPresidente.setCodigo(listCargoPresidente.get(0).getCodigo());
			}

			Socio socioPresidente = (Socio) sessao.getAttribute("socioPresidente");
			if(socioPresidente == null){
				socioPresidente = CargoDAO.consultarNomeFiltro(listCargoPresidente.get(0).getCodigo(), paramData, objNucleo.getCodigo());
			}

			if(request.getParameter("cargo_presidente")!=null&&request.getParameter("cargo_presidente")!=""){
				objCargoPresidente.setCodigo(Integer.parseInt(request.getParameter("cargo_presidente")));
				socioPresidente = CargoDAO.consultarNomeFiltro(Integer.parseInt(request.getParameter("cargo_presidente")), paramData, objNucleo.getCodigo());
			}
			objCargoPresidente = CargoDAO.consultarCargo(objCargoPresidente);
			//---------------------------------------------------------------------------

			//--------------Trata tesoureiro---------------------------------------------
			Cargo objCargoTesoureiro = (Cargo) sessao.getAttribute("objCargoTesoureiro");
			if(objCargoTesoureiro == null){
				objCargoTesoureiro = new Cargo();
				objCargoTesoureiro.setCodigo(listCargoTesoureiro.get(0).getCodigo());
			}

			Socio socioTesoureiro = (Socio) sessao.getAttribute("socioTesoureiro");
			if(socioTesoureiro == null){
				socioTesoureiro = CargoDAO.consultarNomeFiltro(listCargoTesoureiro.get(0).getCodigo(), paramData, objNucleo.getCodigo());
			}

			if(request.getParameter("cargo_tesoureiro")!=null&&request.getParameter("cargo_tesoureiro")!=""){
				socioTesoureiro = CargoDAO.consultarNomeFiltro(Integer.parseInt(request.getParameter("cargo_tesoureiro")), paramData, objNucleo.getCodigo());
				objCargoTesoureiro.setCodigo(Integer.parseInt(request.getParameter("cargo_tesoureiro")));
			}
			objCargoTesoureiro = CargoDAO.consultarCargo(objCargoTesoureiro);
			//---------------------------------------------------------------------------

			//--------------Trata presid. cons. fiscal-----------------------------------
			Cargo objCargoConsFiscal = (Cargo) sessao.getAttribute("objCargoConsFiscal");
			if(objCargoConsFiscal == null){
				objCargoConsFiscal = new Cargo();
				objCargoConsFiscal.setCodigo(listCargoPresFiscal.get(0).getCodigo());
			}

			Socio socioPresFiscal = (Socio) sessao.getAttribute("socioPresFiscal");
			if(socioPresFiscal == null){
				socioPresFiscal = CargoDAO.consultarNomeFiltro(listCargoPresFiscal.get(0).getCodigo(), paramData, objNucleo.getCodigo());
			}

			if(request.getParameter("cargo_prconsfiscal")!=null&&request.getParameter("cargo_prconsfiscal")!=""){
				socioPresFiscal = CargoDAO.consultarNomeFiltro(Integer.parseInt(request.getParameter("cargo_prconsfiscal")), paramData, objNucleo.getCodigo());
				objCargoConsFiscal.setCodigo(Integer.parseInt(request.getParameter("cargo_prconsfiscal")));
			}
			objCargoConsFiscal = CargoDAO.consultarCargo(objCargoConsFiscal);
			//---------------------------------------------------------------------------

			sessao.setAttribute("socioPresidente", socioPresidente);
			sessao.setAttribute("socioTesoureiro", socioTesoureiro);
			sessao.setAttribute("socioPresFiscal", socioPresFiscal);

			sessao.setAttribute("objCargoPresidente", objCargoPresidente);
			sessao.setAttribute("objCargoTesoureiro", objCargoTesoureiro);
			sessao.setAttribute("objCargoConsFiscal", objCargoConsFiscal);

			sessao.setAttribute("listCargoPresidente", listCargoPresidente);
			sessao.setAttribute("listCargoTesoureiro", listCargoTesoureiro);
			sessao.setAttribute("listCargoPresFiscal", listCargoPresFiscal);

		}

		if(codAno!=0&&codMes!=0){
			controle++;
			itemMov.setAno(codAno); //Atualiza ano para impressão do balancete.

			SaldoCaixa itemSaldoAnterior = new SaldoCaixa();
			itemSaldoAnterior.setAno(codAno);
			itemSaldoAnterior.setMes(codMes);

			itemSaldoAnterior.setNucleo(objNucleo);

			//busca historico de caixa para o mes e ano do movimento formato: 01/2011 ant_caixa;ant_banco;ant_divida
			itemSaldoAnterior = SaldoCaixaDAO.consultarSaldoCaixa(itemSaldoAnterior);
			
			/*
			if(itemSaldoAnterior==null){
				controle = 0;
				mens = "Não existe saldo anterior para o mês "+codMes+" de "+codAno;  
			}
			*/
			
			if(!pTipo.equalsIgnoreCase("periodo")){
				//se não for por periodo
				codMesFim=codMes;
			}

			double dividaAbertaNoMes = 0;
			double dividaPagaNoMes = 0;
			double saldoBanco = 0;

			SaldoBanco itemSaldoBanco = new SaldoBanco();
			itemSaldoBanco.setAno(codAno);
			itemSaldoBanco.setMes(codMesFim);

			itemSaldoBanco.setNucleo(objNucleo);

			ArrayList<SaldoBanco> listSaldoBanco = new ArrayList<SaldoBanco>();

			listSaldoBanco = SaldoBancoDAO.listarSaldoBanco(itemSaldoBanco);
		
			if(listSaldoBanco!=null){
				for (SaldoBanco valorSaldo : listSaldoBanco) {
					saldoBanco = saldoBanco+valorSaldo.getValor();
					//totaliza saldo atual em bancos
				}
				sessao.setAttribute("tamanhoLista", listSaldoBanco.size());
			}
			
			DividaDAO objDivDAO = new DividaDAO();

			dividaAbertaNoMes = objDivDAO.somaDividaAbertaPeriodo(codMes,codMesFim,codAno,objNucleo.getCodigo());
			dividaPagaNoMes = objDivDAO.somaDividaPagaPeriodo(codMes,codMesFim,codAno,objNucleo.getCodigo());

			sessao.setAttribute("objSaldoAnt", itemSaldoAnterior);
			
			sessao.setAttribute("saldoBanco", saldoBanco);
			sessao.setAttribute("listaSaldoBanco", listSaldoBanco);

			sessao.setAttribute("dividaAberta", dividaAbertaNoMes);
			sessao.setAttribute("dividaPaga", dividaPagaNoMes);

			itemMovFim.setMes(codMesFim);
			sessao.setAttribute("objMovFin", itemMovFim);
		}

		itemMov.setMes(codMes);
		sessao.setAttribute("objMovIni", itemMov);

		sessao.setAttribute("controle", controle);
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}