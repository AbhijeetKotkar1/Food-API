/**
 * Spring Boot Controller class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodapi.rest.exception.NullResponseException;
import com.foodapi.rest.model.FoodRequest;
import com.foodapi.rest.model.ImageResponse;
import com.foodapi.rest.service.FoodService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class FoodController {

	@Autowired
	private FoodService foodService;

	/**
	 * Returns a Response in JSON format containing all the details of the Food
	 * item's image provided
	 * 
	 * @param FoodRequest Object of class FoodRequest containing API Key & Image
	 *                    Path is provided in the object
	 * @return Object of ImageResponse class is returned which contains Food Item,
	 *         Nutrition Values and Recipes associated with that Food item
	 */
	@ApiOperation(value = "Fetches food information from provided image")
	@PostMapping("/identifyFood")
	public ImageResponse foodInfo(@RequestBody FoodRequest foodRequest) throws NullResponseException {

		// Output JSON Response
		ImageResponse imageResponse = new ImageResponse();

		// Result food item obtained from Vision API LABEL DETECTION
		imageResponse.setFoodItem(
				foodService.getNutritionContents(foodService.getFoodIdentification(foodRequest)).getFoods()[0]
						.getFood_name());

		// Fetching Nutrition Values of the food item obtained from Vision API
		imageResponse.setNutritionValue(
				foodService.getNutritionContents(foodService.getFoodIdentification(foodRequest)).getFoods()[0]);

		// Fetching Recipe using the top food identified by NutritionAPI
		imageResponse.setRecipe(foodService
				.getRecipe(foodService.getNutritionContents(foodService.getFoodIdentification(foodRequest))));

		return imageResponse;

	}

}
