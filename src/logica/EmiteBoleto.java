package logica;

import java.io.File;
import java.io.IOException;

public class EmiteBoleto {

	static void mostraBoletoNaTela(File arquivoBoleto) {
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

		try {
			desktop.open(arquivoBoleto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
