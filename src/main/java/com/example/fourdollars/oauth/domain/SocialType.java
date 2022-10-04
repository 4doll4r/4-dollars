package com.example.fourdollars.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SocialType {
	Kakao("kakao", "kakao"),
	Apple("apple", "apple");

	private String key;
	private String detail;
}
