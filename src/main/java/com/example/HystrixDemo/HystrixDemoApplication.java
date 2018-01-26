package com.example.HystrixDemo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.retry.annotation.EnableRetry;
//import org.springframework.retry.annotation.Recover;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * I've used Hystrix in this example however I've left commented code
 * for another circuit breaker using Retryable.
 */
//@EnableRetry
@EnableCircuitBreaker
@SpringBootApplication
public class HystrixDemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(HystrixDemoApplication.class, args);
  }
}

@RestController
class MoesRestController {
  private final MoesService moesService;

  @Autowired
  public MoesRestController(MoesService moesService) {
      this.moesService = moesService;
  }

  @GetMapping("/helloworld")
  public int helloworld() throws InterruptedException {
    return this.moesService.generateNumber();
  }
}

@Service
class MoesService {

  /**
   * fallback() with RuntimeException will execute when generateNumber()
   * throws the RuntimeException.
   * @param ex
   * @return
   */
//  @Recover
//  public int fallback(RuntimeException ex) {
//    return 2;
//  }

  public int fallback() {
    return 2;
  }

//  @Retryable
  @HystrixCommand(fallbackMethod = "fallback")
  int generateNumber() throws InterruptedException {
    if (Math.random() > .5) {
      Thread.sleep(1000 * 5);
      throw new RuntimeException("How about, no, scott");
    }
    return 1;
  }


}
