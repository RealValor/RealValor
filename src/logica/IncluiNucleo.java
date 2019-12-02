package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BairroDAO;
import DAO.BancoDAO;
import DAO.EstadoDAO;
import DAO.NucleoDAO;
import beans.Bairro;
import beans.Banco;
import beans.Cidade;
import beans.Estado;
import beans.Login;
import beans.Nucleo;

public class IncluiNucleo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiNucleo.jsp";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		

		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		String nomeOperador = objOperador.getNome();

		Nucleo itemNucleo = new Nucleo();

		String nomeNucleo = objNucleo.getNome();

		Bairro objBairro = new Bairro();
		ArrayList<Bairro> listBairro = BairroDAO.listarBairro();

		if(objNucleo.getBairro()!=null){
			objBairro.setCodigo(objNucleo.getBairro().getCodigo());
			objBairro.setNome(objNucleo.getBairro().getNome());
		}else{
			if(listBairro != null){
				objBairro.setCodigo(listBairro.get(0).getCodigo());
				objBairro.setNome(listBairro.get(0).getNome());
			}
		}
		sessao.setAttribute("listBairro", listBairro);		                
		sessao.setAttribute("objBairro", objBairro);		                

		Cidade objCidade = new Cidade();

		if(objNucleo.getCidade()!=null){
			objCidade.setCodigo(objNucleo.getCidade().getCodigo());
			objCidade.setNome(objNucleo.getCidade().getNome());
		}
		
		sessao.setAttribute("objCidade", objCidade);		                

		Estado objEstado = new Estado();
		ArrayList<Estado> listEstado = EstadoDAO.listarEstado();
		
		if(objNucleo.getEstado()!=null){
			objEstado.setCodigo(objNucleo.getEstado().getCodigo());
			objEstado.setNome(objNucleo.getEstado().getNome());
		}else{
			if(listEstado != null){
				objEstado.setCodigo(listEstado.get(0).getCodigo());
				objEstado.setNome(listEstado.get(0).getNome());
				objEstado.setUf(listEstado.get(0).getUf());
				objEstado.setRegiaoUF(listEstado.get(0).getRegiaoUF());
			}
		}
		sessao.setAttribute("listEstado", listEstado);		                
		sessao.setAttribute("objEstado", objEstado);		                

		Banco objBanco = new Banco();
		ArrayList<Banco> listBanco = BancoDAO.listarBanco();

		if(objNucleo.getBanco_recebimento()!=null){
			objBanco.setCodigo(objNucleo.getBanco_recebimento().getCodigo());
			objBanco.setDescricao(objNucleo.getBanco_recebimento().getDescricao());
		}else{
			if(listBanco != null){
				objBanco.setCodigo(listBanco.get(0).getCodigo());
				objBanco.setDescricao(listBanco.get(0).getDescricao());
				objBanco.setSigla(listBanco.get(0).getSigla());
			}
		}
		sessao.setAttribute("listBanco", listBanco);		                
		sessao.setAttribute("objBanco", objBanco);		                

		itemNucleo.setNome(nomeNucleo);
		
		String cnpjNucleo = request.getParameter("cnpj_nucleo");
		
		if(cnpjNucleo!=null&&cnpjNucleo!=""){
			itemNucleo.setCnpj(cnpjNucleo);
		}
		
		itemNucleo.setRegiao(objOperador.getRegiao());
		
		Login objLogin = new Login();

		if(nomeOperador!=null&&nomeOperador!=""){
			objLogin.setNome(nomeOperador);
		}
		
		if(objOperador.getCpf()!=0){	
			objLogin.setCpf(objOperador.getCpf());
		}
		
		objLogin.setNivel(objOperador.getNivel());
		
		sessao.removeAttribute("objNucleo");
		
		NucleoDAO objNucleoDAO = new NucleoDAO();
		Nucleo itemRecNucleo = objNucleoDAO.consultarNomeNucleo(itemNucleo);

		sessao.setAttribute("objNucleo", itemRecNucleo);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
