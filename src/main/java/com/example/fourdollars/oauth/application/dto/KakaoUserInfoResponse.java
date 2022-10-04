package com.example.fourdollars.oauth.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KakaoUserInfoResponse {
	private Long id;
	private KakaoAccount kakaoAccount;
	private Properties properties;

	@NoArgsConstructor
	@Getter
	public static class KakaoAccount{
		private String email;
	}

	@NoArgsConstructor
	@Getter
	public static class Properties{
		private String nickname;
	}
}


