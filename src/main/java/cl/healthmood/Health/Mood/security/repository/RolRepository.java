package cl.healthmood.Health.Mood.security.repository;


import cl.healthmood.Health.Mood.security.model.Rol;
import cl.healthmood.Health.Mood.security.model.NombreRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(NombreRol nombre);
}