package com.example.demo.security.service;

import com.example.demo.security.entity.Usuario;
import com.example.demo.security.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    public boolean getExisteEmail(String email) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
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
