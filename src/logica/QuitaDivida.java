package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.DividaDAO;
import DAO.MovAtualDAO;
import beans.Divida;
import beans.Login;
import beans.MovAtual;

public class QuitaDivida implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "Informe filtro da pesquisa"; //"Dívidas não encontradas para o filtro da pesquisa";
		String pgJsp = "/quitaDivida.jsp";
		
		String pagamento = "";
		if(request.getParameter("pagamento")!=null&&request.getParameter("pagamento")!=""){
			pagamento = request.getParameter("pagamento");	
		}
		
		String posicoes = "";
		if(request.getParameter("pos")!=null&&request.getParameter("pos")!="")
			posicoes = request.getParameter("pos");

		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));

		int mes = 0;
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!="")
			mes = Integer.parseInt(request.getParameter("mes"));		

		Double valor = 0.0;
		if(request.getParameter("valor")!=null&&request.getParameter("valor")!=""){
			valor = Double.parseDouble(request.getParameter("valor"));
		}

		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login) sessao.getAttribute("objUsu");

		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		itemMov.setNucleo(objOperador.getNucleo());
		MovAtual objMov = MovAtualDAO.consultarMovAtual(itemMov);
		//objMov.setMes(0); //não sugerir o mes na tela de entrada
		
		
		request.setAttribute("objMov", objMov);

		Divida objDivida = new Divida();
		
		objDivida.setNucleo(objOperador.getNucleo());
		objDivida.setAno(ano);
		objDivida.setMes(mes);
		objDivida.setValor(valor);
		

		sessao.setAttribute("objDiv", objDivida);

		ArrayList<Divida> listDiv = null;
		if(objDivida.getAno()!=0||objDivida.getMes()!=0||objDivida.getValor()!=0){

			listDiv = DividaDAO.listarDivida(objDivida);
			if(listDiv!=null){
				mens = "Selecione dívida à esquerda da linha para quitação";
			}else{
				mens = "Dívida não encontrada para o filtro aplicado";
			}
		}

		if(posicoes!=""){
			
			listDiv = (ArrayList<Divida>) sessao.getAttribute("listDiv");

			int numero;
			int anterior = 0;
			int separador = -1;
			int tamStr = posicoes.length();
			for (int i = 0;i < tamStr; i++) {
				if(posicoes.charAt(i)==';'){
					anterior = separador;
					separador = i;
					
					numero = Integer.parseInt(posicoes.substring(anterior+1,i));
					
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

					if(pagamento!=null&&!pagamento.equals("")){				
						Calendar tipoCalendar = Calendar.getInstance();				
	    				tipoCalendar.setTime((Date)formato.parse(pagamento));
						listDiv.get(numero).setPagamento(tipoCalendar);
					}
					
					DividaDAO objDivDAO = new DividaDAO();
					objDivDAO.quitarDivida(listDiv.get(numero));
				}
			}
			listDiv = DividaDAO.listarDivida(objDivida);

		}else{
			sessao.removeAttribute("listDiv");
		}
		sessao.setAttribute("listDiv", listDiv);
	
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
