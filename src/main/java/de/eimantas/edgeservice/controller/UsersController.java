package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.UsersClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {


  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UsersClient usersClient;

  @GetMapping("/me")
  @CrossOrigin(origins = "*")
  public ResponseEntity<?> getCurrentUser() {
    logger.info("edge current user request");
    ResponseEntity current = usersClient.getCurrentUser();
    logger.info("account list response: " + current.toString());
    return current;
  }


  @GetMapping("/get/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getUserFromId(@PathVariable String id) {
    logger.info("edge account overview request");
    ResponseEntity response = usersClient.getUserBykeykloackId(id);
    logger.info("expenses count: " + response.toString());
    return response;
  }

  @PostMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity persistAccount(@RequestBody Object userDTO) {
    logger.info("saving account : " + userDTO.toString());
    ResponseEntity response = usersClient.saveUser(userDTO);
    return response;
  }
  
  public ResponseEntity fallback(Throwable e) {
    logger.warn("faLLING BACK on get expenses");
    e.printStackTrace();
    logger.warn("failed to fallback", e);
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
