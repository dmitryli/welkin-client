package me.limonov.client.assessment;

import me.limonov.client.token.AccessToken;
import me.limonov.client.token.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AssessmentService {

    Logger log = LoggerFactory.getLogger(AssessmentService.class);

    @Value("${welkin.assessment.response.collection}")
    private String assessmentCollectionUrl;

    @Value("${welkin.assessment.response}")
    private String assessmentUrl;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RestTemplate restTemplate;

    public void findAssessments() {
        AccessToken at = jwtService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", at.getAccessToken()));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                assessmentCollectionUrl, HttpMethod.GET, entity, String.class);

        log.info("Assessment collection");
    }
    public String getAssessmentData(String assessmentId) {
        AccessToken at = jwtService.getAccessToken();

        String url = String.format(assessmentUrl, assessmentId);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", at.getAccessToken()));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
