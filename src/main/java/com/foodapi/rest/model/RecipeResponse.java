/**
 * Spring Boot Entity class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.model;

import lombok.Data;

@Data
public class RecipeResponse {

	private RecipeResult[] results;
	private String baseUri;
	private int offset;
	private int number;
	private int totalResults;
	private int processingTimeMs;
	private double expires;

}
