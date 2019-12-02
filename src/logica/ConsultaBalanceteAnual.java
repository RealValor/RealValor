package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.DividaDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import DAO.SaidaDAO;
import DAO.SaldoBancoDAO;
import DAO.SaldoCaixaDAO;
import DAO.TipoEntradaDAO;
import DAO.TipoSaidaDAO;
import beans.Divida;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.Nucleo;
import beans.Saida;
import beans.SaldoBanco;
import beans.SaldoCaixa;
import beans.TipoEntrada;
import beans.TipoSaida;

public class ConsultaBalanceteAnual implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaBalanceteAnual.jsp";
		int controle = 0;
		
		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		int codAno = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			codAno = Integer.parseInt(request.getParameter("ano"));

		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");	
		
		MovAtual itemMov = new MovAtual();
		itemMov.setAno(codAno);
		itemMov.setNucleo(objNucleo);
		itemMov.setFechado("N");
		
		itemMov = MovAtualDAO.consultarMovAtual(itemMov);

		if(codAno!=0){
			if(itemMov==null){
				itemMov = new MovAtual();
				itemMov.setAno(codAno);
				controle = controle+1;
			}else{
				controle = -1;
			}

			SaldoCaixa itemSaldoAnterior = new SaldoCaixa();
			SaldoBanco itemSaldoBanco = new SaldoBanco();

			SaldoCaixaDAO objSaldoCaixaDAO = new SaldoCaixaDAO(); 
			HistoricoRecebimentoDAO objHistoricoRecebimentoDAO = new HistoricoRecebimentoDAO();
			SaidaDAO objSaidaDAO = new SaidaDAO();
			SaldoBancoDAO objSaldoBancoDAO = new SaldoBancoDAO();  
			DividaDAO  objDividaDAO = new DividaDAO();

			itemSaldoAnterior.setAno(codAno);
			itemSaldoBanco.setAno(itemSaldoAnterior.getAno());

			//Busca historico de caixa para todos os meses fechados do ano do movimento: ant_caixa;ant_banco;ant_divida
			ArrayList<SaldoCaixa> listSaldoAnterior = objSaldoCaixaDAO.consultarSaldoCaixaAnual(itemSaldoAnterior.getAno(), objNucleo.getCodigo());

			//Busca receitas do mes e ano totalizando por tipo de entrada de janeiro a dezembro
			ArrayList<HistoricoRecebimento> listEntrada = objHistoricoRecebimentoDAO.listarHistoricoEntradaAnual(itemSaldoBanco.getAno(), objNucleo);

			//Soma totais antecipacao Aberta
			ArrayList<Divida> antecipacaoAnualAberta = objDividaDAO.consultarAntecipacaoAnualAberta(itemSaldoBanco.getAno(), objNucleo.getCodigo());

			//Busca despesas do mes e ano totalizando por tipo de saida
			ArrayList<Saida> listSaida = objSaidaDAO.listarHistoricoSaidaAnual(itemSaldoBanco.getAno(), objNucleo.getCodigo());

			//Soma totais antecipacao Paga
			ArrayList<Divida> antecipacaoAnualPaga = objDividaDAO.consultarAntecipacaoAnualPaga(itemSaldoBanco.getAno(), objNucleo.getCodigo());

			//Somar totais entradas menos saidas
			ArrayList<SaldoCaixa> listReceitasMenosDespesas = new ArrayList<SaldoCaixa>();

			//Somar totais entradas
			ArrayList<SaldoCaixa> listReceitas = new ArrayList<SaldoCaixa>();

			//Somar totais saídas
			ArrayList<SaldoCaixa> listDespesas = new ArrayList<SaldoCaixa>();

			//Somar totais do caixa
			ArrayList<SaldoCaixa> listSaldoCaixa = new ArrayList<SaldoCaixa>();

			//Soma saldo de mais de um banco pro mesmo mes/ano, que pode inclusive ser zero.
			ArrayList<SaldoBanco> saldoBancos = objSaldoBancoDAO.consultarSaldoBancoAnual(itemSaldoBanco.getAno(), objNucleo);

			//Somar saldos totais de receitas
			ArrayList<SaldoCaixa> listSaldoReceitas = new ArrayList<SaldoCaixa>();

			//Somar totais do saldo anterior do caixa
			ArrayList<SaldoCaixa> listSaldoAnteriorAuxiliar = new ArrayList<SaldoCaixa>();

			//Pode não existir todos os meses, para todas as entradas, em listEntrada. Então, listEntrada é transferida para 
			//listEntradaAuxiliar, onde alimentam-se todos os meses.
			ArrayList<HistoricoRecebimento> listEntradaAuxiliar = new ArrayList<HistoricoRecebimento>();

			ArrayList<Saida> listSaidaAuxiliar = new ArrayList<Saida>();
			ArrayList<SaldoBanco> saldoBancosAuxiliar = new ArrayList<SaldoBanco>();
			ArrayList<Divida> dividaAnualAuxiliarAberta = new ArrayList<Divida>();
			ArrayList<Divida> dividaAnualAuxiliarPaga = new ArrayList<Divida>();

			for (int i = 0; i < 12; i++) {
				
				SaldoCaixa objSaldoCaixaAnterior =  new SaldoCaixa();
				
				objSaldoCaixaAnterior.setMes(i+1); //alimenta meses 1 a 12
				objSaldoCaixaAnterior.setSaldoAnteriorCaixa(0); //alimenta valores para TODOS os meses

				listSaldoAnteriorAuxiliar.add(objSaldoCaixaAnterior);

				for (SaldoCaixa saldoCaixa : listSaldoAnterior) {
					if (saldoCaixa.getMes()==(i+1)) {
						listSaldoAnteriorAuxiliar.get(i).setSaldoAnteriorCaixa(saldoCaixa.getSaldoAnteriorCaixa()); //alimenta valores para TODOS os meses
						//break;
					}
				}
			}

			for (int i = 0; i < 12; i++) {

				SaldoCaixa objSaldoCaixaReceitas =  new SaldoCaixa();

				objSaldoCaixaReceitas.setMes(i+1); //alimenta meses 1 a 12
				objSaldoCaixaReceitas.setSaldoAnteriorCaixa(0); //alimenta com valor zero TODOS os meses (Entradas)

				listReceitas.add(objSaldoCaixaReceitas); //zera listReceitas
			}

			int mes=1,codEntrada=listEntrada.get(0).getEntrada().getCodigo();
			for (HistoricoRecebimento entrada : listEntrada) {

				if (entrada.getEntrada().getCodigo()!=codEntrada) {
					
					TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(codEntrada);
                	objTipoEntrada.setNucleo(objOperador.getNucleo());
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);

                	if(objTipoEntrada==null){
                		objTipoEntrada = new TipoEntrada();
                		objTipoEntrada.setCodigo(codEntrada);
                		objTipoEntrada.setDescricao("Tipo Excluído");
                		objTipoEntrada.setValor(0.0);
                	}

                	for (int j = mes; j < 13; j++) { //12 meses
						
						HistoricoRecebimento objEntrada = new HistoricoRecebimento(); 
						
						objEntrada.setEntrada(objTipoEntrada);
						objEntrada.setMes(j);
						objEntrada.setValor(0); 
						
						listEntradaAuxiliar.add(objEntrada);
					}

					codEntrada = entrada.getEntrada().getCodigo();
					mes=1;
				}

				while (mes<entrada.getMes()) { //caso a entrada iniciar com mes maior do que o mes sequencial disponivel
					
					HistoricoRecebimento objEntrada = new HistoricoRecebimento(); 
					
					objEntrada.setEntrada(entrada.getEntrada());
					objEntrada.setMes(mes);
					objEntrada.setValor(0);
					
					listEntradaAuxiliar.add(objEntrada);
					mes++;
				}
				
				if(entrada.getMes()==mes){
					
					HistoricoRecebimento objEntrada = new HistoricoRecebimento(); 
					
					objEntrada.setEntrada(entrada.getEntrada());
					objEntrada.setMes(mes);
					objEntrada.setValor(entrada.getValor());
					
					listEntradaAuxiliar.add(objEntrada);
					
					//totais das entradas mes a mes.
					for (SaldoCaixa saldoCaixa : listReceitas) {
						if (saldoCaixa.getMes()==(mes)) {
							double valorAtual = listReceitas.get(mes-1).getSaldoAnteriorCaixa(); //mes-1 devido lista iniciar em 0
							listReceitas.get(mes-1).setSaldoAnteriorCaixa(valorAtual+objEntrada.getValor());
						}
					}

				}

				mes++;
			}

			for (int i = 0; i < 12; i++) {

				SaldoCaixa objSaldoCaixaDespesas =  new SaldoCaixa();

				objSaldoCaixaDespesas.setMes(i+1); //alimenta meses 1 a 12
				objSaldoCaixaDespesas.setSaldoAnteriorCaixa(0.0); //alimenta com valor zero TODOS os meses (Saídas)
				
				listDespesas.add(objSaldoCaixaDespesas); //Zera listDespesas
			}

			mes=1; 
			int codSaida=listSaida.get(0).getSaida().getCodigo(); //Memoriza o primeiro código do TIPO de saída
			for (Saida saida : listSaida) {
				
				if (saida.getSaida().getCodigo()!=codSaida) { //se mudou o tipo de saída

					TipoSaida objTipoSaida = new TipoSaida();
					objTipoSaida.setCodigo(codSaida);
					objTipoSaida.setNucleo(objNucleo);
					
					objTipoSaida = TipoSaidaDAO.consultarTipoSaida(objTipoSaida);

					if(objTipoSaida==null){
						objTipoSaida = new TipoSaida();
						objTipoSaida.setCodigo(codSaida);
						objTipoSaida.setDescricao("Tipo Excluído");
                	}
					
					for (int j = mes; j < 13; j++) { //12 meses
						
						Saida objSaida = new Saida(); 
						
						objSaida.setSaida(objTipoSaida);
						objSaida.setMes(j);
						objSaida.setValor(0);
						
						listSaidaAuxiliar.add(objSaida);
					}
					codSaida = saida.getSaida().getCodigo();
					mes=1;
				}

				while (mes<saida.getMes()) { //se a saida iniciar com mes maior do que o mes sequencial disponível
					
					Saida objSaida = new Saida(); 
					
					objSaida.setSaida(saida.getSaida());
					objSaida.setMes(mes);
					objSaida.setValor(0);
					
					listSaidaAuxiliar.add(objSaida); //armazena valor zerado para os meses não populados
					mes++;
				}

				if(saida.getMes()==mes){ //se o mes nesta linha é o mesmo mes tratado

					Saida objSaida = new Saida(); 
					
					objSaida.setSaida(saida.getSaida());
					objSaida.setMes(mes);
					objSaida.setValor(saida.getValor()); 
					
					listSaidaAuxiliar.add(objSaida); //armazena com o valor existente para este mes
					
					//totais das saidas mes a mes.
					for (SaldoCaixa saldoCaixa : listDespesas) {
						
						if (saldoCaixa.getMes()==mes) {
							double valorAtual = listDespesas.get(mes-1).getSaldoAnteriorCaixa(); //mes-1 devido lista começar em 0
							listDespesas.get(mes-1).setSaldoAnteriorCaixa(valorAtual+objSaida.getValor());
							//Pode ser em única linha.
						}
					}
				}
				
				mes++;
				/*if (mes>12) {mes=1;}*/
			}

			for (int i = 0; i < 12; i++) {

				SaldoBanco objSaldoBanco =  new SaldoBanco();

				objSaldoBanco.setMes(i+1); //alimenta meses 1 a 12
				objSaldoBanco.setValor(0); //alimenta valores para TODOS os meses

				saldoBancosAuxiliar.add(objSaldoBanco);

				if(saldoBancos!=null){
					for (SaldoBanco saldoBanco : saldoBancos) {
						if (saldoBanco.getMes()==(i+1)) {
							saldoBancosAuxiliar.get(i).setValor(saldoBanco.getValor()); //alimenta valores para TODOS os meses
						}
					}
				}
			}
			//-----------------------------------------------------------------
			for (int i = 0; i < 12; i++) {
				
				Divida objDivida =  new Divida();
				
				objDivida.setMes(i+1); //alimenta meses 1 a 12
				objDivida.setValor(0); //alimenta valores para TODOS os meses

				dividaAnualAuxiliarAberta.add(objDivida);

				for (Divida divida : antecipacaoAnualAberta) { //dividaAberta
					
					if (divida.getMes()==(i+1)) {
						dividaAnualAuxiliarAberta.get(i).setValor(divida.getValor()); //alimenta valores para TODOS os meses
						//break;
					}
				}
			}

			for (int i = 0; i < 12; i++) {
				
				Divida objDivida =  new Divida();
				
				objDivida.setMes(i+1); //alimenta meses 1 a 12
				objDivida.setValor(0); //alimenta valores para TODOS os meses

				dividaAnualAuxiliarPaga.add(objDivida);

				for (Divida divida : antecipacaoAnualPaga) { //dividaAberta
					
					if (divida.getMes()==(i+1)) {
						dividaAnualAuxiliarPaga.get(i).setValor(divida.getValor()); //alimenta valores para TODOS os meses
						//break;
					}
				}
			}
			//-----------------------------------------------------------------

			for (int i = 0; i < 12; i++) {

				SaldoCaixa objReceitasMenosDespesas =  new SaldoCaixa();
				SaldoCaixa objSaldoCaixa =  new SaldoCaixa();
				SaldoCaixa objSaldoReceitas =  new SaldoCaixa();

				objReceitasMenosDespesas.setMes(i+1); //alimenta meses 1 a 12
				objSaldoReceitas.setMes(i+1);
				objSaldoCaixa.setMes(i+1);
				
				objReceitasMenosDespesas.setSaldoAnteriorCaixa((listReceitas.get(i).getSaldoAnteriorCaixa()+listSaldoAnteriorAuxiliar.get(i).getSaldoAnteriorCaixa()+dividaAnualAuxiliarAberta.get(i).getValor())-(listDespesas.get(i).getSaldoAnteriorCaixa()+dividaAnualAuxiliarPaga.get(i).getValor())); //alimenta com valor zero TODOS os meses (Saídas)
				listReceitasMenosDespesas.add(objReceitasMenosDespesas);

				objSaldoCaixa.setSaldoAnteriorCaixa((objReceitasMenosDespesas.getSaldoAnteriorCaixa()-saldoBancosAuxiliar.get(i).getValor()));
				listSaldoCaixa.add(objSaldoCaixa);
				
				objSaldoReceitas.setSaldoAnteriorCaixa(listReceitas.get(i).getSaldoAnteriorCaixa()+listSaldoAnteriorAuxiliar.get(i).getSaldoAnteriorCaixa()+dividaAnualAuxiliarAberta.get(i).getValor());
				listSaldoReceitas.add(objSaldoReceitas);
				
				//posteriormente melhorar este código!
			}

			sessao.setAttribute("listSaldoAnterior", listSaldoAnteriorAuxiliar);//1 - lista de saldo anterior para todos os meses do ano
			sessao.setAttribute("listEntrada", listEntradaAuxiliar);	//2 - lista de entradas para todos os meses do ano
			sessao.setAttribute("listSaida", listSaidaAuxiliar);		//3 - lista de saidas para todos os meses do ano
			sessao.setAttribute("saldoBancos", saldoBancosAuxiliar);	//4 - lista de saldo em bancos para todos os meses do ano
			
			sessao.setAttribute("listDividaAberta", dividaAnualAuxiliarAberta);	//5 - lista de antecipação aberta para todos os meses do ano
			sessao.setAttribute("listDividaPaga", dividaAnualAuxiliarPaga);	//6 - lista de antecipação paga para todos os meses do ano

			sessao.setAttribute("listReceitas", listReceitas);			//7 - lista de receitas para todos os meses do ano
			sessao.setAttribute("listDespesas", listDespesas);			//8 - lista de despesas para todos os meses do ano
			sessao.setAttribute("listReceitasMenosDespesas", listReceitasMenosDespesas); //9 - lista de receitas menos despesas para todos os meses do ano
			sessao.setAttribute("listSaldoCaixa", listSaldoCaixa);		//9 - lista de saldo caixa para todos os meses do ano
			sessao.setAttribute("listSaldoReceitas", listSaldoReceitas);//10 - lista de saldo receitas para todos os meses do ano
		}
		
		sessao.setAttribute("objMovIni", itemMov);
		request.setAttribute("controle", controle);
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
