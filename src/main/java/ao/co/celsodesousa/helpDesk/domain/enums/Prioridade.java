package ao.co.celsodesousa.helpDesk.domain.enums;

public enum Prioridade {

	
	BAIXA(1,"Baixa"),
	MEDIA(2,"Media"),
	ALTA(3,"Alta");
	
	private Integer code;
	private String description;
	
	
	private Prioridade(Integer code, String description) {
		this.code = code;
		this.description = description;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	public static Prioridade toEnum(Integer code) {
		
		if(code == null)
		{
			return null;
		}
		
		for(Prioridade p : Prioridade.values())
		{
			if(p.code == code)
			{
				return p;
			}
		}
		
		throw new IllegalArgumentException("Codigo do Prioridade invalido");
	}
	
}
