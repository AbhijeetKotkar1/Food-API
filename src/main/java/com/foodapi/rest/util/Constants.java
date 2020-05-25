/**
 * Spring Boot Constants class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.util;

public class Constants {

	private Constants() {
		// Controlled Access
		super();
	}

	public static final String NUTRITION_API_BASE_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";
	public static final String RECIPE_API_BASE_URL = "https://api.spoonacular.com/recipes/search";

	public static final String TIMEZONE = "US/Eastern";

	public static final String NUTRITION_APP_ID = "";
	public static final String NUTRITION_KEY = "";
	public static final String RECIPE_KEY = "";
	public static final String SERVER_ERROR_MESSAGE = "Server Error, Please contact Administrator";

}
