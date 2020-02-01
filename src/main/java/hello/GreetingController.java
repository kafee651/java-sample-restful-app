package hello;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream; 

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.GetModelOptions;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechModel;
import com.ibm.watson.speech_to_text.v1.model.SpeechModels;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.speech_to_text.v1.websocket.BaseRecognizeCallback;


import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

@RestController
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();
  SpeechRecognitionResults speechRecognitionResults = null;
  IamAuthenticator authenticator = new IamAuthenticator("F_DeO8U6y42r1dLd_ofJQEMBH96rRTFh5016CdoJKOv9");
	SpeechToText speechToText = new SpeechToText(authenticator);
	


  @CrossOrigin
  @RequestMapping("/greeting")
  public Greeting[] greeting(@RequestParam(value="name", defaultValue="World") String name) {
    Greeting[] greetingar = new Greeting[2];
    greetingar[0] = new Greeting(counter.incrementAndGet(),String.format(template, name));
    greetingar[1] = new Greeting(counter.incrementAndGet(),String.format(template, name));
    return greetingar;
  }
  @CrossOrigin
  @PostMapping("/uploadFile")
    public SpeechRecognitionResults uploadFile(@RequestParam("file") MultipartFile file) {
      speechToText.setServiceUrl("https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/c65fddb6-4ad7-4152-a2b9-b952b3b938fd");

        try {
            RecognizeOptions recognizeOptions = new RecognizeOptions.Builder()
              .audio(new BufferedInputStream(file.getInputStream()))
              .contentType("audio/wav")
              .model("en-US_BroadbandModel")
              .keywords(Arrays.asList("men", "women", "shirt", "slim","unisex","shirt"))
              .keywordsThreshold((float) 0.5)
              .maxAlternatives(3)
              .build();

          speechRecognitionResults = speechToText.recognize(recognizeOptions).execute().getResult();
            //System.out.println(speechRecognitionResults);
          } catch(IOException io){
            io.printStackTrace();
          }
          return speechRecognitionResults;
  
    }
  @CrossOrigin
  @PostMapping("/uploadImage")
    public ClassifiedImages uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
      IamOptions options = new IamOptions.Builder()
				.apiKey("IhlwwaNpSW-mgm7K0NHC5p5EHABra-nTnAm_B6fYmmj6")
				.build();
			VisualRecognition service = new VisualRecognition("2018-03-19", options);
			ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
				.imagesFile(new BufferedInputStream(file.getInputStream()))
				.imagesFilename("1003.jfif")
				.threshold((float) 0.6)
				.classifierIds(Arrays.asList("watson-visual-service_3166914"))
				.build();
			ClassifiedImages result = service.classify(classifyOptions).execute();
      System.out.println(result);
      return result;
  
    }
 
}
