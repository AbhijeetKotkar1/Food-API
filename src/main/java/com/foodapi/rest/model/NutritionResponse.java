/**
 * Spring Boot Entity class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.model;

import lombok.Data;

@Data
public class NutritionResponse {

	private Food[] foods;

}
