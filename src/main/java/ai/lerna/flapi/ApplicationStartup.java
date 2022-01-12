/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai.lerna.flapi;

import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.manager.FLManager;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.service.StorageService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ApplicationStartup 
implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private FLManager flManager;
  /**
   * This event is executed as late as conceivably possible to indicate that 
   * the application is ready to service requests.
	 * @param event
   */
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
	Logger.getLogger(this.getClass().getName()).log(Level.INFO, "LernaFL v0.12");
	
		try {
			flManager.startup();
		} catch (Exception ex) {
			Logger.getLogger(ApplicationStartup.class.getName()).log(Level.SEVERE, null, ex);
		}
	
	
 
    
  }
 
} // class