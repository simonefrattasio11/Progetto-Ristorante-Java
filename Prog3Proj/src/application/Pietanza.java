package application;

// La classe relativa alla generica pietanza

public class Pietanza {
	private double prezzo;
	private String nome;
	private String categoria;
	private int quantità;
	private int id;
	private double percentuale;
	
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getQuantità() {
		return quantità;
	}
	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPercentuale() {
		return percentuale;
	}
	public void setPercentuale(double percentuale) {
		this.percentuale = percentuale;
	}
	
}
