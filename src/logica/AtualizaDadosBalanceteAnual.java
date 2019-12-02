package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cargo;
import beans.MovAtual;
import beans.Nucleo;
import beans.Socio;


import DAO.CargoDAO;
import DAO.MovAtualDAO;

public class AtualizaDadosBalanceteAnual implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaBalanceteAnual.jsp";
		int controle = 0;

		HttpSession sessao = request.getSession();
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		int codAno = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			codAno = Integer.parseInt(request.getParameter("ano"));
		
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
		listCargoTesoureiro = CargoDAO.listarCargoFiltro("Tesoureir");
		listCargoPresFiscal = CargoDAO.listarCargoFiltro("Fiscal");
		
		for (Cargo cargo : listCargoPresFiscal) {
			for (int i = 0; i < listCargoPresidente.size(); i++) {
				if(listCargoPresidente.get(i).getDescricao().equalsIgnoreCase(cargo.getDescricao())){
					listCargoPresidente.remove(i);
				}
			}
		}
		MovAtual itemMov = new MovAtual();
		itemMov.setNucleo(objNucleo);
		
		if(codAno==0){
			itemMov.setFechado("N");
			itemMov = MovAtualDAO.consultarMovAtual(itemMov);
			
			if(itemMov.getFechado().equalsIgnoreCase("N")&&itemMov.getMes()<2){
				itemMov.setAno(itemMov.getAno()-1); //Janeiro está em aberto para o ano encontrado.
			}
		}else{
			itemMov.setAno(codAno);
			itemMov.setMes(12);
		}

		String mes="";
		if(Integer.toString(itemMov.getMes()).length()<2) mes="0";
		//melhorar esse código.
		mes=mes+Integer.toString(itemMov.getMes());
		String paramData = Integer.toString(itemMov.getAno())+"-"+mes+"-01";
		
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
		
		itemMov.setMes(1);
		sessao.setAttribute("objMovIni", itemMov);
		
		sessao.setAttribute("controle", controle);
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
