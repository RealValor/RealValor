package logica;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Autenticador extends Authenticator {
	//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp
	public String username = null;
	public String password = null;

	public Autenticador(String usuario, String senha) {
		username = usuario;
		password = senha;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication (username,password);
	}
}
