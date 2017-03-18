package model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 

@SuppressWarnings("restriction")
@XmlRootElement(name = "resposta")
public class Resposta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String descResposta;

	public Resposta () {
		
	}
	
	public Resposta (Long id, String descResposta) {
		this.id = id;
		this.descResposta = descResposta;
	}
	
	public Long getId() {
		return id;
	}

	@XmlElement
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescResposta() {
		return descResposta;
	}

	@XmlElement
	public void setDescResposta(String descResposta) {
		this.descResposta = descResposta;
	}
	
	
}
