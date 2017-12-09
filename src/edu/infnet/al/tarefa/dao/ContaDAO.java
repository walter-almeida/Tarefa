package edu.infnet.al.tarefa.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.infnet.al.tarefa.conector.Conector;
import edu.infnet.al.tarefa.exception.DAOException;
import edu.infnet.al.tarefa.model.Conta;
import edu.infnet.al.tarefa.model.Item;

public class ContaDAO extends GenericoDAO<Conta>{

	private Connection conn;
	
	public ContaDAO() throws DAOException {
		conn = Conector.getConnection();
	}
	
	@Override
	public void incluir(Conta entidade) throws DAOException {
		String sql = "INSERT INTO conta(mesa, dataHoraAbertura, dataHoraFechamento) VALUES (?,?,?)";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			
			preencheCampos(comando, entidade);
			
			
			if (comando.executeUpdate() > 0){
	            ResultSet rs = comando.getGeneratedKeys();
	            rs.next();            
	            //testar
	            entidade.setId(rs.getInt(1));
			}
			
			for(Item item : entidade.getItens()) {
				ItemDAO itemDao = new ItemDAO();
				item.setIdConta(entidade.getId());
				itemDao.incluir(item);
			}
			
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar uma nova conta",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em ContaDAO", e);
			}
		}
		
		
	}

	@Override
	public void alterar(Conta entidade) throws DAOException {
		String sql = "UPDATE conta SET mesa=?, dataHoraAbertura=?, dataHoraFechamento=? WHERE id=?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			comando.setInt(3, entidade.getId());
			comando.execute();
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar uma nova conta",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em ContaDAO", e);
			}
		}
		
	}

	@Override
	public void excluir(Conta entidade) throws DAOException {
		String sql = "DELETE FROM conta WHERE id = ?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, entidade.getId());
			comando.execute();
		} catch (SQLException e) {
			throw new DAOException("Erro ao deletar a conta", e);
		}
		
	}

	@Override
	public Conta obter(int id) throws DAOException{
		String sql = "SELECT * FROM conta WHERE id= ?";
		
		Conta conta = new Conta();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			
			comando.setInt(0, id);
			
			rs = comando.executeQuery();
			
			if(rs.next()) {
				conta = obterConta(rs);				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma conta", e);
		}
		
		return conta;
	}

	@Override
	public List<Conta> listar() throws DAOException{
		String sql = "SELECT * FROM conta";
		
		ArrayList<Conta> contas = new ArrayList<Conta>();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			rs = comando.executeQuery();
			
			while(rs.next()) {
				contas.add(obterConta(rs));				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de contas", e);
		}
		
		
		
		return contas;
	}
	
	public void preencheCampos(PreparedStatement comando, Conta entidade) throws SQLException{
		comando.setInt(0, entidade.getMesa());
		comando.setDate(1, new Date(entidade.getDataHoraAbertura().getTime()));
		comando.setDate(2, new Date(entidade.getDataHoraFechamento().getTime()));
	}
	
	public Conta obterConta(ResultSet rs) throws SQLException, DAOException {
		Conta conta = new Conta();
		conta.setId(rs.getInt("id"));
		conta.setMesa(rs.getInt("mesa"));
		conta.setDataHoraAbertura(rs.getDate("dataHoraAbertura"));
		conta.setDataHoraFechamento(rs.getDate("dataHoraFechamento"));
		conta.setItens(new ItemDAO().listar(conta.getId()));
		
		return conta;
	}
	
}
