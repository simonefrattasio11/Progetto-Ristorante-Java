package View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.SimpleSingleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
// Classe che controlla la schermata di login
// E' possibile accedere in modalità cameriere o cassiere 
// Esiste un solo utente cassiere quindi non è necessario ricorrere ad un DB mentre essendoci vari camerieri 
// si effettua una query per controllare la validità di username e password.
public class LoginController {
	
	public TextField usernameCameriere;
	public PasswordField passwordCameriere;
	public TextField usernameCassiere;
	public PasswordField passwordCassiere;
	
	public void cameriereButtonClick() throws IOException, SQLException {
		
		 boolean match = false;
		 ResultSet rs = null;
		Connection connection = (Connection) SimpleSingleton.getinstance().con;
		try (Statement stmt = connection.createStatement()){
			 rs=stmt.executeQuery("select * From Cameriere");
		while(rs.next() && match == false){
			if(usernameCameriere.getText().equals(rs.getString(2)) && passwordCameriere.getText().equals(rs.getString(5))) {
		match = true;
		}
			
		}
		if(match==true)
		{  
			Parent cameriereViewParent = FXMLLoader.load(getClass().getResource("CameriereView.fxml"));
			Scene cameriereViewScene= new Scene(cameriereViewParent);
			Stage window = new Stage();
		    window.setTitle("Schermata cameriere");
			window.setScene(cameriereViewScene);
			usernameCameriere.setText("");
			passwordCameriere.setText("");
			window.showAndWait();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setContentText("Username o Password errati");
			usernameCameriere.setText("");
			passwordCameriere.setText("");
			alert.showAndWait();
		}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally {
			rs.close();
			
		}
		
	}
	
	public void cassiereButtonClick() throws IOException{


		
		if(usernameCassiere.getText().equals("cassiere") && passwordCassiere.getText().equals("password")){
		Parent cassiereViewParent = FXMLLoader.load(getClass().getResource("CassiereView.fxml"));
		Scene cassiereViewScene= new Scene(cassiereViewParent);
		Stage window = new Stage();
		window.setTitle("Schermata Cassiere");
		window.setScene(cassiereViewScene);
		usernameCassiere.setText("");
		passwordCassiere.setText("");
		window.showAndWait();
		}
		else {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setContentText("Username o Password errati");
			usernameCassiere.setText("");
			passwordCassiere.setText("");
			alert.showAndWait();
		}
		
	}

}
