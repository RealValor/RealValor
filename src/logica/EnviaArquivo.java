package logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import beans.Login;

public class EnviaArquivo {
	public static void uploadArquivo(Login pSocio, String pArquivo) {
		//Retornar String com o resultado obtido
		String server = "174.142.161.97";
		int port = 21;

		String user = "udvnmg";
		String pass = "57udV@nm6";

		String diretorioLocal  = "C:\\RVoffline\\";
		String diretorioRemoto = "receive//";

		String arquivoRemoto = diretorioRemoto+pArquivo+pSocio.getNucleo().getCodigo()+".xml";
		String arquivoLocal = diretorioLocal+pArquivo+pSocio.getNucleo().getCodigo()+".xml";

		FTPClient ftpClient = new FTPClient();

		try {

			ftpClient.connect(server, port);
			ftpClient.login(user,pass);
			
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			InputStream arqEnviar = new FileInputStream(arquivoLocal);
			boolean success = ftpClient.storeFile(arquivoRemoto, arqEnviar);

			arqEnviar.close();

			if (success) {
				System.out.println("Ftp file successfully upload.");
				//fazer uma mostragem do resultado obtido.
			}else{
				System.out.println("Ftp file ERRO upload.");
			}

		} catch (IOException ex) {
			System.out.println("Error occurs in uploading files from ftp Server : " + ex.getMessage());
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

