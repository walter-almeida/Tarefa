package edu.infnet.al.tarefa.model;

import java.util.Date;

public class Conta {
	private int id;
	private int mesa;
	private Date dataHoraAbertura;
	private Date dataHoraFechamento;
	private Item item;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMesa() {
		return mesa;
	}
	public void setMesa(int mesa) {
		this.mesa = mesa;
	}
	public Date getDataHoraAbertura() {
		return dataHoraAbertura;
	}
	public void setDataHoraAbertura(Date dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}
	public Date getDataHoraFechamento() {
		return dataHoraFechamento;
	}
	public void setDataHoraFechamento(Date dataHoraFechamento) {
		this.dataHoraFechamento = dataHoraFechamento;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	
}
