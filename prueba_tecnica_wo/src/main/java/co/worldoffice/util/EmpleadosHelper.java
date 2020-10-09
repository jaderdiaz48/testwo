package co.worldoffice.util;

import java.math.BigDecimal;

public class EmpleadosHelper {

	private String nombre;
	private String cargo;
	private BigDecimal salario;
	private Boolean tiempoCompleto;
	private String departamento;
	
	
	
	public EmpleadosHelper(String nombre, String cargo, BigDecimal salario, Boolean tiempoCompleto,
			String departamento) {
		super();
		this.nombre = nombre;
		this.cargo = cargo;
		this.salario = salario;
		this.tiempoCompleto = tiempoCompleto;
		this.departamento = departamento;
	}

	public EmpleadosHelper () {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Boolean getTiempoCompleto() {
		return tiempoCompleto;
	}

	public void setTiempoCompleto(Boolean tiempoCompleto) {
		this.tiempoCompleto = tiempoCompleto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	
	
}
