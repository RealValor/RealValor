package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import DAO.ListaDAO;
import DAO.MovAtualDAO;
import DAO.NaoSocioDAO;
import DAO.SocioDAO;
import DAO.TipoEntradaDAO;
import beans.Dependente;
import beans.HistoricoRecebimento;
import beans.Lista;
import beans.Login;
import beans.MovAtual;
import beans.NaoSocio;
import beans.Recibo;
import beans.Socio;
import beans.TipoEntrada;

public class IncluiRecebimento implements LogicaDeNegocio{

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String pgJsp;
		String mens = "Para consultar mensalidades inclua um nome válido";
		
		String nomePaga = "";
		if(request.getParameter("consulta")!=null&&request.getParameter("consulta")!="")
			nomePaga = request.getParameter("consulta");

		int codSocio = 0;
		if(request.getParameter("codsocio")!=null&&request.getParameter("codsocio")!="")
			codSocio = Integer.parseInt(request.getParameter("codsocio"));

		int codTipo = 0;
		if(request.getParameter("codtipo")!=null&&request.getParameter("codtipo")!="")
			codTipo = Integer.parseInt(request.getParameter("codtipo"));
		
		int codLista = 0;
		if(request.getParameter("cod_lista")!=null&&request.getParameter("cod_lista")!="")
			codLista = Integer.parseInt(request.getParameter("cod_lista"));

		int anoReceb = 0;
		if(request.getParameter("ano_paga")!=null&&request.getParameter("ano_paga")!="")
			anoReceb = Integer.parseInt(request.getParameter("ano_paga"));

		Double valorTotal = 0.0;
		if(request.getParameter("valortotal")!=null&&request.getParameter("valortotal")!="")
			valorTotal =  Double.parseDouble(request.getParameter("valortotal"));

		int geradopelaLista = 0;
		if(request.getParameter("vialista")!=null&&request.getParameter("vialista")!="")
			geradopelaLista = Integer.parseInt(request.getParameter("vialista"));

		int anolista = 0;
		if(request.getParameter("anolista")!=null&&request.getParameter("anolista")!="")
			anolista = Integer.parseInt(request.getParameter("anolista"));

		int incluiDependente=1;
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

		if(itemComum!=null&&itemComum.getNivel()<2){
			pgJsp = "/incluiRecebimentoUsuario.jsp";
			codSocio=itemComum.getUsuario();
		}else{
			pgJsp = "/incluiRecebimento.jsp";
		}

		if(request.getParameter("ctrl")!=null&&request.getParameter("ctrl").equals("limpar")){
			sessao.removeAttribute("objPaga");
			sessao.removeAttribute("objMovIni");
			sessao.removeAttribute("objMovFin");
			sessao.removeAttribute("listTEnt");
			sessao.removeAttribute("listaRecibo");
			sessao.removeAttribute("listaSocio");
			sessao.removeAttribute("listDependente");
			incluiDependente=0;
			limpar=true;
			if(itemComum!=null&&itemComum.getNivel()<2){
				int consultaMensalidade=0; 
				sessao.setAttribute("consultaMensalidade", consultaMensalidade);
			}
		}//Necessário o posicionamento deste bloco, anterior às linhas abaixo.

		String[] situPaga = new String[2];
        situPaga[0] = "A";
        situPaga[1] = "Associado";

		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!=""){
			situPaga[0] = request.getParameter("situ_paga");
			situPaga[1] = request.getParameter("situ_paga").equals("N")?"Não Associado":"Associado";
		}

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

		int codPaga = 0;
		if(request.getParameter("cod_paga")!=null&&request.getParameter("cod_paga")!="")
			codPaga = Integer.parseInt(request.getParameter("cod_paga"));

		if(codSocio !=0){ //Utilizado apenas na exclusão de itens
			//Verificar essa condição uma vez que a exclusão de itens está em outra classe
			
			itemPaga.setUsuario(codSocio);

			if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga").equals("N")){
				NaoSocio objPagador = new NaoSocio();
				objPagador.setCodigo(codSocio);

				NaoSocioDAO naoSocioDAO = new NaoSocioDAO();

				itemPaga.setNome(naoSocioDAO.consultarNaoSoc(objPagador,objOperador.getRegiao().getCodigo()).getNome());
			}else{
				Socio objPagador = new Socio();
				objPagador.setCodigo(codSocio);

				itemPaga.setNucleo(objOperador.getNucleo());
				
				SocioDAO socioDAO = new SocioDAO();
				objPagador = socioDAO.consultarSocio(objPagador, objOperador.getNucleo());

				itemPaga.setNome(objPagador.getNome());
				
				nomePaga = itemPaga.getNome(); 
				codPaga = codSocio;
			}

			sessao.setAttribute("objPaga", itemPaga);
		}

		sessao.removeAttribute("recibosReserva");

		ArrayList<HistoricoRecebimento> recibosReserva = HistoricoRecebimentoDAO.consultarRecibosReserva(objOperador.getNucleo().getCodigo(),0,0);

		if(nomePaga!=null&&nomePaga!=""&&codPaga!=0&&codTipo==0){

			ArrayList<Dependente> listDependente = (ArrayList<Dependente>)sessao.getAttribute("listDependente");

			if(listDependente!=null&&request.getParameter("dependentes")!=null&&request.getParameter("dependentes")!=""){
				incluiDependente=listDependente.size()+1;
				if(Integer.parseInt(request.getParameter("dependentes"))<1){
					incluiDependente = 1;
					sessao.removeAttribute("listaSocio");
					sessao.removeAttribute("listDependente");
				}
			}

			for (int totalDep = 0; totalDep < incluiDependente; totalDep++) {

				if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!="")
					situPaga[0] = request.getParameter("situ_paga");

				double vlrReceb = 0;
				if(request.getParameter("vlr_unit")!=null&&request.getParameter("vlr_unit")!="")
					vlrReceb = Double.parseDouble(request.getParameter("vlr_unit"));

				if(totalDep>0){

					codPaga=listDependente.get(totalDep-1).getDependente().getCodigo();
					nomePaga=listDependente.get(totalDep-1).getDependente().getNome();
					codTipo=0;
					
					listTEnt = TipoEntradaDAO.listarTipoEntrada(objOperador.getNucleo().getCodigo(), 0); //0 - todos os tipos;
					//Realimenta entradas para dependentes
					
					//----------------------------------------------------------------------------------
					listTEnt = BuscaSocioValor.buscaSocioValor(codPaga, listTEnt);
					//----------------------------------------------------------------------------------

					for (TipoEntrada tipoEntrada : listTEnt) {
						if(tipoEntrada.getCodigo()==(tipReceb==0?1:tipReceb)){
							vlrReceb = tipoEntrada.getValor();
							break;
						}
					}
				}

				itemPaga.setNome(nomePaga);
				itemPaga.setNucleo(((Login)sessao.getAttribute("objUsu")).getNucleo());

				if(codPaga!=0)
					itemPaga.setUsuario(codPaga);

				Recibo itemRecibo = new Recibo();

				TipoEntrada tipoEntrada = new TipoEntrada();
				tipoEntrada.setCodigo(tipReceb);
				tipoEntrada.setNucleo(objOperador.getNucleo());

				tipoEntrada = TipoEntradaDAO.consultarTipoEntrada(tipoEntrada);
				itemRecibo.setEntrada(tipoEntrada);

				itemRecibo.setMes(itemRecMov.getMes());

				itemRecibo.setAno(anoReceb);
				itemRecibo.setData(Calendar.getInstance());
				itemRecibo.setCpfOperador(objOperador.getCpf());

				Socio objSocio = new Socio();
				objSocio.setCodigo(codPaga);

				if(situPaga[0].equalsIgnoreCase("N")){
					situPaga[1]="Não Associado";
					
					NaoSocio objNaoSocio = new NaoSocio();
					objNaoSocio.setNome(itemPaga.getNome());
					
					NaoSocioDAO buscaNaoSocio = new NaoSocioDAO();
					objNaoSocio = buscaNaoSocio.consultarNaoSoc(objNaoSocio, objOperador.getNucleo().getRegiao().getCodigo());
					
					objSocio.setNome(objNaoSocio.getNome());
					objSocio.setEmail(objNaoSocio.getEmail());
					
					itemRecibo.setFlSocio("N"); //S<im> ou <N>ão
				}else{
					SocioDAO buscaSocio = new SocioDAO();
					objSocio = 	buscaSocio.consultarSocio(objSocio, objOperador.getNucleo());
					itemRecibo.setFlSocio("S"); //S<im> ou <N>ão
				}

				itemRecibo.setSocioDevedor(objSocio);

				if(itemRecibo.getSocioPagador()==null)
					itemRecibo.setSocioPagador(objSocio);

				itemRecibo.setFechado("N"); //S<im> ou <N>ão
				
				ArrayList<HistoricoRecebimento> listMens = new ArrayList<HistoricoRecebimento>();
				//--------------------------------------------------------------Trabalhando - inicio
				//este trecho deve ser melhorado.

				if(totalDep>0){
					
					listMens = HistoricoRecebimentoDAO.listarHistoricoRecebimento(itemPaga,2,itemRecibo.getAno());

					if(listMens==null){
						listMens = HistoricoRecebimentoDAO.listarHistoricoRecebimento(itemPaga,1,itemRecibo.getAno());
					}
					
					iniReceb=1;
					if(listMens!=null){
						iniReceb=(listMens.get(listMens.size()-1).getMes()+1)>12?1:(listMens.get(listMens.size()-1).getMes()+1);
						
						boolean existeMes;
						boolean iniciaMes = false;
						for (int i = 0; i < 12; i++) {
							HistoricoRecebimento hisRec = new HistoricoRecebimento();
							hisRec.setMes(i+1);
							hisRec.setAno(anoReceb);
							existeMes = false;
							for (HistoricoRecebimento histRec : listMens) {
								if(histRec.getMes()==(i+1)){
									existeMes = true;
									iniciaMes = true;
								}
							}
							if (!existeMes){
								if(!iniciaMes){
									hisRec.setAno(0);
								}
								listMens.add(i,hisRec);
							}
						}
					}
					//--------------------------------------------------------------Trabalhando final.
				}else{
					listMens = (ArrayList<HistoricoRecebimento>)sessao.getAttribute("listMens");
				}
				
				int meses[] = new int[ 12 ];
				for (int i = 0; i <= 11; i++) {
					meses[i]=0;

					if(i+1>=iniReceb&&i+1<=finReceb){
						meses[i]=i+1;
						if((listMens != null) && (listMens.get(i).getData() != null)&&(tipReceb < 3)){
							meses[i]=0;
						}
					}
				}

				itemRecibo.setMeses(meses);
				itemRecibo.setValor(vlrReceb);
				
				itemRecibo.setNumeroLista(0);

				ArrayList<Recibo> listaDadosRecibo = (ArrayList<Recibo>)sessao.getAttribute("listaRecibo");
				
				if(sessao.getAttribute("listaRecibo")==null){
					listaDadosRecibo = new ArrayList<Recibo>();
				}
				
				//------------------verifica repetição de entrada
				int indice=-1,tipo=0;
				for (Recibo recibo : listaDadosRecibo) {
					if(itemRecibo.getSocioDevedor().getCodigo()==recibo.getSocioDevedor().getCodigo()&&itemRecibo.getEntrada().getCodigo()==recibo.getEntrada().getCodigo()){
						//verifica se já existe a entrada para o mesmo pagador.
						indice = listaDadosRecibo.indexOf(recibo);
						tipo=recibo.getEntrada().getCodigo();
						break;
					}
				}
				
				if(indice!=-1){
					//já existe a entrada para o mesmo pagador.
					listaDadosRecibo.remove(indice); //exclui item do tipo que será incluido.
					if(tipo==1){ //se o tipo entrada for mensalidade 

						int indicesExcluir[] = new int[listaDadosRecibo.size()]; 
						indice=-1;

						for (Recibo recibo : listaDadosRecibo) {
							if(itemRecibo.getSocioDevedor().getCodigo()==recibo.getSocioDevedor().getCodigo()&&recibo.getEntrada().getMensal().equalsIgnoreCase("S")){
								indice++;
								indicesExcluir[indice]=listaDadosRecibo.indexOf(recibo);
							}
						}

						while(indice>-1){
							listaDadosRecibo.remove(indicesExcluir[indice]); //se for mensalidade, excluindo todos de desconto mensal (F.P.S.G.), etc.
							indice--;
						}
					}
				}
				
				listaDadosRecibo.add(itemRecibo); //adiciona item ao recibo.
				//------------------verifica repetição de entrada (final)

				ArrayList<Socio> listaDadosSocio = (ArrayList<Socio>)sessao.getAttribute("listaSocio");
				if(sessao.getAttribute("listaSocio")==null){
					listaDadosSocio = new ArrayList<Socio>();
				}

				boolean existeSocio=false;
				for (Socio socio : listaDadosSocio) {
					if(itemRecibo.getSocioDevedor().getCodigo()==socio.getCodigo()){
						existeSocio=true;
					}
				}

				if(!existeSocio){
					listaDadosSocio.add(itemRecibo.getSocioDevedor());
				}

				if(tipoEntrada.getCodigo()==1){//se for mensalidade

					for (TipoEntrada tipoEntr : listTEnt) {

						if(tipoEntr.getMensal().equalsIgnoreCase("S")&&tipoEntr.getCodigo()!=1){
							//adiciona automaticamente todas as entradas de desconto mensal
							Recibo fpsgRecibo = new Recibo();

							fpsgRecibo.setEntrada(tipoEntr);

							fpsgRecibo.setMes(itemRecibo.getMes());
							fpsgRecibo.setAno(itemRecibo.getAno());
							fpsgRecibo.setData(Calendar.getInstance());
							fpsgRecibo.setCpfOperador(itemRecibo.getCpfOperador());
							fpsgRecibo.setFlSocio(itemRecibo.getFlSocio());
							fpsgRecibo.setSocioDevedor(itemRecibo.getSocioDevedor()); 
							fpsgRecibo.setSocioPagador(itemRecibo.getSocioPagador());
							fpsgRecibo.setMeses(meses);
							fpsgRecibo.setValor(tipoEntr.getValor());
							fpsgRecibo.setNumeroLista(0);

							listaDadosRecibo.add(fpsgRecibo);
							sessao.setAttribute("recibo", fpsgRecibo);

						}
					}
				}

				if(itemRecibo.getFlSocio().equalsIgnoreCase("S")){

					ArrayList<Lista> devedorLista = ListaDAO.consultarDevedor(itemRecibo.getSocioDevedor().getCodigo(), objOperador.getNucleo(),codLista,anolista);
					//Busca de dívida em listas.
					
					if (devedorLista!=null){

						codLista = 0;

						for (Lista tipoLista : devedorLista) {

							Recibo fpsgRecibo = new Recibo();

							fpsgRecibo.setEntrada(tipoLista.getEntrada());

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(tipoLista.getData().getTime());

							int mesLista = calendar.get(Calendar.MONTH)+1;
							
							fpsgRecibo.setMes(mesLista);
							fpsgRecibo.setAno(tipoLista.getAno());
							fpsgRecibo.setData(Calendar.getInstance());
							fpsgRecibo.setCpfOperador(itemRecibo.getCpfOperador());
							fpsgRecibo.setFlSocio(itemRecibo.getFlSocio());
							fpsgRecibo.setSocioDevedor(tipoLista.getSocioDevedor()); 
							fpsgRecibo.setSocioPagador(itemRecibo.getSocioPagador());
							
							int mesesLista[] = new int[ 12 ];
							for (int i = 0; i <= 11; i++) {
								mesesLista[i]=0;
								if(i==mesLista-1){
									mesesLista[i]=mesLista;
								}
							} //realimentar meses

							if(valorTotal==0){
								valorTotal = tipoLista.getValorPendente();
							}

							if(tipoLista.getFlContinua().equalsIgnoreCase("S")){
								
								if(geradopelaLista==1){
									mesesLista[data.get(Calendar.MONTH)]=data.get(Calendar.MONTH)+1;
								}else{
									mesesLista = itemRecibo.getMeses();
									valorTotal = tipoLista.getQtdePagamentos()>0?tipoLista.getValorPendente()/tipoLista.getQtdePagamentos():tipoLista.getValorPendente();
								}
							}

							fpsgRecibo.setMeses(mesesLista);
							
							fpsgRecibo.setValor(valorTotal);
							
							fpsgRecibo.setNumeroLista(tipoLista.getNumero());
							fpsgRecibo.setParcelasRestantes(tipoLista.getQtdePagamentos());

							fpsgRecibo.setFechado("N");
							
							int indiceLista=-1;
							for (Recibo objRecibo : listaDadosRecibo) {
								if(objRecibo.getEntrada().getCodigo()==fpsgRecibo.getEntrada().getCodigo()&&
										objRecibo.getSocioDevedor().getCodigo()==fpsgRecibo.getSocioDevedor().getCodigo()&&
										objRecibo.getAno()==fpsgRecibo.getAno()&&objRecibo.getNumeroLista()==fpsgRecibo.getNumeroLista()){

									indiceLista=listaDadosRecibo.indexOf(objRecibo);
								}
							}
							if(indiceLista>-1){
								listaDadosRecibo.remove(listaDadosRecibo.get(indiceLista)); 
								//exclui entrada duplicada.
							}

							listaDadosRecibo.add(fpsgRecibo);
							sessao.setAttribute("recibo", fpsgRecibo);

							valorTotal=0.0;
						}
					}
				}

				//------------------------------------------------trabalhando
				ArrayList<Recibo> listaReciboAuxiliar = new ArrayList<Recibo>();
				boolean excluiLinhaZerada=false;
				for (Recibo objRecibo : listaDadosRecibo){
					//Trecho exclui linhas zeradas do recibo.
					boolean existeDebito=false;
					for (int i = 0; i < objRecibo.getMeses().length; i++) {
						if(objRecibo.getMeses()[i]>0 && objRecibo.getValor()>0){
							existeDebito=true;
							break;
						}
					}
					if(existeDebito){
						listaReciboAuxiliar.add(objRecibo);
					}else{
						excluiLinhaZerada=true;
					}
				}
				
				if(excluiLinhaZerada){
					listaDadosRecibo=listaReciboAuxiliar;
				}
				//------------------------------------------------trabalhando final
				
				int tamanhoLista = listaDadosRecibo.size();
				request.setAttribute("tamanhoLista" , tamanhoLista);

				sessao.setAttribute("listaRecibo", listaDadosRecibo);
				sessao.setAttribute("listaSocio", listaDadosSocio);
				sessao.setAttribute("listaSoc", listaDadosSocio);

				if(totalDep>0){
					//Posiciona o nome do sócio pagante, anterior aos dependentes. 
					itemPaga.setUsuario(listaDadosSocio.get(0).getCodigo());
					itemPaga.setNome(listaDadosSocio.get(0).getNome());
				}
			}

		}else{
			if(!(codSocio!=0&&codTipo!=0)){
				sessao.removeAttribute("listaRecibo");
				sessao.removeAttribute("listaSocio");
				sessao.removeAttribute("listDependente");
			}
		}

		request.setAttribute("situPaga", situPaga);
		
		if(codSocio!=0&&codTipo!=0){
			//Bloco que trata exclusão de socios do ArrayList, para evitar repetição de nomes no recibo.
			ArrayList<Recibo> listDadosRec = (ArrayList<Recibo>)sessao.getAttribute("listaRecibo");

			int indice=-1;
			for (Recibo objRecibo : listDadosRec) {
				if(objRecibo.getSocioDevedor().getCodigo()==codSocio&&objRecibo.getEntrada().getCodigo()==codTipo
						&&objRecibo.getNumeroLista()==codLista&&objRecibo.getAno()==anoReceb){
					indice = listDadosRec.indexOf(objRecibo);
					break;
				}
			}
			if(indice!=-1){
				listDadosRec.remove(indice);
			}
			sessao.setAttribute("listaRecibo", listDadosRec);
		}

		sessao.setAttribute("consultaRecibo", 0);

		sessao.setAttribute("objPaga", itemPaga);
		sessao.setAttribute("listaDados", sessao.getAttribute("listaRecibo"));
		sessao.setAttribute("recibosReserva", recibosReserva);
		sessao.setAttribute("video", "imagens/VideoRecebimento.mp4;loop=1");

		request.setAttribute("mudouMes", mudouMes);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
