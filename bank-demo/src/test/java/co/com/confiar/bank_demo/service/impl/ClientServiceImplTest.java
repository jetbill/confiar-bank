package co.com.confiar.bank_demo.service.impl;

import co.com.confiar.bank_demo.controller.dto.request.CreateClientDTO;
import co.com.confiar.bank_demo.controller.dto.response.ClientResponseDTO;
import co.com.confiar.bank_demo.controller.mappers.ClientMapper;
import co.com.confiar.bank_demo.model.entity.Client;
import co.com.confiar.bank_demo.model.exception.ClientAlreadyExistsException;
import co.com.confiar.bank_demo.model.exception.ClientNotFoundException;
import co.com.confiar.bank_demo.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private CreateClientDTO clientDTO;
    private Client clientEntity;
    private ClientResponseDTO clientResponseDTO;

    @BeforeEach
    void setUp() {
        // Datos de prueba
        clientDTO = CreateClientDTO.builder()
                .nit("1234567890")
                .name("John Doe")
                .entryDate(LocalDate.now())
                .build();

        clientEntity = Client.builder()
                .nit("1234567890")
                .name("John Doe")
                .entryDate(LocalDate.now())
                .build();

        clientResponseDTO = ClientResponseDTO.builder()
                .nit("1234567890")
                .name("John Doe")
                .entryDate(LocalDate.now())
                .build();
    }

    @Test
    void testCreateClient_Success() {
        // Arrange
        when(clientRepository.existsByNit(clientDTO.nit())).thenReturn(false);
        when(clientMapper.toClient(clientDTO)).thenReturn(clientEntity);
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        when(clientMapper.toClientResponse(clientEntity)).thenReturn(clientResponseDTO);

        // Act
        ClientResponseDTO result = clientService.createClient(clientDTO);

        // Assert
        verify(clientRepository, times(1)).existsByNit(clientDTO.nit());
        verify(clientRepository, times(1)).save(clientEntity);
        verify(clientMapper, times(1)).toClient(clientDTO);
        verify(clientMapper, times(1)).toClientResponse(clientEntity);
        assertEquals(clientResponseDTO, result);
    }

    @Test
    void testCreateClient_ExistingNit() {
        // Arrange
        when(clientRepository.existsByNit(clientDTO.nit())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> clientService.createClient(clientDTO))
                .isInstanceOf(ClientAlreadyExistsException.class)
                .hasMessage("Client 1234567890 already exists");
    }
    @Test
    void testFindClientByNit_ExistingClient() {
        // Arrange
        when(clientRepository.findByNit(clientDTO.nit()))
                .thenReturn(Optional.of(clientEntity));
        when(clientMapper.toClientResponse(clientEntity)).thenReturn(clientResponseDTO);

        // Act
        ClientResponseDTO result = clientService.findClientByNit(clientDTO.nit());

        // Assert
        verify(clientRepository, times(1)).findByNit(clientDTO.nit());
        verify(clientMapper, times(1)).toClientResponse(clientEntity);
        assertEquals(clientResponseDTO, result);
    }

    @Test
    void testFindClientByNit_NotFound() {
        // Arrange
        when(clientRepository.findByNit("invalid_nit"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> clientService.findClientByNit("invalid_nit"))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessage("Client invalid_nit not found");
    }




}