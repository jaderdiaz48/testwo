package co.worldoffice.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.worldoffice.models.Empleados;
import co.worldoffice.services.EmpleadosServiceImpl;
import co.worldoffice.util.CSVHelper;
import co.worldoffice.util.EmpleadosHelper;
import co.worldoffice.models.Departamentos;
import co.worldoffice.services.DepartamentosServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/testwo/csv")
public class CsvController {

	@Autowired
	private EmpleadosServiceImpl empleadosService;

	@Autowired
	private DepartamentosServiceImpl departamentosService;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (CSVHelper.hasCSVFormat(file)) {

				List<EmpleadosHelper> empleados = CSVHelper.csvToEmpleados(file.getInputStream());

				if (createDepartments(empleados)) {

					if (createEmployees(empleados)) {

						response.put("mensaje", "Empelados y Departamentos creados correctamente");
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
					} else {
						response.put("mensaje", "Ocurrio un error creando los empleados");
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
					}

				} else {
					response.put("mensaje", "Ocurrio un error creando los departamentos");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				response.put("mensaje", "Error, debe subir un archivo tipo CSV");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/highestSalaries")
	public ResponseEntity<?> getHighestSalaries() {
		Map<String, Object> response = new HashMap<>();
		try {
			return new ResponseEntity<List<Empleados>>(empleadosService.getHighestSalaries(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/groupSalaries")
	public ResponseEntity<?> groupSalaries() {
		Map<String, Object> response = new HashMap<>();
		try {
			return new ResponseEntity<List<Object>>(empleadosService.goupSalaryByDepartment(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findByDepartment")
	public ResponseEntity<?> findByDepartment(@RequestParam Integer department, @RequestParam Integer pageNumber) {
		Map<String, Object> response = new HashMap<>();
		try {
			return new ResponseEntity<List<Empleados>>(empleadosService.findByDepartment(department, pageNumber, 10), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al realizar el query en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Boolean createDepartments(List<EmpleadosHelper> empleados) {

		try {
			Map<String, Long> departments = empleados.stream()
					.collect(Collectors.groupingBy(EmpleadosHelper::getDepartamento, Collectors.counting()));

			for (String department : departments.keySet()) {
				Departamentos departamento = new Departamentos();
				departamento.setNombre(department);
				departamentosService.save(departamento);
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean createEmployees(List<EmpleadosHelper> empleados) {

		try {

			Iterator it = empleados.iterator();

			while (it.hasNext()) {

				EmpleadosHelper next = (EmpleadosHelper) it.next();

				Empleados empleado = new Empleados();
				empleado.setNombre(next.getNombre());
				empleado.setCargo(next.getCargo());
				empleado.setSalario(next.getSalario());
				
				if (next.getTiempoCompleto()) {
					empleado.setTiempoCompleto(1);
				}else {
					empleado.setTiempoCompleto(0);
				}
				
				empleado.setDepartamento(departamentosService.findByName(next.getDepartamento()));
				
				empleadosService.save(empleado);
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
