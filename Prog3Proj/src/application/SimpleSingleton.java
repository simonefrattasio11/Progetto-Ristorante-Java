package application;
// Classe relativa al design pattern Singleton che permette di avere una sola istanza della classe in questo caso 
// è utile per rendere unica la connessione al nostro database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public  class SimpleSingleton {
	private static SimpleSingleton instance = null;
	public   Connection con;
	private SimpleSingleton()  //costruttore privato
	{
	}
	public static SimpleSingleton getinstance () {
		if(instance==null)
		{
			instance = new SimpleSingleton();	
			instance.con= instance.connessione();
		}
		
		return instance;
	}
	private  Connection  connessione ( )
	{
	
		try 
		{
			
			con =  DriverManager.getConnection("jdbc:sqlite:C://Users/987789/eclipse-workspace/Prog3Proj/Ristorante");
	
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;		
		}
}