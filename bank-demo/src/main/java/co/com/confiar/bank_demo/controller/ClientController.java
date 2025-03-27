package co.com.confiar.bank_demo.controller;


import co.com.confiar.bank_demo.controller.dto.request.CreateClientDTO;
import co.com.confiar.bank_demo.controller.dto.response.ClientResponseDTO;
import co.com.confiar.bank_demo.controller.mappers.ClientMapper;
import co.com.confiar.bank_demo.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Valid CreateClientDTO request) {
        ClientResponseDTO response = clientService.createClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/{nit}")
    public ResponseEntity<ClientResponseDTO> findClientByNit(@PathVariable("nit") @Size(max = 10) String nit) {

        return new ResponseEntity<>(clientService.findClientByNit(nit),HttpStatus.OK);
    }
}
