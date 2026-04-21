package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Alert;

 //classe che gestisce l'ArrayList di pietanze che viene riempito nel momento in cui si aggiungono pietanze
 // e svuotato nel momento in cui si rimuovono se si aggiunge o rimuove una pietanza con lo stesso nome di una già presente 
// nell'ArrayList andrà soltanto modificato il campo quantità dell'oggetto pietanza;

public class Ordine {

	private ArrayList<Pietanza> listaPietanze = new ArrayList<>();
	private int tavolo;

	public void add(String nome,int quantità)throws IOException {
		boolean flag=false;
	    Pietanza p = new Pietanza();
	    ResultSet rs = null;
	    PreparedStatement prepStat = null;
		Connection connection=(Connection) SimpleSingleton.getinstance().con;
		try (Statement stmt = connection.createStatement()){
			String sql="select * From Pietanza where Nome = ?";
			 prepStat = connection.prepareStatement(sql);
			prepStat.setString(1,nome);
			 rs=prepStat.executeQuery();
			 
			if(rs == null)
			{
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setContentText("Non esiste una pietanza con questo nome");
				alert.showAndWait();
			}
			else {
				
				 p.setId(rs.getInt(1));
				 p.setQuantità(quantità);
				 p.setNome(rs.getString(2));
				 p.setPrezzo(rs.getDouble(3));
				 p.setCategoria(rs.getString(4));
			
			   for(Pietanza piet:listaPietanze)
			   {
				   if(p.getNome().equals(piet.getNome()) && flag==false)
						   {
					        piet.setQuantità(piet.getQuantità()+quantità);
					        flag=true;
						   }
			   }
			  if(flag==false) {
			   listaPietanze.add(p);
			  }
			  
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				prepStat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for(int i=0;i<listaPietanze.size();i++)
		{
		System.out.println(listaPietanze.get(i).getNome());
		System.out.println(listaPietanze.get(i).getPrezzo());
		System.out.println(listaPietanze.get(i).getCategoria());
		System.out.println(listaPietanze.get(i).getQuantità());
		}
}

	
	
	public ArrayList<Pietanza> getlistaPietanze() {
		return listaPietanze;
	}
	
	
	
	
	public void remove(String nome,int quantità) throws IOException {
	
			for(int i=0;i<listaPietanze.size();i++)
			{
				if(listaPietanze.get(i).getNome().equals(nome)){
					if(listaPietanze.get(i).getQuantità()<=quantità)
					{
						listaPietanze.remove(i);
					}
					else {
						listaPietanze.get(i).setQuantità(listaPietanze.get(i).getQuantità()-quantità);
					}
				}
			}
			for(int i=0;i<listaPietanze.size();i++) {
			System.out.println(listaPietanze.get(i).getNome());
			System.out.println(listaPietanze.get(i).getPrezzo());
			System.out.println(listaPietanze.get(i).getCategoria());
			System.out.println(listaPietanze.get(i).getQuantità());
			}
		}



	public int getTavolo() {
		return tavolo;
	}



	public void setTavolo(int tavolo) {
		this.tavolo = tavolo;
	}
		
	}