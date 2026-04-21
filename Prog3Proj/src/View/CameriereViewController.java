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

import Command.AnnullaComanda;
import Command.Cameriere;
import Command.Comanda;
import Command.OrdineCucina;
import application.Conto;
import application.Cucina;
import application.Ordine;
import application.Percentuale;
import application.SimpleSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CameriereViewController implements Initializable {
	// Classe che controlla la schermata cameriere
	 // La schermata cameriere è ricca di componenti visive
	// I ComboBox contengono le informazioni relative ai tavoli e ai nomi delle pietanze
	// I textField permettono di inserire la quantità delle pietanze desiderate
	// I pulsanti aggiungi e rimuovi consentono di gesitre un ArrayList temporaneo di Pietanze che 
	// alla pressione del pulsante Ordina verranno aggiunte alla tabella Ordini del DB
	// Il pulsante Scontrino permette di generare uno scontrino raccogliendo dal DB tutti gli ordini relativi al tavolo selezionato
	// Il pulsante Annulla consente di Annullare l'ultimo ordine aggiunto all'arrayList solo se non è stato ancora iniziato a cucinare
	@FXML public ComboBox<String> tavoliBox;
	@FXML public ComboBox<String> primiBox;
	@FXML public ComboBox<String> secondiBox;
	@FXML public ComboBox<String> contorniBox;
	@FXML public ComboBox<String> dessertBox;
	@FXML public TextField primiText;
	@FXML public TextField secondiText;
	@FXML public TextField contorniText;
	@FXML public TextField dessertText;
	@FXML public Text primiVendutoText;
	@FXML public Text secondiVendutoText;
	@FXML public Text contorniVendutoText;
	@FXML public Text dessertVendutoText;
	

	      private Ordine ordine = new Ordine();
	      public static ArrayList<Ordine>  listaOrdine=new ArrayList<Ordine>();
	      private static Object mutex = new Object();
	      public static Cucina c = new Cucina();
	      public Thread t = new Thread(c);
	      
	public static Object getMutex() {
			return mutex;
		}
		

	public  void initialize(URL url,ResourceBundle rb)
	{
		primiVendutoText.setText(Percentuale.calcolaCategoria("Primi piatti").getNome());
		secondiVendutoText.setText(Percentuale.calcolaCategoria("Secondi piatti").getNome());
		contorniVendutoText.setText(Percentuale.calcolaCategoria("Contorni").getNome());
		dessertVendutoText.setText(Percentuale.calcolaCategoria("Dessert").getNome());

		loadPrimi();
		loadSecondi();
		loadContorni();
		loadDessert();
		loadTavoli();
		t.start();
        }
	private void loadTavoli() {
		
		tavoliBox.getItems().addAll("1","2","3","4","5","6");
		tavoliBox.setValue("");
		
	}
	private ObservableList<String> prendiPietanze(String categoria){
		ObservableList<String> list=FXCollections.observableArrayList();
		 Connection connection=(Connection) SimpleSingleton.getinstance().con;
		 PreparedStatement prepStat;
			ResultSet rs=null;
			try {
				String sql="select * From Pietanza WHERE Categoria =?";
				 prepStat=connection.prepareStatement(sql);
                prepStat.setString(1,categoria);
                rs=prepStat.executeQuery();
                while(rs.next()) {
                	list.add(rs.getString(2));
                	
                }
	}
			catch(SQLException e) {
				e.printStackTrace();
			}
			return list;
	}
	private void loadPrimi()
	{	
		primiBox.getItems().addAll(this.prendiPietanze("Primi piatti"));
		primiText.setText("");
		primiBox.setValue("");
	}
	private void loadSecondi() {
		secondiBox.getItems().addAll(this.prendiPietanze("Secondi piatti"));
		secondiText.setText("");
		secondiBox.setValue("");
	}
	private void loadContorni() {
		contorniBox.getItems().addAll(this.prendiPietanze("Contorni"));
		contorniText.setText("");
		contorniBox.setValue("");
	}
	private void loadDessert() {
		dessertBox.getItems().addAll(this.prendiPietanze("Dessert"));
		dessertText.setText("");
		dessertBox.setValue("");
	}
	
	public void aggiungiPrimiButtonClick() throws NumberFormatException, IOException{
		if(primiBox.getValue().equals("") || primiText.getText().equals("")){
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else if(tavoliBox.getValue()=="") {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona un tavolo");
			alert.showAndWait();
		}
		else {
			ordine.add(primiBox.getValue(),Integer.parseInt(primiText.getText()));
			primiText.setText("");
			primiBox.setValue("");
			
		}
	}
	
	public void aggiungiSecondiButtonClick() throws NumberFormatException, IOException{
		if(secondiBox.getValue().equals("") || secondiText.getText().equals("")) {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else if(tavoliBox.getValue()=="") {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona un tavolo");
			alert.showAndWait();
		}
		else {
			ordine.add(secondiBox.getValue(),Integer.parseInt(secondiText.getText()));
			secondiText.setText("");
			secondiBox.setValue("");
		}
	}
	
	
	public void aggiungiContorniButtonClick() throws NumberFormatException, IOException{
		if(contorniBox.getValue().equals("") || contorniText.getText().equals("")) {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else if(tavoliBox.getValue()=="") {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona un tavolo");
			alert.showAndWait();
		}
		else {
			ordine.add(contorniBox.getValue(),Integer.parseInt(contorniText.getText()));
			contorniText.setText("");
			contorniBox.setValue("");
			
		}
	}
	
	
	public void aggiungiDessertButtonClick() throws NumberFormatException, IOException{
		if(dessertBox.getValue().equals("") || dessertText.getText().equals("")){
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else if(tavoliBox.getValue()=="") {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona un tavolo");
			alert.showAndWait();
		}
		else {
			ordine.add(dessertBox.getValue(),Integer.parseInt(dessertText.getText()));
			dessertText.setText("");
			dessertBox.setValue("");
			
		}
	}
	
	public void rimuoviPrimiButtonClick() throws NumberFormatException, IOException {
		if(primiBox.getValue().equals("") || primiText.getText().equals("")) {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else {
			ordine.remove(primiBox.getValue(),Integer.parseInt(primiText.getText()));
			primiText.setText("");
			primiBox.setValue("");
			
		}
	}
	
	public void rimuoviSecondiButtonClick() throws NumberFormatException, IOException {
		if(secondiBox.getValue().equals("") || secondiText.getText().equals("")){
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else {
		ordine.remove(secondiBox.getValue(),Integer.parseInt(secondiText.getText()));
		secondiText.setText("");
		secondiBox.setValue("");
			
		}
	}
	
	public void rimuoviContorniButtonClick() throws NumberFormatException, IOException {
		if(contorniBox.getValue().equals("") || contorniText.getText().equals("")) {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else {
		ordine.remove(contorniBox.getValue(),Integer.parseInt(contorniText.getText()));
		contorniText.setText("");
		contorniBox.setValue("");
			
		}
	}
	public void rimuoviDessertButtonClick() throws NumberFormatException, IOException {
		if(dessertBox.getValue().equals("") || dessertText.getText().equals("")) {
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setContentText("Seleziona Una pietanza ed inserisci un numero");
			alert.showAndWait();
		}
		else {
			ordine.remove(dessertBox.getValue(),Integer.parseInt(dessertText.getText()));
			dessertText.setText("");
			dessertBox.setValue("");
			
		}
	}
	
	public void ordinaButtonClick() {
		if(tavoliBox.getValue().equals("")) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Selezionare un tavolo");
			alert.setTitle("Errore");
			alert.showAndWait();
		 ordine = new Ordine();
		}
		else if(ordine.getlistaPietanze().size() == 0){
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Aggiungere le pietanze da ordinare");
			alert.setTitle("Errore");
			alert.showAndWait();
		 ordine = new Ordine();
		}
		else {
			PreparedStatement prepStat=null;
			ResultSet rs=null;
			Connection connection=SimpleSingleton.getinstance().con;
			try (Statement stmt = connection.createStatement()){
				String sql="select * From Scontrino  where IDTavolo = ? and Pagato= 'No' ";
				 prepStat = connection.prepareStatement(sql);
				prepStat.setInt(1,Integer.parseInt(tavoliBox.getValue()));
				 rs=prepStat.executeQuery();
				 if(rs != null && rs.next()) {
					    do { Alert alert=new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Errore");
						alert.setContentText("Tavolo occupato");
						alert.showAndWait();
					    } while (rs.next());
					} else {
		 OrdineCucina primo = new OrdineCucina(Integer.parseInt(tavoliBox.getValue()),ordine);
		 Comanda or = new Comanda(primo);
		 Cameriere c = new Cameriere(or);
		 c.sendCucina();
		 
		 
		 Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Ordine inviato alla cucina");
			alert.showAndWait();
		 ordine = new Ordine();
		   }
	}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		}
    public void scontrinoButtonClick() {
    	if(tavoliBox.getValue()!="") {
    	Conto conto=new Conto();
    	conto.calcolaTotale(Integer.parseInt(tavoliBox.getValue()));
    	Connection connection=SimpleSingleton.getinstance().con;
    	PreparedStatement prepStat;
    	ResultSet rs=null;
    	System.out.println(conto.getTotale());
    	try (Statement stmt = connection.createStatement()){
			String sql="select * From Scontrino  where IDTavolo = ? and Pagato= 'No' ";
			 prepStat = connection.prepareStatement(sql);
			prepStat.setInt(1,Integer.parseInt(tavoliBox.getValue()));
			 rs=prepStat.executeQuery();
			 if(rs != null && rs.next()) {
				    do { Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Errore");
					alert.setContentText("Esiste già uno scontrino non pagato per questo tavolo");
					alert.showAndWait();
				    } while (rs.next());
				} else {
					
					if(conto.getTotale()==0) {
						 Alert alert=new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Errore");
							alert.setContentText("Non ci sono ordini per questo tavolo");
							alert.showAndWait();
					}
					else {
					PreparedStatement prepStat2=null;
					String sql2="insert into Scontrino(Pagato,IDTavolo,Totale) values(?,?,?)";
						prepStat2 = connection.prepareStatement(sql2);
						prepStat2.setString(1,"No");
						prepStat2.setInt(2,Integer.parseInt(tavoliBox.getValue()));
						prepStat2.setDouble(3,conto.getTotale());
						prepStat2.execute();
						 Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
						 alert.setContentText("Scontrino creato correttamente");
						 alert.showAndWait();
						 prepStat2.close();
					}
				}  
			 rs.close();
			 prepStat.close();
				   
					} catch (SQLException e) {
						e.printStackTrace();
					}
          
    	}
    	else  {
    		Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setContentText("Selezionare un tavolo");
			alert.showAndWait();
    	}
				 }
    
    public void annullaButtonClick() {
    	if(CameriereViewController.getListaOrdine().size()<=1) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Impossibile annullare l'ultimo ordine");
			alert.showAndWait();
    	}
    	else {
    		OrdineCucina primo=new OrdineCucina();
    		AnnullaComanda or=new AnnullaComanda(primo);
    		Cameriere c = new Cameriere(or);
    		c.sendCucina();	
    		 Alert alert=new Alert(Alert.AlertType.INFORMATION);
 			alert.setContentText("Ultimo ordine annullato");
 			alert.showAndWait();
 		 ordine = new Ordine();
    		
    	}
    }

	public ComboBox<String> getTavoliBox() {
		return tavoliBox;
	}


	public void setTavoliBox(ComboBox<String> tavoliBox) {
		this.tavoliBox = tavoliBox;
	}


	public Ordine getOrdine() {
		return ordine;
	}


	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}


	public static ArrayList<Ordine> getListaOrdine() {
		return listaOrdine;
	}


	public static void setListaOrdine(ArrayList<Ordine> listaOrdine) {
		CameriereViewController.listaOrdine = listaOrdine;
	}			
			 }
		
		 
		
    
	


