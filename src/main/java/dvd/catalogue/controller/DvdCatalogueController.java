package dvd.catalogue.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dvd.catalogue.controller.model.DvdData;
import dvd.catalogue.controller.model.DvdData.DvdOwner;
import dvd.catalogue.controller.model.DvdData.DvdStyle;
import dvd.catalogue.service.DvdCatalogueService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dvd_catalogue")
@Slf4j
public class DvdCatalogueController {

	@Autowired
	private DvdCatalogueService dvdCatalogueService;
	
	@PostMapping("/owner/{ownerId}/dvd")
	@ResponseStatus(code= HttpStatus.CREATED)
	public DvdData newDvd (@PathVariable Long ownerId, @RequestBody DvdData dvdData) {
		log.info("New DVD {} is being added to the catalogue", dvdData);
		return dvdCatalogueService.saveDvd(ownerId, dvdData);
	}
	
	@PostMapping("/owner")
	@ResponseStatus(code= HttpStatus.CREATED)
	public DvdOwner newOwner (@RequestBody DvdOwner dvdOwner) {
		log.info("New Owner {} is being added to the catalogue", dvdOwner);
		return dvdCatalogueService.saveOwner(dvdOwner);
	}
	
	@PostMapping ("/dvd/{dvdId}/style")
	@ResponseStatus(code= HttpStatus.CREATED)
	public DvdStyle newStyle (@PathVariable Long dvdId, @RequestBody DvdStyle dvdStyle) {
		log.info("New Style {} is being added to the catalogue", dvdStyle);
		return dvdCatalogueService.saveStyle(dvdId, dvdStyle);
	}
	
	@PutMapping ("/owner/{ownerId}")
	public DvdOwner updateOwner (@PathVariable Long ownerId, @RequestBody DvdOwner dvdOwner) {
		dvdOwner.setOwnerId(ownerId);
		log.info("Updating Owner with ID {} ", ownerId);
		return dvdCatalogueService.saveOwner(dvdOwner);
	}
	@PutMapping("/owner/{ownerId}/dvd")
	public DvdData updateDvd (@PathVariable Long dvdId, @RequestBody DvdData dvdData) {
		log.info("Updating DVD with ID {} ", dvdId);
		return dvdCatalogueService.saveDvd(dvdId, dvdData);
	}
	
	@GetMapping("/owner")
	public List <DvdOwner> retrieveAllOwners() {
		log.info("Calling All Owners! Calling All Owners!");
		return dvdCatalogueService.retrieveAllOwners();
	}
	
	@DeleteMapping ("/owner/{ownerId}")
	public Map <String,String> deleteOwnerById (@PathVariable Long ownerId){
		log.info("Owner with ID {} is being banished!", ownerId);
		dvdCatalogueService.deleteOwnerById(ownerId);
		
		return Map.of("message", "Abolishment of Owner with ID " + ownerId + " has been completed");
		
	}
	
	
}
