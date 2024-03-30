package dvd.catalogue.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dvd.catalogue.entity.Owner;

public interface OwnerDao extends JpaRepository<Owner, Long>{

}
