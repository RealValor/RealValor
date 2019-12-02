package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.TipoSaidaDAO;
import beans.Login;
import beans.TipoSaida;

public class ExcluiTipoSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoSaida.jsp";
		String desTipSai = request.getParameter("desc_tipo_saida");
		
		HttpSession sessao = request.getSession();
		TipoSaida itemTipSai = new TipoSaida();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if(desTipSai!=null&&desTipSai!=""){	
			itemTipSai.setDescricao(desTipSai);
				
			String codTipSai = request.getParameter("cod_tipo_saida");
	
			if(codTipSai!=null&&codTipSai!=""){
				itemTipSai.setCodigo(Integer.parseInt(codTipSai));
			}
			
			itemTipSai.setNucleo(objOperador.getNucleo());
			
			TipoSaida itemRecCar = TipoSaidaDAO.consultarTipoSaida(itemTipSai);
			mens = "Cadastro não encontrado: "+itemTipSai.getDescricao();  

			if(itemRecCar!=null){
				
				if(TipoSaidaDAO.VerificaTipoSaidaHistorico(itemRecCar)){
					mens = "A exclusão do tipo "+itemRecCar.getDescricao()+" poderá comprometer o acesso a dados históricos. EXCLUSÃO BLOQUEADA!";
				}else{
					TipoSaidaDAO daoAltF = new TipoSaidaDAO(); 
					daoAltF.excluirTipoSaida(itemTipSai);
					mens = "Tipo de Saída excluído: "+itemTipSai.getDescricao();
					itemTipSai=null;
				}
			}

			request.setAttribute("objTipSai", itemTipSai);
			sessao.setAttribute("objTipSai", itemTipSai);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
