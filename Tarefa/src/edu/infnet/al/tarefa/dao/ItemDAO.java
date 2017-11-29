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
			comando.setInt(0, entidade.getQuantidade());
			comando.setInt(1, entidade.getIdConta());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Item entidade) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item obter(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<Item> listar() throws DAOException {
		// TODO Auto-generated method stub
		return null;
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
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setIdConta(rs.getInt("idconta"));
				item.setQuantidade(rs.getInt("quantidade"));
				item.setProduto(new ProdutoDAO().obterDoItem(item.getId()));
				itens.add(item);
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de itens da conta "+idConta, e);
		}
		
		return itens;
	}

}
