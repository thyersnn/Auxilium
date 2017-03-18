package auxilium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Resposta;
import service.ConversationApi;

@Controller
@EnableAutoConfiguration
public class AuxiliumApp {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.GET, path="/resposta",produces="application/json")
	@ResponseBody
	public Resposta getResposta(@RequestParam(value="p") String p) throws Exception {

		Resposta pergunta = new Resposta();
		pergunta.setDescResposta(p);

		return ConversationApi.GetResposa(pergunta);

	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuxiliumApp.class, args);
	}
}