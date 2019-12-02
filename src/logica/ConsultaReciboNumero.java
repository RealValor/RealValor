package logica;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import DAO.NaoSocioDAO;
import DAO.SocioDAO;
import DAO.TipoEntradaDAO;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.NaoSocio;
import beans.Recibo;
import beans.Socio;
import beans.TipoEntrada;

public class ConsultaReciboNumero implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "Para consultar recibos inclua um número válido";
		String pgJsp = "/consultaReciboNumero.jsp";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if(objOperador.getNucleo().getCodigo()==38){
			pgJsp = "/consultaReciboMudaData.jsp";  //Temporário e específico para atender solicitação AJGC
		}

		int numRecibo = 0;
		if(request.getParameter("numero_recibo")!=null&&request.getParameter("numero_recibo")!="")
			numRecibo = Integer.parseInt(request.getParameter("numero_recibo"));
		
		String consultaRecibo = "";
		if(request.getParameter("ctrl")!=null&&request.getParameter("ctrl")!="")
			consultaRecibo = request.getParameter("ctrl");
		
		String recibo = "";
		if(consultaRecibo.equalsIgnoreCase("consulta")){
			pgJsp = "stu?p=ConsultaMensalidade";
		}else if(consultaRecibo.equalsIgnoreCase("pornome")){
			pgJsp = "/consultaRecibo.jsp";
		}else if(consultaRecibo.equalsIgnoreCase("cancelar")){
			pgJsp = "/cancelaRecibo.jsp";
		}else if(consultaRecibo.equalsIgnoreCase("lista")){
			recibo = request.getParameter("recibo");
			numRecibo = Integer.parseInt(recibo.substring(0,recibo.length()-5));
		}

		MovAtual itemMov = new MovAtual();

		itemMov.setFechado("N");
		itemMov.setNucleo(objOperador.getNucleo());
		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		
		Calendar data = Calendar.getInstance();
		itemRecMov.setAno(data.get(Calendar.YEAR));
		
		if(request.getParameter("mesini_paga")!=null&&Integer.parseInt(request.getParameter("mesini_paga"))>0){
			itemRecMov.setMes(Integer.parseInt(request.getParameter("mesini_paga")));
		}
		if(request.getParameter("ano_paga")!=null&&Integer.parseInt(request.getParameter("ano_paga"))>0){
			itemRecMov.setAno(Integer.parseInt(request.getParameter("ano_paga")));
		}

		sessao.setAttribute("objMovIni", itemRecMov);

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

		sessao.setAttribute("numeroRecibo", 0);
		sessao.setAttribute("cargarecibo", 0);
		sessao.removeAttribute("listaRec");
		
		if(numRecibo!=0){

			int anoRecibo = 0;
			if(request.getParameter("ano_paga")!=null&&request.getParameter("ano_paga")!="")
				anoRecibo = Integer.parseInt(request.getParameter("ano_paga"));
			
			if(recibo.length()>0){ //Quando a consulta é a partir de lista
				anoRecibo = Integer.parseInt(recibo.substring(recibo.length()-4));

			}
			
			ArrayList<HistoricoRecebimento> listHistRec = new ArrayList<HistoricoRecebimento>();
			listHistRec = HistoricoRecebimentoDAO.listarHistoricoRecebimento(numRecibo, objOperador.getNucleo(),anoRecibo, 0); //cancelaRecibo

			mens = "Recibo "+numRecibo+"/"+anoRecibo+" não encontrado";

			if(listHistRec!=null){
				ArrayList<Recibo> listaDadosRecibo = new ArrayList<Recibo>(); 
				ArrayList<Socio> listaDadosSocio = new ArrayList<Socio>();
				boolean existeSocio=false;

				int meses[] = null;
				Recibo fpsgRecibo = null;
				int tipoEntr=0,codigoSoc=0,indice=-1,gravaMes=0,selecionaSocioPagador=0; //posicao=0,
				double valor=0.00;

				for (HistoricoRecebimento historico : listHistRec) {

					if (codigoSoc!=historico.getSocioDevedor().getCodigo()||tipoEntr!=historico.getEntrada().getCodigo()
							||(tipoEntr==historico.getEntrada().getCodigo()&&historico.getValor()!=valor)||gravaMes==historico.getMes()){
						
						valor=historico.getValor();
						
						meses = new int[12];
						for (int i = 0; i <= 11; i++){
							meses[i]=0;
						}
						meses[historico.getMes()-1]=historico.getMes();

						TipoEntrada objEntrada = new TipoEntrada();
						objEntrada.setCodigo(historico.getEntrada().getCodigo());
						
						Socio socDev = new Socio();
						Socio socPag = new Socio();
						
						tipoEntr=objEntrada.getCodigo();
						objEntrada.setNucleo(objOperador.getNucleo());
						
						objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);

						fpsgRecibo = new Recibo();
						
						fpsgRecibo.setEntrada(objEntrada);
						fpsgRecibo.setNumero(historico.getRecibo());

						fpsgRecibo.setNucleo(historico.getNucleo().getCodigo());

						fpsgRecibo.setMes(historico.getMes());
						fpsgRecibo.setAno(historico.getAno());
						fpsgRecibo.setData(historico.getData());
						fpsgRecibo.setCpfOperador(historico.getCpfOperador());
						fpsgRecibo.setFlSocio(historico.getFlSocio());

						socDev.setCodigo(historico.getSocioDevedor().getCodigo());

						SocioDAO objSocioDev = new SocioDAO();
						
						if(historico.getFlSocio().equalsIgnoreCase("N")){ //naoSocioPagador
							
							NaoSocio objNaoSocio = new NaoSocio();
							objNaoSocio.setCodigo(socDev.getCodigo());
							
							NaoSocioDAO objNaoSocioDAO = new NaoSocioDAO();
							objNaoSocio = objNaoSocioDAO.consultarNaoSoc(objNaoSocio,objOperador.getRegiao().getCodigo());

							socDev.setNome(objNaoSocio.getNome());
						}else{
							socDev = objSocioDev.consultarSocio(socDev, objOperador.getNucleo());
						}
						codigoSoc=socDev.getCodigo();

						fpsgRecibo.setSocioDevedor(socDev);
						socPag.setCodigo(historico.getSocioPagador().getCodigo());

						if(selecionaSocioPagador==0){
							
							SocioDAO objSocioPag = new SocioDAO();
							socPag = objSocioPag.consultarSocio(socPag, objOperador.getNucleo());
							
							if(socPag==null){
								//aqui para naoSocioPagador
								NaoSocio objNaoSocio = new NaoSocio();
								objNaoSocio.setCodigo(historico.getSocioPagador().getCodigo());

								NaoSocioDAO objNaoSocioDev = new NaoSocioDAO();
								objNaoSocio = objNaoSocioDev.consultarNaoSoc(objNaoSocio,objOperador.getRegiao().getCodigo());

								socPag = new Socio();

								socPag.setCodigo(objNaoSocio.getCodigo());
								socPag.setNome(objNaoSocio.getNome());

							}
							selecionaSocioPagador++;
						}

						fpsgRecibo.setSocioPagador(socPag);
						fpsgRecibo.setMeses(meses);
						fpsgRecibo.setValor(historico.getValor());
						//----------------------------------------
						fpsgRecibo.setObservacao(historico.getObservacao());
						
						listaDadosRecibo.add(fpsgRecibo);
						indice = listaDadosRecibo.indexOf(fpsgRecibo);
						
						existeSocio=false;
						if(listaDadosSocio!=null){
							for (Socio socio : listaDadosSocio) {
								if(fpsgRecibo.getSocioDevedor().getCodigo()==socio.getCodigo()){
									existeSocio=true;
								}
							}
						}

						if(!existeSocio){
							listaDadosSocio.add(fpsgRecibo.getSocioDevedor());
						}

						sessao.setAttribute("excluido", historico.getExcluido());
						
					}else{

						meses[historico.getMes()-1]=historico.getMes();
						listaDadosRecibo.get(indice).setMeses(meses);
					}
					gravaMes=historico.getMes();
					
				}

				DecimalFormat numero = new DecimalFormat( "0000" );		 
				SimpleDateFormat sdf = new SimpleDateFormat( "EEEE, dd 'de' MMMM 'de' yyyy" );
				mens = "Recibo "+numero.format(numRecibo)+"/"+anoRecibo+" - Emissão: "+sdf.format(fpsgRecibo.getData().getTime());

				sessao.setAttribute("recibo", fpsgRecibo);

				sessao.setAttribute("listaRec", listaDadosRecibo);
				sessao.setAttribute("consultaRecibo", 1);

				sessao.setAttribute("listaSoc", listaDadosSocio);
				sessao.setAttribute("numeroRecibo", listaDadosRecibo.get(0).getNumero());
				sessao.setAttribute("anoRecibo", anoRecibo);
			}else{
				mens = "Recibo "+numRecibo+"/"+anoRecibo+" é um recibo reservado ainda não utilizado. Não contém itens para exibição";
				if((HistoricoRecebimentoDAO.listarReciboReserva(numRecibo, objOperador.getNucleo(),anoRecibo)).equalsIgnoreCase("S")){
					mens = "Recibo "+numRecibo+"/"+anoRecibo+" é um recibo reservado cancelado. Não contém itens para exibição";
				}
			}

		}else{
			sessao.removeAttribute("objPaga");
		}
		
		sessao.setAttribute("dadosSocio", sessao.getAttribute("listaSoc")); 		
		request.setAttribute("situPaga", situPaga);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
