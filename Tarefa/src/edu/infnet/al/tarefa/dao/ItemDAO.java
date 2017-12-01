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

public class ItemDAO extends GenericoDAO<Item>{

	private Connection conn;
	
	public ItemDAO() throws DAOException {
		conn = Conector.getConnection();
	}
	
	
	@Override
	public void incluir(Item entidade) throws DAOException {
		String sql = "INSERT INTO item (quantidade, idConta, idProduto) VALUES (?,?,?)";
		PreparedStatement comando = null;
		try {
			comando = conn.prepareStatement(sql);
			preencheCampos(comando, entidade);
			comando.setInt(2, entidade.getProduto().getId());
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar um novo item",e);
		}finally {
			if(comando != null) {
				try {
					comando.close();
				} catch (SQLException e) {
					throw new DAOException("Erro ao fechar o PrepareStatement - ItemDAO", e);					
				}
			}
		
		}
	}

	@Override
	public void alterar(Item entidade) throws DAOException {
		String sql = "UPDATE item SET quantidade=?, idconta=? WHERE id=?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			comando.setInt(2, entidade.getId());
			comando.execute();
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar um novo item",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em ItemDAO", e);
			}
		}
		
	}

	@Override
	public void excluir(Item entidade) throws DAOException {
		String sql = "DELETE FROM item WHERE id = ?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, entidade.getId());
			comando.execute();
		} catch (SQLException e) {
			throw new DAOException("Erro ao deletar o item", e);
		}
		
		
	}

	@Override
	public Item obter(int id) throws DAOException {
		String sql = "SELECT * FROM item WHERE id= ?";
		
		Item item = new Item();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			
			comando.setInt(0, id);
			
			rs = comando.executeQuery();
			
			if(rs.next()) {
				item = obterItem(rs);				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar um item", e);
		}
		
		return item;
	}
	

	private Item obterItem(ResultSet rs) throws SQLException, DAOException {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setQuantidade(rs.getInt("quantidade"));
		item.setIdConta(rs.getInt("idconta"));
		item.setProduto(new ProdutoDAO().obterDoItem(item.getId()));

		return item;
	}


	@Override
	public List<Item> listar() throws DAOException {
		String sql = "SELECT * FROM item";
		
		ArrayList<Item> itens = new ArrayList<Item>();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			rs = comando.executeQuery();
			
			while(rs.next()) {
				itens.add(obterItem(rs));				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de itens", e);
		}
		
		return itens;
	}


	public List<Item> listar(int idConta) throws DAOException {
		String sql = "SELECT * FROM item WHERE idconta=?";		
		ArrayList<Item> itens = new ArrayList<Item>();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, idConta);
			rs = comando.executeQuery();
			
			while(rs.next()) {
				itens.add(obterItem(rs));	
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de itens da conta "+idConta, e);
		}
		
		return itens;
	}
	
	public void preencheCampos(PreparedStatement comando, Item entidade) throws SQLException{
		comando.setInt(0, entidade.getQuantidade());
		comando.setInt(1, entidade.getIdConta());		
	}

}
