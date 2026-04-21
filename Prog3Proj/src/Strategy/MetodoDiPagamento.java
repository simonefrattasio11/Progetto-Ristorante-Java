package Strategy;

import java.io.IOException;
// Il Design pattern Strategy si adatta perfettamente alla situazione richiesta,abbiamo un azione comune da compiere e 3 modi per compierla
// pagamento in contanti,carta di credito e bancomat.
// Il tutto può essere riassunto da 3 classi che implementano un interfaccia  comune

public interface MetodoDiPagamento {
	public void paga() throws IOException;

}
