package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// La classe percentuale ha 2 metodi che servono per calcolare le percentuali di vendita :
// il primo calcola le percentuali su tutti i prodotti venduti e restituisce una observable list (che poi sarà visualizzata attraverso una tableview
// accessibile dalla schermata cassiere;
//  il secondo restituisce direttamente la pietanza più venduta di una categoria data in input è utile per la schermata cameriere per consigliare
//  al cliente i piatti più venduti di ogni categoria


public class Percentuale {
     public static  ObservableList<Pietanza> calcolaTutte(){
    	 ObservableList<Pietanza> ob=FXCollections.observableArrayList();
    	double totVenduti=0;
		double parziale=0;
		Connection connection=(Connection) SimpleSingleton.getinstance().con;
		ResultSet rs=null;
		try (Statement stmt = connection.createStatement()){
			 rs=stmt.executeQuery("select * From Pietanza");
			 while(rs.next()) {
				totVenduti=totVenduti+rs.getInt(5);
				System.out.println("Tot venduti" + totVenduti);
			 }
		 }
		catch(SQLException e) {
			e.printStackTrace();	 
		}
		rs=null;
		try (Statement stmt = connection.createStatement()){
			 rs=stmt.executeQuery("select * From Pietanza");
			 while(rs.next()) {
				 Pietanza p= new Pietanza();
				 p.setId(rs.getInt(1));
				 p.setNome(rs.getString(2));
				 p.setPrezzo(rs.getDouble(3));
				 p.setCategoria(rs.getString(4));
				 parziale=(rs.getInt(5)/totVenduti)*100;
				 p.setPercentuale(parziale);
				 ob.add(p);
				 System.out.println("venduti " + rs.getInt(5));
				 System.out.println("TOtale " + totVenduti);
				 System.out.println("parziale" + parziale);
				
                        	} 
	 	
                    }
		catch(SQLException e) {
			e.printStackTrace();
		                      }
		return ob;
	}
     
	public static Pietanza calcolaCategoria(String categoria) {
	
		 double totVenduti=0;
         int max =0;
		 Pietanza pmax= new Pietanza();
		 Connection connection=(Connection) SimpleSingleton.getinstance().con;
		 PreparedStatement prepStat;
			ResultSet rs=null;
			try {
				String sql="select * From Pietanza WHERE Categoria =?";
				 prepStat=connection.prepareStatement(sql);
                prepStat.setString(1,categoria);
                rs=prepStat.executeQuery();
				 while(rs.next()) {
					 totVenduti=totVenduti+rs.getInt(5);
				 }
				 }
			catch(SQLException e) {
				e.printStackTrace();
			}
			rs=null;
			
			try {
				String sql="select * From Pietanza WHERE Categoria =?";
				 prepStat=connection.prepareStatement(sql);
                prepStat.setString(1,categoria);
                rs=prepStat.executeQuery();
				 while(rs.next()) {
					if(rs.getInt(5)>max) {
						max=rs.getInt(5);
						pmax.setNome(rs.getString(2));
						pmax.setPercentuale((rs.getInt(5)/totVenduti)*100);
					}
				 }
				 prepStat.close();
				 rs.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		
	
			return pmax;
}
}


