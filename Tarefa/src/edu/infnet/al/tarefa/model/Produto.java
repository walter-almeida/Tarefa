package edu.infnet.al.tarefa.model;

public class Produto {
	
	private int id;
	private Enum categoria;
	private double preco;
	private int codigo;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Enum getCategoria() {
		return categoria;
	}
	public void setCategoria(Enum categoria) {
		this.categoria = categoria;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	

}
