package asw.dbManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
	
	/**
	 * Método que devuelve el agente el cual es buscado por email
	 * en la base de datos
	 * @param email del agente
	 * @return El Agente con dicho email
	 */
	@Deprecated
	public Agent findByEmail(String email);
	
	
	
	/**
	 * Devuelve el agente encontrado al buscar por identificador
	 * en la base de datos
	 * @param identificador del agente
	 * @return el agente encontrado
	 */
	public Agent findByIdent(String ident);
	
}
