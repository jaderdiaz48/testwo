package co.worldoffice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.worldoffice.models.Departamentos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name="departamentos")
public class Departamentos {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "departamento")
    private Collection<Empleados> empleadosCollection;
	
    public Departamentos() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Empleados> getEmpleadosCollection() {
		return empleadosCollection;
	}

	public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
		this.empleadosCollection = empleadosCollection;
	}

    
    
}
