package com.codechallenge.f1.testserver;

import com.codechallenge.f1.dto.DriverFeign;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile({"local","test","dev"})
@RequestMapping("/f1-server-test")
@Slf4j
public class F1ServerTest {

  @Value("classpath:f1-server-test/alonso.json")
  private Resource alonsoFile;
  @Value("classpath:f1-server-test/raikkonen.json")
  private Resource raikkonenFile;
  @Value("classpath:f1-server-test/senna.json")
  private Resource sennaFile;

  private final ObjectMapper objectMapper;

  public F1ServerTest(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @GetMapping("/f1/drivers/{driver}.json")
  public ResponseEntity<DriverFeign> getDrivers(@PathVariable("driver") String driver )
      throws IOException {


    log.info("Entrando en el endpoint de test para f1/drivers/{}.json", driver);
    DriverFeign driverFeign;
    switch(driver.toLowerCase()){
      case "alonso":
        driverFeign = getDriver(alonsoFile);
        break;
      case "raikkonen":
        driverFeign = getDriver(raikkonenFile);
        break;
      case "senna":
        driverFeign = getDriver(sennaFile);
        break;

      default:
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().body(driverFeign);

  }

  private DriverFeign getDriver(Resource alonsoFile) throws IOException {
    return objectMapper.readValue(alonsoFile.getFile(), DriverFeign.class);
  }

}
