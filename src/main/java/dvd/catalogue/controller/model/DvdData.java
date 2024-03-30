package dvd.catalogue.controller.model;

import java.util.HashSet;
import java.util.Set;

import dvd.catalogue.entity.Dvd;
import dvd.catalogue.entity.Owner;
import dvd.catalogue.entity.Style;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DvdData {

	private Long dvdId;
	private String dvdName;
	private String dvdAge;
	private String dvdCondition;
	private String dvdReleaseDate;
	private String filmDirector;
	private Set<DvdStyle> styles = new HashSet<>();
	private Owner owner;
	
	public DvdData (Dvd dvd) {
		dvdId = dvd.getDvdId();
		dvdName = dvd.getDvdName();
		dvdAge = dvd.getDvdAge();
		dvdCondition= dvd.getDvdCondition();
		dvdReleaseDate = dvd.getDvdReleaseDate();
		filmDirector = dvd.getFilmDirector();
		owner = dvd.getOwner();
		 for (Style style : dvd.getStyles()) {
			styles.add(new DvdStyle(style));
		 }
	}
	
	@Data
	@NoArgsConstructor
	public static class DvdOwner {
		private Long ownerId;
		
		private String ownerName;
		private String ownerUserName;
		private String ownerLocation;
		
		private Set<DvdData> dvds = new HashSet<>();
		
		public DvdOwner (Owner owner) {
			ownerId= owner.getOwnerId();
			ownerName= owner.getOwnerName();
			ownerUserName = owner.getOwnerUserName();
			ownerLocation= owner.getOwnerLocation();
			
			for (Dvd dvd: owner.getDvds()) {
				dvds.add(new DvdData(dvd));
			}
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class DvdStyle {
		private Long styleId;
		private String styleName;
		
		public DvdStyle (Style style) {
			styleId = style.getStyleId();
			styleName = style.getStyleName();
		}
	}
}
