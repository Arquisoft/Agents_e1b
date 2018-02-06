package asw.dbManagement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Participant")
public class Participant {

	// Id generado automáticamente para diferenciar cada uno (para mapear)
	@Id
	@GeneratedValue
	private Long id;

	// Atributos del participante
	private String nombre;
	private String apellidos;
	private String password;
	private Date fechaNacimiento;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String DNI;
	private String direccion;
	private String nacionalidad;

	private boolean isAdmin;
	private boolean isPolitician;

	/**
	 * Constructor vacío (ya que es para mapear)
	 */
	Participant() {
	}

	/**
	 * Constructor
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param password
	 * @param fechaNacimiento
	 * @param email
	 * @param dNI
	 * @param direccion
	 * @param nacionalidad
	 */
	public Participant(String nombre, String apellidos, String password, Date fechaNacimiento, String email, String dNI,
			String direccion, String nacionalidad, boolean isAdmin, boolean isPolitician) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.DNI = dNI;
		this.direccion = direccion;
		this.nacionalidad = nacionalidad;
		this.isAdmin = isAdmin;
		this.isPolitician = isPolitician;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDNI() {
		return DNI;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isPolitician() {
		return isPolitician;
	}

	public void setPolitician(boolean isPolitician) {
		this.isPolitician = isPolitician;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Participant [nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", DNI=" + DNI + ", direccion="
				+ direccion + ", nacionalidad=" + nacionalidad + ", isAdmin=" + isAdmin + ", isPolitician="
				+ isPolitician + "]";
	}

}
