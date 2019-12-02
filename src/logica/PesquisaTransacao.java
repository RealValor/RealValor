package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import DAO.StuDAOException;
import beans.Nucleo;

public class PesquisaTransacao {

	public static void pesquisaTransacao(String pTransacao, Nucleo pNucleo) throws StuDAOException, IOException{
		
		//URL u = new URL("https://ws.pagseguro.uol.com.br/v3/transactions/"+pTransacao+"?email=udvnmgstu@gmail.com.br&token=9812CB06751D4358B37DDB60014993C7");
		URL u = new URL("https://sandbox.pagseguro.uol.com.br/v3/transactions/"+pTransacao+"?email="+pNucleo.getEmail()+"&token=1A1C6552D077407F817B6BFD699BF120");

		//System.out.println("vai acionar URL "+u.getFile());

		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);
		uc.setDoInput(false);
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		/*
		PrintWriter pw = new PrintWriter(uc.getOutputStream());
		pw.println(validaNPI.toString());
		pw.close();
		*/

		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		//String res = in.readLine();
		//System.out.println(res);
		
		in.close();
	}
}
