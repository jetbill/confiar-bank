package co.com.confiar.bank_demo.service.impl;

import co.com.confiar.bank_demo.controller.dto.request.CreateClientDTO;
import co.com.confiar.bank_demo.controller.dto.response.ClientResponseDTO;
import co.com.confiar.bank_demo.controller.mappers.ClientMapper;
import co.com.confiar.bank_demo.model.entity.Client;
import co.com.confiar.bank_demo.model.exception.ClientAlreadyExistsException;
import co.com.confiar.bank_demo.model.exception.ClientNotFoundException;
import co.com.confiar.bank_demo.repository.ClientRepository;
import co.com.confiar.bank_demo.service.ClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    @Override
    public ClientResponseDTO createClient(CreateClientDTO clientDto) {
        if (clientRepository.existsByNit(clientDto.nit())){
            throw new ClientAlreadyExistsException("Client " + clientDto.nit() + " already exists");
        }
        Client savedClient = clientMapper.toClient(clientDto);
        return clientMapper.toClientResponse(clientRepository.save(savedClient));
    }

    @Transactional(readOnly = true)
    @Override
    public ClientResponseDTO findClientByNit(String nit) {
        return clientMapper.toClientResponse(clientRepository.findByNit(nit)
                .orElseThrow(() -> new ClientNotFoundException("Client " + nit + " not found")));
    }
}
