package dvd.catalogue.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dvd.catalogue.entity.Dvd;

public interface DvdDao extends JpaRepository<Dvd, Long>{

}
