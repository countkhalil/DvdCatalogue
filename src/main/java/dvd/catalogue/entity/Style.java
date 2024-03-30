package dvd.catalogue.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Style {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long styleId;
	
	private String styleName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (mappedBy = "styles", cascade = CascadeType.PERSIST)
	private Set <Dvd> dvds = new HashSet<Dvd>();
}
