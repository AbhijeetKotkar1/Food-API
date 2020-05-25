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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.foodapi.rest.exception.NullResponseException;
import com.foodapi.rest.model.NutritionResponse;
import com.foodapi.rest.util.Constants;
import com.google.api.gax.rpc.UnauthenticatedException;

public class NutritionAPIClient {

	private static Logger LOGGER = LoggerFactory.getLogger(NutritionAPIClient.class);

	private NutritionAPIClient() {
		// Controlled Access
		super();
	}

	/**
	 * Returns Object of NutritionResponse class containing Nutrition Values of food
	 * items identified by Vision API
	 * 
	 * @param String containing the food items, space separated
	 * @return Object of NutritionResponse class containing Nutrition Values of food
	 *         items identified by Vision API
	 * @throws NullResponseException
	 */
	public static NutritionResponse fetchNutritionValue(String foodItem) throws NullResponseException {

		NutritionResponse nutriResponse = new NutritionResponse();

		try {

			// Using Spring MVC's RestTemlate to call POST request to Nutrition API
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = Constants.NUTRITION_API_BASE_URL;
			URI uri = new URI(baseUrl);

			LOGGER.info("Nutrition API Base URI: {}", baseUrl);

			// Creating Request headers to send in POST Request
			HttpHeaders headers = new HttpHeaders();
			headers.set("x-app-id", Constants.NUTRITION_APP_ID);
			headers.set("x-app-key", Constants.NUTRITION_KEY);
			headers.set("Content-Type", "application/json");

			// Passing POST Request Body
			Map<String, Object> map = new HashMap<>();
			map.put("query", foodItem);
			map.put("timezone", Constants.TIMEZONE);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

			// Capturing the response in NutritionResponse POJO
			ResponseEntity<NutritionResponse> response = restTemplate.postForEntity(uri, request,
					NutritionResponse.class);

			nutriResponse = response.getBody();

			LOGGER.info("Response from Nutrition API: {}", nutriResponse);

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

		return nutriResponse;

	}

}
