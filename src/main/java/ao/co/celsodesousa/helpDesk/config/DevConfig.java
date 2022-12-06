package ao.co.celsodesousa.helpDesk.config;

import ao.co.celsodesousa.helpDesk.services.DBService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String value;

	@Bean
	public boolean instanciaDB() {

		if (value.equalsIgnoreCase("create")) {

			this.dbService.instanciaDB();
			return true;
		}
		
		return false;
	}
	

	@Bean
	public ModelMapper modelMapperConfig() {
		return new ModelMapper();
	}


}
