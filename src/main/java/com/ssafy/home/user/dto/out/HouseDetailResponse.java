package com.ssafy.home.user.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseDetailResponse {

	private int no;
	private String date;
	private int floor;
	private String excluUseAr;
	private int dealAmount;
	private String price;
}
