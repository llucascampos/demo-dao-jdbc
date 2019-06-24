package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connection.ConnectionFactory;
import connection.DBException;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {
	
	private Connection conn;
	
	
	public  VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into funcionario (nome, email, aniversario, baseSalario, idDepartamento)"+
										"values (?, ?, ?, ?, ?)",
										Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getAniversario().getTime()));
			st.setDouble(4, obj.getBaseSalario());
			st.setInt(5, obj.getDepartamento().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs  = st.getGeneratedKeys();
				if(rs.next()) {
					int id  = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DBException("Erro, nenhuma linh alterada");
			}
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			ConnectionFactory.closeStmt(st);
		}
		
	}

	@Override
	public void update(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update funcionario set nome = ?, email = ?, aniversario = ?, baseSalario = ?, idDepartamento = ?");
	
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getAniversario().getTime()));
			st.setDouble(4, obj.getBaseSalario());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());		
			st.executeUpdate();
			
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			ConnectionFactory.closeStmt(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
		st = conn.prepareStatement("delete from funcionario where id = ? ");
		st.setInt(1, id);
		st.executeUpdate();
		
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			ConnectionFactory.closeStmt(st);
		}
	}

	@Override
	public Vendedor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select funcionario.*,"+
										"departamento.nome as depNome "+
										"from funcionario inner join departamento "+
										"on funcionario.idDepartamento = departamento.id "+
										"where funcionario.id = ?");
			
			st.setInt(1, id);	
			rs = st.executeQuery();
			if (rs.next()) {
				Departamento dep = instanciandoDepartamento(rs);
				Vendedor obj = instanciandoVendedor(rs, dep);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			ConnectionFactory.closeRs(st, rs);
		}
	}
	
	
	private Vendedor instanciandoVendedor(ResultSet rs, Departamento dep) throws SQLException {
		
		Vendedor vend = new Vendedor();
		vend.setId(rs.getInt("id"));
		vend.setNome(rs.getString("nome"));
		vend.setEmail(rs.getString("email"));
		vend.setAniversario(rs.getDate("aniversario"));
		vend.setBaseSalario(rs.getDouble("baseSalario"));
		vend.setDepartamento(dep);
		
		return vend;
		
	}

	private Departamento instanciandoDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("id"));
		dep.setNome(rs.getString("nome"));
		return dep;
	}

	@Override
	public List<Vendedor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select funcionario.*,"+
										"departamento.nome as depNome "+
										"from funcionario inner join departamento "+
										"on funcionario.idDepartamento = departamento.id ");
			
			
			rs = st.executeQuery();
			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>(); 
			
			while (rs.next()) {	
				
				Departamento dep = map.get(rs.getInt("id"));
				
				if(dep == null) {
					dep = instanciandoDepartamento(rs);
					map.put(rs.getInt("id"), dep);
				}
			
				Vendedor obj = instanciandoVendedor(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			ConnectionFactory.closeRs(st, rs);
		}
	}

	@Override
	public List<Vendedor> findByDepartamento(Departamento departamento) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select funcionario.*,"+
										"departamento.nome as depNome "+
										"from funcionario inner join departamento "+
										"on funcionario.idDepartamento = departamento.id "+
										"where departamento.id = ? ");
			
			st.setInt(1,departamento.getId());	
			rs = st.executeQuery();
			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>(); 
			
			while (rs.next()) {	
				
				Departamento dep = map.get(rs.getInt("id"));
				
				if(dep == null) {
					dep = instanciandoDepartamento(rs);
					map.put(rs.getInt("id"), dep);
				}
			
				Vendedor obj = instanciandoVendedor(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			ConnectionFactory.closeRs(st, rs);
		}
	}
	

}
