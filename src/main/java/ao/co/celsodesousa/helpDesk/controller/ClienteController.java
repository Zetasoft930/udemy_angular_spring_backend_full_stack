package ao.co.celsodesousa.helpDesk.controller;

import ao.co.celsodesousa.helpDesk.domain.Cliente;
import ao.co.celsodesousa.helpDesk.domain.dto.ClienteDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.ClienteNotIdDTO;
import ao.co.celsodesousa.helpDesk.services.ClienteService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ClienteService service;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {

		List<Cliente> list = this.service.findAll();
		Type listType = new TypeToken<List<ClienteDTO>>() {
		}.getType();
		List<ClienteDTO> listDTO = modelMapper.map(list, listType);

		return ResponseEntity.ok(listDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {

		Cliente modelo = this.service.findById(id);

		ClienteDTO dto = modelMapper.map(modelo, ClienteDTO.class);

		HttpStatus status = HttpStatus.OK;

		if (modelo == null) {
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<ClienteDTO>(dto, status);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@RequestBody(required = true) @Valid ClienteNotIdDTO dto) {

		Cliente model = modelMapper.map(dto, Cliente.class);
		model.setSenha(bCryptPasswordEncoder.encode(dto.getSenha()));
		model = this.service.save(model);

		HttpStatus status = HttpStatus.CREATED;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		ClienteDTO dtoResult = modelMapper.map(model, ClienteDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable(required = true) Integer id,
			@RequestBody @Valid ClienteNotIdDTO dto) {

		Cliente model = modelMapper.map(dto, Cliente.class);
		model.setId(id);
		model.setSenha(bCryptPasswordEncoder.encode(dto.getSenha()));

		model = this.service.update(model);

		HttpStatus status = HttpStatus.OK;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		ClienteDTO dtoResult = modelMapper.map(model, ClienteDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable(required = true) Integer id) {

		Cliente model = this.service.delete(id);

		HttpStatus status = HttpStatus.OK;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		ClienteDTO dtoResult = modelMapper.map(model, ClienteDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

}
