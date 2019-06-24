package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class Program {

	public static void main(String[] args) {
		VendedorDao vendedorDao = DaoFactory.createVendedorDao();
		DepartamentoDao departamentoDao = DaoFactory.createDepartamentoDao();
		
		
		System.out.println("=== Teste 1 findById");
		Vendedor vendedor = vendedorDao.findById(1);
		Departamento departamento = departamentoDao.findById(1); 
		System.out.println(vendedor);
		
		
		System.out.println("=== Teste 2 findByDepartamento");
		Departamento dep = new Departamento(1, null);
		List<Vendedor> list = vendedorDao.findByDepartamento(dep);
		for(Vendedor obj : list) {
			System.out.println(obj);
		}
		
	 
		
		System.out.println("===Teste 3 findAll");
		list = vendedorDao.findAll();
		for(Vendedor obj : list) {
			System.out.println(obj);
		}
		
		
		
		System.out.println("===Teste 4 insert");
		Vendedor novoVendedor = new Vendedor(null, "Lucas", "lucascampos@gmail.com", new Date(), 4000.0, dep);
		vendedorDao.insert(novoVendedor);
		
		System.out.println("Novo vendedor inserido: " + novoVendedor.getId());

		

	}

}
