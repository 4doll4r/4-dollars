package com.example.fourdollars.oauth.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fourdollars.oauth.application.KakaoOauthService;
import com.example.fourdollars.oauth.application.dto.KakaoAccessToken;
import com.example.fourdollars.oauth.presentation.dto.JoinRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OauthController {

	private final KakaoOauthService kakaoOauth;

	@GetMapping("/oauth/kakao")
	public String getKakaoAuthorizationCode(){
		return kakaoOauth.getAuthorizationCode();
	}

	@GetMapping("/kakao/callback")
	public void getKakaoToken(@RequestParam("code") String code){
		kakaoOauth.getToken(code);
	}

	@GetMapping("/login/kakao")
	public void kakaoLogin(@RequestBody KakaoAccessToken kakaoAccessTokenDto){
		kakaoOauth.getUserProfile(kakaoAccessTokenDto.getAccess_token());
	}

	@GetMapping("/join")
	public void join(@RequestBody JoinRequestDto joinRequestDto){
	}


}
