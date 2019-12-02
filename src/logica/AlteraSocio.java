package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CargoDAO;
import DAO.LoginDAO;
import DAO.SocioDAO;
import beans.Cargo;
import beans.HistoricoSocio;
import beans.Login;
import beans.Socio;

public class AlteraSocio implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiSocio.jsp";
		String nomeSoc = request.getParameter("consulta");

		HttpSession sessao = request.getSession();
		Socio itemSoc = new Socio();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		String codSoc  = request.getParameter("cod_socio");
		String grauSoc = request.getParameter("grau_socio");
		String histSoc = request.getParameter("data_historico");
		String cargSoc = request.getParameter("cargo_socio");		
		String situSoc = request.getParameter("situacao");
		String obsSoc  = request.getParameter("observacao");
		String isencaoSoc = request.getParameter("isencao");
		String notificaSoc = request.getParameter("notificacao");

		Login itemComum = new Login();
		itemComum = (Login)sessao.getAttribute("objUsu");
		//Refatorar este codigo

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		if(itemComum!=null&&itemComum.getNivel()<3){
			pgJsp = "/incluiSocioUsuario.jsp";
			itemSoc.setCodigo(itemComum.getUsuario());
			
			SocioDAO ConSocioDAO = new SocioDAO();			
			itemSoc = ConSocioDAO.consultarSocio(itemSoc, objOperador.getNucleo());
		}else{
			pgJsp = "/incluiSocio.jsp";

			if(codSoc!=null&&codSoc!=""){
				itemSoc.setCodigo(Integer.parseInt(codSoc));
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

		String emailSoc = request.getParameter("email_socio");
		String teleSoc = request.getParameter("telefone_socio");
		String cpfSoc  = request.getParameter("cpf_socio");
		String sexoSoc = request.getParameter("sexo_socio");
		String nascSoc = request.getParameter("data_nasc");
		String assoSoc = request.getParameter("data_assoc");
		String senhaSoc= request.getParameter("senha_socio");

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

		ArrayList<Cargo> listCar = CargoDAO.listarCargo();
		if(listCar != null){
			request.setAttribute("listCar", listCar);		                
		}

		if(senhaSoc==null||senhaSoc.equals("")){	
			senhaSoc="123456";
		}
		itemSoc.setSenha(Encripta.encriptaDados(senhaSoc));

		if(nomeSoc!=null&&nomeSoc!=""){	

			itemSoc.setNome(null);
			//Necessário para suportar alteração de nome

			SocioDAO daoRecF = new SocioDAO();
			Socio itemRecF = daoRecF.consultarSocio(itemSoc, objOperador.getNucleo());

			mens = "Cadastro não encontrado: "+nomeSoc;  

			Boolean cpfValido=true;
			if(cpfSoc!=null&&cpfSoc!=""){
				if (VerificaCPF.verificarCPF(cpfSoc)==1){
					itemSoc.setCpf(Long.parseLong(cpfSoc));
				}else{
					mens = "CPF Inválido. Cadastro não efetuado";
					cpfValido=false;
				}
			}

			if(itemRecF!=null&&cpfValido){
				SocioDAO daoAltF = new SocioDAO(); 

				//---------------------------------------------------
				itemSoc.setNome(nomeSoc);			
				//Necessário para garantir a alteração de nome

				Login objLogin = new Login();
				objLogin.setCpf(itemSoc.getCpf());

				LoginDAO objLoginDAO = new LoginDAO();
				objLogin = objLoginDAO.consultarCPFOperador(objLogin);

				/*
				if(objLogin!=null&&objLogin.getNucleo().getCodigo()!=objOperador.getNucleo().getCodigo()){
					mens = "O cpf "+itemSoc.getCpfStr()+" consta em cadastro do Núcleo "+objLogin.getNucleo().getNome();
				}else{
				*/	
					daoAltF.alterarSocio(itemSoc);
					daoAltF.unificarCargoSocio(itemSoc, objOperador.getNucleo());
					
					if(itemSoc.getCpf()!=0&&itemSoc.getSenha()!=null){

						Login itemOper = new Login(); //Inclui Sócio como operador do sistema em nível 1
						
						itemOper.setCpf(itemSoc.getCpf());
						itemOper.setNucleo(objOperador.getNucleo());
						itemOper.setSenha(Encripta.encriptaDados(senhaSoc));
						itemOper.setUsuario(itemSoc.getCodigo());
						itemOper.setNivel(1);

						if(objLoginDAO.consultarOperador(itemOper)==null){
							if(objLoginDAO.consultarCPFOperador(itemOper)==null){
								objLoginDAO.incluirLogin(itemOper);
							}
						}else{
							itemOper.setNivel(objLoginDAO.consultarOperador(itemOper).getNivel());
							
							objLoginDAO.alterarLogin(itemOper);
						}
					}

					SocioDAO daoConSocio = new SocioDAO();
					itemSoc = daoConSocio.consultarSocio(itemSoc, objOperador.getNucleo());
					
					if(obsSoc!=null&&!obsSoc.equals("")){	
						itemSoc.setObservacao(obsSoc);
					}
					
					mens = "Item atualizado: "+itemSoc.getNome();
					
					//----- inicio inclusao historico do socio.
					HistoricoSocio objHistoricoSocio = new HistoricoSocio();
					objHistoricoSocio.setSocio(itemSoc);
					
					objHistoricoSocio.setDataHistorico(new Date()); //AQUI
					
					if(histSoc!=null&&!histSoc.equals("")){	
						objHistoricoSocio.setDataHistorico((Date)formato.parse(histSoc));
					}

					
					if(itemComum.getNivel()>2){
						objHistoricoSocio.setCpfOperador(objOperador.getCpf());
						objHistoricoSocio.setDataLancamento(new java.util.Date());
						
						SocioDAO daoRecHist = new SocioDAO();
						HistoricoSocio objHisSoc = daoRecHist.consultarHistSocio(objHistoricoSocio, objOperador);
						HistoricoSocio objHisCar = daoRecHist.consultarHistCargo(objHistoricoSocio, objOperador);

						SocioDAO daoIncHist = new SocioDAO();
						if(objHisSoc==null&&objHisCar==null){
							daoIncHist.incluirHistSoc(objHistoricoSocio, itemComum.getNucleo());
							
						}else{
							if(objHisSoc!=null){
								daoIncHist.alterarHistSocio(objHistoricoSocio, itemComum.getNucleo());
								//Se a data de alteração for igual a data do histórico, apenas altera.
							}else{
								daoIncHist.alterarHistCargo(objHistoricoSocio);
							}

						}
					}
				//}
			}
			
			request.setAttribute("objSoc", itemSoc);
			sessao.setAttribute("objSoc", itemSoc);

		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
