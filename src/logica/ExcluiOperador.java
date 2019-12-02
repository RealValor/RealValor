package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;


import DAO.LoginDAO;

public class ExcluiOperador implements LogicaDeNegocio{ 
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiOperador.jsp";
		String nomeOper = request.getParameter("consulta");
		String cpfOper = request.getParameter("cpf_oper");
		
		HttpSession sessao = request.getSession();
		Login itemOper = new Login();
		
		if((nomeOper!=null&&nomeOper!="")||(cpfOper!=null&&cpfOper!="")){	
			itemOper.setNome(nomeOper);

			String codOp = request.getParameter("cod_op");
			String codOper = request.getParameter("cod_oper");			
			String senhaOper = request.getParameter("senha_oper");
			
			if(codOper!=null&&codOper!=""){
				itemOper.setUsuario(Integer.parseInt(codOper));
			} else if(codOp!=null&&codOp!=""){
				itemOper.setUsuario(Integer.parseInt(codOp));
			}
			
			if(senhaOper!=null&&senhaOper!="0"){	
				itemOper.setSenha(senhaOper);
			}

			if(cpfOper!=null&&cpfOper!=""){	
				itemOper.setCpf(Long.parseLong(cpfOper));
			}

			Login itemRecF=null;
			LoginDAO daoRecF = new LoginDAO();

			//System.out.println("operador: "+itemOper.getCpf()+" cod: "+itemOper.getUsuario());
			
			if(itemOper.getUsuario()!=0){
				itemRecF = daoRecF.consultarOperador(itemOper);
			}else{
				itemRecF = daoRecF.consultarUsuNome(itemOper);
			}
			
			mens = "Cadastro não encontrado: "+itemOper.getCpf();
			
			if(itemRecF!=null){
				mens = "Cadastro excluído: "+itemRecF.getCpf();
				LoginDAO daoIncF = new LoginDAO();
				daoIncF.excluirLogin(itemRecF);
				itemOper=null;
			}
			request.setAttribute("objOper", itemOper);
			sessao.setAttribute("objOper", itemOper);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
