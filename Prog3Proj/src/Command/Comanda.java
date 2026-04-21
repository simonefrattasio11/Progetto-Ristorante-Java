//	COMMAND

package Command;
// Implementazione dell'interfaccia Command che in questo caso ci permette di chiamare il  metodo ordinato del reciever
public class Comanda implements Command {

	IOrdineCucina o;
	
	public Comanda(IOrdineCucina newPiatto) {
		o = newPiatto;
	}
	@Override
	public void execute() {
		o.ordinato();
		//System.out.println("Manda in cucina");
		
	}		

}
