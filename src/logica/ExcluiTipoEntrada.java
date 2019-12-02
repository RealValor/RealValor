package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import DAO.ListaDAO;
import DAO.TipoEntradaDAO;
import beans.Login;
import beans.TipoEntrada;

public class ExcluiTipoEntrada implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEntrada.jsp";
		String desTipEnt = request.getParameter("desc_tipo_entrada");
		
		HttpSession sessao = request.getSession();
		TipoEntrada itemTipEnt = new TipoEntrada();
		
		Login objOperador = new Login(); 
		objOperador = (Login) sessao.getAttribute("objUsu");

		if(desTipEnt!=null&&desTipEnt!=""){	
			itemTipEnt.setDescricao(desTipEnt);
				
			String codCar = request.getParameter("cod_tipo_entrada");
	
			if(codCar!=null&&codCar!=""){
				itemTipEnt.setCodigo(Integer.parseInt(codCar));
			}
			itemTipEnt.setNucleo(objOperador.getNucleo());
			
			TipoEntrada itemRecCar = TipoEntradaDAO.consultarTipoEntrada(itemTipEnt);
			mens = "Cadastro não encontrado: "+itemTipEnt.getDescricao();  

			if(itemRecCar!=null){
				if(HistoricoRecebimentoDAO.VerificaEntradaHistorico(itemRecCar)||ListaDAO.VerificaEntradaLista(itemRecCar)||itemRecCar.getCodigo()<3){
					mens = "A exclusão do item "+itemTipEnt.getDescricao()+" poderá comprometer o acesso a dados históricos. EXCLUSÃO BLOQUEADA!";
				}else{
					TipoEntradaDAO daoAltF = new TipoEntradaDAO();
					daoAltF.excluirTipoEntrada(itemTipEnt);
					mens = "Cadastro excluído: "+itemTipEnt.getDescricao();
					itemTipEnt=null;
				}
			}

			request.setAttribute("objTipEnt", itemTipEnt);
			sessao.setAttribute("objTipEnt", itemTipEnt);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
