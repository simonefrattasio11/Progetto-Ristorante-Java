	package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import View.CassiereViewController;

    // Classe molto semplice che ci consente di calcolare il totale relativo ad un tavolo attraverso 2 query 
public class Conto extends CassiereViewController {
	private double totale=0;
	
	
	public void calcolaTotale(int numeroTavolo) {
		
		 ResultSet rs = null;
		 PreparedStatement prepStat = null;
		
		 Connection connection=(Connection) SimpleSingleton.getinstance().con;
		 try {
				String sql="select * From Ordine  where IDTavolo = ?";
				String sql2="select * From Pietanza where IDPietanza = ?";
				 prepStat = connection.prepareStatement(sql);
				prepStat.setInt(1,numeroTavolo);
				 rs=prepStat.executeQuery();
				 while(rs.next()) {
					 PreparedStatement prepStat2=null;
					 ResultSet rs2=null;
					 try {
					 prepStat2=connection.prepareStatement(sql2);
					 prepStat2.setInt(1,rs.getInt(2));
					 rs2=prepStat2.executeQuery();
					 totale=totale+(rs2.getDouble(3)*rs.getInt(3));
					 System.out.println("Totale tavolo" + numeroTavolo + totale);
					 rs2.close();
					 prepStat2.close();
					 }
					 catch(SQLException e) {
						 e.printStackTrace();
					 }
					 }
				 
				 prepStat.close();

				 rs.close();
				 }
				 
				
			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 
			
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(int totale) {
		this.totale = totale;
	}

}
