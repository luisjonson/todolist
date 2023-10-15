package br.com.rockset.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
/**
 * id
 * Usuarioa (ID_USUARIO)
 * Descrição 
 * Título
 * Data de Inicio
 * Data de Termino
 *  Prioridade
 */
@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
     private UUID id;
     private String descricao;

     @Column(length = 50)
     private String titulo;
     private LocalDateTime startAt;
     private LocalDateTime endAt;
     private String prioridade;
     private  UUID idUser;

     @CreationTimestamp
     private LocalDateTime createdAt;
    
}
