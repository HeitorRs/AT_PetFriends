package info.heitor.petfriends_almoxarifado.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "estoques")
@AllArgsConstructor@NoArgsConstructor@Data@Builder
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Embedded
    private Localizacao localizacao;

    public void adicionarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade a ser adicionada deve ser positiva.");
        }
        this.quantity += quantidade;
    }

    public void removerQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade a ser removida deve ser positiva.");
        }
        if (this.quantity < quantidade) {
            throw new IllegalStateException("Não há estoque suficiente para a remoção.");
        }
        this.quantity -= quantidade;
    }
}
