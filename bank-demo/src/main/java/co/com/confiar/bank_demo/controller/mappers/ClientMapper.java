package co.com.confiar.bank_demo.controller.mappers;

import co.com.confiar.bank_demo.controller.dto.request.CreateClientDTO;
import co.com.confiar.bank_demo.controller.dto.response.ClientResponseDTO;
import co.com.confiar.bank_demo.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    Client toClient(CreateClientDTO request);

    ClientResponseDTO toClientResponse(Client client);

    List<ClientResponseDTO> toClientResponseList(List<Client> clientList);

}
