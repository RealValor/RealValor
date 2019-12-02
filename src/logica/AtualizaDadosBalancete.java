package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CargoDAO;
import DAO.MovAtualDAO;
import beans.Cargo;
import beans.MovAtual;
import beans.Nucleo;
import beans.Socio;

public class AtualizaDadosBalancete implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaBalanceteFinalizado.jsp";
		int controle = 0;

		HttpSession sessao = request.getSession();

		Nucleo objNucleo = new Nucleo(); 
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
		
		String pTipo="";
		if(request.getParameter("tipo")!=null&&request.getParameter("tipo")!="")
			pTipo = request.getParameter("tipo");

		if(pTipo.equalsIgnoreCase("periodo")){
			pgJsp = "/consultaBalancetePeriodo.jsp";
		}

		if(pTipo.equalsIgnoreCase("previo")){
			pgJsp = "/consultaBalancetePrevio.jsp";
		}

		Calendar data = Calendar.getInstance();
		
		int codMes = 1;
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!="")
			codMes = Integer.parseInt(request.getParameter("mes"));		
		
		int codMesFim = 1;
		if(request.getParameter("mesfim")!=null&&request.getParameter("mesfim")!="")
			codMesFim = Integer.parseInt(request.getParameter("mesfim"));		
		
		int codAno = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""&&codMes!=0){
			codAno = Integer.parseInt(request.getParameter("ano"));
		}
		
		codAno=(codAno==0?data.get(Calendar.YEAR):codAno);

		MovAtual itemMov = new MovAtual();
		itemMov.setNucleo(objNucleo);
		itemMov.setAno(codAno);
		itemMov.setFechado("N");
		itemMov = MovAtualDAO.consultarMovAtual(itemMov);

		if(itemMov==null){

			itemMov = new MovAtual();
			
			itemMov.setNucleo(objNucleo);
			itemMov.setAno(codAno);
			itemMov.setMes(12);
			
			itemMov = MovAtualDAO.consultarMovAtual(itemMov);
			
			if(itemMov==null){
				do {
					
					itemMov = new MovAtual();
					codAno--;

					itemMov.setNucleo(objNucleo);
					itemMov.setAno(codAno);
					itemMov.setFechado("N");
					
					itemMov = MovAtualDAO.consultarMovAtual(itemMov);
					
				} while (itemMov.getMes()<=0&&codAno>(data.get(Calendar.YEAR)-3));
				//Busca o mes em aberto em até três anos. 
			}
		}

		if ((!pTipo.equalsIgnoreCase("previo"))&&itemMov.getFechado().equalsIgnoreCase("N")){

			itemMov.setMes(itemMov.getMes()-1); //exclui o mês ainda não finalizado
			
			if(itemMov.getMes()==0){
				itemMov.setAno(itemMov.getAno()-1);
				itemMov.setMes(12);
			}
		}

		MovAtual itemMovFim = new MovAtual();
		itemMovFim = MovAtualDAO.consultarMovAtual(itemMov);
		
		ArrayList<MovAtual> listObjMov = new ArrayList<MovAtual>();		

		/*
		for (int i = 1; i <= itemMov.getMes(); i++) {
			MovAtual itemMovAux = new MovAtual();
			itemMovAux.setMes(i);
			listObjMov.add(itemMovAux);
		}
		*/
		
		//------------------------
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
		//------------------------
		
		sessao.setAttribute("listObjMov", listObjMov);

		Socio socioPresidente = new Socio();
		Socio socioTesoureiro = new Socio();
		Socio socioPresFiscal = new Socio();

		Cargo objCargoPresidente = new Cargo();
		Cargo objCargoTesoureiro = new Cargo();
		Cargo objCargoConsFiscal = new Cargo();
		
		ArrayList<Cargo> listCargoPresidente = new ArrayList<Cargo>();
		ArrayList<Cargo> listCargoTesoureiro = new ArrayList<Cargo>();
		ArrayList<Cargo> listCargoPresFiscal = new ArrayList<Cargo>();

		listCargoPresidente = CargoDAO.listarCargoFiltro("President"); 
		listCargoTesoureiro = CargoDAO.listarCargoFiltro("Tesoureir"); //Caso seja cadastrado Tesoureira
		listCargoPresFiscal = CargoDAO.listarCargoFiltro("Fiscal");
		
		for (Cargo cargo : listCargoPresFiscal) {
			for (int i = 0; i < listCargoPresidente.size(); i++) {
				if(listCargoPresidente.get(i).getDescricao().equalsIgnoreCase(cargo.getDescricao())){
					listCargoPresidente.remove(i);
				}
			}
		}
		
		String mes="";
		if(Integer.toString(itemMov.getMes()).length()<2) mes="0";
		
		mes=mes+Integer.toString(itemMov.getMes());
		String paramData = Integer.toString(itemMov.getAno())+"-"+codMesFim+"-01";
		
		//System.out.println("paramData "+paramData);

		if(request.getParameter("cargo_presidente")!=null&&request.getParameter("cargo_presidente")!=""){
			socioPresidente = CargoDAO.consultarNomeFiltro(Integer.parseInt(request.getParameter("cargo_presidente")), paramData, objNucleo.getCodigo());
			objCargoPresidente.setCodigo(Integer.parseInt(request.getParameter("cargo_presidente")));
		}else{
			socioPresidente = CargoDAO.consultarNomeFiltro(listCargoPresidente.get(0).getCodigo(), paramData, objNucleo.getCodigo());
			objCargoPresidente.setCodigo(listCargoPresidente.get(0).getCodigo());
		}
		objCargoPresidente = CargoDAO.consultarCargo(objCargoPresidente);

		if(request.getParameter("cargo_tesoureiro")!=null&&request.getParameter("cargo_tesoureiro")!=""){
			socioTesoureiro = CargoDAO.consultarNomeFiltro(Integer.parseInt(request.getParameter("cargo_tesoureiro")), paramData, objNucleo.getCodigo());
			objCargoTesoureiro.setCodigo(Integer.parseInt(request.getParameter("cargo_tesoureiro")));
		}else{
			socioTesoureiro = CargoDAO.consultarNomeFiltro(listCargoTesoureiro.get(0).getCodigo(), paramData, objNucleo.getCodigo());
			objCargoTesoureiro.setCodigo(listCargoTesoureiro.get(0).getCodigo());
		}
		objCargoTesoureiro = CargoDAO.consultarCargo(objCargoTesoureiro);

		if(request.getParameter("cargo_prconsfiscal")!=null&&request.getParameter("cargo_prconsfiscal")!=""){
			socioPresFiscal = CargoDAO.consultarNomeFiltro(Integer.parseInt(request.getParameter("cargo_prconsfiscal")), paramData, objNucleo.getCodigo());
			objCargoConsFiscal.setCodigo(Integer.parseInt(request.getParameter("cargo_prconsfiscal")));
		}else{
			socioPresFiscal = CargoDAO.consultarNomeFiltro(listCargoPresFiscal.get(0).getCodigo(), paramData, objNucleo.getCodigo());
			objCargoConsFiscal.setCodigo(listCargoPresFiscal.get(0).getCodigo());
		}
		objCargoConsFiscal = CargoDAO.consultarCargo(objCargoConsFiscal);
		
		sessao.setAttribute("listCargoPresidente", listCargoPresidente);
		sessao.setAttribute("listCargoTesoureiro", listCargoTesoureiro);
		sessao.setAttribute("listCargoPresFiscal", listCargoPresFiscal);
		
		sessao.setAttribute("socioPresidente", socioPresidente);
		sessao.setAttribute("socioTesoureiro", socioTesoureiro);
		sessao.setAttribute("socioPresFiscal", socioPresFiscal);
		
		sessao.setAttribute("objCargoPresidente", objCargoPresidente);
		sessao.setAttribute("objCargoTesoureiro", objCargoTesoureiro);
		sessao.setAttribute("objCargoConsFiscal", objCargoConsFiscal);
		
		itemMov.setMes(codMes);
		sessao.setAttribute("objMovIni", itemMov);
		
		itemMovFim.setMes(codMesFim);
		sessao.setAttribute("objMovFin", itemMovFim);
		
		sessao.setAttribute("controle", controle);
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
