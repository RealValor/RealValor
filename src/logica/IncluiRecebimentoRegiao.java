package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import DAO.TipoEntradaDAO;
import beans.Login;
import beans.MovAtual;
import beans.TipoEntrada;

public class IncluiRecebimentoRegiao implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RequestDispatcher rd = null;
		
		String pgJsp;
		pgJsp = "/incluiRecebimentoRegiao.jsp";

		String mens = "Para consultar mensalidades inclua um nome válido";
		
		String nomePaga = "";
		if(request.getParameter("consulta")!=null&&request.getParameter("consulta")!="")
			nomePaga = request.getParameter("consulta");
		
		int codSocio = 0;
		if(request.getParameter("codsocio")!=null&&request.getParameter("codsocio")!="")
			codSocio = Integer.parseInt(request.getParameter("codsocio"));

		int anoReceb = 0;
		if(request.getParameter("ano_paga")!=null&&request.getParameter("ano_paga")!="")
			anoReceb = Integer.parseInt(request.getParameter("ano_paga"));

		Double valorTotal = 0.0;
		if(request.getParameter("valortotal")!=null&&request.getParameter("valortotal")!="")
			valorTotal =  Double.parseDouble(request.getParameter("valortotal"));

		boolean limpar=false;
		
		HttpSession sessao = request.getSession();
		Login itemPaga = new Login();
		Login itemComum = new Login();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		itemComum = objOperador;

		int mudouMes;
		HistoricoRecebimentoDAO objHistDAO = new HistoricoRecebimentoDAO();
		
		mudouMes = objHistDAO.verificarMudancaDeMes(objOperador.getNucleo().getCodigo());
		//Refatorar (Por esta checagem de mudança de mês na classe FechaRecibo).

		String[] situPaga = new String[2];
        situPaga[0] = "A";
        situPaga[1] = "Núcleo";

		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!=""){
			situPaga[0] = request.getParameter("situ_paga");
			situPaga[1] = request.getParameter("situ_paga").equals("N")?"Não Associado":"Núcleo";
		}

		@SuppressWarnings("unchecked")
		ArrayList<TipoEntrada> listTEnt = (ArrayList<TipoEntrada>) sessao.getAttribute("listTEnt");
		if(listTEnt == null){
			listTEnt = TipoEntradaDAO.listarTipoEntrada(objOperador.getNucleo().getCodigo(), (situPaga[0].equals("N")?1:3)); //0 = todos; 1 = mensal="N"; 2 = mensal="S"; 3 = ativo<>"N";
			sessao.setAttribute("listTEnt", listTEnt);
		}

		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		
		itemMov.setNucleo(objOperador.getNucleo());

		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);

		Calendar data = Calendar.getInstance();

		MovAtual objMovI = new MovAtual();
		objMovI.setAno(data.get(Calendar.YEAR));
		objMovI.setMes(1); //janeiro

		MovAtual objMovF = (MovAtual) sessao.getAttribute("objMovFin");
		if(objMovF==null){
			objMovF = new MovAtual();
			objMovF.setAno(data.get(Calendar.YEAR));
			objMovF.setMes(1);
		}

		int iniReceb = 0;
		int finReceb = 0;

		if(request.getParameter("ctrl")==null||!limpar){
			if(request.getParameter("mesini_paga")!=null&&request.getParameter("mesini_paga")!=""){
				iniReceb = Integer.parseInt(request.getParameter("mesini_paga"));
				objMovI.setMes(iniReceb);
			}
			if(request.getParameter("mesfin_paga")!=null&&request.getParameter("mesfin_paga")!=""){
				finReceb = Integer.parseInt(request.getParameter("mesfin_paga"));
			}
		}
		
		if(objMovF.getAno()==0){
			objMovF.setAno(data.get(Calendar.YEAR));
		}
		if(objMovF.getMes()==0){
			objMovF.setAno(data.get(Calendar.MONTH)+1);
		}

		TipoEntrada objTipoEntrada = new TipoEntrada();
		objTipoEntrada.setCodigo(0);
		
		//---------------------
		int tipReceb = 0; //Mensalidade
		if(request.getParameter("tipo_entrada")!=null&&request.getParameter("tipo_entrada")!="")
			tipReceb = Integer.parseInt(request.getParameter("tipo_entrada"));

		objTipoEntrada.setCodigo(tipReceb);
		//---------------------

		request.setAttribute("tipoEntrada", objTipoEntrada);
		
		sessao.setAttribute("objMovIni", objMovI);
		sessao.setAttribute("objMovFin", objMovF);

		request.setAttribute("situPaga", situPaga);

		//sessao.setAttribute("consultaRecibo", 0);

		//sessao.setAttribute("objPaga", itemPaga);
		//sessao.setAttribute("listaDados", sessao.getAttribute("listaRecibo"));
		//sessao.setAttribute("recibosReserva", recibosReserva);
		//sessao.setAttribute("video", "imagens/VideoRecebimento.mp4;loop=1");

		//request.setAttribute("mudouMes", mudouMes);
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);


	}
}
