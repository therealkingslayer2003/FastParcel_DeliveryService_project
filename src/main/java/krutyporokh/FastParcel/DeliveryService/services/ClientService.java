package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.Client;
import krutyporokh.FastParcel.DeliveryService.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    public Optional<Client> findById(Integer clientId) {
        return clientRepository.findById(clientId);
    }
}
