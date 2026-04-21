//	INVOKER

package Command;
// Classe invoker che esegue il command generico 
public class Cameriere {

	Command comanda = null;
	public Cameriere(Command newCommand) {
		comanda = newCommand;
	}
	
	public void sendCucina() {
		comanda.execute();
	}
}
