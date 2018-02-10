package asw.agents.util;

import java.util.Calendar;
import java.util.Date;


public  class Utilidades {
	///////////////////////////////////////////////////////////////
	/// //
	/// Clase creada para realizar funciones que no //
	/// tengan que ver con la l�gica de los controladores //
	/// //
	//////////////////////////////////////////////////////////
	
	@Deprecated
	public static int getEdad(Date fechaNacimiento) {
		Calendar calendarioNacimiento = Calendar.getInstance();
		calendarioNacimiento.setTime(fechaNacimiento);
		int dianacimiento = calendarioNacimiento.get(Calendar.DAY_OF_YEAR);

		Calendar calendariohoy = Calendar.getInstance();
		int diaHoy = calendariohoy.get(Calendar.DAY_OF_YEAR);
		if (dianacimiento - diaHoy == 0) {// Si la resta de días que llevamos de
											// año es 0, significa que hemos
											// cumplido años
			return calendariohoy.get(Calendar.YEAR) - calendarioNacimiento.get(Calendar.YEAR);

		} else {

			return calendariohoy.get(Calendar.YEAR) - calendarioNacimiento.get(Calendar.YEAR) - 1;

		}

	}

}
