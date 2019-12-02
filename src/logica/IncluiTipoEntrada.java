package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.TipoEntradaDAO;
import beans.Login;
import beans.TipoEntrada;

public class IncluiTipoEntrada implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String desTipEnt = request.getParameter("desc_tipo_entrada");
		String pgJsp = "/incluiTipoEntrada.jsp";
		
		HttpSession sessao = request.getSession();
		TipoEntrada itemTipEnt = new TipoEntrada();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if(desTipEnt!=null&&desTipEnt!=""){	
			itemTipEnt.setDescricao(desTipEnt);

			String codTipEnt = request.getParameter("cod_tipo_entrada");
			String vlrTipEnt = request.getParameter("valor_tipo_entrada");
			String mensTipEnt = request.getParameter("mens_tipo_entrada");
			String ativoTipEnt = request.getParameter("ativo_tipo_entrada");
			
			if(codTipEnt!=null&&codTipEnt!=""){
				itemTipEnt.setCodigo(Integer.parseInt(codTipEnt));
			}
			
			if(vlrTipEnt!=null&&vlrTipEnt!=""){
				itemTipEnt.setValor(Double.parseDouble(vlrTipEnt));
			}

			itemTipEnt.setMensal("N");
			if(mensTipEnt!=null&&mensTipEnt!=""){
				itemTipEnt.setMensal(mensTipEnt);
			}

			itemTipEnt.setAtivo("S");
			if(ativoTipEnt!=null&&ativoTipEnt!=""){
				itemTipEnt.setAtivo(ativoTipEnt);
			}

			itemTipEnt.setNucleo(objOperador.getNucleo());
			
			TipoEntrada itemRecTipEnt = TipoEntradaDAO.consultarTipoEntrada(itemTipEnt);
			mens = "TipoEntrada já cadastrado: "+itemTipEnt.getDescricao();  

			
			if(itemRecTipEnt==null){
				TipoEntradaDAO daoIncCar = new TipoEntradaDAO();

				daoIncCar.incluirTipoEntrada(itemTipEnt);
				mens = itemTipEnt.getDescricao()+" Incluído! ";
			}

			//request.setAttribute("objTipEnt", itemRecTipEnt);
			sessao.setAttribute("objTipEnt", itemRecTipEnt);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
