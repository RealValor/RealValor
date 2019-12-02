package logica;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class CapturaImagem {
	public static void capturarImagem(String pDestino) throws Exception {

		System.out.println(" chegou na Class captura imagem");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		//Definindo a dimensão que quer capturar
		//pode ser definido o tamanho que desejar
		
		System.out.println(" vai ler o tamanho da tela");

		//Dimension screenSize = new Dimension(810, 660);
		
		//-----------------------------------------------
		//-----------------------------------------------
		
		Dimension screenSize = toolkit.getScreenSize().getSize(); //este funciona localmente
		//Dimension screenSize = toolkit.getScreenSize();

		//verificar https://ctrlq.org/code/19136-screenshots-javascript
		
		System.out.println(" vai definir coordenadas pra captura");
		
		Rectangle screenRect = new Rectangle(screenSize);

		System.out.println(" vai definir coordenadas pra captura2");

		screenRect.setBounds(119, 153, 810, 660);
		//screenRect.setBounds(x, y, width, height)

		System.out.println(" vai capturar imagem");
		// criando o print screen
		Robot robot = new Robot();
		BufferedImage screenCapturedImage = robot.createScreenCapture(screenRect);
		
		System.out.println("antes de gravar arquivo");
		
		ImageIO.write(screenCapturedImage, "png", new File(pDestino));
		
	}
}
