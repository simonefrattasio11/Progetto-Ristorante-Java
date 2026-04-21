package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ContantiViewController implements Initializable{
   // Classe che gestisce l'interfaccia per il pagamento in contanti
   // Presenta un interfaccia molto semplice che ci consente di visualizzare già dall'inizializzazione l'importo dovuto
  // Poi in base al'importo versato (Inserito attraverso l'apposito TextField) calcola il resto dovuto al cliente
	// Se si cerca di pagare un importo inferiore il programma visualizzerà un messaggio di errore
	// analogamente anche se si cerca di premere il pulsante senza aver versato una cifra adeguata il programma ci darà un errore
	private double totale;
	private double importo;
	private double resto;
	public static boolean flag;
	@FXML
	Button finitoButton;
	@FXML
	Text dovutoText;
	@FXML
	Text restoText;
	@FXML
	TextField versatoTextField;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		totale=CassiereViewController.getTotaleCassa();
		dovutoText.setText(String.valueOf(CassiereViewController.getTotaleCassa()));
		flag=false;
		
	}
	
	public void finitoButtonClick(){
		if(flag==true) {
		Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Transazione effettuata");
		alert.setContentText("Importo pagato in contanti");
		alert.showAndWait();
		Stage stage = (Stage) finitoButton.getScene().getWindow(); 
		stage.close(); 
		}
		else {Alert alert=new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore");
		alert.setContentText("Devi pagare prima");
		alert.showAndWait();
			
		}
	}
	public void pagaButtonClick() {
		if(Double.parseDouble(versatoTextField.getText())<totale){
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setContentText("Devi pagare di più");
			alert.showAndWait();
		}
		else {
			resto=Double.parseDouble(versatoTextField.getText())-totale;
			restoText.setText(String.valueOf(resto));
			flag=true;
		}
	}

	
	public static boolean getContantiFlag() {
		return flag;
	}
	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}
	
}
