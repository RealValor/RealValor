package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LoginDAO;
import beans.Login;
import beans.Nucleo;

public class ConsultaOperador implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiOperador.jsp";
		String nomeOper = request.getParameter("consulta");
		String codOper  = request.getParameter("cod_oper");
		
		HttpSession sessao = request.getSession();
		Login itemOper = new Login();
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo) sessao.getAttribute("objNucleo");
		
		itemOper.setNucleo(objNucleo);
		
		if((nomeOper!=null&&nomeOper!="")||(codOper!=null&&codOper!="")){	
			if(nomeOper!=null&&nomeOper!=""){	
				itemOper.setNome(nomeOper);
			}
			
			String codOp = request.getParameter("cod_op");
			
			if(codOper!=null&&codOper!=""){
				itemOper.setUsuario(Integer.parseInt(codOper));
			} else if(codOp!=null&&codOp!=""){
				itemOper.setUsuario(Integer.parseInt(codOp));
			}

			Login itemRecF=null;
			LoginDAO daoRecF = new LoginDAO();

			if(itemOper.getCpf()!=0){
				itemRecF = daoRecF.consultarLogin(itemOper);
			}else{
				itemRecF = daoRecF.consultarUsuNome(itemOper);
			}

			mens = "Operador não encontrado: --> "+itemOper.getNome();  

			if(itemRecF!=null){
				itemOper = itemRecF;
				mens = "Operador localizado: "+itemRecF.getNome();  
			}

			request.setAttribute("objOper", itemRecF);
			sessao.setAttribute("objOper", itemRecF);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
