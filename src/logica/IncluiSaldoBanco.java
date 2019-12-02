package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Banco;
import beans.Login;
import beans.MovAtual;
import beans.SaldoBanco;


import DAO.BancoDAO;
import DAO.MovAtualDAO;
import DAO.SaldoBancoDAO;

public class IncluiSaldoBanco implements LogicaDeNegocio{

	@SuppressWarnings("null")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String codBan = request.getParameter("banco");
		String pgJsp = "/incluiSaldoBanco.jsp";
		
		HttpSession sessao = request.getSession();
		
		ArrayList<Banco> listBan = BancoDAO.listarBanco();
		if(listBan != null){

			sessao.setAttribute("listBan", listBan);
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			MovAtual itemMov = new MovAtual();
			MovAtual itemRecMov = new MovAtual();
		
			Calendar data = Calendar.getInstance();

			itemMov.setNucleo(objOperador.getNucleo());
			itemMov.setFechado("N");
			itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
			
			if(itemRecMov==null){
				itemRecMov.setAno(data.get(Calendar.YEAR));
				itemRecMov.setMes(data.get(Calendar.MONTH)+1);
			}
			
			SaldoBanco itemSaldo = new SaldoBanco();
			/*
			if(sessao.getAttribute("objSal")!=null){
				itemSaldo = (SaldoBanco)sessao.getAttribute("objSal");
			}
			*/
			if(itemSaldo.getBanco()==null){
				itemSaldo.setBanco(listBan.get(0));
			}
			
			itemSaldo.setAno(itemRecMov.getAno());
			itemSaldo.setMes(itemRecMov.getMes());
			itemSaldo.setNucleo(objOperador.getNucleo());
			
			Banco itemBanco = new Banco();

			if(codBan!=null&&codBan!=""){
				
				itemBanco.setCodigo(Integer.parseInt(codBan));
				itemBanco = BancoDAO.consultarBanco(itemBanco);
				
				itemSaldo.setBanco(itemBanco);
				int numAno;
				int numMes;
				double numSal;
				
				if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""){
					numAno = Integer.parseInt(request.getParameter("ano"));
					itemSaldo.setAno(numAno);
				}
				
				if(request.getParameter("mes")!=null&&request.getParameter("mes")!=""){
					numMes = Integer.parseInt(request.getParameter("mes"));
					itemSaldo.setMes(numMes);
				}
				
				numSal = 0;
				if(request.getParameter("saldo")!=null&&request.getParameter("saldo")!=""){
					numSal = Double.parseDouble(request.getParameter("saldo"));
				}
				itemSaldo.setValor(numSal);
				
				SaldoBanco itemRecSal = SaldoBancoDAO.consultarSaldoBanco(itemSaldo);
				mens = "Saldo já incluído para o mes especificado, escolha alterar saldo"; 
				
				if(itemRecSal==null){
					SaldoBancoDAO daoIncSal = new SaldoBancoDAO();
					daoIncSal.incluirSaldoBanco(itemSaldo);
					mens = " Incluído saldo mensal para "+itemSaldo.getBanco().getDescricao();
				}
			}
			
			//----------------------------------------
			itemSaldo.setBanco(itemBanco);
			
			ArrayList<SaldoBanco> listSal = SaldoBancoDAO.listarSaldoBanco(itemSaldo);
			if(listSal != null){
				request.setAttribute("listSal", listSal);		                
			}
			//----------------------------------------

			request.setAttribute("objSal", itemSaldo);
			//sessao.setAttribute("objSal", itemSaldo);
		}else{
			mens="A inclusão de saldos exige cadastro bancos";
		}
				
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
