package asw.dbManagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;
import asw.dbManagement.repository.ParticipantRepository;

@Service
public class UpdateInfoImpl implements UpdateInfo {

	private ParticipantRepository repository;
	
	@Autowired
	public UpdateInfoImpl(ParticipantRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Método que permite la actualización de la contraseña del Participante
	 * Se comprueba que las contraseñas no estén vacías, sean distintas y 
	 * la actual coincida con la del participante
	 */
	@Override
	public void updatePassword(Participant participant, String password, String newPassword) {
		
		if (password != null && newPassword != null && !(password.equals(newPassword))
				&& participant.getPassword().equals(password)) {
			participant.setPassword(newPassword);
			this.repository.save(participant);
		}
		
	}

	/**
	 * Método que permite la actualización del email del Participante
	 * Se comprueba que el email no esté vacío
	 */
	@Override
	public void updateEmail(Participant participant, String email) {
		if(email != null){
			participant.setEmail(email);
			this.repository.save(participant);
		}
	}

}
