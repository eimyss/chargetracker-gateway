package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.ProjectsClient;
import de.eimantas.edgeservice.controller.expcetions.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/projects")
public class ProjectsController {


    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectsClient projectsClient;

    @GetMapping("/get/all")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List> getAccountList() {
        logger.info("edge projects request");
        return projectsClient.getProjectList();
    }

    @GetMapping("/get/address/all")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List> getAddressList() {
        logger.info("edge addresses request");
        return projectsClient.getAllAddresses();
    }


    @GetMapping("/get/address/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity getAddressById(@PathVariable long id) {
        logger.info("edge address get request for id {0}", id);
        ResponseEntity addressById = projectsClient.getAddressById(id);
        logger.info("address id response: " + addressById.toString());
        return addressById;
    }

    @GetMapping("/get/{id}/address")
    @CrossOrigin(origins = "*")
    public ResponseEntity getProjectAddress(@PathVariable long id) {
        logger.info("edge project address for id {0}", id);
        ResponseEntity projectAddress = projectsClient.getProjectAddress(id);
        logger.info("address id response: " + projectAddress.toString());
        return projectAddress;
    }

    @GetMapping("/get/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity getProject(@PathVariable long id) {
        logger.info("edge project get request for id {0}", id);
        ResponseEntity project = projectsClient.getProjectById(id);
        logger.info("account id response: " + project.toString());
        return project;
    }


    @PostMapping("/save")
    @CrossOrigin(origins = "*")
    public ResponseEntity persistProject(@RequestBody Object project) throws BadRequestException {
        if (project != null) {
            logger.info("saving account : " + project.toString());
            return projectsClient.postProject(project);
        }

        logger.warn("passed object to save account is null");
        throw new BadRequestException("account is null");
    }

    public ResponseEntity fallback(Throwable e) {
        logger.warn("failed to fallback", e);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
