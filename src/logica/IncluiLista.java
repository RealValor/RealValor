package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ListaDAO;
import DAO.MovAtualDAO;
import DAO.TipoEntradaDAO;
import beans.Lista;
import beans.Login;
import beans.MovAtual;
import beans.Nucleo;
import beans.TipoEntrada;

public class IncluiLista implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiLista.jsp";
		
		String dataLista = "";
		if(request.getParameter("data_lista")!=null&&request.getParameter("data_lista")!="")
			dataLista = request.getParameter("data_lista");

		int codEntrada = 0;
		if(request.getParameter("entrada")!=null&&request.getParameter("entrada")!="")
			codEntrada = Integer.parseInt(request.getParameter("entrada"));

		int statusLista = 0;
		if(request.getParameter("fechada")!=null&&request.getParameter("fechada")!="")
			statusLista = Integer.parseInt(request.getParameter("fechada"));

		String listaContinua = "N";
		if(request.getParameter("continua")!=null&&request.getParameter("continua")!="")
			listaContinua = request.getParameter("continua");
		
		int qtdPagamentos = 0;
		if(request.getParameter("qtde")!=null&&request.getParameter("qtde")!="")
			qtdPagamentos = Integer.parseInt(request.getParameter("qtde"));
		
		HttpSession sessao = request.getSession();
		
		mens = (String) request.getAttribute("retorno");
		if(mens==null){
			mens = "Manutenção de listas de arrecadação";
		}

		Lista itemLista = new Lista();

		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
		
		MovAtual itemMov = new MovAtual();
		itemMov.setNucleo(objNucleo);
		itemMov.setFechado("N");
		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		//se a data lançada não corresponder ao mes em berto, não permitir a inclusão

		if(itemRecMov != null){
			request.setAttribute("objMovIni", itemRecMov);
			request.setAttribute("objMovFin", itemRecMov);
		}		

		String[] situPaga = new String[2];
        situPaga[0] = "N";
        situPaga[1] = "Não Associado";

        sessao.removeAttribute("listListas");
        Lista objLista = new Lista();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
        
        ArrayList<TipoEntrada> listTEntr = TipoEntradaDAO.listarTipoEntrada(objOperador.getNucleo().getCodigo(),1); //0 = todos; 1 = mensal="N"; 2 = mensal="S" 
		if(listTEntr != null){
			sessao.setAttribute("listTEntr", listTEntr);	                
		}

		if(codEntrada!=0){
			
			TipoEntrada tipoEntrada = new TipoEntrada();
			tipoEntrada.setCodigo(codEntrada);
			tipoEntrada.setNucleo(objOperador.getNucleo());
			
			tipoEntrada = TipoEntradaDAO.consultarTipoEntrada(tipoEntrada);
			
			if(tipoEntrada.getMensal().equalsIgnoreCase("S")){
				mens = " O tipo de entrada "+tipoEntrada.getDescricao().toUpperCase()+" já faz parte do pagamento mensal e não pode ser incluído em listas";
				codEntrada=0;
			}
		}

		if(!dataLista.equals("")&&codEntrada!=0){ //inclusão de lista
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
			Calendar tipoCalendar = Calendar.getInstance();
			tipoCalendar.setTime(formatoData.parse(dataLista));

			int mes,ano;
			mes = (tipoCalendar.get(Calendar.MONTH)+1);
			ano = (tipoCalendar.get(Calendar.YEAR));


			if(ano<itemRecMov.getAno()||(mes<itemRecMov.getMes()&&ano<=itemRecMov.getAno())){
				mens = " Lista não pode ser incluída em mes fechado. Escolha mês "+itemRecMov.getMes()+"/"+itemRecMov.getAno()+" ou posterior";
			}else{

				ListaDAO numListaDAO = new ListaDAO();
				int numeroLista = numListaDAO.buscaNumeroLista(ano,objNucleo.getCodigo());

				itemLista.setNumero(numeroLista);
				itemLista.setNucleo(objNucleo);
				itemLista.setAno(tipoCalendar.get(Calendar.YEAR));
				itemLista.setData(tipoCalendar);

				double vlrUnico = 0;
				if(request.getParameter("valor_unico")!=null&&request.getParameter("valor_unico")!="")
					vlrUnico = Double.parseDouble(request.getParameter("valor_unico"));

				itemLista.setCpfOperador(objOperador.getCpf());

				TipoEntrada objEntrada = new TipoEntrada();
				objEntrada.setCodigo(codEntrada);
				objEntrada.setNucleo(objOperador.getNucleo());
				
				objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);

				itemLista.setEntrada(objEntrada);
				itemLista.setFlContinua(listaContinua);
				itemLista.setQtdePagamentos(qtdPagamentos);

				ListaDAO daoRecF = new ListaDAO();
				Lista itemRecF = daoRecF.consultarLista(itemLista);

				if(itemRecF==null){
					ListaDAO daoIncF = new ListaDAO();
					
					//System.out.println("aqui 1"); 
					
					daoIncF.incluirLista(itemLista,vlrUnico);
					mens = "Lista Incluída! ";

					//System.out.println("aqui 2");

					ArrayList<Lista> detalheLista = ListaDAO.consultarDetalheLista(itemLista);

					sessao.setAttribute("objDetalheLista", detalheLista.get(0));	
					sessao.setAttribute("detalheLista", detalheLista);	
				}
			}
		}

		ArrayList<Lista> listListas = ListaDAO.listarLista(objNucleo);

		if(listListas != null){
			objLista.setNumero(listListas.get(0).getNumero());
			objLista.setNucleo(objNucleo);
			objLista.setAno(listListas.get(0).getAno());
			
			ListaDAO objListaDAO = new ListaDAO(); 
			objLista = objListaDAO.consultarLista(objLista);
			
			sessao.setAttribute("listListas", listListas);		                
		}

		sessao.removeAttribute("listFechadas");
		if(statusLista>0){
			ArrayList<Lista> listFechadas = ListaDAO.listarListaFechada(objNucleo);
			if(listFechadas != null){
				sessao.setAttribute("listFechadas", listFechadas);		                
			}
		}

		sessao.setAttribute("statusLista", statusLista);
		request.setAttribute("objLista", itemLista);
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
