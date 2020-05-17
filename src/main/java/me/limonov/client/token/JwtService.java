package me.limonov.client.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class JwtService {
    Logger log = LoggerFactory.getLogger(JwtService.class);

    @Value("${welkin.client.id}")
    private String clientId;
    @Value("${welkin.client.secret}")
    private String clientSecret;
    @Value("${welkin.token.endpoint}")
    private String tokenEndpoint;

    @Autowired
    private RestTemplate restTemplate;


    //{
//    "iss": "bbd75730-2d82-4d9e-8075-a61d4e5cd02b",
//    "aud" : "https://api.welkinhealth.com/v1/token",
//    "exp" : "1589571582",
//    "scope": "all"
//}
    public String buildToken()  {

        String result = null;
        try {
             result = Jwts.builder().
                    setIssuer(clientId)
                    .setAudience(tokenEndpoint)
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .claim("scope", "all")
                    .signWith(SignatureAlgorithm.HS256,
                            clientSecret.getBytes("UTF-8"))
                    .compact();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("JWT Token: {}", result);

        return result;
    }

    public AccessToken getAccessToken() {
        String accessToken = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("assertion", buildToken());
        map.add("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<AccessToken> response = restTemplate.postForEntity( tokenEndpoint, request , AccessToken.class );

        log.info("Response status code {}", response.getStatusCode());
        return response.getBody();

    }
}
