package edu.infnet.al.tarefa.dao;

import java.util.List;

public abstract class GenerricoDAO<T> {
	public abstract void incluir(T entidade);
	public abstract void alterar(T entidade);
	public abstract void excluir(T entidade);
	public abstract T obter(int id);
	public abstract List<T> listar(int id);
}
