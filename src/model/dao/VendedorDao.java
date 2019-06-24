package model.dao;

import java.util.List;

import model.entidades.Departamento;
import model.entidades.Vendedor;

public interface VendedorDao {

	 void insert(Vendedor obj);
	 void update(Vendedor obj);
	 void deleteById(Integer id);
	 Vendedor findById(Integer id);
	 List<Vendedor> findByDepartamento(Departamento dep);
	 List<Vendedor> findAll();
	 
}
