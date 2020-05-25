/**
 * Spring Boot DAO Implementation class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.foodapi.rest.exception.NullResponseException;
import com.foodapi.rest.model.Food;
import com.foodapi.rest.model.FoodRequest;
import com.foodapi.rest.model.NutritionResponse;
import com.foodapi.rest.model.RecipeResponse;
import com.google.protobuf.ByteString;

@Repository
public class FoodDAOImpl implements FoodDAO {

	private static Logger LOGGER = LoggerFactory.getLogger(FoodDAOImpl.class);

	/**
	 * Returns List of food items identified by Google's Vision API
	 * 
	 * @throws NullResponseException if Image Path provided in request body is null
	 * 
	 * @param FoodRequest Object of class FoodRequest containing API Key & Image
	 *                    Path is provided in the object
	 * @return String containing all the objects identified from the image, space
	 *         separated
	 */
	@Override
	public String getFoodIdentification(FoodRequest foodRequest) throws NullResponseException {

		String identifiedFood = null;

		try {

			if (foodRequest.getImagePath() != null) {

				// The path to the image file to annotate
				String fileName = foodRequest.getImagePath();

				LOGGER.info("File name passed: {}", fileName);

				// Reads the image file into memory
				Path path = Paths.get(fileName);

				byte[] data = Files.readAllBytes(path);

				ByteString imgBytes = ByteString.copyFrom(data);

				// Calling Google's Vision API and fetching Labels
				identifiedFood = VisionAPIClient.identifyImageVisionAPI(imgBytes);

			} else {
				throw new NullResponseException("Image Path provided in request body is null");
			}

		} catch (IOException e) {
			LOGGER.error("IO Exception while reading image: {}", e.getMessage());
		}

		return identifiedFood;
	}

	/**
	 * Returns Object of NutritionResponse class containing all the Nutrition
	 * Contents of the food item provided
	 * 
	 * @throws NullResponseException if Identified food provided in request is null
	 * @param String of food items previously identified by Vision API
	 * @return Object of NutritionResponse class containing all the Nutrition
	 */
	@Override
	public NutritionResponse getNutritionContents(String identifiedFood) throws NullResponseException {

		NutritionResponse nutritionResp;

		if (identifiedFood != null) {

			// Fetching Nutrition values from NutritionAPI by passing food list obtained
			// from Vision API LABEL DETECTION
			nutritionResp = NutritionAPIClient.fetchNutritionValue(identifiedFood);

			if (nutritionResp.getFoods() == null)
				throw new NullResponseException("No Food item identified corresponding to the image provided");

		} else {
			throw new NullResponseException("Identified food provided in request body is null");
		}

		return nutritionResp;
	}

	/**
	 * Returns Object of RecipeResponse class containing all the Recipes
	 * 
	 * @throws NullResponseException if Nutrition Response body is empty
	 * @param Object of NutritionResponse to pick top identified food item by the
	 *               Nutrition API
	 * @return Object of RecipeResponse class containing all the Recipes
	 */
	@Override
	public RecipeResponse getRecipe(NutritionResponse nutritionResponse) throws NullResponseException {

		RecipeResponse recipeResponse;

		if (nutritionResponse.getFoods() != null) {

			// Fetching food details for each food item identified by Nutrition API
			Food[] food = nutritionResponse.getFoods();

			// Fetching Recipe for the first item identified by Nutrition API
			recipeResponse = RecipeAPIClient.fetchRecipe(food[0].getFood_name());

		} else {
			throw new NullResponseException("Nutrition Response body is empty");
		}

		return recipeResponse;
	}

}
