package ao.co.celsodesousa.helpDesk.domain.enums;

public enum Perfil {

	
	ADMINISTRADO(1,"ROLE_ADMIN"),
	CLIENTE(2,"ROLE_CLIENTE"),
	TECNICO(3,"ROLE_TECNICO");
	
	
	private Integer code;
	private String description;
	
	
	private Perfil(Integer code, String description) {
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
	
	
	
	
	public static Perfil toEnum(Integer code) {
		
		if(code == null)
		{
			return null;
		}
		
		for(Perfil p : Perfil.values())
		{
			if(p.code == code)
			{
				return p;
			}
		}
		
		throw new IllegalArgumentException("Codigo do perfil invalido");
	}
	
	
}
