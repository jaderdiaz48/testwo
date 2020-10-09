package co.worldoffice.services;

import java.util.List;

import co.worldoffice.models.Empleados;


public interface IEmpleadosService {
	

	public List<Empleados> findAll();

	public Empleados findById(Integer id);

	public Empleados save(Empleados user);

	public List<Empleados> getHighestSalaries();
	
	public List<Empleados> findByDepartment(Integer departmentId, Integer pageNumber, Integer pageSize);
	
	public List<Object> goupSalaryByDepartment();
	
	public void delete(Integer id);
}
