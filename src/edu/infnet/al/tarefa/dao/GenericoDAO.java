package edu.infnet.al.tarefa.dao;

import java.util.List;

import edu.infnet.al.tarefa.exception.DAOException;

public abstract class GenericoDAO<T> {
	public abstract void incluir(T entidade)  throws DAOException;
	public abstract void alterar(T entidade)  throws DAOException;
	public abstract void excluir(T entidade)  throws DAOException;
	public abstract T obter(int id)  throws DAOException;
	public abstract List<T> listar() throws DAOException;
}
