package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import DAO.TipoDocumentoDAO;
import DAO.TipoSaidaDAO;

public class IncluiSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiPagamento.jsp";

		String nomePaga = "";
		if(request.getParameter("consulta")!=null&&request.getParameter("consulta")!="")
			nomePaga = request.getParameter("consulta");


		HttpSession sessao = request.getSession();
		Login itemPaga = new Login();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		itemMov.setNucleo(objOperador.getNucleo());
		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		
		if(itemRecMov != null){
			request.setAttribute("objMovFin", itemRecMov);
			request.setAttribute("objMovIni", itemRecMov); //Observar. Talvez não seja utilizado.
		}		

		String[] situPaga = new String[2];
        situPaga[0] = "N";
        situPaga[1] = "Não Sócio";

		TipoDocumento objTipDoc = new TipoDocumento();
		TipoSaida objTipSai = new TipoSaida();
		objTipSai.setNucleo(objOperador.getNucleo());
		
		Saida itemSaida = new Saida();

		ArrayList<TipoDocumento> listTDoc = TipoDocumentoDAO.listarTipoDocumento();
		if(listTDoc != null){
			objTipDoc.setCodigo(listTDoc.get(0).getCodigo());
			objTipDoc = TipoDocumentoDAO.consultarTipoDocumento(objTipDoc);
			itemSaida.setTipoDocumento(objTipDoc);
			
			sessao.setAttribute("listTDoc", listTDoc);	                
		}

		ArrayList<TipoSaida> listTSai = TipoSaidaDAO.listarTipoSaida(1,objOperador.getNucleo());

		if(listTSai != null){
			objTipSai.setCodigo(listTSai.get(0).getCodigo());
			objTipSai = TipoSaidaDAO.consultarTipoSaida(objTipSai);
			itemSaida.setSaida(objTipSai);
			
			sessao.setAttribute("listTSai", listTSai);		                
		}

		if(nomePaga!=null&&nomePaga!=""){
			
			itemPaga.setNome(nomePaga);

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
				dataNota = request.getParameter("data_nota"); //verificar formato

			String numeroDoc = "";			
			if(request.getParameter("numero_doc")!=null&&request.getParameter("numero_doc")!="")
				numeroDoc = request.getParameter("numero_doc");

			double vlrPago = 0;
			if(request.getParameter("valor_doc")!=null&&request.getParameter("valor_doc")!="")
				vlrPago = Double.parseDouble(request.getParameter("valor_doc"));

			String observacao = "";			
			if(request.getParameter("observacao")!=null&&request.getParameter("observacao")!="")
				observacao = request.getParameter("observacao");

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
			if(!dataNota.equals("")){

				itemSaida.setDataDocumento((Date)formato.parse(dataNota));
				
				Calendar data = Calendar.getInstance();
				data.setTime(itemSaida.getDataDocumento());
			
				int anoNota = data.get(Calendar.YEAR);
				int mesNota = data.get(Calendar.MONTH)+1;
			
				if(anoNota != itemRecMov.getAno() || mesNota > itemRecMov.getMes()){
					mens = "Lançamento não efetuado! Nota do mês "+mesNota+"/"+anoNota+" movimento do mês "+itemRecMov.getMes()+"/"+itemRecMov.getAno();
				}else{

					SaidaDAO numSaiDAO = new SaidaDAO();
					int numeroSaida = numSaiDAO.buscaNumeroSaida(itemRecMov.getAno(), objOperador.getNucleo().getCodigo());

					itemSaida.setNumero(numeroSaida);
					itemSaida.setNucleo(objOperador.getNucleo());
					itemSaida.setMes(itemRecMov.getMes());
					itemSaida.setAno(itemRecMov.getAno());
					
					TipoSaida tipoSaida = new TipoSaida();
					tipoSaida.setCodigo(codTipo);
					tipoSaida.setNucleo(objOperador.getNucleo());
					
					tipoSaida = TipoSaidaDAO.consultarTipoSaida(tipoSaida);

					itemSaida.setSaida(tipoSaida);
					
					itemSaida.setData(Calendar.getInstance());
					itemSaida.setCpfOperador(objOperador.getCpf());
					
					TipoDocumento objTipoDoc = new TipoDocumento();
					objTipoDoc.setCodigo(codDoc);
					//objTipoDoc = TipoDocumentoDAO.consultarTipoDocumento(objTipoDoc);

					itemSaida.setTipoDocumento(objTipoDoc);
					itemSaida.setDocumento(numeroDoc);
					itemSaida.setFornecedor(codPaga);
					itemSaida.setFlSocio(situPaga[0]);
					itemSaida.setValor(vlrPago);
					itemSaida.setObservacao(observacao);			
					itemSaida.setFlEstornada("N");
					itemSaida.setFlFechada("N");
					
					SaidaDAO daoRecF = new SaidaDAO();
					Saida itemRecF = daoRecF.consultarSaida(itemSaida);

					if(itemRecF==null){
						SaidaDAO daoIncF = new SaidaDAO();
						daoIncF.incluirSaida(itemSaida);
						mens = "Saída Incluída! ";
					}
				}
			}
		}
		request.setAttribute("objSaida", itemSaida);
		request.setAttribute("situPaga", situPaga);
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
