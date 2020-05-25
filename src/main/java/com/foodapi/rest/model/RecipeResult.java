/**
 * Spring Boot Entity class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.model;

import lombok.Data;

@Data
public class RecipeResult {

	private int id;
	private String title;
	private int readyInMinutes;
	private int servings;
	private String sourceUrl;
	private int openLicense;
	private String image;

}
