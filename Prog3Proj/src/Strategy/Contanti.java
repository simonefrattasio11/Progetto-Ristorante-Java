package Strategy;

import java.io.IOException;

import View.ContantiViewController;
import application.Pagamento;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// Metodo di pagamento Contanti relativo al Design pattern strategy
public class Contanti implements MetodoDiPagamento{
	private int tavolo;
	public Contanti(int t) {
		setTavolo(t);
	}
	@Override
	public void paga() throws IOException {
	Parent contantiViewParent = FXMLLoader.load(getClass().getResource("/View/ContantiView.fxml"));
	Scene contantiViewScene= new Scene(contantiViewParent);
	Stage window = new Stage();
    window.setTitle("Pagamento in contanti");
	window.setScene(contantiViewScene);
	window.showAndWait();
	System.out.println(ContantiViewController.getContantiFlag());
	if(ContantiViewController.getContantiFlag()==true) {
	Pagamento p = new Pagamento();
	p.inserimento(tavolo);
	}
	}

	public void setTavolo(int t) {
		this.tavolo=t;
	}
	public int getTavolo() {
		return tavolo;
	}
}
