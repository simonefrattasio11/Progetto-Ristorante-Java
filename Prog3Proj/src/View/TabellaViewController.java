package View;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.Pietanza;
import application.SimpleSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Classe che semplicemente effettua una query alla tabella ordini del DB e visualizza una tabella di tutti gli ordini relativi al tavolo selezionato.

public class TabellaViewController extends CassiereViewController implements Initializable {
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
	public TableColumn<Pietanza,Integer> quantit‡Column = new TableColumn<>("Quantit‡");
	@FXML
	public TableColumn<Pietanza,String> categoriaColumn =  new TableColumn<>("Categoria");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ResultSet rs = null;
		ResultSet rs2=null;
		PreparedStatement prepStat2=null;
	    PreparedStatement prepStat = null;
		Connection connection=(Connection) SimpleSingleton.getinstance().con;
		try (Statement stmt = connection.createStatement()){
			String sql="select * From Ordine where IDTavolo=?";
			 prepStat = connection.prepareStatement(sql);
			 prepStat.setInt(1,CassiereViewController.getNumeroTavolo());
			 rs=prepStat.executeQuery();
			 while(rs.next()) {
				 Pietanza p = new Pietanza();
				 p.setQuantit‡(rs.getInt(3));
				 String sql2="Select * From Pietanza where IDPietanza=?";
				 prepStat2=connection.prepareStatement(sql2);
				 prepStat2.setInt(1,rs.getInt(2));
				 rs2=prepStat2.executeQuery();
				 p.setId(rs.getInt(2));
				 p.setCategoria(rs2.getString(4));
				 p.setNome(rs2.getString(2));
				 p.setPrezzo(rs2.getDouble(3));
				 ob.add(p);
			 }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	
		    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        prezzoColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
	        quantit‡Column.setCellValueFactory(new PropertyValueFactory<>("quantit‡"));
	        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
	       
	        tableView.setItems(ob);
	}

}
