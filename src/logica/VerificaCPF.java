package logica;

import DAO.StuDAOException;

public class VerificaCPF {

	public static int verificarCPF(String pCpf) throws StuDAOException{
		
		String s = pCpf;
		for (int i = s.length(); i < 11; i++) {
			s="0"+s;
		}
		
		String c = s.substring(0,9);
		String dv = s.substring(9,11);

		int d1 = 0;
		int result = 1;	

		for (int i = 0; i < 9; i++){
			d1 += Integer.parseInt(c.substring(i,i+1))*(10-i);
		}
		
		if (d1 == 0){
			result = 0;
		}

		d1 = 11 - (d1 % 11);
		if (d1 > 9) d1 = 0;
		
		if (Integer.parseInt(dv.substring(0, 1)) != d1){
			result = 0;
		}

		d1 *= 2;
		for (int i = 0; i < 9; i++){
			d1 += Integer.parseInt(c.substring(i,i+1))*(11-i);
		}

		d1 = 11 - (d1 % 11);
		if (d1 > 9) d1 = 0;
		
		if(Integer.parseInt(dv.substring(1, 2)) != d1){
			result = 0;
		}

		if (result==0) {
			return 0;
		}else{
			return 1;
		}
	}
}
