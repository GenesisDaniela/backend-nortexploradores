/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.security.dao;

/**
 *
 * @author santi
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nexp.com.app.security.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String nombreUsuario);
    boolean existsByUsername(String nombreUsuario);
     Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

}

