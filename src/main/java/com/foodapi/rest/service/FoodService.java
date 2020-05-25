/**
 * Spring Boot Service interface
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.service;

import com.foodapi.rest.exception.NullResponseException;
import com.foodapi.rest.model.FoodRequest;
import com.foodapi.rest.model.NutritionResponse;
import com.foodapi.rest.model.RecipeResponse;

public interface FoodService {
	
	public String getFoodIdentification(FoodRequest foodRequest) throws NullResponseException;

	public NutritionResponse getNutritionContents(String identifiedFood) throws NullResponseException;

	public RecipeResponse getRecipe(NutritionResponse nutritionResponse) throws NullResponseException;
	
}
