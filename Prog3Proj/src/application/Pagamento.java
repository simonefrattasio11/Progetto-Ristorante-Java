package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pagamento {
  // Classe molto semplice che ci permette di aggiornare il DB Scontrino quando un ordine viene pagato ,ho deciso di farlo in 
	// una classe a parte per evitare ridondanza di codice
	public void inserimento(int t){
		PreparedStatement prepStat2;
    	PreparedStatement prepStat;
    	Connection connection=SimpleSingleton.getinstance().con;
    	try {
    		 String sql2 = "UPDATE Scontrino set Tempo = CURRENT_TIMESTAMP where IDTavolo=? and Pagato = 'No' ";
		      prepStat2 = connection.prepareStatement(sql2);
		      prepStat2.setInt(1,t);
		      prepStat2.execute();
		      prepStat2.close();
    	      String sql = "UPDATE Scontrino set Pagato = 'Si' where IDTavolo=?";
    	      prepStat = connection.prepareStatement(sql);		      
    	      prepStat.setInt(1,t);
    	      prepStat.execute();
    	      prepStat.close();
    	      prepStat2.close();
    	      
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
		
	}
}
