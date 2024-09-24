package br.edu.infnet.pedidos.domain;

import br.edu.infnet.pedidos.infra.repository.PedidoStatusConverter;
import br.edu.infnet.pedidos.infra.repository.ValorMonetarioConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
@AllArgsConstructor@Data@Builder
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(name = "ORDER_DATE")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @JsonIgnoreProperties(value = "orderId")
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<ItemPedido> itemList;
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    @Convert(converter = PedidoStatusConverter.class)
    @Column(name = "STATUS")
    private PedidoStatus status;
    @Convert(converter = ValorMonetarioConverter.class)
    @Column(name = "VALOR_TOTAL")
    private ValorMonetario valorTotal;

    public Pedido() {
        this.orderDate = new Date();
        this.status = PedidoStatus.NOVO;        
        this.valorTotal = new ValorMonetario(BigDecimal.ZERO);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", orderDate=" + orderDate + ", customerId=" + customerId + ", status=" + status + ", valorTotal=" + valorTotal + '}';
    }

    public void adicionarItem(Long productId, int quantidade, double unitValue) {
        if (productId == null) {
            throw new IllegalArgumentException("Produto inválido");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade precisa ser positiva");
        }
        if (this.status != PedidoStatus.NOVO) {
            throw new IllegalStateException("Não é possível inserir itens em um pedido em andamento");
        }
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setOrderId(this);
        itemPedido.setProductId(productId);
        itemPedido.setQuantity(quantidade);
        itemPedido.setTotal(new ValorMonetario(new BigDecimal(unitValue * quantidade)));
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        this.itemList.add(itemPedido);
        this.setValorTotal(this.valorTotal.somar(itemPedido.getTotal()));        
    }

    public void fecharPedido() {
        if (this.status != PedidoStatus.NOVO) {
            throw new IllegalStateException("Não é possível fechar um pedido que não é novo");
        }
        if (this.itemList.isEmpty()) {
            throw new IllegalStateException("Não é possível fechar um pedido vazio");
        }
        this.status = PedidoStatus.FECHADO;
    }

    public void cancelarPedido() {
        if (this.status != PedidoStatus.FECHADO) {
            throw new IllegalStateException("Não é possível cancelar um pedido que não esteja fechado");
        }
        this.status = PedidoStatus.CANCELADO;
    }

    public void erroPedido() {
        this.status = PedidoStatus.ERRO;
    }

    public void enviarPedido() {
        if (this.status != PedidoStatus.FECHADO) {
            throw new IllegalStateException("Não é possível enviar um pedido que não esteja fechado");
        }
        this.status = PedidoStatus.EM_PREPARACAO;
    }

    public void transportandoPedido() {
        if (this.status != PedidoStatus.EMBALADO) {
            throw new IllegalStateException("Não é possível enviar um pedido que não esteja embalado");
        }
        this.status = PedidoStatus.EM_TRANSITO;
    }
}
