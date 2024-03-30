package dvd.catalogue.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Dvd {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dvdId;
	
	private String dvdName;
	private String dvdAge;
	private String dvdCondition;
	private String dvdReleaseDate;
	private String filmDirector;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (cascade = CascadeType.PERSIST)
	@JoinTable (name = "dvd_style", joinColumns = @JoinColumn (name= "dvd_id"), inverseJoinColumns = @JoinColumn(name= "style_id"))
	private Set<Style> styles = new HashSet<Style>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn (name= "owner_id")
	private Owner owner;
}
