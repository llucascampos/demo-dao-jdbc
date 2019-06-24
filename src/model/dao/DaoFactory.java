package model.dao;

import connection.ConnectionFactory;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class DaoFactory {
	
	public static VendedorDao createVendedorDao() {
		return new VendedorDaoJDBC(ConnectionFactory.getConnection());
	}
	
	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(ConnectionFactory.getConnection());
	}

}
