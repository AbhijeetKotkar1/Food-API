/**
 * Spring Boot Entity class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.model;

import lombok.Data;

@Data
public class Photo {

	private String thumb;
	private String highres;
	private boolean is_user_uploaded;

}
