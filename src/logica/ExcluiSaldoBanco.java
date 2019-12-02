package logica;

import java.util.ArrayList;

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

public class ExcluiSaldoBanco implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiSaldoBanco.jsp";
		String codBan  = request.getParameter("banco");

		HttpSession sessao = request.getSession();
		SaldoBanco itemSaldo = new SaldoBanco();
		
		if(codBan!=null&&codBan!=""){

			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			Banco itemBanco = new Banco();
			itemBanco.setCodigo(Integer.parseInt(codBan));
			itemBanco = BancoDAO.consultarBanco(itemBanco);
			
			itemSaldo.setBanco(itemBanco);
			int numAno=0;
			int numMes=0;
			double numSal;
			
			if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""){
				numAno = Integer.parseInt(request.getParameter("ano"));
				itemSaldo.setAno(numAno);
			}
			
			if(request.getParameter("mes")!=null&&request.getParameter("mes")!=""){
				numMes = Integer.parseInt(request.getParameter("mes"));
				itemSaldo.setMes(numMes);
			}
			
			if(request.getParameter("saldo")!=null&&request.getParameter("saldo")!=""){
				numSal = Double.parseDouble(request.getParameter("saldo"));
				itemSaldo.setValor(numSal);
			}
			
			itemSaldo.setNucleo(objOperador.getNucleo());

			SaldoBanco itemRecSal = SaldoBancoDAO.consultarSaldoBanco(itemSaldo);

			if(itemRecSal!=null){
				
				MovAtual itemMov = new MovAtual();
				itemMov.setFechado("N");
				itemMov.setNucleo(objOperador.getNucleo());
				MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
				
				if(itemRecSal.getAno()>=itemRecMov.getAno()&&itemRecSal.getMes()==itemRecMov.getMes()){
					SaldoBancoDAO daoAltF = new SaldoBancoDAO(); 
					daoAltF.excluirSaldoBanco(itemRecSal);
					mens = " Saldo excluído: "+itemSaldo.getBanco().getDescricao()+" "+itemSaldo.getValor();

					itemSaldo.setAno(0);
					itemSaldo.setMes(0);
					itemSaldo.setValor(0);
				}else{
					mens = "Exclusão bloqueada para "+ConverteMes.numericoExtenso(itemSaldo.getMes())+"/"+itemSaldo.getAno()+". Este procedimento só é aplicável para o mês em aberto: "+ConverteMes.numericoExtenso(itemRecMov.getMes())+"/"+itemRecMov.getAno();
				}

				//----------------------------------------
				
				itemSaldo.setAno(numAno);
				itemSaldo.setMes(numMes);
				itemSaldo.setBanco(itemBanco);
				
				ArrayList<SaldoBanco> listSal = SaldoBancoDAO.listarSaldoBanco(itemSaldo);
				if(listSal != null){
					request.setAttribute("listSal", listSal);		                
				}
				//----------------------------------------

				request.setAttribute("objSal", itemSaldo);
				sessao.setAttribute("objSal", itemSaldo);
				
			}
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
