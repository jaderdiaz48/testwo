package co.worldoffice.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.worldoffice.models.Departamentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.worldoffice.dao.IDepartamentosDao;

@Service
public class DepartamentosServiceImpl implements IDepartamentosService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IDepartamentosDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Departamentos> findAll() {
		return (List<Departamentos>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Departamentos findById(Integer id) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM departamentos WHERE departamentos.id = ?1",
				Departamentos.class);
		q.setParameter(1, id);
		if (q.getResultList().size() == 1) {
			return (Departamentos) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Departamentos) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Departamentos findByName(String name) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM departamentos WHERE departamentos.nombre like ?1",
				Departamentos.class);
		q.setParameter(1, name);
		if (q.getResultList().size() == 1) {
			return (Departamentos) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Departamentos) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		javax.persistence.Query q = em.createNativeQuery("DELETE FROM departamentos WHERE departamentos.id = ?1");
		q.setParameter(1, id);
		q.executeUpdate();
	}

	@Override
	@Transactional
	public Departamentos save(Departamentos object) {
		// TODO Auto-generated method stub
		return dao.save(object);
	}

	
}
