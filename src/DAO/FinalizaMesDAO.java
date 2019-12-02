package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import logica.StuConecta;

public class FinalizaMesDAO {

	public static void cancelarRecibosReserva(int pNucleo, int pMes, int pAno) throws StuDAOException{
		
		String SQL1 = "update tb_historico_recebimento set fl_excluido='S' where fl_excluido='R' and cd_pagador=0 and month(dt_data)="+pMes+" and vl_ano="+pAno+" and cd_nucleo="+pNucleo; 
		String SQL2 = "insert into tb_itens_historico_recebimento (select cd_recibo,cd_nucleo,vl_ano,month(dt_data),dt_data,0,"
				+"null,'S',1,0.00 from tb_historico_recebimento where cd_pagador=0 and month(dt_data)="+pMes+" and vl_ano="+pAno+" and cd_nucleo="+pNucleo+")"; 

		PreparedStatement ps = null;
		Connection conn = null;

		try {
			//------------------------------- SQL 1
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL1);

			ps.executeUpdate();
			StuConecta.closeConnection(conn, ps);

			//------------------------------- SQL 2
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL2);

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}

	}
	
	public static void finalizarMes(int pNucleo, int pMes,int pAno) throws StuDAOException{
		Connection conn = null;
		CallableStatement cs = null;
		
		//System.out.println("no DAO pNucleo,pMes,pAno "+pNucleo+" "+pMes+" "+pAno);
		
		try {
			conn = StuConecta.getConnection();

			cs = (CallableStatement) conn.prepareCall("{call finalizaMes(?,?,?)}");
			cs.setInt(1, pNucleo);
			cs.setInt(2, pMes);
			cs.setInt(3, pAno);

			cs.execute();

		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, cs);
		}
	}
}
