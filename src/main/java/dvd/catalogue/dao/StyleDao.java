package dvd.catalogue.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dvd.catalogue.entity.Style;

public interface StyleDao extends JpaRepository<Style, Long> {

}
