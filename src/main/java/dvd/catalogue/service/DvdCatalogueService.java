package dvd.catalogue.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dvd.catalogue.controller.model.DvdData;
import dvd.catalogue.controller.model.DvdData.DvdOwner;
import dvd.catalogue.controller.model.DvdData.DvdStyle;
import dvd.catalogue.dao.DvdDao;
import dvd.catalogue.dao.OwnerDao;
import dvd.catalogue.dao.StyleDao;
import dvd.catalogue.entity.Dvd;
import dvd.catalogue.entity.Owner;
import dvd.catalogue.entity.Style;

@Service
public class DvdCatalogueService {


	@Autowired
	private DvdDao dvdDao;
	
	@Autowired 
	private OwnerDao ownerDao;
	
	@Autowired
	private StyleDao styleDao;

	@Transactional (readOnly = false)
	public DvdData saveDvd(Long ownerId, DvdData dvdData) {
		Owner owner = findOwnerById(ownerId);
		Long dvdId = dvdData.getDvdId();
		Dvd dvd = findDvd (ownerId, dvdId); 
		
		setDvdFields(dvd, dvdData);
		
		dvd.setOwner(owner);
		owner.getDvds().add(dvd);
		
		Dvd dbDvd = dvdDao.save(dvd);
		return new DvdData(dbDvd);
	}
private Dvd findDvd(Long ownerId, Long dvdId) {
		Dvd dvd;
		if (Objects.isNull(dvdId)) {
			dvd = new Dvd();
		}else {
			dvd = findDvdById(ownerId, dvdId);
		}
		return dvd;
	}

@Transactional (readOnly = true)
private Dvd findDvdById(Long ownerId, Long dvdId) {
	findOwnerById(ownerId);
	Dvd dvd = dvdDao.findById(dvdId).orElseThrow(
			() -> new NoSuchElementException("DVD ID " + dvdId + " doesn't exist, man"));
	
	if (dvd.getOwner().getOwnerId() == ownerId) {
		return dvd;
	}else {
		throw new IllegalStateException("Owner ID " + ownerId + " is non existent, big dawg");
	}
}
private void setDvdFields(Dvd dvd, DvdData dvdData) {
		dvd.setDvdAge(dvdData.getDvdAge());
		dvd.setDvdCondition(dvdData.getDvdCondition());
		dvd.setDvdId(dvdData.getDvdId());
		dvd.setDvdName(dvdData.getDvdName());
		dvd.setDvdReleaseDate(dvdData.getDvdReleaseDate());
		dvd.setFilmDirector(dvdData.getFilmDirector());
		
	}
@Transactional (readOnly = false)
	public DvdOwner saveOwner(DvdOwner dvdOwner) {
		Long ownerId= dvdOwner.getOwnerId();
		Owner owner = findOwner (ownerId);
		
		setOwnerFields(owner, dvdOwner);
		
		return new DvdOwner(ownerDao.save(owner));
	}

	private void setOwnerFields(Owner owner, DvdOwner dvdOwner) {
		owner.setOwnerName(dvdOwner.getOwnerName());
		owner.setOwnerUserName(dvdOwner.getOwnerUserName());
		owner.setOwnerLocation(dvdOwner.getOwnerLocation());
		
	}

	private Owner findOwner(Long ownerId) {
		Owner owner;
		
		if (Objects.isNull(ownerId)) {
			owner = new Owner();
		}else {
			owner = findOwnerById (ownerId);
		}
		
		return owner;
		
	}

	private Owner findOwnerById(Long ownerId) {
		return ownerDao.findById(ownerId).orElseThrow(
				() -> new NoSuchElementException(ownerId+ " ain't a real ID, dawg."));
	}
	
	@Transactional (readOnly = false)
	public DvdStyle saveStyle(Long dvdId, DvdStyle dvdStyle) {
		Long styleId = dvdStyle.getStyleId();
		Style style = findStyle(dvdId, styleId);
		
		setStyleFields(style, dvdStyle);
		
		for (Dvd dvdVari : style.getDvds()) {
			dvdVari.getStyles().add(style);
			style.getDvds().add(dvdVari);
		}
		
		Style dbStyle = styleDao.save(style);
		return new DvdStyle(dbStyle);
	}
	
	
	private Style findStyle(Long dvdId, Long styleId) {
		Style style;
		
		if (Objects.isNull(styleId)) {
			style = new Style();
		}else {
			style = findStyleById(dvdId, styleId);
		}
		return style;
	}
	private Style findStyleById(Long dvdId, Long styleId) {
		
		Style style = styleDao.findById(styleId).orElseThrow(
				() -> new NoSuchElementException("Style with ID=" + styleId + "isn't real, pal"));
		
		return style;
	}
	private void setStyleFields(Style style, DvdStyle dvdStyle) {
		style.setStyleId(dvdStyle.getStyleId());
		style.setStyleName(dvdStyle.getStyleName());
		
	}
	
	@Transactional (readOnly = true)
	public  List<DvdOwner> retrieveAllOwners() {
		List <Owner> owners = ownerDao.findAll();
		List <DvdOwner> response = new LinkedList<>();
		
		for (Owner owner: owners) {
			DvdOwner dod = new DvdOwner (owner);
			
			dod.getDvds().clear();
			
			response.add(dod);
		}
		return response;
	}
	
	public DvdOwner retrieveOwnerById (Long ownerId) {
		Owner owner = findOwnerById(ownerId);
		return new DvdOwner(owner);
	}
	
	public void deleteOwnerById(Long ownerId) {
		Owner owner = findOwnerById(ownerId);
		ownerDao.delete(owner);
		
	}

	

	
}
