package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import logica.StuConecta;
import beans.Recibo;


public class RecebimentoOffLineDAO {
	
	String MensErr = "Erro de acesso aos dados "; 

	public void incluirReciboOffline(ArrayList<Recibo> pListRec) throws StuDAOException{
		
		if(pListRec.get(0).getOperador().getNivel()==2){
			return;
		}else{
			
			String SQL1 = "insert into tb_recebimento_offline values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String SQL2 = "insert into tb_itens_recebimento_offline values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = null;
			Connection conn = null;

			try {
				int x = 0;

				for (Recibo itemRecibo : pListRec) {

					if(x<1){

						//------------------------------------------entrada
						conn = StuConecta.getConnection();
						ps = conn.prepareStatement(SQL1);
						
						ps.setInt(1, itemRecibo.getNumero());
						ps.setInt(2, itemRecibo.getNucleo());
						ps.setInt(3, itemRecibo.getAno());
						ps.setDate(4, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
						ps.setInt(5, itemRecibo.getData().get(Calendar.YEAR));
						ps.setLong(6, itemRecibo.getCpfOperador());
						ps.setInt(7, itemRecibo.getSocioPagador().getCodigo());
						ps.setString(8, itemRecibo.getFechado());
						ps.setString(9, "N");

						ps.executeUpdate();
						StuConecta.closeConnection(conn, ps);
					}

					int meses[]=itemRecibo.getMeses();
					
					for (int i = 0; i < itemRecibo.getMeses().length; i++) {

						if(meses[i]!=0){
							//------------------------------------------itens entrada

							conn = StuConecta.getConnection();
							ps = conn.prepareStatement(SQL2);

							ps.setInt(1, itemRecibo.getNumero());
							ps.setInt(2, itemRecibo.getNucleo());
							ps.setInt(3, itemRecibo.getAno());
							ps.setInt(4, meses[i]);
							ps.setDate(5, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
							ps.setInt(6, itemRecibo.getData().get(Calendar.YEAR));
							ps.setInt(7, itemRecibo.getSocioDevedor().getCodigo()); //Long? (int)
							ps.setString(8, itemRecibo.getSocioDevedor().getNome());
							ps.setString(9, itemRecibo.getFlSocio()); //<S/N>
							ps.setInt(10, itemRecibo.getEntrada().getCodigo());
							ps.setDouble(11, itemRecibo.getValor());

							ps.executeUpdate();
							StuConecta.closeConnection(conn, ps);
						}
					}
					x++;
				}
			}catch (SQLException sqle){
				throw new StuDAOException(MensErr+sqle);
			}
			finally{
				StuConecta.closeConnection(conn, ps);
			}
		}
	}
}
