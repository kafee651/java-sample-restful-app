package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @CrossOrigin
  @RequestMapping("/greeting")
  public Greeting[] greeting(@RequestParam(value="name", defaultValue="World") String name) {
    Greeting[] greetingar = new Greeting[2];
    greetingar[0] = new Greeting(counter.incrementAndGet(),String.format(template, name));
    greetingar[1] = new Greeting(counter.incrementAndGet(),String.format(template, name));
    return greetingar;
  }
  @CrossOrigin
  @RequestMapping("/profilelist")
  public Profile[] profileList(@RequestParam(value="name", defaultValue="uddin") String name){
    Profile[] profiles = new Profile[5];
    profiles[0] = new Profile("Mohammad", "Kafee");
    profiles[1] = new Profile("Mohammad", "Konain");
    profiles[2] = new Profile("Mohammad", "Sakif");
    profiles[3] = new Profile("Abu", "Zar");
    profiles[4] = new Profile("Abdus", "Salam");
    return profiles;
  }
}