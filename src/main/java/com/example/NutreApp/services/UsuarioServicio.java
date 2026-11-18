package com.example.NutreApp.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.NutreApp.models.Usuario;
import com.example.NutreApp.repositories.IUsuarioRepositorio;

@Service
public class UsuarioServicio {

    // Para cocinar necesito las recetas
    // Para programar los servicios necesito los repositorios

    // INYECCIÓN DE DEPENDENCIAS
    @Autowired
    private IUsuarioRepositorio repositorio;

    // método para guardar un usuario
    public Usuario guardar(Usuario datos) {
        return this.repositorio.save(datos);

    }

    // Método para buscar un ID
    public Usuario buscarPorId(UUID id) {
        Optional<Usuario> usuarioBuscado = this.repositorio.findById(id);
        if (!usuarioBuscado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id no encontrado");

        } else {
            return usuarioBuscado.get();
        }
    }

    // Método para buscar todos
    public List<Usuario> buscarTodo() {
        return this.repositorio.findAll();
    }

    // Método para actualizar(modificar)

    public Usuario modificar(UUID id, Usuario datos) {

        Optional<Usuario> usuarioBuscado = this.repositorio.findById(id);
        if (!usuarioBuscado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id no encontrado");

        } else {

            // 1. precargar la info que existe actualmente en la BD
            Usuario usuarioEncontrado = usuarioBuscado.get();

            // 2. espacio para modificar sólo las propiedades permitidas
            usuarioEncontrado.setNombre(datos.getNombre());
            usuarioEncontrado.setRol(datos.getRol());

            // 3. espacio para volver a guardar el usuario que se acaba de modificar
            return this.repositorio.save(usuarioEncontrado);

        }
    }

    // Método para eliminar
    public boolean eliminar(UUID id) {
        Optional<Usuario> usuarioBuscado = this.repositorio.findById(id);
        if (!usuarioBuscado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id no encontrado");

        } else {
            this.repositorio.deleteById(id);
            return true;

        }

    }

}