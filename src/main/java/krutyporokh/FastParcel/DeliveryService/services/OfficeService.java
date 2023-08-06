package krutyporokh.FastParcel.DeliveryService.services;

import krutyporokh.FastParcel.DeliveryService.models.Office;
import krutyporokh.FastParcel.DeliveryService.repositories.OfficeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OfficeService {
    private final OfficeRepository officeRepository;
    public Optional<Office> findById(Integer officeId) {
        return officeRepository.findById(officeId);
    }
}
