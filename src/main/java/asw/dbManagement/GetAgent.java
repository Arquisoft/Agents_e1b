package asw.dbManagement;

import asw.dbManagement.model.Agent;

public interface GetAgent {
	/**
	 * Permite la solicitud la de información para el usuario.
	 */
	public Agent getAgent(String ident);

	public Agent getAgentByEmail(String email);

}
