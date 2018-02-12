package asw.dbManagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbManagement.GetAgent;
import asw.dbManagement.model.Agent;
import asw.dbManagement.repository.AgentRepository;

@Service
public class GetAgentImpl implements GetAgent {

	private AgentRepository repository;

	@Autowired
	public GetAgentImpl(AgentRepository repository) {
		this.repository = repository;
	}

	/**
	 * Método que devuelve el Agente buscado por identificador Hace uso del método
	 * findByIdent (mapeador)
	 */
	@Override
	public Agent getAgent(String ident) {
		return this.repository.findByIdent(ident);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Agent getAgentByEmail(String email) {
		return this.repository.findByEmail(email);
	}

}
