package asw.dbManagement;

import asw.dbManagement.model.Participant;

public interface UpdateInfo {
	/**
	 * Permite la solicitud de cambio de contraseña
	 */
	public void updatePassword(Participant participant, String password, String newPassword);
	
	public void updateEmail(Participant participant, String email);
}
