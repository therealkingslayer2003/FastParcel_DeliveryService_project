package krutyporokh.FastParcel.DeliveryService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.events.Event;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
}
