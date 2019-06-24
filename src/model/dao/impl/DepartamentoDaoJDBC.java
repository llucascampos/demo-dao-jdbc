package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connection.ConnectionFactory;
import connection.DBException;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;


public class DepartamentoDaoJDBC implements DepartamentoDao {
		
	private Connection conn;
	
	
	public  DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Departamento obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("insert into departamento (nome) "+
										"values(?)");
			st.setString(1, obj.getNome());
			
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
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			ConnectionFactory.closeStmt(st);
		}
	}

	@Override
	public void update(Departamento obj) {
		PreparedStatement st = null;
		
		try {
		st = conn.prepareStatement("update departamento set nome = ? where id = ?");
		st.setString(1, obj.getNome());
		st.setInt(2, obj.getId());
		
		st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			ConnectionFactory.closeStmt(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {	
		PreparedStatement st = null;
		
		
		try {
		st = conn.prepareStatement("delete departamento where id = ?");
		st.setInt(1, id);
		
		st.executeUpdate();
		
		
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
	}

	@Override
	public Departamento findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from departamento where id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("id"));
				dep.setNome(rs.getString("nome"));
				
				return dep;
			}
			return null;
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			ConnectionFactory.closeRs(st, rs);
		}
		
	}

	@Override
	public List<Departamento> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from departamento");
			
			rs = st.executeQuery();
			
			List<Departamento> list = new ArrayList<>();
			
			Map<Integer, Departamento> map = new HashMap<>(); 
			
			while (rs.next()) {	
				
				Departamento dep = map.get(rs.getInt("id"));
				
				if(dep == null) {
					dep = new Departamento();
					dep.setNome(rs.getString("nome"));
					map.put(rs.getInt("id"), dep);
				}
			
			
				list.add(dep);
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
