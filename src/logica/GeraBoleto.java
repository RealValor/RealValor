package logica;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import DAO.NucleoDAO;
import beans.Login;
import beans.Nucleo;

public class GeraBoleto implements LogicaDeNegocio{

	@SuppressWarnings("deprecation")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiRecebimentoUsuario.jsp";

		Double totalBoleto = 0.0;
		if(request.getParameter("total")!=null&&request.getParameter("total")!="")
			totalBoleto = Double.parseDouble(request.getParameter("total"));

		
		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		Nucleo objNucleo = new Nucleo();
		//objNucleo.setCodigo(objOperador.getNucleo().getCodigo());

		NucleoDAO objNucleoDAO = new NucleoDAO();
		objNucleo = objNucleoDAO.consultarNucleoLogin(objOperador);
		
		/*
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 */
		Cedente cedente = new Cedente("CEBUDV - N�cleo "+objNucleo.getNome(),objNucleo.getCnpj());

		/*
		 * INFORMANDO DADOS SOBRE O SACADO.
		 */
		
		Sacado sacado = new Sacado(objOperador.getNome(), objOperador.getCpfStr());

		// Informando o endere�o do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setUF(UnidadeFederativa.RO);
		enderecoSac.setLocalidade("Natal"); //inclui dados a partir do nucleo
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setBairro("Grande Centro");
		enderecoSac.setLogradouro("Rua poeta dos programas");
		enderecoSac.setNumero("1");
		sacado.addEndereco(enderecoSac);

		/*
		 * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
		
		SacadorAvalista sacadorAvalista = new SacadorAvalista("Sem avalista", "00.000.000/0001-91");

		// Informando o endere�o do sacador avalista.
		Endereco enderecoSacAval = new Endereco();

		enderecoSacAval.setUF(UnidadeFederativa.DF);
		enderecoSacAval.setLocalidade("Bras�lia");
		enderecoSacAval.setCep(new CEP("59000-000"));
		enderecoSacAval.setBairro("Grande Centro");
		enderecoSacAval.setLogradouro("Rua Principal");
		enderecoSacAval.setNumero("001");
		sacadorAvalista.addEndereco(enderecoSacAval);

		 * INFORMANDO OS DADOS SOBRE O T�TULO.
		 */

		// Informando dados sobre a conta banc�ria do t�tulo.
		/*
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
		*/
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_ITAU.create());

		contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0")); //Pegar no cadastro do n�cleo
		contaBancaria.setCarteira(new Carteira(30));
		contaBancaria.setAgencia(new Agencia(1234, "1"));

		Date vencimento = new Date();		
		vencimento.setDate(vencimento.getDate() + 3);
		
		Titulo titulo = new Titulo(contaBancaria, sacado, cedente); //, sacadorAvalista
		titulo.setNumeroDoDocumento("123456");
		titulo.setNossoNumero("99345678912");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(totalBoleto));
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(vencimento);
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(Aceite.A);
		titulo.setDesconto(new BigDecimal(0.05));
		titulo.setDeducao(BigDecimal.ZERO);
		titulo.setMora(BigDecimal.ZERO);
		titulo.setAcrecimo(BigDecimal.ZERO);
		titulo.setValorCobrado(BigDecimal.ZERO);

		//System.out.println("aqui 1");

		/*
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 */
		Boleto boleto = new Boleto(titulo);

		//System.out.println("aqui 2");

		boleto.setLocalPagamento("Pag�vel em qualquer Banco at� o Vencimento.");
		boleto.setInstrucaoAoSacado("Senhor sacado observe a data do vencimento!");
		boleto.setInstrucao1("Ap�s 5 dias cobrar acr�scimo de 3% a.m.");
		/*
		boleto.setInstrucao2("PARA PAGAMENTO 2 at� Amanh� N�o cobre!");
		boleto.setInstrucao3("PARA PAGAMENTO 3 at� Depois de amanh�, OK, n�o cobre.");
		boleto.setInstrucao4("PARA PAGAMENTO 4 at� 04/xx/xxxx de 4 dias atr�s COBRAR O VALOR DE: R$ 01,00");
		boleto.setInstrucao5("PARA PAGAMENTO 5 at� 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
		boleto.setInstrucao6("PARA PAGAMENTO 6 at� 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
		boleto.setInstrucao7("PARA PAGAMENTO 7 at� xx/xx/xxxx COBRAR O VALOR QUE VOC� QUISER!");
		boleto.setInstrucao8("AP�S o Vencimento, Pag�vel Somente na Rede X.");
		*/

		/*
		 * GERANDO O BOLETO BANC�RIO.
		 */
		// Instanciando um objeto "BoletoViewer", classe respons�vel pela
		// gera��o do boleto banc�rio.
		BoletoViewer boletoViewer = new BoletoViewer(boleto);

		// Gerando o arquivo. No caso o arquivo mencionado ser� salvo na mesma
		// pasta do projeto. Outros exemplos:
		// WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
		// LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");

		File arquivoPdf = boletoViewer.getPdfAsFile("C:/Temp/Boleto_UDV.pdf");

		// Mostrando o boleto gerado na tela.
		EmiteBoleto.mostraBoletoNaTela(arquivoPdf);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);

	}

}
