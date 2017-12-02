package edu.infnet.al.tarefa.dao;

import java.util.List;

import edu.infnet.al.tarefa.model.PercentualComissao;

public class PercentualComissaoDAO extends GenericoDAO<PercentualComissao>{

private Connection conn;
	
	public PercentualComissaoDAO() throws DAOException {
		conn = Conector.getConnection();
	}
	
	@Override
	public void incluir(PercentualComissao entidade) throws DAOException {
		String sql = "INSERT INTO percentualcomissao(valor, dataHora, idGarcom) VALUES (?,?,?)";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			
			preencheCampos(comando, entidade);			
			
			comando.execute();
			
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar uma nova percentualcomissao",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em PercentualComissaoDAO", e);
			}
		}
		
		
	}

	@Override
	public void alterar(PercentualComissao entidade) throws DAOException {
		String sql = "UPDATE percentualcomissao SET valor=?, dataHora=?, idGarcom=? WHERE id=?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			
			preencheCampos(comando, entidade);
			comando.setInt(3, entidade.getId());
			comando.execute();
			
		} catch (SQLException e) {
				throw new DAOException("Erro ao criar uma nova percentualcomissao",e);
		}finally {
			try {
				comando.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar PreparedStatement em PercentualComissaoDAO", e);
			}
		}
		
	}

	@Override
	public void excluir(PercentualComissao entidade) throws DAOException {
		String sql = "DELETE FROM percentualcomissao WHERE id = ?";
		PreparedStatement comando = null;
		
		try {
			comando = conn.prepareStatement(sql);
			comando.setInt(0, entidade.getId());
			comando.execute();
		} catch (SQLException e) {
			throw new DAOException("Erro ao deletar a percentualcomissao", e);
		}
		
	}

	@Override
	public PercentualComissao obter(int id) throws DAOException{
		String sql = "SELECT * FROM percentualcomissao WHERE id= ?";
		
		PercentualComissao percentualcomissao = new PercentualComissao();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			
			comando.setInt(0, id);
			
			rs = comando.executeQuery();
			
			if(rs.next()) {
				percentualcomissao = obterPercentualComissao(rs);				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma percentualcomissao", e);
		}
		
		return percentualcomissao;
	}

	@Override
	public List<PercentualComissao> listar() throws DAOException{
		String sql = "SELECT * FROM percentualcomissao";
		
		ArrayList<PercentualComissao> percentualcomissaos = new ArrayList<PercentualComissao>();
		PreparedStatement comando = null;
		ResultSet rs = null;
		try {
			comando = conn.prepareStatement(sql);
			rs = comando.executeQuery();
			
			while(rs.next()) {
				percentualcomissaos.add(obterPercentualComissao(rs));				
			}
			
		} catch (SQLException e) {
			throw new DAOException("Erro ao buscar uma lista de percentualcomissaos", e);
		}
		
		
		
		return percentualcomissaos;
	}
	
	public void preencheCampos(PreparedStatement comando, PercentualComissao entidade) throws SQLException{
		comando.setDouble(0, entidade.getValor());
		comando.setDate(1, new Date(entidade.getDataHora().getTime()));
		comando.setInt(2, new Date(entidade.getIdGarcom().getTime()));
	}
	
	public PercentualComissao obterPercentualComissao(ResultSet rs) throws SQLException, DAOException {
		PercentualComissao percentualcomissao = new PercentualComissao();
		percentualcomissao.setId(rs.getInt("id"));
		percentualcomissao.setValor(rs.getDouble("valor"));
		percentualcomissao.setDataHora(rs.getDate("dataHora"));
		percentualcomissao.setIdGarcom(rs.getInt("idGarcom"));
		
		
		return percentualcomissao;
	}
	
}
