package logica;

public class ConverteMes {

	public static String numericoExtenso(int pMes){
		final String MesExtenso = "jan;fev;mar;abr;mai;jun;jul;ago;set;out;nov;dez;";
		if(pMes>0){
			pMes = pMes>12?12:pMes;
			return MesExtenso.substring(((4*pMes)-4),((4*pMes)-1));
		}else{
			return "";
		}
	}
}
