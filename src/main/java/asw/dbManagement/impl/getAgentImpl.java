package asw.dbManagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbManagement.GetAgent;
import asw.dbManagement.model.Agent;
import asw.dbManagement.repository.AgentRepository;


@Service
public class getAgentImpl implements GetAgent {
	
	private AgentRepository repository;
	
	@Autowired
	public getAgentImpl(AgentRepository repository) {
		this.repository = repository;
	}
	
	
	/**
	 * Método que devuelve el Agente buscado por email
	 * Hace uso del método findByEmail (mapeador)
	 */
	@Override
	public Agent getAgent(String email) {
		
		return this.repository.findByEmail(email);
	}

}
