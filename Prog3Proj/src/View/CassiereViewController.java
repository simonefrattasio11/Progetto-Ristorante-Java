package View;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Strategy.Bancomat;
import Strategy.CartaDiCredito;
import Strategy.Contanti;
import Strategy.MetodoDiPagamento;
import application.OrdiniPagati;
import application.SimpleSingleton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CassiereViewController implements Initializable {
	// Classe che controlla la schermata cassiere
	// La schermata cassiere è ricca di elementi andiamo ad analizzarli
	// Innanzitutto abbiamo i ComboBox che consentono di scegliere il tavolo sul quale eseguire le operazioni
	// e i metodi di pagamento 
	// Il tasto paga permette di Cambiare lo stato nel DB di scontrino relativo al tavolo scelto attraverso il metodo di pagamento selezionato
	// Il tasto annulla analogamente permette di cambiare lo stato dell'ultimo scontrino pagato per annullarne il pagamento (Sato :stornato)
	// Il tasto Visualizza sotto la voce Scontrino permette di visualizzare tutti gli ordini relativi ad un tavolo selezionato
	// Il tasto Visualizza sotto la voce visualizza percentuali di vendita consente di visualizzare le percentuali di vendita di ogni prodotto
	
    public Text testoTotale;
	public ComboBox<String> tavoliBox;
	public ComboBox<String> pagamentoBox;
	private ArrayList<String> list= new ArrayList<>();
	private MetodoDiPagamento pagamento;
	public static int n;
	public static double totale;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		list.removeAll(list);
		list.add("Carta di credito");
		list.add("Bancomat");
		list.add("Contanti");
		pagamentoBox.getItems().addAll(list);
	    tavoliBox.getItems().addAll("1","2","3","4","5","6");
	    pagamentoBox.setValue("");
	    tavoliBox.setValue("");
	    testoTotale.setText(""	);
	}
	
	public void cambioNumeroTavolo() {
		n=Integer.parseInt(tavoliBox.getValue());
		Connection connection=SimpleSingleton.getinstance().con;
    	PreparedStatement prepStat;
    	ResultSet rs=null;
    	try (Statement stmt = connection.createStatement()){
			String sql="select Totale From Scontrino  where IDTavolo = ? and Pagato= 'No' ";
			 prepStat = connection.prepareStatement(sql);
			prepStat.setInt(1,Integer.parseInt(tavoliBox.getValue()));
			 rs=prepStat.executeQuery();
			 if(rs != null && rs.next()) {
				 do {
					 totale=rs.getFloat(1);
					 testoTotale.setText(String.valueOf(totale));
			 
			
    	}while(rs.next());
    }
				 else { testoTotale.setText("0");
					 
				 }
			 rs.close();
			 prepStat.close();
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void annullaButtonClick() throws SQLException {
		Connection connection=SimpleSingleton.getinstance().con;
		ResultSet rs = null;
		try(Statement stmt = connection.createStatement()){
			
			rs=stmt.executeQuery("SELECT * FROM Scontrino WHERE Tempo IN(SELECT max(Tempo) FROM Scontrino)");
			if(rs.getString(2).equals("Si")) {
		    	try (Statement stmt2 = connection.createStatement()){
					stmt2.executeUpdate("UPDATE Scontrino set Pagato = 'Stornato'  WHERE Tempo IN (SELECT max(Tempo) FROM Scontrino) and Pagato = 'Si' ");
					stmt2.close();
		    	}
		    	catch(SQLException e) {
		    		e.printStackTrace();
		    	}
		   
		    	Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Annullato");
				alert.setContentText("Ultimo pagamento stornato");
				alert.showAndWait();
				}
				else {
					Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Errore");
					alert.setContentText("Impossibile stornare ultimo pagamento");
					alert.showAndWait();
		}
			stmt.close();
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	
    	
	}
	
	public void pagaButtonClick() throws NumberFormatException, IOException{
	
		
		if(tavoliBox.getValue().equals("")) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setContentText("Selezionare un tavolo");
			alert.showAndWait();
		}
		else {
		int t=Integer.parseInt(tavoliBox.getValue());
		Connection connection=SimpleSingleton.getinstance().con;
    	PreparedStatement prepStat;
    	ResultSet rs=null;
    	try (Statement stmt = connection.createStatement()){
			String sql="select * From Scontrino  where IDTavolo = ? and Pagato= 'No' ";
			 prepStat = connection.prepareStatement(sql);
			prepStat.setInt(1,Integer.parseInt(tavoliBox.getValue()));
			 rs=prepStat.executeQuery();
			 if(rs != null && rs.next()) {
				  do { 
				
				    
		OrdiniPagati ordiniPagati = new OrdiniPagati(t);
		
		if(pagamentoBox.getValue().equals("Contanti")) {
		pagamento=new Contanti(t);
		pagamento.paga();
		ordiniPagati.aggiorna();
		ordiniPagati.rimuoviDalDB();
	
		}
		else if(pagamentoBox.getValue().equals("Bancomat")) {
			pagamento=new Bancomat(t);
			pagamento.paga();
			ordiniPagati.aggiorna();
			ordiniPagati.rimuoviDalDB();
	
		}
		else if(pagamentoBox.getValue().equals("Carta di credito")) {
			pagamento=new CartaDiCredito(t);
			pagamento.paga();
			ordiniPagati.aggiorna();
			ordiniPagati.rimuoviDalDB();
	}
		else {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setContentText("Selezionare un metodo di pagamento");
			alert.showAndWait();
		}
				  
				  
				  }while (rs.next());
			 }
				  else {
					  Alert alert=new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Errore");
						alert.setContentText("Non ci sono scontrini non pagati per questo tavolo");
						alert.showAndWait();
					  
				  }
			 }
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	}
		
    	
}
	public void visualizzaOrdineButtonClick() throws IOException{
		Parent tabellaViewParent = FXMLLoader.load(getClass().getResource("TabellaView.fxml"));
		Scene tabellaViewScene= new Scene(tabellaViewParent);
		Stage window = new Stage();
		window.setTitle("Scontrino");
		window.setScene(tabellaViewScene);
		window.showAndWait();	
	}
	public void visualizzaPercentualiButtonClick()throws IOException{
		Parent tabellaViewParent = FXMLLoader.load(getClass().getResource("TabellaView2.fxml"));
		Scene tabellaViewScene= new Scene(tabellaViewParent);
		Stage window = new Stage();
		window.setTitle("Percentuali di vendita");
		window.setScene(tabellaViewScene);
		window.showAndWait();	
	}
public static int getNumeroTavolo()
{
	return n;
}
public static double getTotaleCassa() {
	return totale;
}
	
}