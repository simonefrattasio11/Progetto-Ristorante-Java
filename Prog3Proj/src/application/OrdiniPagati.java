package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
  // Classe che permette l'aggiornamento della quantità di pietanze vendute nella tabella Pietanze ed attraverso la funzione 
// rimuoviDalDB la loro rimozione,le funzioni vengono chiamate nel momento in cui viene effettuato un pagamento 

public class OrdiniPagati {
	private int tavolo;
    ArrayList<Pietanza> arrayP=new ArrayList<Pietanza>();
    
	public 
	OrdiniPagati(int tavolo){
      setTavolo(tavolo);
		
	}
	public int getTavolo() {
		return tavolo;
	}
	public void setTavolo(int tavolo) {
		this.tavolo = tavolo;
	}
	
	public void estraiPietanza() {		
		ResultSet rs;
		PreparedStatement prepStat = null;
		Connection connection=SimpleSingleton.getinstance().con;
    	try {
    		String sql="Select * from Ordine where IDTavolo=?";
    		prepStat=connection.prepareStatement(sql);
    		prepStat.setInt(1,tavolo);
			rs=prepStat.executeQuery();
			while(rs.next()) {
				Pietanza p= new Pietanza();
				p.setId(rs.getInt(2));
				p.setQuantità(rs.getInt(3));
				arrayP.add(p);
			}
			prepStat.close();
			rs.close();
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public int aggiorna(){
		this.estraiPietanza();
		int totale=0;
		ResultSet rs;
		PreparedStatement prepStat = null;
		Connection connection=SimpleSingleton.getinstance().con;
		for(int i=0 ; i<arrayP.size();i++) {
    	try {
    		String sql="Select * from Pietanza where IDPietanza=?";
    		prepStat=connection.prepareStatement(sql);
    		prepStat.setInt(1,arrayP.get(i).getId());
			rs=prepStat.executeQuery();
			while(rs.next()) {
				totale=0;
				totale=rs.getInt(5)+arrayP.get(i).getQuantità();
				this.aggiornaPietanze(totale,arrayP.get(i).getId());
			}
			prepStat.close();
			rs.close();
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
		}
		return totale;
	}
	
	public void aggiornaPietanze(int tot,int id) {
		PreparedStatement prepStat = null;
		Connection connection=SimpleSingleton.getinstance().con;
    	try {
    		String sql="UPDATE Pietanza set Venduti = ? where IDPietanza = ?";
    		prepStat=connection.prepareStatement(sql);
    		prepStat.setInt(1,tot);
    		prepStat.setInt(2,id);
			prepStat.executeUpdate();
			prepStat.close();
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	
	public void rimuoviDalDB() {
		PreparedStatement prepStat = null;
		Connection connection=SimpleSingleton.getinstance().con;
    	try {
    		String sql="DELETE from Ordine  where IDTavolo=?";
    		prepStat=connection.prepareStatement(sql);
    		prepStat.setInt(1,tavolo);
			prepStat.executeUpdate();
			prepStat.close();
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
	}

}
