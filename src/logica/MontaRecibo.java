package logica;

import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Recibo;
import beans.TipoEntrada;


import DAO.TipoEntradaDAO;

public class MontaRecibo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiRecebimentos.jsp";
		String tipoPaga = request.getParameter("tipo_entrada");
		String codPaga  = request.getParameter("cod_paga"); //codigo do pagador
		String situPaga  = request.getParameter("situ_paga"); //socio ou nao socio
		int mesIni   = Integer.parseInt(request.getParameter("mesini"));
		int mesFin   = Integer.parseInt(request.getParameter("mesfin"));
		String nomePaga = request.getParameter("consulta");		
		
		HttpSession sessao = request.getSession();

		Login itemPaga = new Login();
		Login objOperador = new Login();

		if((nomePaga!=null&&nomePaga!="")||(codPaga!=null&&codPaga!="")){			
			if(nomePaga!=null&&nomePaga!=""){	
				itemPaga.setNome(nomePaga);
			}
			
			if(codPaga!=null&&codPaga!=""){
				itemPaga.setUsuario(Integer.parseInt(codPaga));
			}
			
			TipoEntrada tipoEntr = new TipoEntrada();
			if (tipoPaga!=null){
				tipoEntr.setCodigo(Integer.parseInt(tipoPaga));
				tipoEntr.setNucleo(objOperador.getNucleo());
				tipoEntr = TipoEntradaDAO.consultarTipoEntrada(tipoEntr);
			}

			Recibo objRecibo = new Recibo();
			
			objRecibo.setEntrada(tipoEntr);
			objRecibo.setMes(Calendar.getInstance().get(Calendar.MONTH));
			objRecibo.setAno(Calendar.getInstance().get(Calendar.YEAR));
			objRecibo.setData(Calendar.getInstance());
			
			objOperador = (Login) sessao.getAttribute("objUsu");
			
			objRecibo.setCpfOperador(objOperador.getCpf());
			objRecibo.setFlSocio(situPaga);
			
			int[] meses = new int[12];
			for(int i=1;i<=12;i++){
				meses[i]=(i>=mesIni&&i<=mesFin)?i:0;
			}
			objRecibo.setMeses(meses);
		
			request.setAttribute("objPaga", itemPaga);
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
