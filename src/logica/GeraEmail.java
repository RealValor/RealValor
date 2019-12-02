package logica;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import DAO.HistoricoRecebimentoDAO;
import DAO.SocioDAO;
import DAO.StuDAOException;
import beans.Email;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.Notificacao;
import beans.Socio;

public class GeraEmail {
	
	public static void gerarEmail(Login pOperador, String pEmail, String pTexto, int pAnexo, String pCaminho){	
		
        Email mj = new Email();
        //mj.setSmtpHostMail("smtp.gmail.com");
        //mj.setSmtpPortMail("465");

        mj.setSmtpHostMail("mail.udvnmg.org");
        mj.setSmtpPortMail("587");

        mj.setSmtpAuth("true");
        mj.setSmtpStarttls("false");

        
        mj.setFromNameMail("Real Valor");
        mj.setUserMail("realvalor@udvnmg.org"); //email de envio 
        mj.setPassMail("rvudvnmg");

        mj.setCharsetMail("ISO-8859-1");
        mj.setSubjectMail("E-mail automático, não responder - Lembrete Real Valor");

        //mj.setBodyMail(htmlMessage());
        mj.setBodyMail(pTexto);
        
        mj.setTypeTextMail(Email.TYPE_TEXT_HTML);

        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<String, String>();
        map.put(pEmail, "email gmail");
        //map.put("franca@credjurd.com.br", "email gmail");

        mj.setToMailsUsers(map);

        //seta quantos anexos desejar
        List<String> files = new ArrayList<String>();

        if(pCaminho!=null){
        	files.add(pCaminho);
        }
        
        //files.add("F:\\Desenvolvimento\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp3\\wtpwebapps\\STU\\imagens\\"+"Recibo"+pOperador.getNucleo().getCodigo()+".png");
        //files.add("//home//udvnmg//appservers//apache-tomcat-7x//webapps//ROOT//imagens//Recibo"+pOperador.getNucleo().getCodigo()+".png");
        
        mj.setFileMails(files);

        try {
        	new EnviaEmail().senderMail(mj, pAnexo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /*
	@SuppressWarnings("unused")
    private static String htmlMessage() {
        return  "<html> " +"<head>" +"<title>Email no formato HTML com Javamail!</title> " +"</head> " +"<body> "+"</body> "+"</html>";
    }
	*/
	
	public static Notificacao gerarNotificacao(Login pUsuario){

		Date dataHoje = new Date();

		Notificacao notificacao = new Notificacao();
		notificacao.setDatanotificacao((new java.sql.Date(dataHoje.getTime())));
		notificacao.setNucleo(pUsuario.getNucleo().getCodigo());
		
		SimpleDateFormat dia = new SimpleDateFormat("dd");
		
		String tipo = Integer.parseInt(dia.format(dataHoje))<11?"A VENCER":"VENCIDA";
		
		notificacao.setTipo(tipo);
		
		return notificacao;
	}
	
	public static void enviarNotificacaoMensalidades(Socio pSocio, Login pOperador) throws StuDAOException{
		
		Socio objSocio = new Socio();
		objSocio.setCodigo(pSocio==null?0:pSocio.getCodigo()); 
		
		ArrayList<Socio> listSocio = new ArrayList<Socio>();
		listSocio = SocioDAO.listarSocioNotificacao(pOperador.getNucleo().getCodigo(), objSocio.getCodigo());

		if(listSocio!=null){
			
			Date dataHoje = new Date();
			Login itemPaga = new Login();
			
			SimpleDateFormat ano = new SimpleDateFormat("yyyy");
			SimpleDateFormat mes = new SimpleDateFormat("MM");
			SimpleDateFormat dia = new SimpleDateFormat("dd");
			
			for (Socio socio : listSocio) {


				if(socio.getEmail().indexOf("@") != -1){

					itemPaga.setUsuario(socio.getCodigo());
					itemPaga.setNucleo(pOperador.getNucleo());
					itemPaga.setNome(socio.getNome());
					itemPaga.setSituacao(socio.getSituacao());

					ArrayList<HistoricoRecebimento> listRecF = HistoricoRecebimentoDAO.listarHistoricoRecebimento(itemPaga,2,Integer.parseInt(ano.format(dataHoje)));
					
					int meslimite = Integer.parseInt(mes.format(dataHoje))-1;
					int totalmes  = Integer.parseInt(mes.format(dataHoje));
					int pagamentos[] = new int[meslimite]; //todos os meses

					for (int i = 0; i < meslimite; i++) {
						pagamentos[i] = 0; //considera todos os meses em aberto
					}

					for (HistoricoRecebimento historicoRecebimento : listRecF) {
						pagamentos[historicoRecebimento.getMes()-1] = historicoRecebimento.getMes();
						totalmes--;
					}

					//System.out.println("totalmes "+totalmes);

					if(totalmes>0){ //se existe algum mes em aberto incluindo a mensalidade a vencer
						
						String inadimplencia="";
						for (int i = 0; i < meslimite; i++) {
							if(pagamentos[i] == 0){
								inadimplencia = inadimplencia+ConverteMes.numericoExtenso(i+1)+", ";
							}
						}

						inadimplencia = inadimplencia.substring(0, inadimplencia.length()-(inadimplencia.length()>0?2:0));
						
						String grau = socio.getGrau().equalsIgnoreCase("M")?"M.":(socio.getGrau().equalsIgnoreCase("C")?"Cons.":"Ir.");
						String tratamento = socio.getSexo().equalsIgnoreCase("M")?"Prezado":"Prezada";
						//String complemento = socio.getSexo().equalsIgnoreCase("M")?"pelo Sr.":"pela Sra.";
						
						String textoInicial = "<html>"+tratamento+" "+grau+" "+socio.getNome()+", <br/><br/>Lembramos que";
						/*
						if(objSocio.getCodigo()!=0){
							textoInicial = "<html>"+tratamento+" "+grau+" "+socio.getNome()+", <br/><br/>Lembramos que";
						}
						*/
						String textoFinal = "<br/><br/>Atenciosamente, <br/>"+pOperador.getNome()+"<br/>"+pOperador.getCargo()+" - "+pOperador.getNucleo().getNome()+"</html>";

						//String anocomplemento = "/"+ano.format(dataHoje)+textoFinal;
						String textoMensagem = textoInicial+" dia 10 (dez) vence sua mensalidade.<br/>Acesse o Real Valor para consultar os valores, ou contate a Tesouraria para maiores informações."+textoFinal;
						//+ConverteMes.numericoExtenso(Integer.parseInt(mes.format(dataHoje)))+anocomplemento;

						if (totalmes>1||Integer.parseInt(dia.format(dataHoje))>10){ //existindo mais de uma mensalidade em aberto incluindo o mes a vecer
							/*
							textoMensagem = textoInicial+" está vencida a mensalidade do mês "+inadimplencia+anocomplemento;
							if(totalmes>2){
								textoMensagem = textoInicial+" estão vencidas as mensalidades dos meses "+inadimplencia+anocomplemento;
							}
							*/
							
							textoMensagem = textoInicial+" existem mensalidades em aberto.<br/>Acesse o Real Valor para consultar os valores, ou contate a Tesouraria para maiores informações."+textoFinal;
							//+inadimplencia+anocomplemento;
						}
						
						String email = socio.getEmail().trim();
						int finalemail = email.indexOf(" ");

						if(finalemail != -1){
							email = email.substring(0, finalemail) ;
						}
						
						//System.out.println("textoMensagem "+textoMensagem);
						
						if(email.indexOf("@") != -1){
							gerarEmail(pOperador, socio.getEmail(), textoMensagem, 0, null);
						}
					}

				}
				
			}
		}
	}
	
	public static void enviarEmailRecibo(Socio pSocio, Login pOperador, String pEmail, String pContexto){
		
		String grau = pSocio.getGrau().equalsIgnoreCase("M")?"M.":(pSocio.getGrau().equalsIgnoreCase("C")?"Cons.":"Ir.");
		String tratamento = pSocio.getSexo().equalsIgnoreCase("M")?"Prezado":"Prezada";
		
		String textoInicial = "<html>"+tratamento+" "+grau+" "+pSocio.getNome()+", <br/><br/>Encaminhamos recibo de pagamento de mensalidade anexo";
		String textoFinal = ". <br/><br/>Atenciosamente, <br/>"+pOperador.getNome()+"<br/>"+pOperador.getCargo()+" - "+pOperador.getNucleo().getNome()+"</html>";

		String textoMensagem = textoInicial+textoFinal;

		gerarEmail(pOperador, pEmail, textoMensagem, 1, pContexto);

	}

}
