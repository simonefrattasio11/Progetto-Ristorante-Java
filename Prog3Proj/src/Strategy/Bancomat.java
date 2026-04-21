package Strategy;

import java.util.Optional;

import application.Pagamento;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
 // Metodo di pagamento Bancomat relativo al Design pattern strategy
public class Bancomat implements MetodoDiPagamento{
	int tavolo;
	public Bancomat(int t) {
		setTavolo(t);
	}
	@Override
	public void paga() {
		TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Bancomat");
        dialog.setHeaderText("Inserire il pin della carta");
        dialog.setContentText("PIN");
        
        Optional<String> result=dialog.showAndWait();
        if(result.isPresent()) {
        	Pagamento p = new Pagamento();
        	p.inserimento(tavolo);
        	Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
        	alert1.setTitle("Pagamento");
        	alert1.setContentText("Pagamento con Bancomat effettuato con successo");
        	alert1.setOnCloseRequest(e->dialog.close());            
        	alert1.showAndWait();
        }
        else {
        	Alert alert2=new Alert(Alert.AlertType.ERROR);
        	alert2.setTitle("Errore");
        	alert2.setContentText("Digitare il pin");
        	alert2.showAndWait();
        	        
        }
		
	}
	public void setTavolo(int t) {
		this.tavolo=t;
	}
	public int getTavolo() {
		return tavolo;
	}
}
