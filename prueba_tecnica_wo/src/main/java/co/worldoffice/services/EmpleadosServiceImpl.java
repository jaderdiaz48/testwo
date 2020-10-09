package co.worldoffice.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.worldoffice.models.Empleados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.worldoffice.dao.IEmpleadosDao;

@Service
public class EmpleadosServiceImpl implements IEmpleadosService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IEmpleadosDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Empleados> findAll() {
		return (List<Empleados>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Empleados findById(Integer id) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM empleados WHERE empleados.id = ?1",
				Empleados.class);
		q.setParameter(1, id);
		if (q.getResultList().size() == 1) {
			return (Empleados) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Empleados) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		javax.persistence.Query q = em.createNativeQuery("DELETE FROM empleados WHERE empleados.id = ?1");
		q.setParameter(1, id);
		q.executeUpdate();
	}

	@Override
	@Transactional
	public Empleados save(Empleados object) {
		// TODO Auto-generated method stub
		return dao.save(object);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleados> getHighestSalaries() {
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM empleados ORDER BY salario DESC LIMIT 5 ",
				Empleados.class);
		return q.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleados> findByDepartment(Integer departmentId, Integer pageNumber, Integer pageSize) {
		javax.persistence.Query q = em.createNativeQuery(
				"SELECT * FROM empleados WHERE empleados.departamento = ?1 LIMIT ?2,?3 ", Empleados.class);
		q.setParameter(1, departmentId);
		q.setParameter(2, ((pageNumber - 1) * 10));
		q.setParameter(3, pageSize);
		return q.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> goupSalaryByDepartment() {
		javax.persistence.Query q = em.createNativeQuery("SELECT departamentos.nombre as `departamento`, SUM(salario) AS `salario` FROM empleados INNER JOIN departamentos ON empleados.departamento = departamentos.id GROUP BY departamentos.nombre");
		return q.getResultList();
	}
}
