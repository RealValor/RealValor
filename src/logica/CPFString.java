package logica;

import java.text.DecimalFormat;

import DAO.StuDAOException;

public class CPFString {
	public static String converteCPF(long pCpf) throws StuDAOException{
		String cpfResult="";
		if(pCpf>0){
			long cpf=pCpf;
			DecimalFormat cpfStrForm = new DecimalFormat( "00000000000" );		 
			String cpfStr=cpfStrForm.format(cpf);
			cpfResult= cpfStr.substring(0,3)+"."+cpfStr.substring(3,6)+"."+cpfStr.substring(6,9)+"-"+cpfStr.substring(9,11);
		}
		return cpfResult;
	}
}
