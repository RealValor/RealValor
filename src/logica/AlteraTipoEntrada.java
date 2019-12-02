package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ReciboDAO;
import DAO.SocioValorDAO;
import DAO.TipoEntradaDAO;
import beans.Login;
import beans.SocioValor;
import beans.TipoEntrada;

public class AlteraTipoEntrada implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEntrada.jsp";
		String desTipEnt = request.getParameter("desc_tipo_entrada");
		
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
			
			if(mensTipEnt!=null&&mensTipEnt!=""){
				itemTipEnt.setMensal(mensTipEnt);
			}

			if(ativoTipEnt!=null&&ativoTipEnt!=""){
				itemTipEnt.setAtivo(ativoTipEnt);
			}
			
			itemTipEnt.setNucleo(objOperador.getNucleo());
			
			TipoEntrada itemRTipEnt = TipoEntradaDAO.consultarTipoEntrada(itemTipEnt);
			mens = "Cadastro não encontrado: "+itemTipEnt.getDescricao();  

			if(itemRTipEnt!=null){
				TipoEntradaDAO daoAltF = new TipoEntradaDAO(); 
				
				daoAltF.alterarTipoEntrada(itemTipEnt);

				SocioValor objSocioValor = new SocioValor();
				objSocioValor.setCodEntrada(itemTipEnt.getCodigo());
				objSocioValor.setCodSocio(0);
				
				ArrayList<SocioValor> listSocioValor = SocioValorDAO.listarSocioValor(objSocioValor,objOperador.getNucleo()); 

				if(listSocioValor!=null){
					for (SocioValor socioValor : listSocioValor) {
						ReciboDAO.alteraValorSocio(socioValor.getCodSocio(),itemTipEnt.getCodigo(),itemTipEnt.getValor());
					}
				}

				itemTipEnt = TipoEntradaDAO.consultarTipoEntrada(itemTipEnt);
				mens = "Item atualizado: "+itemTipEnt.getDescricao();
			}

			request.setAttribute("objTipEnt", itemTipEnt);
			sessao.setAttribute("objTipEnt", itemTipEnt);
		
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
