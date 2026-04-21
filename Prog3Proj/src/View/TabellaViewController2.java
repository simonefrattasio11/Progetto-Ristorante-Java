package View;

import java.net.URL;
import java.util.ResourceBundle;

import application.Percentuale;
import application.Pietanza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
// Classe che gestisce la tabella delle percentuali di vendita prese in input dal metodo statico della classe Percentuale
public class TabellaViewController2 implements Initializable {
	ObservableList<Pietanza> ob=FXCollections.observableArrayList();
	@FXML
	public TableView<Pietanza> tableView;
	@FXML
	public TableColumn<Pietanza,Integer> idColumn = new TableColumn<>("ID");
	@FXML
	public TableColumn<Pietanza,String> nomeColumn = new TableColumn<>("Nome");
	@FXML
	public TableColumn<Pietanza,Double> prezzoColumn = new TableColumn<>("Prezzo");
	@FXML
	public TableColumn<Pietanza,Double> percentualeColumn = new TableColumn<>("Percentuale");
	@FXML
	public TableColumn<Pietanza,String> categoriaColumn =  new TableColumn<>("Categoria");
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ob=Percentuale.calcolaTutte();
		
		    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        prezzoColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
	        percentualeColumn.setCellValueFactory(new PropertyValueFactory<>("percentuale"));
	        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
	        tableView.setItems(ob);
	}

}


