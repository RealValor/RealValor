package logica;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Saida;
import beans.TipoDocumento;
import beans.TipoSaida;


import DAO.SaidaDAO;

public class AlteraSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiPagamento.jsp";
		
		HttpSession sessao = request.getSession();
		
		String[] situPaga = new String[2];

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

		int codTipo = 0;
		if(request.getParameter("tipo_pagamento")!=null&&request.getParameter("tipo_pagamento")!="")
			codTipo = Integer.parseInt(request.getParameter("tipo_pagamento"));
					
		int codPaga = 0;
		if(request.getParameter("cod_paga")!=null&&request.getParameter("cod_paga")!="")
			codPaga = Integer.parseInt(request.getParameter("cod_paga"));

		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!="")
			situPaga[0] = request.getParameter("situ_paga");		
		
		int codDoc = 0;
		if(request.getParameter("tipo_documento")!=null&&request.getParameter("tipo_documento")!="")
			codDoc = Integer.parseInt(request.getParameter("tipo_documento"));
		
		
		String dataNota = "";
		if(request.getParameter("data_nota")!=null&&request.getParameter("data_nota")!="")
			dataNota = request.getParameter("data_nota");

		String numeroDoc = "";			
		if(request.getParameter("numero_doc")!=null&&request.getParameter("numero_doc")!="")
			numeroDoc = request.getParameter("numero_doc");

		double vlrPago = 0;
		if(request.getParameter("valor_doc")!=null&&request.getParameter("valor_doc")!="")
			vlrPago = Double.parseDouble(request.getParameter("valor_doc"));

		String observacao = "";			
		if(request.getParameter("observacao")!=null&&request.getParameter("observacao")!="")
			observacao = request.getParameter("observacao");

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		itemSaida.setNucleo(objOperador.getNucleo());

		itemSaida.setNumero(codNum);
		itemSaida.setAno(codAno);

		SaidaDAO daoRecF = new SaidaDAO();
		Saida itemRecF = daoRecF.consultarSaida(itemSaida);

		itemSaida.setMes(itemRecF.getMes());

		TipoSaida tipoSaida = new TipoSaida();
		tipoSaida.setCodigo(codTipo);

		itemSaida.setSaida(tipoSaida);

		itemSaida.setData(Calendar.getInstance());
		itemSaida.setCpfOperador(objOperador.getCpf());
		
		TipoDocumento objTipoDoc = new TipoDocumento();
		objTipoDoc.setCodigo(codDoc);
		
		itemSaida.setTipoDocumento(objTipoDoc);
		itemSaida.setDocumento(numeroDoc);
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		if(!dataNota.equals("")){					
			itemSaida.setDataDocumento((Date)formato.parse(dataNota));
		}
		
		itemSaida.setFornecedor(codPaga);
		itemSaida.setFlSocio(situPaga[0]);
		itemSaida.setValor(vlrPago);
		itemSaida.setObservacao(observacao);			
		itemSaida.setFlEstornada("N");


		SaidaDAO daoIncF = new SaidaDAO();
		daoIncF.alterarSaida(itemSaida);
		mens = "Saída alterada! ";

		request.setAttribute("objSaida", itemSaida);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
