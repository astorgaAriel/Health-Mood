package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCartId(Integer cartId);

    List<CartItem> findByProductProductId(Integer productId);

    Optional<CartItem> findByCartIdAndProductProductId(Integer cartId, Integer productId);

    @Query("SELECT SUM(ci.quantity) FROM CartItem ci WHERE ci.cartId = :cartId")
    Long getTotalItemsInCart(@Param("cartId") Integer cartId);

    @Query("SELECT SUM(ci.quantity * ci.product.price) FROM CartItem ci WHERE ci.cartId = :cartId")
    Double getCartTotal(@Param("cartId") Integer cartId);

    void deleteByCartId(Integer cartId);

    void deleteByCartIdAndProductProductId(Integer cartId, Integer productId);

}