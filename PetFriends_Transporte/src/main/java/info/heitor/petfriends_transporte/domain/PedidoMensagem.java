package info.heitor.petfriends_transporte.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@AllArgsConstructor@NoArgsConstructor@Data
public class PedidoMensagem {

    @JsonProperty("idPedido")
    private Long idPedido;

    @JsonProperty("estado")
    private PedidoStatus estado;

    @JsonProperty("momento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date momento;

    @JsonProperty("itens")
    private List<ItemPedido> itens;


    @AllArgsConstructor@NoArgsConstructor@Data@Builder
    public static class ItemPedido {
        private Integer quantity;
        private Long productId;
    }
}
