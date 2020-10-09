package co.worldoffice.services;

import java.util.List;

import co.worldoffice.models.Departamentos;


public interface IDepartamentosService {
	

	public List<Departamentos> findAll();

	public Departamentos findById(Integer id);

	public Departamentos save(Departamentos user);
	
	public Departamentos findByName(String name);

	public void delete(Integer id);
}
