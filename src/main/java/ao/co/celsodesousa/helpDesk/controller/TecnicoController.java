package ao.co.celsodesousa.helpDesk.controller;

import ao.co.celsodesousa.helpDesk.domain.Tecnico;
import ao.co.celsodesousa.helpDesk.domain.dto.TecnicoDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.TecnicoNotIdDTO;
import ao.co.celsodesousa.helpDesk.services.TecnicoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(value = "/tecnico")
public class TecnicoController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private TecnicoService service;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {

		List<Tecnico> list = this.service.findAll();
		Type listType = new TypeToken<List<TecnicoDTO>>() {
		}.getType();
		List<TecnicoDTO> listDTO = modelMapper.map(list, listType);

		return ResponseEntity.ok(listDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {

		Tecnico modelo = this.service.findById(id);

		TecnicoDTO dto = modelMapper.map(modelo, TecnicoDTO.class);

		HttpStatus status = HttpStatus.OK;

		if (modelo == null) {
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<TecnicoDTO>(dto, status);
	}

	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@RequestBody(required = true) @Valid TecnicoNotIdDTO dto) {

		
		
		Tecnico model = modelMapper.map(dto, Tecnico.class);
		model.setSenha(bCryptPasswordEncoder.encode(dto.getSenha()));
		model = this.service.save(model);

		HttpStatus status = HttpStatus.CREATED;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		TecnicoDTO dtoResult = modelMapper.map(model, TecnicoDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable(required = true) Integer id,
			@RequestBody @Valid TecnicoNotIdDTO dto) {

		Tecnico model = modelMapper.map(dto, Tecnico.class);

		Tecnico antigo = this.service.findById(id);

		if(antigo!= null)
		{
			if(!antigo.getSenha().equals(dto.getSenha()))
			{
				System.out.println("teste de senha >>>>");
				model.setSenha(bCryptPasswordEncoder.encode(dto.getSenha()));

			}
		}


		model.setId(id);

		model = this.service.update(model);

		HttpStatus status = HttpStatus.OK;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		TecnicoDTO dtoResult = modelMapper.map(model, TecnicoDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

	//@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable(required = true) Integer id) {

		Tecnico model = this.service.delete(id);

		HttpStatus status = HttpStatus.OK;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		TecnicoDTO dtoResult = modelMapper.map(model, TecnicoDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

}
