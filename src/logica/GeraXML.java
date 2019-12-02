package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.DependenteDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.ListaDAO;
import DAO.LoginDAO;
import DAO.NucleoDAO;
import DAO.SocioDAO;
import DAO.SocioValorDAO;
import DAO.TipoEntradaDAO;
import beans.Dependente;
import beans.HistoricoDevedor;
import beans.Lista;
import beans.Login;
import beans.Nucleo;
import beans.Socio;
import beans.SocioValor;
import beans.TipoEntrada;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GeraXML implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pgJsp = "/stu?p=Acesso&flg=1";
		RequestDispatcher rd = null;

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		
		String diretorio = "//home//udvnmg//xmls//";
		
		/*
		String diretorio = "C:\\RVoffline\\";
		*/
		
		//necessário gerar um xml que contenha o código do sócio, nome, grau e situação e os meses pagos e em aberto
		ArrayList<HistoricoDevedor> listaHistoricoDevedor = new ArrayList<HistoricoDevedor>();
		listaHistoricoDevedor = HistoricoRecebimentoDAO.listarHistoricoDevedor(objOperador.getNucleo()); 
		
		//e um segundo xml que contenha lista de tipos de entrada com respectivos valores
		ArrayList<TipoEntrada> listTipoEntrada = new ArrayList<TipoEntrada>();
		listTipoEntrada = TipoEntradaDAO.listarTipoEntrada(objOperador.getNucleo().getCodigo(), 3);
		
		//um terceiro com as listas em aberto
		ArrayList<Lista> listDevedorLista = new ArrayList<Lista>();
		listDevedorLista = ListaDAO.listarListaDevedor(objOperador.getNucleo());
		//identificar o mês - data da lista. Trabalhar melhor o resultado desta consulta
		
		//um quarto com valores de entrada diferenciados
		ArrayList<SocioValor> listSocioValor = new ArrayList<SocioValor>();
		SocioValor objSocioValor = new SocioValor();
		
		objSocioValor.setCodSocio(0);
		objSocioValor.setCodEntrada(0);
		
		listSocioValor = SocioValorDAO.listarSocioValor(objSocioValor, objOperador.getNucleo());

		//um quinto com dados do Núcleo
		ArrayList<Nucleo> listNucleo = new ArrayList<Nucleo>();
		listNucleo = NucleoDAO.listarNucleoXML(objOperador.getNucleo().getCodigo());
		
		//um sexto com dados de operadores
		ArrayList<Login> listLogin = new ArrayList<Login>();
		listLogin = LoginDAO.listarLogin(objOperador);

		//um sétimo com socios dependetes
		ArrayList<Dependente> listDependente = new ArrayList<Dependente>();
		listDependente = DependenteDAO.listarDepenente(objOperador.getNucleo());

		//um oitavo com socios do núcleo
		ArrayList<Socio> listSocio = new ArrayList<Socio>();
		Socio objSocio = new Socio();
		
		String tipoSocio = "todos";
		listSocio = SocioDAO.listarSocio(objOperador.getNucleo().getCodigo(), objSocio, tipoSocio, 2); //

		//fazer a leitura dos xmls e tratar em memória o recebimento.
		//O resultado deve ser gravado em outro arquivo.
		XStream xstreamRecibo = new XStream(new DomDriver("UTF-8"));
		xstreamRecibo.alias("HistoricoDevedor", HistoricoDevedor.class);
		xstreamRecibo.registerConverter(new ConverteDataXML());
		xstreamRecibo.setMode(XStream.NO_REFERENCES);
		String xmlRecibo = xstreamRecibo.toXML(listaHistoricoDevedor);

		PrintWriter print = null;

		try {

			String nomeXML = diretorio+"historicoDevedor"+objOperador.getNucleo().getCodigo()+".xml";
			
			File file = new File(nomeXML);
			print = new PrintWriter(file);
			print.write(xmlRecibo);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}

		//---------------------------------------------------
		XStream xstreamEntrada = new XStream(new DomDriver("UTF-8"));
		xstreamEntrada.alias("TipoEntrada", TipoEntrada.class);

		String xmlEntrada = xstreamEntrada.toXML(listTipoEntrada);

		try {
			String nomeXML = diretorio+"tiposEntrada"+objOperador.getNucleo().getCodigo()+".xml";

			File file = new File(nomeXML);

			print = new PrintWriter(file);
			print.write(xmlEntrada);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}
		
		
		XStream xstreamLista = new XStream(new DomDriver("UTF-8"));
		xstreamLista.alias("Lista", Lista.class);

		String xmlLista = xstreamLista.toXML(listDevedorLista);

		try {
			String nomeXML = diretorio+"listasArrecadacao"+objOperador.getNucleo().getCodigo()+".xml";

			File file = new File(nomeXML);

			print = new PrintWriter(file);
			print.write(xmlLista);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}
		
		XStream xstreamSocioValor = new XStream(new DomDriver("UTF-8"));
		xstreamSocioValor.alias("SocioValor", SocioValor.class);

		String xmlSocioValor = xstreamSocioValor.toXML(listSocioValor);

		try {
			
			String nomeXML = diretorio+"socioValor"+objOperador.getNucleo().getCodigo()+".xml";

			File file = new File(nomeXML);

			print = new PrintWriter(file);
			print.write(xmlSocioValor);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}
		
		XStream xstreamNucleo = new XStream(new DomDriver("UTF-8"));

		xstreamNucleo.alias("Nucleo", Nucleo.class);
		String xmlNucleo = xstreamNucleo.toXML(listNucleo);

		
		try {
			
			String nomeXML = diretorio+"nucleo"+objOperador.getNucleo().getCodigo()+".xml";

			File file = new File(nomeXML);

			print = new PrintWriter(file);
			print.write(xmlNucleo);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}
		
		XStream xstreamLogin = new XStream(new DomDriver("UTF-8"));
		xstreamLogin.alias("Login", Login.class);

		String xmlLogin = xstreamLogin.toXML(listLogin);

		try {
			
			//File file = new File("C:\\RVoffline\\login.xml");
			//String nomeXML = "C:\\RVoffline\\login"+objOperador.getNucleo().getCodigo()+".xml";

			String nomeXML = diretorio+"login"+objOperador.getNucleo().getCodigo()+".xml";

			File file = new File(nomeXML);

			print = new PrintWriter(file);
			print.write(xmlLogin);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}
		
		XStream xstreamDependente = new XStream(new DomDriver("UTF-8"));
		xstreamDependente.alias("Dependente", Dependente.class);

		String xmlDependente = xstreamDependente.toXML(listDependente);

		try {
			
			String nomeXML = diretorio+"dependente"+objOperador.getNucleo().getCodigo()+".xml";

			File file = new File(nomeXML);

			print = new PrintWriter(file);
			print.write(xmlDependente);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}
		
		XStream xstreamSocio = new XStream(new DomDriver());
		xstreamSocio.alias("Socio", Socio.class);

		String xmlSocio = xstreamSocio.toXML(listSocio);

		try {
			
			String nomeXML = diretorio+"socio"+objOperador.getNucleo().getCodigo()+".xml";

			File file = new File(nomeXML);

			print = new PrintWriter(file);
			print.write(xmlSocio);
			print.flush();
			print.close();
			
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
		}finally{
			print.close();
		}
		
		//request.setAttribute("retorno", "Exportação Concluida");
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
