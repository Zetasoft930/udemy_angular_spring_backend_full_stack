package ao.co.celsodesousa.helpDesk.controller;

import ao.co.celsodesousa.helpDesk.domain.Chamado;
import ao.co.celsodesousa.helpDesk.domain.dto.ChamadoDTO;
import ao.co.celsodesousa.helpDesk.domain.dto.ChamadoNotIdDTO;
import ao.co.celsodesousa.helpDesk.domain.enums.Status;
import ao.co.celsodesousa.helpDesk.services.ChamadoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(value = "/chamado")
public class ChamadoController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ChamadoService service;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll() {

		List<Chamado> list = this.service.findAll();
		Type listType = new TypeToken<List<ChamadoDTO>>() {
		}.getType();
		List<ChamadoDTO> listDTO = modelMapper.map(list, listType);

		return ResponseEntity.ok(listDTO);
	}
	@GetMapping(path = "findByStatus/{status}")
	public ResponseEntity<List<ChamadoDTO>> findAll(@PathVariable(required = true) Integer status ) {

		List<Chamado> list = this.service.findByStatus(Status.toEnum(status));
		Type listType = new TypeToken<List<ChamadoDTO>>() {
		}.getType();
		List<ChamadoDTO> listDTO = modelMapper.map(list, listType);

		return ResponseEntity.ok(listDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {

		Chamado modelo = this.service.findById(id);

		ChamadoDTO dto = modelMapper.map(modelo, ChamadoDTO.class);

		HttpStatus status = HttpStatus.OK;

		if (modelo == null) {
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<ChamadoDTO>(dto, status);
	}

	@PostMapping
	public ResponseEntity<ChamadoDTO> create(@RequestBody(required = true) @Valid ChamadoNotIdDTO dto) {

		Chamado model = modelMapper.map(dto, Chamado.class);

		model = this.service.save(model);

		HttpStatus status = HttpStatus.CREATED;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		ChamadoDTO dtoResult = modelMapper.map(model, ChamadoDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@PathVariable(required = true) Integer id,
			@RequestBody @Valid ChamadoNotIdDTO dto) {

		Chamado model = modelMapper.map(dto, Chamado.class);
		model.setId(id);

		model = this.service.update(model);

		HttpStatus status = HttpStatus.OK;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		ChamadoDTO dtoResult = modelMapper.map(model, ChamadoDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@PathVariable(required = true) Integer id) {

		Chamado model = this.service.delete(id);

		HttpStatus status = HttpStatus.OK;

		if (model == null) {

			status = HttpStatus.BAD_REQUEST;
		}

		ChamadoDTO dtoResult = modelMapper.map(model, ChamadoDTO.class);

		return ResponseEntity.status(status).body(dtoResult);

	}

}
