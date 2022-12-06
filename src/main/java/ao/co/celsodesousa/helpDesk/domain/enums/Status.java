package ao.co.celsodesousa.helpDesk.domain.enums;

public enum Status {

	
	ABERTO(1,"Aberto"),
	ANDAMENTO(2,"Andamento"),
	ENCERRADO(3,"Encerrado");
	
	private Integer code;
	private String description;
	
	
	private Status(Integer code, String description) {
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
	
	
	
	
	public static Status toEnum(Integer code) {
		
		if(code == null)
		{
			return null;
		}
		
		for(Status p : Status.values())
		{
			if(p.code == code)
			{
				return p;
			}
		}
		
		throw new IllegalArgumentException("Codigo do Status invalido");
	}
	
}
