package logica;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LoginDAO;
import DAO.NucleoDAO;
import DAO.NucleoReuniDAO;
import DAO.SocioDAO;
import DAO.SocioReuniDAO;
import beans.DadosSocioReuni;
import beans.HistoricoSocio;
import beans.Login;
import beans.MovAtual;
import beans.Notificacao;
import beans.Nucleo;
import beans.NucleoReuni;
import beans.Socio;
import beans.SocioReuni;

import com.google.gson.Gson;

public class Acesso implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String mens = "";
		String pgJsp = "/index.jsp";
		RequestDispatcher rd = null;
		HttpSession sessao = request.getSession();
		int cod=0,unidade=0,nivel=0;

		if(request.getParameter("flg")!=null&&request.getParameter("flg")!=""){
			cod=Integer.parseInt(request.getParameter("flg"));
			sessao.setAttribute("objUsu", null);
		}

		Calendar dataAtual = Calendar.getInstance();

		MovAtual objAnoMes = new MovAtual();
		objAnoMes.setAno(dataAtual.get(Calendar.YEAR)); 
		objAnoMes.setMes(dataAtual.get(Calendar.MONTH));

		if(sessao.getAttribute("objUsu")!=null&&cod==0){

			String escolheMes[] = {"jan","fev","mar","abr","mai","jun","jul","ago","set","out","nov","dez"};
			Login usuSet = (Login)sessao.getAttribute("objUsu");

			Locale idioma = new Locale("pt", "BR");
			SimpleDateFormat sdf = new SimpleDateFormat( "EEEE, dd 'de' MMMM 'de' yyyy",idioma);

			mens = "Real Valor - "+sdf.format(dataAtual.getTime());

			if(request.getParameter("cpf")!=null&&request.getParameter("cpf")!=""){
				usuSet.setCpf(Long.parseLong(request.getParameter("cpf").trim()));

				mens = "Tamanho mínimo da senha 6 caracteres";
				if(request.getParameter("senha")!=null&&request.getParameter("senha")!=""&&(request.getParameter("senha").trim()).length()>5){
					usuSet.setSenha(Encripta.encriptaDados(request.getParameter("senha").trim()));
					usuSet.setNivel(1);
				}
			}

			if(request.getParameter("tipo_unidade")!=null&&request.getParameter("tipo_unidade")!=""){
				unidade=Integer.parseInt(request.getParameter("tipo_unidade"));
				mens = "";
			}

			Nucleo usuNucleo = new Nucleo();
			usuNucleo.setCodigo(unidade);
			usuSet.setNucleo(usuNucleo);

			LoginDAO daoRecOp = new LoginDAO();
			usuSet = daoRecOp.consultarLogin(usuSet);

			if(usuSet!=null){

				NucleoDAO objNucleoDAO = new NucleoDAO();

				//-----------------------------------
				File origem = null;
				origem = new File("//home//udvnmg//receive//Nucleo"+usuSet.getNucleo().getCodigo()+".png");

				String caminhoImagem = null;
				if(origem.exists()){
					
					File destino = null;
					destino = new File("//home//udvnmg//appservers//apache-tomcat-7x//webapps//ROOT//imagens//Nucleo"+usuSet.getNucleo().getCodigo()+".png");
					
					if(!destino.exists()){
						Path source = Paths.get("//home//udvnmg//receive//Nucleo"+usuSet.getNucleo().getCodigo()+".png");
						Path destination = Paths.get("//home//udvnmg//appservers//apache-tomcat-7x//webapps//ROOT//imagens//Nucleo"+usuSet.getNucleo().getCodigo()+".png");
						Files.copy(source, destination);
					}
					
					caminhoImagem = "imagens/Nucleo"+usuSet.getNucleo().getCodigo()+".png";
				}

				sessao.setAttribute("imagem", caminhoImagem);
				//-----------------------------------

				pgJsp = "/principal"+usuSet.getNivel()+".jsp";

				if(!LoginDAO.consultarAcesso()){
					pgJsp = "/sistemaEmManutencao.jsp";
				}

				usuNucleo = objNucleoDAO.consultarNucleoLogin(usuSet);
				usuSet.setNucleo(usuNucleo);

				//sessao.setAttribute("objUsu", usuSet);
				//sessao.setAttribute("objNucleo", usuNucleo);

				int codigoNucleoReuni = NucleoReuniDAO.consultarCodigoNucleoReuni(usuSet.getNucleo().getCodigo());
				NucleoReuni objNucleoReuni = new NucleoReuni(); 

				Date dataHoje = new Date();
				
				boolean dataAtualizada = false; //sugere que são datas iguais, não precisa atualizar 
				//boolean dataAtualizada = false; //forçar atualizar sempre! Porque houveram uns erros de busca de nomes. Depois verificar como corrigir esta situação

				if(usuSet.getNivel()==3&&unidade<1){
					
					/*
					SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
					Date dataBanco = new Date();
					objNucleoReuni.setId_nucleo(codigoNucleoReuni);
					objNucleoReuni = NucleoReuniDAO.consultarNucleoReuni(objNucleoReuni);
					dataAtualizada = false;
					if(objNucleoReuni.getDt_atualizacao_socios()!=null){
						
						dataBanco = objNucleoReuni.getDt_atualizacao_socios();
						
						SimpleDateFormat formataDataBanco = new SimpleDateFormat("dd/MM/yyyy");
						formataDataBanco.format( dataBanco );
						
						dataAtualizada = formatador.format( dataHoje ).equals(formataDataBanco.format( dataBanco ));
					}
					 */
					dataAtualizada = true;
					
				}

				if(dataAtualizada&&codigoNucleoReuni>0){ //se for o primeiro acesso do dia. TRECHO MODIFICADO!
					//Inicialmente faz a gravação da data atual na tb_nucleo.

					objNucleoReuni.setDt_atualizacao_socios(dataHoje);
					NucleoReuniDAO objNucleoReuniDAO = new NucleoReuniDAO();
					objNucleoReuniDAO.alterarNucleoReuni(objNucleoReuni);
					objNucleoReuni.setId_nucleo(codigoNucleoReuni);
					
					//Utiliza estes dados para atualização nos respectivos campos do Real Valor, considerando apenas os sócios ativos. 
					String rootobj = LerDadosReuni.lerDadosReuni("http://api-reuni.udv.org.br/v1/core/"+codigoNucleoReuni+"/affiliates?api_token=9gWuhXweQJFKX18kUh66oIP2g6mzf1FkqHysJfG2");
					//Acima endpoint antigo do reuni
					
					//String rootobj = LerDadosReuni.lerDadosReuni("http://reunidev.udv.com.br/api/v1/core/"+codigoNucleoReuni+"/affiliates?api_token=9gWuhXweQJFKX18kUh66oIP2g6mzf1FkqHysJfG2");
					//Novo endpoint http://reunidev.udv.com.br/api/v1/ (Problemas ao baixar nomes)
					
					Gson gson = new Gson();
					DadosSocioReuni objDdadosSocioReuni = gson.fromJson(rootobj, DadosSocioReuni.class);

					SocioReuniDAO.zerarDadosAuxiliarReuni(codigoNucleoReuni);
					SocioReuniDAO.zerarDadosSocioReuni(codigoNucleoReuni);
					//TEM que apagar sempre a tb_socio_reuni, porque pode ocorrer de um status ser modificado e o id permanecer, 
					//por exemplo o sócio mudar de núcleo

					SocioDAO.gravarHistSocioTemp(usuSet);
					//inclui historico de sócio como transferido pra todos que estão em status <A>tivo
					
					SocioDAO.alterarStatusSocio(usuSet);
					//altera o status de todos os sócios ativos para <T>ransferido, abaixo atualiza o status de sócios constantes do Reuni que no caso de 
					//transferidos não são visíveis naquele sistema			

					int codigoSocio = 0;
					SocioReuniDAO objSocioReuniDAO = new SocioReuniDAO();

					for (int i = 0; i < objDdadosSocioReuni.getData().size()  ; i++) {

						objSocioReuniDAO.incluirSocioReuni(objDdadosSocioReuni.getData().get(i),objNucleoReuni.getId_nucleo());
						//Inclui sócio na tb_socio_reuni para depois incluir na tb_socio ou tb_auxiliar

						SocioDAO objSocioDAO = new SocioDAO();

						codigoSocio = objSocioDAO.compararReuniRealValor(objDdadosSocioReuni.getData().get(i),usuSet.getNucleo().getCodigo());

						if(codigoSocio>0){ //Encontrou nome igual - atualizar dados relevantes na tb_socio.

							SocioDAO.excluirHistSocioTemp(usuSet,codigoSocio);
							//exclui o historico com status transferido 'INCLUSAO AUTOMATICA' nesta data
							
							Socio objSocio = new Socio();

							objSocio.setCodigo(codigoSocio);
							
							objSocio = objSocioDAO.consultarSocio(objSocio, usuSet.getNucleo());
							
							objSocio.setNome(objDdadosSocioReuni.getData().get(i).getName());
							objSocio.setEmail(objDdadosSocioReuni.getData().get(i).getEmail());
							objSocio.setTelefone(objDdadosSocioReuni.getData().get(i).getCellphone());

							String status = objDdadosSocioReuni.getData().get(i).getStatus().toUpperCase().contains("FREQUENTE")?"A":objDdadosSocioReuni.getData().get(i).getStatus().toUpperCase().contains("LICENCIADO")?"L":(objDdadosSocioReuni.getData().get(i).getStatus().toUpperCase().contains("AFASTADO")||objDdadosSocioReuni.getData().get(i).getStatus().toUpperCase().contains("SUSPENSO"))?"F":"O";
							objSocio.setSituacao(status);
							
							String grau = objDdadosSocioReuni.getData().get(i).getDegree().substring(1,2).equalsIgnoreCase("D")?"C":objDdadosSocioReuni.getData().get(i).getDegree().substring(1,2);
							objSocio.setGrau(grau);

							objSocio.setAvatar(objDdadosSocioReuni.getData().get(i).getAvatar());
							//objSocio.setCpf(objSocio.getCpf());

							SocioDAO.atualizarDadosPrincipais(objSocio);
							
							if(SocioDAO.mudouStatusGrau(objSocio)){
								
								HistoricoSocio pHistoricoSocio = new HistoricoSocio();

								pHistoricoSocio.setCpfOperador(usuSet.getCpf());
								pHistoricoSocio.setDataHistorico(dataHoje);
								pHistoricoSocio.setDataLancamento(dataHoje);
								pHistoricoSocio.setSocio(objSocio);

								objSocioDAO.incluirHistSoc(pHistoricoSocio, usuSet.getNucleo());
								//Verifica se a atualização resultou em mudança de cargo ou status, em caso de mudança, grava historico 
							}
						}else{

							if(objDdadosSocioReuni.getData().get(i).getStatus().toUpperCase().contains("FREQUENTE")){
								
								objSocioReuniDAO.incluirAuxiliarReuni(objDdadosSocioReuni.getData().get(i),usuSet.getNucleo().getCodigo(),objNucleoReuni.getId_nucleo());
								//Verificar os casos em que o sócio é não frequente mas é pagante.
							}
						}
					}

					//Nivel 3 e primeiro acesso do dia - ALTERADO em 4/8/2019
					Notificacao objNotificacao = new Notificacao();
					objNotificacao = GeraEmail.gerarNotificacao(usuSet);
							
					int dianotificacao = NucleoDAO.checarNotificacaoNucleo(objNotificacao);
					
					SimpleDateFormat dia = new SimpleDateFormat("dd");

					if( (dianotificacao<1) || (Integer.parseInt(dia.format(dataHoje))>10 && dianotificacao<11) ){

						NucleoDAO.incluirNotificacao(GeraEmail.gerarNotificacao(usuSet));
						GeraEmail.enviarNotificacaoMensalidades(null, usuSet);

					}
				}

				ArrayList<SocioReuni> listaSocioReuni = SocioReuniDAO.listarAuxiliarSocioReuni(usuSet.getNucleo());

				if(listaSocioReuni!=null&&usuSet.getNivel()==3){
					pgJsp = "/listaSocioReuni.jsp";
					sessao.setAttribute("gravaTodos", 0);
					sessao.setAttribute("listaSocioReuni", listaSocioReuni);
					sessao.setAttribute("totalSocios", listaSocioReuni.size());
				}

				if(unidade<1){

					ArrayList<Login> listOperadorMultiploAcesso = new ArrayList<Login>();
					listOperadorMultiploAcesso = LoginDAO.listarMultiploLogin(usuSet);
					
					if(listOperadorMultiploAcesso.size()>1){
						sessao.setAttribute("operadorMultiplo", listOperadorMultiploAcesso);
						pgJsp = "/selecionaLogin.jsp";
						usuSet.setNucleo(listOperadorMultiploAcesso.get(0).getNucleo());
					}

				}

				sessao.setAttribute("objUsu", usuSet);
				sessao.setAttribute("objNucleo", usuNucleo);

				sessao.setAttribute("video", "imagens/VideoInicial.mp4;loop=1");

				String urlSet = "https://www.udvnmg.org/";
				/*
				String urlSet = "http://localhost:8080/STU/";
				*/

				sessao.setAttribute("urlSet", urlSet);
				nivel = usuSet.getNivel();
			}else{
				mens = "Digite usuário e senha válidos";
			}

			sessao.setAttribute("escolheMes", escolheMes);
			sessao.setAttribute("controleNivel", nivel);
		}
		
		sessao.setAttribute("objAnoMes", objAnoMes);
		request.setAttribute("retorno", mens);

		if(cod==1){
			sessao.invalidate();
		}
		
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);

	}
}
