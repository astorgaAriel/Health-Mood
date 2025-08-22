package cl.healthmood.Health.Mood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;

@Entity
@Table(name = "img")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idimg")
    private Integer imageId;

    @Column(name = "imgURL", nullable = false, length = 150)
    private String imageUrl;

    @Column(name = "pedido_number", nullable = false)
    private Integer pedido;

    @Column(name = "is_primary", nullable = false, length = 1)
    private String isPrimary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_product_id", nullable = false)
    private Product product;
}