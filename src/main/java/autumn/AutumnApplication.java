package autumn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@Async
public class AutumnApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutumnApplication.class, args);
    }

}
