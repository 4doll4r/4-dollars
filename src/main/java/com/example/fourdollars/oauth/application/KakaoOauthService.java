package com.example.fourdollars.oauth.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.fourdollars.member.domain.Member;
import com.example.fourdollars.member.infra.MemberRepository;
import com.example.fourdollars.oauth.application.dto.KakaoAccessToken;
import com.example.fourdollars.oauth.application.dto.KakaoUserInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class KakaoOauthService {

	@Value("${oauth.kakao.client_id}")
	private String KAKAO_CLIENT_ID;

	@Value("${oauth.kakao.client_secret}")
	private String KAKAO_CLIENT_SECRET;

	@Value("${oauth.kakao.redirect}")
	private String KAKAO_REDIRECT_URL;

	@Value("${oauth.kakao.url.login}")
	private String KAKAO_LOGIN_URL;

	@Value("${oauth.kakao.url.profile}")
	private String KAKAO_Profile_URL;

	private final MemberRepository memberRepository;

	// 인가코드 받기
	public String getAuthorizationCode() {
		StringBuilder requestUrl = new StringBuilder()
			 			.append(KAKAO_LOGIN_URL)
			 			.append("?client_id=").append(KAKAO_CLIENT_ID)
			 			.append("&redirect_uri=").append(KAKAO_REDIRECT_URL)
			 			.append("&response_type=code");

		log.info("get code url  = {}", requestUrl);

  		return "redirect:" + requestUrl;
	}

	// 토큰받기
	public String getToken(String code){

		WebClient webClient = WebClient.create("https://kauth.kakao.com");

		KakaoAccessToken response = webClient.post()
			.uri(uriBuilder -> uriBuilder
				.path("/oauth/token")
				.queryParam("grant_type", "authorization_code")
				.queryParam("client_id", KAKAO_CLIENT_ID)
				.queryParam("client_secret", KAKAO_CLIENT_SECRET)
				.queryParam("redirect_uri", KAKAO_REDIRECT_URL)
				.queryParam("code", code)
				.build())
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.retrieve().bodyToMono(KakaoAccessToken.class).block();


		log.info("token={}", response.getAccess_token());
		return response.getAccess_token();
	}

	// 사용자 정보 가져오기
	public void getUserProfile(String accessToken){
		WebClient webClient = WebClient.create();
		KakaoUserInfoResponse userResponse = webClient.get()
			.uri(KAKAO_Profile_URL)
			.headers(h -> h.setBearerAuth(accessToken))
			.retrieve()
			.bodyToMono(KakaoUserInfoResponse.class)
			.block();

		log.info("userProfile={}", userResponse);

		Optional<Member> member = memberRepository.findByEmail(userResponse.getKakaoAccount().getEmail());

		// ... 분기처리 (정보없으면 회원가입 및 jwt 발급) 있으면 로그인
	}
}
