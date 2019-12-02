package logica;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.NaoSocio;
import beans.Recibo;
import beans.Socio;
import beans.TipoEntrada;


import DAO.HistoricoRecebimentoDAO;
import DAO.NaoSocioDAO;
import DAO.SocioDAO;
import DAO.TipoEntradaDAO;

public class RetornaRecibo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String pgJsp = "/consultaRecibo.jsp";
		String mens = "Não há recibos neste nome para o ano e mes informado";

		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		int numReceb = Integer.parseInt(sessao.getAttribute("numerorecibo").toString());

		//System.out.println("no RetornaRecibo: "+numReceb);
		
		if(numReceb>0){
			MovAtual objMovAtual = (MovAtual)sessao.getAttribute("objMovIni");
			int anoReceb = objMovAtual.getAno();

			//System.out.println("no RetornaRecibo: "+numReceb+"/"+anoReceb);

			ArrayList<HistoricoRecebimento> listHitRec = new ArrayList<HistoricoRecebimento>();
			listHitRec = HistoricoRecebimentoDAO.listarHistoricoRecebimento(numReceb, objOperador.getNucleo(), anoReceb, 0);

			//System.out.println("Após DAO: "+numReceb+"/"+anoReceb);

			mens = "Recibo "+numReceb+"/"+anoReceb+" não encontrado";

			if(listHitRec!=null){

				ArrayList<Recibo> listaDadosRecibo = new ArrayList<Recibo>(); 
				ArrayList<Socio> listaDadosSocio = new ArrayList<Socio>();
				boolean existeSocio=false;

				int meses[] = null;
				Recibo fpsgRecibo = null;
				int tipoEntr=0,codigoSoc=0,indice=-1;
				for (HistoricoRecebimento historico : listHitRec) {

					if (tipoEntr!=historico.getEntrada().getCodigo()||codigoSoc!=historico.getSocioDevedor().getCodigo()){

						meses = new int[12];
						for (int i = 0; i <= 11; i++){
							meses[i]=0;
						}
						meses[historico.getMes()-1]=historico.getMes();

						TipoEntrada objEntrada = new TipoEntrada();
						objEntrada.setCodigo(historico.getEntrada().getCodigo());
						objEntrada.setNucleo(objOperador.getNucleo());
						
						Socio socDev = new Socio();
						Socio socPag = new Socio();

						tipoEntr=objEntrada.getCodigo();
						objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);

						fpsgRecibo = new Recibo();

						fpsgRecibo.setEntrada(objEntrada);

						fpsgRecibo.setNumero(historico.getRecibo());
						fpsgRecibo.setMes(historico.getMes());
						fpsgRecibo.setAno(historico.getAno());
						fpsgRecibo.setData(historico.getData());
						fpsgRecibo.setFlSocio(historico.getFlSocio());

						socDev.setCodigo(historico.getSocioDevedor().getCodigo());

						SocioDAO objSocioDev = new SocioDAO();
						socDev = objSocioDev.consultarSocio(socDev, objOperador.getNucleo());
						codigoSoc=socDev.getCodigo();

						if(historico.getFlSocio().equalsIgnoreCase("N")){
							//Altera o nome do sócio para naoSocio como forma de simplificar a mostragem,
							//uma vez que não haverá alteração nos dados e o código é de fato não sócio.
							NaoSocio objNaoSocio = new NaoSocio();
							objNaoSocio.setCodigo(socDev.getCodigo());

							NaoSocioDAO objNaoSocioDev = new NaoSocioDAO();
							objNaoSocio = objNaoSocioDev.consultarNaoSoc(objNaoSocio,objOperador.getRegiao().getCodigo());

							socDev.setNome(objNaoSocio.getNome());
						}

						fpsgRecibo.setSocioDevedor(socDev); //<---------------------------------
						socPag.setCodigo(historico.getSocioPagador().getCodigo());

						SocioDAO objSocioPag = new SocioDAO();
						socPag = objSocioPag.consultarSocio(socPag, objOperador.getNucleo());

						if(historico.getFlSocio().equalsIgnoreCase("N")){
							//aqui para naoSocioPagador
							//Altera o nome do sócio para naoSocio como forma de simplificar a mostragem,
							//uma vez que não haverá alteração nos dados e o código é de fato não sócio.
							NaoSocio objNaoSocio = new NaoSocio();
							objNaoSocio.setCodigo(socPag.getCodigo());

							NaoSocioDAO objNaoSocioDev = new NaoSocioDAO();
							objNaoSocio = objNaoSocioDev.consultarNaoSoc(objNaoSocio,objOperador.getRegiao().getCodigo());

							socPag.setNome(objNaoSocio.getNome());
						}

						fpsgRecibo.setSocioPagador(socPag); //<---------------------------------

						fpsgRecibo.setMeses(meses);
						fpsgRecibo.setValor(historico.getValor());

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

						sessao.setAttribute("recibo", fpsgRecibo);
						sessao.setAttribute("excluido", historico.getExcluido());
					}else{
						meses[historico.getMes()-1]=historico.getMes();
						listaDadosRecibo.get(indice).setMeses(meses);
					}
				}
				DecimalFormat numero = new DecimalFormat( "0000" );		 
				SimpleDateFormat sdf = new SimpleDateFormat( "EEEE, dd 'de' MMMM 'de' yyyy" );
				mens = "Recibo "+numero.format(numReceb)+"/"+anoReceb+" - Emissão: "+sdf.format(fpsgRecibo.getData().getTime());

				sessao.setAttribute("anoRecibo", anoReceb);
				sessao.setAttribute("listaSoc", listaDadosSocio);
				sessao.setAttribute("listaRec", listaDadosRecibo);
				sessao.setAttribute("numeroRecibo", listaDadosRecibo.get(0).getNumero());

			}
			sessao.setAttribute("listaDados", sessao.getAttribute("listaRec"));
			sessao.setAttribute("dadosSocio", sessao.getAttribute("listaSoc")); 		
		}
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
