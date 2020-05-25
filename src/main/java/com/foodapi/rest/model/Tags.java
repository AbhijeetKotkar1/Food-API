/**
 * Spring Boot Entity class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.model;

import lombok.Data;

@Data
public class Tags {

	private String item;
	private String measure;
	private double quantity;
	private int food_group;
	private int tag_id;

}
