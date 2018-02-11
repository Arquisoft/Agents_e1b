package asw.dbManagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Agent;
import asw.dbManagement.repository.AgentRepository;

@Service
public class UpdateInfoImpl implements UpdateInfo {

	private AgentRepository repository;
	
	@Autowired
	public UpdateInfoImpl(AgentRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Método que permite la actualización de la contraseña del agente
	 * Se comprueba que las contraseñas no estén vacías, sean distintas y 
	 * la actual coincida con la del agente
	 */
	@Override
	public void updatePassword(Agent agent, String password, String newPassword) {
		
		if (password != null && newPassword != null && !(password.equals(newPassword))
				&& agent.getPassword().equals(password)) {
			agent.setPassword(newPassword);
			this.repository.save(agent);
		}
		
	}

	/**
	 * Método que permite la actualización del email del agente
	 * Se comprueba que el email no esté vacío
	 */
	@Override
	public void updateEmail(Agent agent, String email) {
		if(email != null){
			agent.setEmail(email);
			this.repository.save(agent);
		}
	}
	
	
	/**
	 * Método que permite actualizar el identificador del agente
	 */
	@Override
	public void updateIdentificator(Agent agent, String ident) {
		//Hay que rellenar este metodo
	}


}
