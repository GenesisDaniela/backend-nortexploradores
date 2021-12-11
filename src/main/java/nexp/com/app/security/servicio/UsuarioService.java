/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.security.servicio;

/**
 *
 * @author santi
 */

import nexp.com.app.model.Pasajero;
import nexp.com.app.negocio.response.PaqueteCantidad;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.dao.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByUsername(nombreUsuario);
    }

    public int[][] resultadoMensual(){
        List<Usuario> usuariosReg = usuarioRepository.findAll();
        LocalDate fechaActual = LocalDate.now();
        int resultadoMensual [][] = new int[2][2];
        resultadoMensual[0][0] = fechaActual.getMonth().getValue()-1;
        resultadoMensual[1][0] = fechaActual.getMonth().getValue();

        for(Usuario u: usuariosReg){
//            valido que no sea enero para no tener problema con el anio, cuento usuarios nuevos
            if(fechaActual.getMonth().getValue() > 1 && fechaActual.getMonth().getValue() == u.getFecha().getMonth().getValue()
                    && fechaActual.getYear() == u.getFecha().getYear()){
                resultadoMensual[1][1] += 1;
            }
            //valido que sea enero y descuento uno en el anio para poder comparar, cuento usuarios nuevos
            if(fechaActual.getMonth().getValue() == 1 && u.getFecha().getMonth().getValue() == 12
                    && fechaActual.getYear()-1 == u.getFecha().getYear()){
                resultadoMensual[1][1] += 1;
            }
            //valido que no sea enero y descuento uno en el anio para poder comparar, cuento usuarios antiguos (mes anterior)
            if(fechaActual.getMonth().getValue()-1 != 12 && fechaActual.getMonth().getValue()-1 == u.getFecha().getMonth().getValue()
                    && fechaActual.getYear() == u.getFecha().getYear()){
                resultadoMensual[0][1] += 1;
            }
            //valido que sea enero y descuento uno en el anio para poder comparar, cuento usuarios antiguos (mes anterior)
            if(fechaActual.getMonth().getValue()-1 == 12 && fechaActual.getMonth().getValue()-1 == u.getFecha().getMonth().getValue()
                    && fechaActual.getYear()-1 == u.getFecha().getYear()){
                resultadoMensual[0][1] += 1;
            }
        }


        return (resultadoMensual);
    }

    public Optional<Usuario> getByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
    public boolean existsByNombreUsuario(String nombreUsuario){

        return usuarioRepository.existsByUsername(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    
     @Transactional(readOnly = true)
    public boolean getExisteEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String email){
        return usuarioRepository.existsByUsername(email);
    }

    @Transactional
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).get();
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }


    @Transactional
    public void eliminar(int id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> encontrar(int id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    
}