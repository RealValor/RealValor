package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cargo;
import beans.HistoricoSocio;
import beans.Login;
import beans.Socio;


import DAO.CargoDAO;
import DAO.LoginDAO;
import DAO.SocioDAO;

public class IncluiSocio implements LogicaDeNegocio{ //Socio(Principal)
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RequestDispatcher rd = null;
		
		String mens="";
		String pgJsp = "/incluiSocio.jsp";
		String nomeSoc = request.getParameter("consulta");
		String retorno  = request.getParameter("modulo");
		
		String codSoc  = request.getParameter("cod_socio");
		String teleSoc = request.getParameter("telefone_socio");
		String emailSoc = request.getParameter("email_socio");
		String cpfSoc  = request.getParameter("cpf_socio");
		String sexoSoc = request.getParameter("sexo_socio");
		String nascSoc = request.getParameter("data_nasc");
		String assoSoc = request.getParameter("data_assoc");
		String grauSoc = request.getParameter("grau_socio");
		String histSoc = request.getParameter("data_historico");
		String cargSoc = request.getParameter("cargo_socio");		
		String situSoc = request.getParameter("situacao");
		String senhaSoc= request.getParameter("senha_socio");
		String obsSoc  = request.getParameter("observacao");
		String isencaoSoc = request.getParameter("isencao");
		String notificaSoc = request.getParameter("notificacao");
		
		HttpSession sessao = request.getSession();
		Socio itemSoc = new Socio();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		Login itemComum = new Login();
		itemComum = (Login)sessao.getAttribute("objUsu");
		//Refatorar este codigo!!!

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		if(itemComum!=null&&itemComum.getNivel()<2){
			pgJsp = "/incluiSocioUsuario.jsp";
			itemSoc.setCodigo(itemComum.getUsuario());
			
			SocioDAO ConSocioDAO = new SocioDAO();			
			itemSoc = ConSocioDAO.consultarSocio(itemSoc, objOperador.getNucleo());

			nomeSoc = itemSoc.getNome();

		}else{
			pgJsp = "/incluiSocio.jsp";

			if(codSoc!=null&&codSoc!=""){
				itemSoc.setCodigo(Integer.parseInt(codSoc));
			}
			
			if(emailSoc!=null&&emailSoc!=""){	
				itemSoc.setEmail(emailSoc);
			}
			
			if(teleSoc!=null&&teleSoc!=""){	
				itemSoc.setTelefone(teleSoc);
			}

			String sexo="M";
			if(sexoSoc!=null&&sexoSoc!=""){
				sexo=sexoSoc;
			}
			itemSoc.setSexo(sexo);				
			
			if(nascSoc!=null&&!nascSoc.equals("")){					
				itemSoc.setDataNasc((Date)formato.parse(nascSoc));
			}
			
			if(assoSoc!=null&&!assoSoc.equals("")){	
				itemSoc.setDataAsso((Date)formato.parse(assoSoc));
			}
			
			itemSoc.setGrau("S");
			if(grauSoc!=null&&grauSoc!=""){
				itemSoc.setGrau(grauSoc);
			}
			
			Cargo intemCar = new Cargo();
			intemCar.setCodigo(0);
			if(cargSoc!=null&&cargSoc!="" ){
				intemCar.setCodigo(Integer.parseInt(cargSoc));
			}
			itemSoc.setCargo(intemCar);
			
			String situ="A";
			if(situSoc!=null&&situSoc!=""){
				if(situSoc.trim().equalsIgnoreCase("licenca")){
					situ="L";
				}else if(situSoc.trim().equalsIgnoreCase("afastado")){
					situ="F";
				}else if(situSoc.trim().equalsIgnoreCase("transferido")){
					situ="T";
				}else if(situSoc.trim().equalsIgnoreCase("outro")){
					situ="O";
				}
			}
			itemSoc.setSituacao(situ);

			if(senhaSoc==null||senhaSoc.equals("")){	
				senhaSoc="123456";
			}
			itemSoc.setSenha(Encripta.encriptaDados(senhaSoc));
			
			if(obsSoc!=null&&!obsSoc.equals("")){	
				itemSoc.setObservacao(obsSoc);
			}
			
			itemSoc.setIsencao("N");
			if(isencaoSoc!=null&&!isencaoSoc.equals("")){	
				itemSoc.setIsencao(isencaoSoc);
			}

			itemSoc.setNotifica("N");
			if(notificaSoc!=null&&!notificaSoc.equals("")){	
				itemSoc.setNotifica(notificaSoc);
			}

		}
		
		ArrayList<Cargo> listCar = CargoDAO.listarCargo();
		if(listCar != null){
			request.setAttribute("listCar", listCar);		                
		}
		
		if(nomeSoc!=null&&nomeSoc!=""&&(retorno==null || retorno=="")){	
			itemSoc.setNome(nomeSoc);
			//Refatorar codigo. 

			Boolean cpfValido=true;
			//Inicia em true para permitir inclusão quando não houver CPF
			if(cpfSoc!=null&&cpfSoc!=""){
				if (VerificaCPF.verificarCPF(cpfSoc)==1){
					itemSoc.setCpf(Long.parseLong(cpfSoc));
				}else{
					mens = "CPF Inválido. Cadastro não efetuado";
					cpfValido=false;
				}
			}

			SocioDAO daoRecF = new SocioDAO();			
			Socio itemRecF = daoRecF.consultarSocioNucleo(itemSoc, objOperador.getNucleo());

			mens = "Socio já cadastrado: "+itemSoc.getNome();  

			SocioDAO objSocioDAO = new SocioDAO();

			if(itemRecF==null&&cpfValido){

				SocioDAO daoIncF = new SocioDAO();
				Socio objSocio = new Socio();
				objSocio=null;
				
				Boolean controle = true;

				LoginDAO objLoginDAO = new LoginDAO();
				if(itemSoc.getCpf()!=0){
					objSocio = objSocioDAO.consultarSocioCPF(itemSoc);
				}

				if(objSocio==null){

					daoIncF.incluirSocio(itemSoc);
					itemSoc = daoRecF.consultarCodigoSocio(itemSoc); //atualiza o codigo do novo socio
					mens = itemSoc.getNome()+" Incluído! ";

				}else{
					
					itemSoc=objSocioDAO.consultarNomeSocio(objSocio);
					
					Login objLogin = new Login();
					objLogin.setUsuario(itemSoc.getCodigo());
					objLogin = objLoginDAO.consultarOperador(objLogin);
					
					if(itemSoc.getSituacao().equalsIgnoreCase("T")){

						Cargo objCargo = new Cargo();
						objCargo.setCodigo(0);
						
						itemSoc.setSituacao("A");
						itemSoc.setCargo(objCargo);
						
						objSocioDAO.alterarSocio(itemSoc);
						
						objSocioDAO.excluirSocioNucleo(itemSoc);
						objLoginDAO.excluirLogin(objLogin);
					}else{
						String situacao = itemSoc.getSituacao().equalsIgnoreCase("A")?"Ativo":(itemSoc.getSituacao().equalsIgnoreCase("L")?"em Licença":(itemSoc.getSituacao().equalsIgnoreCase("F")?"Afastado":"Ausente por outro motivo"));
						String nucleoOrigem = objLogin!=null?objLogin.getNucleo().getNome():"(núcleo não encontrado)";
						mens = "O cpf "+itemSoc.getCpf()+" consta em cadastro do Núcleo "+nucleoOrigem;
						if(objLogin!=null){
							mens = mens+" e permanece "+situacao+". Necessário alterar para Transferido no núcleo de origem";
						}
						//controle = false; Assim, libera a inclusão de sócios mesmo estando ativo em outro núcleo. Útil para socios que participam de DAVs 
					}
				}
				
				itemSoc.setSenha(Encripta.encriptaDados(senhaSoc)); //Atualiza senha recebida
				
				if(controle){
					objSocioDAO.incluirSocioNucleo(itemSoc.getCodigo(), itemComum.getNucleo().getCodigo());
					if(itemSoc.getCpf()!=0&&!itemSoc.getSenha().isEmpty()){
						
						if(obsSoc!=null&&!obsSoc.equals("")){	
							itemSoc.setObservacao(obsSoc);
						}
						
						Login itemOper = new Login();
						
						itemOper.setCpf(itemSoc.getCpf());
						itemOper.setNucleo(itemComum.getNucleo());
						itemOper.setUsuario(itemSoc.getCodigo());
						itemOper.setSenha(Encripta.encriptaDados(senhaSoc));
						itemOper.setNivel(1);
						
						if(objLoginDAO.consultarUsuNome(itemOper)==null){
							objLoginDAO.incluirLogin(itemOper);
						}else if(controle){
							objLoginDAO.alterarLogin(itemOper);
						}
						
					}
					
					//----- bloco inclusao historico do socio.
					HistoricoSocio objHistoricoSocio = new HistoricoSocio();
					objHistoricoSocio.setSocio(itemSoc);
					
					objHistoricoSocio.setDataHistorico(new Date());
					
					//System.out.println(objHistoricoSocio.getDataHistorico());
					
					if(histSoc!=null&&!histSoc.equals("")){	
						objHistoricoSocio.setDataHistorico((Date)formato.parse(histSoc));
					}
					
					objHistoricoSocio.setCpfOperador(objOperador.getCpf());
					objHistoricoSocio.setDataLancamento(new java.util.Date());
					
					SocioDAO daoIncHist = new SocioDAO();
					daoIncHist.incluirHistSoc(objHistoricoSocio, itemComum.getNucleo());
				} //Refatorar a codificação acima

				//-----------------------------------------
			}
			
			sessao.setAttribute("objSoc", itemRecF);
			
		}else{
			sessao.removeAttribute("imagemAvatar");
			sessao.removeAttribute("listDependente");
			sessao.removeAttribute("objSoc"); //verificar
		}
		
		if(retorno!=null && retorno!=""){
			itemSoc.setNome(request.getParameter("consulta"));
			request.setAttribute("objSoc", itemSoc);
			sessao.setAttribute("btnRetorna", retorno);			
		}
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}

}
