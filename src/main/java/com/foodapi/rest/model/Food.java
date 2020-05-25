/**
 * Spring Boot Entity class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.model;

import lombok.Data;

@Data
public class Food {

	private String food_name;
	private String brand_name;
	private int serving_qty;
	private String serving_unit;
	private double serving_weight_grams;
	private double nf_calories;
	private double nf_total_fat;
	private double nf_saturated_fat;
	private double nf_cholesterol;
	private double nf_sodium;
	private double nf_total_carbohydrate;
	private double nf_dietary_fiber;
	private double nf_sugars;
	private double nf_protein;
	private double nf_potassium;
	private double nf_p;
	private Nutrients[] full_nutrients;
	private String nix_brand_name;
	private String nix_brand_id;
	private String nix_item_name;
	private int nix_item_id;
	private String upc;
	private String consumed_at;
	private MetaData metadata;
	private int source;
	private int ndb_no;
	private Tags tags;
	private AltMeasure[] alt_measures;
	private String lat;
	private String lng;
	private String meal_type;
	private Photo photo;
	private String sub_recipe;

}
