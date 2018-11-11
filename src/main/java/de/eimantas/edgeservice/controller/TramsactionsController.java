package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.TransactionsClient;
import de.eimantas.edgeservice.controller.expcetions.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transactions")
public class TramsactionsController {


  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private TransactionsClient transactionsClient;

  @GetMapping("/get/all")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getAccountList() {
    logger.info("edge transactions request");
    return transactionsClient.getAllTransactions();
  }


  @GetMapping("/get/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getTransaction(@PathVariable long id) {
    logger.info("edge transactions get request for id {0}", id);
    ResponseEntity transaction = transactionsClient.getTransaction(id);
    logger.info("transactions id response: " + transaction.toString());
    return transaction;
  }


  @GetMapping("/get/{type}/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getTransactionByEntityRef(@PathVariable(name = "type") String type, @PathVariable(name = "id") long id) throws BadRequestException {
    logger.info("get transaction by type and id");
    return transactionsClient.getTransactionByEntityAndType(type, id);
  }

  public ResponseEntity fallback(Throwable e) {
    logger.warn("failed to fallback", e);
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
