package com.example.NutreApp.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.NutreApp.models.Video;
import com.example.NutreApp.repositories.IVideoRepositorio;

@Service
public class VideoServicio {

    @Autowired
    private IVideoRepositorio repositorio;

    public Video guardar(Video datos) {
        return this.repositorio.save(datos);
    }

    // Método para buscar un ID
    public Video buscarPorId(UUID id) {
        Optional<Video> videoBuscado = this.repositorio.findById(id);
        if (!videoBuscado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id no encontrado");

        } else {
            return videoBuscado.get();
        }
    }

    // Método para buscar todos
    public List<Video> buscarTodo() {
        return this.repositorio.findAll();
    }

    // Método para actualizar(modificar)

    public Video modificar(UUID id, Video datos) {

        Optional<Video> videoBuscado = this.repositorio.findById(id);
        if (!videoBuscado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id no encontrado");

        } else {

            // 1. precargar la info que existe actualmente en la BD
            Video videoEncontrado = videoBuscado.get();

            // 2. espacio para modificar sólo las propiedades permitidas
            videoEncontrado.setTitulo(datos.getTitulo());
            videoEncontrado.setDescripcion(datos.getDescripcion());
            videoEncontrado.setCategoria(datos.getCategoria());

            // 3. espacio para volver a guardar el video que se acaba de modificar
            return this.repositorio.save(videoEncontrado);
        }
    }

    // Método para eliminar
    public boolean eliminar(UUID id) {
        Optional<Video> videoBuscado = this.repositorio.findById(id);
        if (!videoBuscado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id no encontrado");

        } else {
            this.repositorio.deleteById(id);
            return true;
        }
    }
}
