package edu.infnet.al.tarefa.dao;


public class GerenteDAO extends FuncionarioDAO{
	@Override
	public void incluir(Funcionario entidade) throws DAOException {
		super.incluir(entidade);
		String sql = "INSERT INTO gerente(cpf) VALUES (?)";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			
			comando.execute();
					
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar um novo gerente",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em GerenteDAO", e);
			}
		}
		
	}

	private void preencheCampos(PreparedStatement comando, Funcionario entidade) throws SQLException, DAOException {
		if(entidade instanceof Gerente) {
			comando.setInt(0, ((Gerente)entidade).getCpf());
			
		} else {
			throw new DAOException("Erro ao tentar inserir/alterar outro tipo de funcionario em Gerente",null);
		}
			
	}

	@Override
	public void alterar(Funcionario entidade) throws DAOException {
		super.alterar(entidade);
		String sql = "UPDATE gerente SET cpf=? WHERE idgerente=?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			comando.setInt(1, ((Gerente)entidade).getIdGerente());
			comando.execute();
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao alterar informacoes do gerente",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em GerenteDAO", e);
			}
		}
		
	}

	@Override
	public void excluir(Funcionario entidade) throws DAOException {
		super.excluir(entidade);
		String sql = "DELETE FROM gerente WHERE idgerente = ?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, ((Gerente)entidade).getIdGerente());
			comando.execute();
		} catch (SQLException e) {
			throw new DAOException("Erro ao deletar o gerente", e);
		}
		
	}

	@Override
	public Funcionario obter(int id) throws DAOException {
		String sql = "SELECT * FROM gerente WHERE idgerente= ?";
		
		Funcionario funcionario = new Gerente();
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
		Funcionario funcionario = new Gerente();
		((Gerente)funcionario).setIdGerente(rs.getInt("idgerente"));
		((Gerente)funcionario).setCpf(rs.getInt("cpf"));
				
		Funcionario temp = super.obter(rs.getInt("idfuncionario"));
		
		funcionario.setEndereco(temp.getEndereco());
		funcionario.setNome(temp.getNome());
		funcionario.setIdFuncionario(temp.getIdFuncionario());
		funcionario.setTelefone(temp.getTelefone());
		
		return funcionario;
	}

	
	public List<Gerente> listarGerentes() throws DAOException {
		String sql = "SELECT * FROM gerente";
		
		ArrayList<Gerente> funcionarios = new ArrayList<Gerente>();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			rs = comando.executeQuery();
			
			while(rs.next()) {
				funcionarios.add((Gerente)obterFuncionario(rs));				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de gerentes", e);
		}
		
		return funcionarios;
	}
	
}
