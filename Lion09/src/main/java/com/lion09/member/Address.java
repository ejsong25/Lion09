package com.lion09.member;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {
	private String address; //동
	private String myLatitude; //위도
	private String myLongitude; //경도
}
