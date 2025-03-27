package co.com.confiar.bank_demo.service;

import co.com.confiar.bank_demo.controller.dto.request.CreateClientDTO;
import co.com.confiar.bank_demo.controller.dto.response.ClientResponseDTO;

public interface ClientService {
    ClientResponseDTO createClient(CreateClientDTO client);
    ClientResponseDTO findClientByNit(String nit);

}
