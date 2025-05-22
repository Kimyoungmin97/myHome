package com.ssafy.home.user.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HouseResponse {
	private String aptSeq;
	private String aptNm;
	private String address;
	private String price;
	private double latitude;
	private double longitude;
	private int avgPriceAll;
	private int avgPriceNearPeak;
}
