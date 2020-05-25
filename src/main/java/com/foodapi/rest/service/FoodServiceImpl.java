/**
 * Spring Boot Service implementation class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodapi.rest.dao.FoodDAO;
import com.foodapi.rest.exception.NullResponseException;
import com.foodapi.rest.model.FoodRequest;
import com.foodapi.rest.model.NutritionResponse;
import com.foodapi.rest.model.RecipeResponse;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodDAO foodDAO;

	@Override
	public String getFoodIdentification(FoodRequest foodRequest) throws NullResponseException {
		return foodDAO.getFoodIdentification(foodRequest);
	}

	@Override
	public NutritionResponse getNutritionContents(String identifiedFood) throws NullResponseException {
		return foodDAO.getNutritionContents(identifiedFood);
	}

	@Override
	public RecipeResponse getRecipe(NutritionResponse nutritionResponse) throws NullResponseException {
		return foodDAO.getRecipe(nutritionResponse);
	}

}
