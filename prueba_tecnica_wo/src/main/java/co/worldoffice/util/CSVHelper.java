package co.worldoffice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<EmpleadosHelper> csvToEmpleados(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<EmpleadosHelper> empleados = new ArrayList<EmpleadosHelper>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				EmpleadosHelper tutorial = new EmpleadosHelper(csvRecord.get("Nombre"), csvRecord.get("Cargo"),
						new BigDecimal(csvRecord.get("Salario")), Boolean.valueOf(csvRecord.get("Tiempo Completo")),
						csvRecord.get("Departamento"));

				empleados.add(tutorial);
			}

			return empleados;
		} catch (IOException e) {
			throw new RuntimeException("Error al parsear el archivo CSV: " + e.getMessage());
		}
	}

}