package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.MovAtualDAO;
import DAO.SaidaDAO;
import beans.Divida;
import beans.Login;
import beans.MovAtual;
import beans.Saida;

public class IncluiDivida implements LogicaDeNegocio{

	@SuppressWarnings("static-access")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiDivida.jsp";

		int numSaida = 0;
		if(request.getParameter("saida")!=null&&request.getParameter("saida")!="")
			numSaida = Integer.parseInt(request.getParameter("saida"));

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login) sessao.getAttribute("objUsu");
		
		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		
		itemMov.setNucleo(objOperador.getNucleo());

		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);

		Saida objSaida = new Saida();
		Saida objSaidaAux = new Saida();
		Divida objDataInicio = new Divida();
		
		objSaida.setNucleo(objOperador.getNucleo());
		objSaidaAux.setNucleo(objOperador.getNucleo());

		if(itemRecMov != null){
			objSaida.setAno(itemRecMov.getAno());
			objSaida.setMes(itemRecMov.getMes());
		}		

		ArrayList<Saida> listSai = SaidaDAO.listarSaida(objSaida);
		if(listSai != null){
			sessao.setAttribute("listSai", listSai);
			objSaida = listSai.get(0);
			objSaidaAux = listSai.get(0);
		}

		if(numSaida!=0){

			int vezes = 1;
			
			if(request.getParameter("vezes")!=null&&request.getParameter("vezes")!="")
				vezes = Integer.parseInt(request.getParameter("vezes"));
						
			int dia = 0;
			int mes = 0;
			int ano = 0;

			String inicioParcelas = request.getParameter("data_inicio");

			SaidaDAO numSaiDAO = new SaidaDAO();
			
			Saida itemSai = new Saida();

			itemSai.setNumero(numSaida);
			itemSai.setAno(itemRecMov.getAno());
			itemSai.setNucleo(objOperador.getNucleo());

			objSaida = numSaiDAO.consultarSaida(itemSai);

			if(inicioParcelas.length()>=10){
				
				itemSai.setDataDocumento(ConverteFormatoData.stringToDate(inicioParcelas));
				objDataInicio.setVencimento(ConverteFormatoData.dateToCalendar(itemSai.getDataDocumento()));
				//observar este comportamento
				
				Calendar data = Calendar.getInstance();
				data.setTime(itemSai.getDataDocumento());
				
				ano = data.get(Calendar.YEAR);
				mes = data.get(Calendar.MONTH)+1;
				dia = data.get(Calendar.DAY_OF_MONTH);

			}

			
			if(objSaida==null){
				mens = "Saída número "+numSaida+" não encontrada! ";
			}else{
				
				ArrayList<Divida> listDiv = new ArrayList<Divida>();
				double valor = 0.0;
				if(request.getParameter("valorTotal")!=null&&request.getParameter("valorTotal")!="")
					valor = Double.parseDouble(request.getParameter("valorTotal"));

				if(valor>0){
					objSaidaAux.setValor(valor);
				}
				
				Calendar movimento = Calendar.getInstance();
				movimento.set(itemRecMov.getAno(), itemRecMov.getMes()-1, dia);
				
				//gerar as parcelas baseado no total de vezes
				for(int i=0;i<vezes;i++){

					Divida objDivida = new Divida();
					
					objDivida.setValor(valor/vezes);

					objDivida.setSaida(objSaida);
					objDivida.setNucleo(objOperador.getNucleo());

					Calendar vencimento = Calendar.getInstance();
					vencimento.set(ano, mes-1, dia);
					vencimento.add(vencimento.MONTH, i);

					objDivida.setAno(vencimento.get(Calendar.YEAR));
					objDivida.setMes(vencimento.get(Calendar.MONTH)+1);
					
					objDivida.setValor(objDivida.getValor());
					
					objDivida.setTipoSaida(objSaida.getTipoDocumento().getCodigo());
					objDivida.setNumeroDocumento(objSaida.getDocumento());

					Calendar dataDocumento = Calendar.getInstance();  
					dataDocumento.setTime(objSaida.getDataDocumento());  
					//as duas linhas acima convertem para Calendar o date: objSaida.getDataDocumento().

					objDivida.setDataDocumento(dataDocumento);
				
					objDivida.setMovimento(movimento);
					objDivida.setVencimento(vencimento);
					objDivida.setPagamento(null);
					objDivida.setPago("N");
					
					listDiv.add(objDivida);
				}
				sessao.setAttribute("listDiv", listDiv);
			}
			
			request.setAttribute("vezes", vezes);
			request.setAttribute("dia", dia);
			request.setAttribute("mes", mes);
			request.setAttribute("ano", ano);
			
			sessao.setAttribute("listSai", listSai);	                
		}else{
			sessao.removeAttribute("listDiv");
		}
		request.setAttribute("objSaida", objSaida);
		request.setAttribute("objSaidaAux", objSaidaAux);
		sessao.setAttribute("objMov", itemRecMov);
		sessao.setAttribute("objDataInicio", objDataInicio);
		sessao.setAttribute("video", "imagens/VideoDivida.mp4;loop=1");

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
