//	RECEIVER

package Command;

import View.CameriereViewController;
import application.Ordine;

// Reciever che contiene le implementazioni dei metodi ordinato ed annullato che ci permettono rispettivamente di ordinare ed annullare 
// gli ordini,estendendo la classe CameriereViewController essi possono gestire la lista ordini 
public class OrdineCucina extends CameriereViewController implements IOrdineCucina {
	
  private int ntv;
  public Ordine ord;
	public OrdineCucina(int n,Ordine o) {
		ntv=n;
		ord=o;
	}
	public OrdineCucina() {
	}
	
	public void ordinato() {
		
		 ord.setTavolo(ntv);
		 synchronized (OrdineCucina.getMutex()) {
		CameriereViewController.getListaOrdine().add(ord);
	    OrdineCucina.getMutex().notifyAll();
		 }
		 
	
	}

	public void annullato() {
		
		 synchronized (OrdineCucina.getMutex()) {
			 CameriereViewController.getListaOrdine().remove(CameriereViewController.getListaOrdine().size()-1);
		 }
	}
	}

