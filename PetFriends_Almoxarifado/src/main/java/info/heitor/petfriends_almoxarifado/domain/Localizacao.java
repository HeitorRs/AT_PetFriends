package info.heitor.petfriends_almoxarifado.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor@NoArgsConstructor@Data@Builder
public class Localizacao {
    private String corredor;
    private String prateleira;
}
