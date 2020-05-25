/**
 * Spring Boot Entity class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.model;

import lombok.Data;

@Data
public class AltMeasure {

	private int serving_weight;
	private String measure;
	private int seq;
	private int qty;
}
