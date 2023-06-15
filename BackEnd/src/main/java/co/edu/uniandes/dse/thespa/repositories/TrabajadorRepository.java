package co.edu.uniandes.dse.thespa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;

public interface TrabajadorRepository extends JpaRepository<TrabajadorEntity, Long> {

}
