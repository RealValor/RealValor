package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.MovAtual;
import beans.Saida;
import beans.TipoDocumento;
import beans.TipoSaida;


import DAO.MovAtualDAO;
import DAO.SaidaDAO;

public class EstornaSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiPagamento.jsp";
		
		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		itemMov.setNucleo(objOperador.getNucleo());
		
		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		
        Saida itemSaida = new Saida();

		TipoDocumento objTipDoc = new TipoDocumento();
		objTipDoc.setCodigo(0);
		itemSaida.setTipoDocumento(objTipDoc);
		
		TipoSaida objTipSai = new TipoSaida();
		objTipSai.setCodigo(0);
		itemSaida.setSaida(objTipSai);

		int codNum = 0;
		if(request.getParameter("numero")!=null&&request.getParameter("numero")!="")
			codNum = Integer.parseInt(request.getParameter("numero"));

		int codAno = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			codAno = Integer.parseInt(request.getParameter("ano")); 

		itemSaida.setNumero(codNum);
		itemSaida.setAno(codAno);
		
		itemSaida.setNucleo(objOperador.getNucleo());

		
		SaidaDAO daoRecF = new SaidaDAO();
		Saida itemRecF = daoRecF.consultarSaida(itemSaida);


		if(itemRecF != null){
			if(itemRecMov.getAno()==itemRecF.getAno() && itemRecMov.getMes()==itemRecF.getMes()){

				itemRecF.setFlEstornada("S");
				itemRecF.setCpfOperador(objOperador.getCpf());
				SaidaDAO daoIncF = new SaidaDAO();
				daoIncF.alterarSaida(itemRecF);
				mens = "Saída estornada! ";

			}
		}

		request.setAttribute("objSaida", itemRecF);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
