	package Command;

// Implementazione dell'interfaccia command che in questo caso ci penmette di eseguire il metodo annullato del Reciever
public class AnnullaComanda implements Command {
 
  IOrdineCucina o;
	
	public AnnullaComanda(IOrdineCucina newOrdine) {
		o = newOrdine;
	}
	@Override
	public void execute() {
		o.annullato();
		
	}
	

}
