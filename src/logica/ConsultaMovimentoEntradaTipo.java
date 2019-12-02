package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.EntradaDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import DAO.TipoEntradaDAO;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.Nucleo;
import beans.TipoEntrada;

public class ConsultaMovimentoEntradaTipo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaMovimentoEntradaTipo.jsp";
		int totEntradas = 0;
		
		HttpSession sessao = request.getSession();
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		MovAtual itemMov = new MovAtual();
		itemMov.setNucleo(objNucleo);
		itemMov.setFechado("N");
		
		MovAtual itemRecMov = new MovAtual();
		itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);

		MovAtual itemRecMovFin = new MovAtual();
		itemRecMovFin = MovAtualDAO.consultarMovAtual(itemMov);

		Calendar data = Calendar.getInstance();
		itemMov.setAno(data.get(Calendar.YEAR));
		
		itemMov.setMes(EntradaDAO.buscaUltimoMesMovimento(data.get(Calendar.YEAR), objNucleo));
		
		if(itemRecMov!=null&&itemRecMov.getAno()!=itemMov.getAno()){
			itemRecMov.setAno(data.get(Calendar.YEAR));
			itemMov.setMes(12);
		}

		sessao.removeAttribute("listObjMov");
		ArrayList<MovAtual> listObjMov = new ArrayList<MovAtual>();
		
		for (int i = 12; i >= 1; i--) { //for (int i = itemMov.getMes(); i >= 1; i--) {
			MovAtual itemMovAux = new MovAtual();
			itemMovAux.setMes(i);
			listObjMov.add(itemMovAux);
		}
		sessao.setAttribute("listObjMov", listObjMov);

		int codTipo = 0;
		if(request.getParameter("tipo_entrada")!=null&&request.getParameter("tipo_entrada")!="")
			codTipo = Integer.parseInt(request.getParameter("tipo_entrada"));

		int codAno = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			codAno = Integer.parseInt(request.getParameter("ano"));

		int codMesInicial = 0;
		if(request.getParameter("mes_inicial")!=null&&request.getParameter("mes_inicial")!="")
			codMesInicial = Integer.parseInt(request.getParameter("mes_inicial"));		

		int codMesFinal = 0;
		if(request.getParameter("mes_final")!=null&&request.getParameter("mes_final")!="")
			codMesFinal = Integer.parseInt(request.getParameter("mes_final"));		

		codMesFinal=codMesFinal==0?12:codMesFinal;
		
		String[] situPaga = new String[2];
		situPaga[0] = "A";
		situPaga[1] = "Associado";

		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!=""){
			situPaga[0] = request.getParameter("situ_paga");
			if(request.getParameter("situ_paga").equalsIgnoreCase("N")){
				situPaga[1] = "Não Associado";  
			}else if(request.getParameter("situ_paga").equalsIgnoreCase("S")){
				situPaga[1] = "Associado";
			}
	    }

		TipoEntrada objTipoEntrada = new TipoEntrada();
		objTipoEntrada.setCodigo(codTipo);

		@SuppressWarnings("unchecked")
		ArrayList<TipoEntrada> listTEnt = (ArrayList<TipoEntrada>) sessao.getAttribute("listTEnt");
		if(listTEnt == null){
			listTEnt = TipoEntradaDAO.listarTipoEntrada(objOperador.getNucleo().getCodigo(), (situPaga[0].equals("N")?1:3)); //0 = todos; 1 = mensal="N"; 2 = mensal="S"; 3 = ativo<>"N";
			sessao.setAttribute("listTEnt", listTEnt);
		}
		
		if(codAno>0&&codMesInicial>0&&codMesFinal>0&&objTipoEntrada.getCodigo()>0){
			
			ArrayList<HistoricoRecebimento> listEntrada = HistoricoRecebimentoDAO.listarHistoricoEntradaTipo(codMesInicial, codMesFinal, codAno, objTipoEntrada, objNucleo);
			if(listEntrada==null){
				mens = "Não existem entradas no período "+codMesInicial+" a "+codMesFinal+" de "+codAno;  
			}else{
				totEntradas = listEntrada.size();
			}
			
			Login itemPaga = new Login();
			itemPaga.setUsuario(codTipo);
			
			sessao.setAttribute("objPaga", itemPaga);
			sessao.setAttribute("listEntrada", listEntrada);
			objTipoEntrada.setCodigo(listEntrada.get(0).getEntrada().getCodigo());
			objTipoEntrada.setDescricao(listEntrada.get(0).getEntrada().getDescricao());

		}
		
		if(objTipoEntrada.getCodigo()<1){
			objTipoEntrada.setCodigo(1);
			objTipoEntrada.setNucleo(objOperador.getNucleo());
			objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
		}

		itemMov.setMes(codMesInicial);
		itemRecMovFin.setMes(codMesFinal);
		
		if(codAno!=0){
			itemMov.setAno(codAno);
		}
		
		sessao.setAttribute("objMovIni", itemMov);
		sessao.setAttribute("objMovFin", itemRecMovFin);
		
		request.setAttribute("tipoEntrada", objTipoEntrada);
		sessao.setAttribute("totEntradas", totEntradas);
		request.setAttribute("situPaga", situPaga);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
