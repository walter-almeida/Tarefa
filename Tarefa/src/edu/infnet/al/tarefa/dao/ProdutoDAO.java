package edu.infnet.al.tarefa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.infnet.al.tarefa.conector.Conector;
import edu.infnet.al.tarefa.exception.DAOException;
import edu.infnet.al.tarefa.model.Item;
import edu.infnet.al.tarefa.model.Produto;

public class ProdutoDAO extends GenericoDAO<Produto>{

	private Connection conn;
	
	public ProdutoDAO() throws DAOException {
		conn = Conector.getConnection();
	}
	
	@Override
	public void incluir(Produto entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Produto entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Produto entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Produto obter(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produto> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Produto obterDoItem(int idItem) throws DAOException {
		String sql = "SELECT * FROM produto WHERE iditem=?";		
		Produto produto = null;
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, idItem);
			rs = comando.executeQuery();
			
			
			if(rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setCodigo(rs.getInt("codigo"));
				produto.setPreco(rs.getDouble("preco"));
				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar o produto do item "+idItem, e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar o comando sql em Produto.obterDoItem", e);
			}
		}
		
		return produto;
	}

	
	
}
