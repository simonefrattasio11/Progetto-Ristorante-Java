package Strategy;

import java.util.Optional;

import application.Pagamento;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
// Metodo di pagamento Carta di credito relativo al Design pattern strategy

public class CartaDiCredito implements MetodoDiPagamento{
	int tavolo;
	public CartaDiCredito(int t) {
		setTavolo(t);
	}
	@Override
	public void paga() {
		TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Carta di Credito");
        dialog.setHeaderText("Inserire il pin della carta");
        dialog.setContentText("PIN");
        
        Optional<String> result=dialog.showAndWait();
        if(result.isPresent()) {
        	Pagamento p = new Pagamento();
        	p.inserimento(tavolo);
        	Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
        	alert1.setTitle("Pagamento");
        	alert1.setContentText("Pagamento con carta di credito effettuato con successo");
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
