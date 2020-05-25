/**
 * Spring Boot Rest client class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.foodapi.rest.exception.NullResponseException;
import com.foodapi.rest.model.RecipeResponse;
import com.foodapi.rest.util.Constants;
import com.google.api.gax.rpc.UnauthenticatedException;

public class RecipeAPIClient {

	private static Logger LOGGER = LoggerFactory.getLogger(RecipeAPIClient.class);

	private RecipeAPIClient() {
		// Controlled Access
		super();
	}

	/**
	 * Returns Object of RecipeResponse class containing all recipes of the food
	 * identified from the image by Vision API
	 * 
	 * @param String containing the food item first identified by Nutrition API only
	 * @return Object of RecipeResponse class containing Recipes of food items
	 *         identified by Vision API
	 * @throws NullResponseException
	 */
	public static RecipeResponse fetchRecipe(String foodItem) throws NullResponseException {

		RecipeResponse recipeResp = new RecipeResponse();

		try {

			String url = Constants.RECIPE_API_BASE_URL + "?query=" + foodItem + "&apiKey=" + Constants.RECIPE_KEY;

			LOGGER.info("Recipe API Base URI: {}", Constants.RECIPE_API_BASE_URL);

			// Using Spring MVC's RestTemlate to call GET request to Nutrition API
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = url;
			URI uri = new URI(baseUrl);

			// Creating request headers to send in GET Request
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

			Map<String, Object> map = new HashMap<>();

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

			// Capturing the response in RecipeResponse POJO
			ResponseEntity<RecipeResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request,
					RecipeResponse.class);

			recipeResp = response.getBody();

			LOGGER.info("Response from Recipe API: {}", recipeResp);

		} catch (URISyntaxException e) {
			LOGGER.info("Invalid POST URI provided - {}", e.getMessage());
			throw new NullResponseException(Constants.SERVER_ERROR_MESSAGE);
		} catch (Unauthorized e) {
			LOGGER.info("Invalid Credentials provided - {}", e.getMessage());
			throw new NullResponseException(Constants.SERVER_ERROR_MESSAGE);
		} catch (UnauthenticatedException e) {
			LOGGER.info("Invalid Credentials provided- {}", e.getMessage());
			throw new NullResponseException(Constants.SERVER_ERROR_MESSAGE);
		} catch (HttpClientErrorException e) {
			LOGGER.info("Client Error occured - {}", e.getMessage());
			throw new NullResponseException(Constants.SERVER_ERROR_MESSAGE);
		} catch (HttpServerErrorException e) {
			LOGGER.info("Server Error occured - {}", e.getMessage());
			throw new NullResponseException(Constants.SERVER_ERROR_MESSAGE);
		}

		return recipeResp;

	}

}
