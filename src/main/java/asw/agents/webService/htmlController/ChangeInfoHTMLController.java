package asw.agents.webService.htmlController;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.agents.util.Assert;
import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Agent;
import asw.agents.webService.responses.errors.ErrorResponse;

@Controller
public class ChangeInfoHTMLController {
	@Autowired
	private UpdateInfo updateInfo;

	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public String changeInfo() {
		return "changeInfo";
	}

	@RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
	public String changePassword(HttpSession session, @RequestParam String password,
			@RequestParam String newPassword, Model model) {
		Assert.isPasswordEmpty(password);
		Assert.isPasswordEmpty(newPassword);
		Assert.isSamePassword(password, newPassword);

		// Participant que se ha logeado antes
		Agent p = (Agent) session.getAttribute("agent");
		Assert.isParticipantNull(p);
		Assert.isPasswordCorrect(password, p);

		// Actualizo sus datos
		updateInfo.updatePassword(p, password, newPassword);

		// Mensaje a mostrar en HTML
		model.addAttribute("info", "Contraseña actualizada correctamente");
		return "agentData";
	}
	
	@RequestMapping(value = "/confirmEmail", method = RequestMethod.POST)
	public String changeEmail(HttpSession session, @RequestParam String email, Model model) {
		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);
		
		// Participant que se ha logeado antes
		Agent p = (Agent) session.getAttribute("agent");
		Assert.isParticipantNull(p);
		Assert.isSameEmail(email, p.getEmail());

		// Actualizo sus datos
		updateInfo.updateEmail(p, email);

		// Mensaje a mostrar en HTML
		model.addAttribute("info", "Email actualizado correctamente");
		return "agentData";
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorResponse excep, Model model) {
		model.addAttribute("error", excep.getMessageStringFormat());
		return "error";
	}
}
