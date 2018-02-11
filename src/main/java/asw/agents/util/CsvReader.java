package asw.agents.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class CsvReader {

	static Map<Integer, String> tipos;

	private void leerCSV() {

		try {
			tipos = new HashMap<Integer, String>();

			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader("csv.csv"));

			List<String[]> li = reader.readAll();

			Iterator<String[]> i1 = li.iterator();

			while (i1.hasNext()) {

				String[] crop = i1.next()[0].split(";");

				tipos.put(Integer.parseInt(crop[0]), crop[1]);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String buscarTipo(int numero) {
		leerCSV();
		for (Map.Entry<Integer, String> e : tipos.entrySet()) {
			if (e.getKey() == numero)
				return e.getValue();
		}
		return null;
	}
}
