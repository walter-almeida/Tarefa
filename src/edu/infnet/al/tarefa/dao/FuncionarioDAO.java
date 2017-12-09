package edu.infnet.al.tarefa.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.infnet.al.tarefa.exception.DAOException;
import edu.infnet.al.tarefa.model.Funcionario;


public class FuncionarioDAO extends GenericoDAO<Funcionario>{
	protected Connection conn;
	
	@Override
	public void incluir(Funcionario entidade) throws DAOException {
		String sql = "INSERT INTO funcionario(nome, endereco, telefone) VALUES (?,?,?)";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			
			preencheCampos(comando, entidade);
			
			
			if (comando.executeUpdate() > 0){
	            ResultSet rs = comando.getGeneratedKeys();
	            rs.next();            
	            //TODO testar
	            entidade.setIdFuncionario(rs.getInt(1));
			}
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar uma novo funcionario",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em FuncionarioDAO", e);
			}
		}
		
	}

	private void preencheCampos(PreparedStatement comando, Funcionario entidade) throws SQLException {
		comando.setString(0, entidade.getNome());
		comando.setString(1,entidade.getEndereco());
		comando.setInt(2,entidade.getTelefone());		
	}

	@Override
	public void alterar(Funcionario entidade) throws DAOException {
		String sql = "UPDATE funcionario SET nome=?, endereco=?, telefone=? WHERE idfuncionario=?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			comando.setInt(3, entidade.getIdFuncionario());
			comando.execute();
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar uma novo funcionario",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em FuncionarioDAO", e);
			}
		}
		
	}

	@Override
	public void excluir(Funcionario entidade) throws DAOException {
		String sql = "DELETE FROM funcionario WHERE idfuncionario = ?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, entidade.getIdFuncionario());
			comando.execute();
		} catch (SQLException e) {
			throw new DAOException("Erro ao deletar o funcionario", e);
		}
		
	}

	@Override
	public Funcionario obter(int id) throws DAOException {
		String sql = "SELECT * FROM funcionario WHERE idfuncionario= ?";
		
		Funcionario funcionario = new Funcionario();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			
			comando.setInt(0, id);
			
			rs = comando.executeQuery();
			
			if(rs.next()) {
				funcionario = obterFuncionario(rs);				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar um funcionario", e);
		}
		return funcionario;
	}

	private Funcionario obterFuncionario(ResultSet rs) throws SQLException {
		Funcionario funcionario = new Funcionario();
		funcionario.setIdFuncionario(rs.getInt("idfuncionario"));
		funcionario.setNome(rs.getString("nome"));
		funcionario.setEndereco(rs.getString("endereco"));
		funcionario.setTelefone(rs.getInt("telefone"));
		
		
		return funcionario;
	}

	@Override
	public List<Funcionario> listar() throws DAOException {
		String sql = "SELECT * FROM funcionario";
		
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			rs = comando.executeQuery();
			
			while(rs.next()) {
				funcionarios.add(obterFuncionario(rs));				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de funcionarios", e);
		}
		
		return funcionarios;
	}
	
}
