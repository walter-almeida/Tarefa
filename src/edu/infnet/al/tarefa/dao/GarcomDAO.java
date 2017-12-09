package edu.infnet.al.tarefa.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.infnet.al.tarefa.exception.DAOException;
import edu.infnet.al.tarefa.model.Funcionario;
import edu.infnet.al.tarefa.model.Garcom;

public class GarcomDAO extends FuncionarioDAO{
	
	@Override
	public void incluir(Funcionario entidade) throws DAOException {
		super.incluir(entidade);
		String sql = "INSERT INTO garcom(identidade, matricula) VALUES (?,?)";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			
			comando.execute();
					
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar um novo garcom",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em GarcomDAO", e);
			}
		}
		
	}

	private void preencheCampos(PreparedStatement comando, Funcionario entidade) throws SQLException, DAOException {
		if(entidade instanceof Garcom) {
			comando.setInt(0, ((Garcom)entidade).getIdentidade());
			comando.setInt(1, ((Garcom)entidade).getMatricula());
		} else {
			throw new DAOException("Erro ao tentar inserir/alterar outro tipo de funcionário em Garçom",null);
		}
			
	}

	@Override
	public void alterar(Funcionario entidade) throws DAOException {
		super.alterar(entidade);
		String sql = "UPDATE garcom SET identidade=?, matricula=? WHERE idgarcom=?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			comando.setInt(2, ((Garcom)entidade).getIdGarcom());
			comando.execute();
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao alterar informações do garcom",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em GarcomDAO", e);
			}
		}
		
	}

	@Override
	public void excluir(Funcionario entidade) throws DAOException {
		super.excluir(entidade);
		String sql = "DELETE FROM garcom WHERE idgarcom = ?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, ((Garcom)entidade).getIdGarcom());
			comando.execute();
		} catch (SQLException e) {
			throw new DAOException("Erro ao deletar o garcom", e);
		}
		
	}

	@Override
	public Funcionario obter(int id) throws DAOException {
		String sql = "SELECT * FROM garcom WHERE idgarcom= ?";
		
		Funcionario funcionario = new Garcom();
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

	private Funcionario obterFuncionario(ResultSet rs) throws SQLException, DAOException {
		Funcionario funcionario = new Garcom();
		((Garcom)funcionario).setIdGarcom(rs.getInt("idgarcom"));
		((Garcom)funcionario).setIdentidade(rs.getInt("identidade"));
		((Garcom)funcionario).setMatricula(rs.getInt("matricula"));
		
		Funcionario temp = super.obter(rs.getInt("idfuncionario"));
		
		funcionario.setEndereco(temp.getEndereco());
		funcionario.setNome(temp.getNome());
		funcionario.setIdFuncionario(temp.getIdFuncionario());
		funcionario.setTelefone(temp.getTelefone());
		
		return funcionario;
	}

	
	public List<Garcom> listarGarcons() throws DAOException {
		String sql = "SELECT * FROM garcom";
		
		ArrayList<Garcom> funcionarios = new ArrayList<Garcom>();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			rs = comando.executeQuery();
			
			while(rs.next()) {
				funcionarios.add((Garcom)obterFuncionario(rs));				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de garcons", e);
		}
		
		return funcionarios;
	}
	
	
}
