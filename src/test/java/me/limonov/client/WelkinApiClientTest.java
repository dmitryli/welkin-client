package me.limonov.client;

import io.jsonwebtoken.lang.Assert;
import me.limonov.client.assessment.AssessmentService;
import me.limonov.client.token.AccessToken;
import me.limonov.client.token.JwtService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WelkinApiClientTest {

	@Autowired
	JwtService service;

	@Autowired
	AssessmentService assessmentService;
//
//	@Test
//	void jwtToken() {
//		Assert.notNull(service);
//
//		AccessToken accessToken = service.getAccessToken();
//
//		Assert.notNull(accessToken);
//
//		System.out.println(accessToken.getAccessToken());
//	}

//	@Test
//	void assessmentService() {
//		Assert.notNull(assessmentService);
//		String assessmentId = "c7944b4c-0c72-42a9-a21a-8224a20e9f85";
//
//		String data = assessmentService.getAssessmentData(assessmentId);
//
//		System.out.println(data);
//	}

	@Test
	void assessmentService() {
		Assert.notNull(assessmentService);

		assessmentService.findAssessments();

	}
}
