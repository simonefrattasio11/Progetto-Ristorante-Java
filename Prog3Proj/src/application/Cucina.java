package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import View.CameriereViewController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
  // Per simulare una vera cucina creiamo un secondo thread che faremo "dormire" per alcuni secondi in base alla quantità dei piatti di un ordine
  // ovviamente i tempi relativi alla preparazione di un piatto sono ridotti per facilitarne i test
  // L'utilizzo di un secondo thread rende possibile effettuare diverse operazioni mentre un ordine è in preparazione 
  // e rende anche possibile stabilire i tempi entro i quali è possibile annullare un ordine
public class Cucina extends CameriereViewController implements Runnable{
    private int tempo;
    
    
    
    
    private void cucinaOrdine(Ordine o) throws InterruptedException {
    	System.out.println("Sto cucinando...");
    	for(Pietanza piet:o.getlistaPietanze()) {
    	
    		Thread.sleep(1000*cucinaPiatto(piet.getQuantità()));
    		try {
    			Platform.runLater(()->{Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    			alert.setContentText("Ordine servito al tavolo " + o.getTavolo() + "\n Buon appetito");
    			alert.setTitle("Servito");
    			alert.showAndWait();});
    		}
    		catch(IllegalStateException e){
    			e.printStackTrace();
    			
    		}
    	}
    	System.out.println("Ho cucinato");
    }
    
    
    
    
    private int cucinaPiatto(int numero) {
    	tempo=numero*10;
    	return tempo;
    	
    }




	@Override
	public void run() {
		while(true) {
		Ordine ordine = null;
		boolean flag = true;
	 synchronized ( Cucina.getMutex()) 
	 {	if(Cucina.listaOrdine.size() > 0)
	    ordine = Cucina.listaOrdine.get(0);
	 else
	 {
		 
		 flag = false;
		 try {
			 Cucina.getMutex().wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 	}
	   try {
		   if(ordine!=null)
		   {   
		cucinaOrdine(ordine);
		Connection connection=SimpleSingleton.getinstance().con;
		 try (Statement stmt = connection.createStatement()){
		   for(Pietanza p:ordine.getlistaPietanze()){
			   PreparedStatement prepStat=null;
				String sql="insert into Ordine(IDPietanza,Quantità,IDTavolo) values(?,?,?)";
					prepStat = connection.prepareStatement(sql);
					prepStat.setInt(1,p.getId());
					prepStat.setInt(2,p.getQuantità());
					prepStat.setInt(3,ordine.getTavolo());
					prepStat.execute();
				}
		 }catch (SQLException e) {
					e.printStackTrace();
				}
	}
	   }catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	   
	   synchronized (Cucina.getMutex()) {
			  	  
   if(flag) {
	   Cucina.listaOrdine.remove(0);
   }
  
	   
	   }
		   }
	  
	  
}	
}
	
