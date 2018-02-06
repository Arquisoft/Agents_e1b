package asw.participants.webService.htmlController;

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

import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.participants.util.Assert;
import asw.participants.util.Utilidades;
import asw.participants.webService.responses.errors.ErrorResponse;

@Controller
public class GetParticipantInfoHTMLController {

	@Autowired
	private GetParticipant getParticipant;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicalicerLogin(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getLogin(HttpSession session, @RequestParam String email, @RequestParam String password,
			Model model) {

		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);
		Assert.isPasswordEmpty(password);

		Participant participant = getParticipant.getParticipant(email);

		Assert.isParticipantNull(participant);
		Assert.isPasswordCorrect(password, participant);

		session.setAttribute("participant", participant);

		if (!participant.isAdmin() && !participant.isPolitician()) {
			session.setAttribute("edad", Utilidades.getEdad(participant.getFechaNacimiento()));
			return "datosParticipant";
		} else{
			if(participant.isAdmin())
				return "dashboardAdmin";
			else
				return "dashboardPolitician";
		}

	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorResponse excep, Model model) {
		model.addAttribute("error", excep.getMessageStringFormat());

		return "error";
	}
}
