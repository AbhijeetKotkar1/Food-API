/**
 * Spring Boot Rest client class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.dao;

import com.foodapi.rest.exception.NullResponseException;
import com.foodapi.rest.util.Constants;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisionAPIClient {

	private VisionAPIClient() {
		// Controlled Access
		super();
	}

	private static Logger LOGGER = LoggerFactory.getLogger(VisionAPIClient.class);

	/**
	 * Returns String containing all labels identified by Vision API, space
	 * separated
	 * 
	 * @param ByteString of the image to be provided to Vision API as input
	 * @return String containing all labels identified by Vision API, space
	 *         separated
	 * @throws NullResponseException
	 */
	public static String identifyImageVisionAPI(ByteString imgBytes) throws NullResponseException {

		StringBuilder sb = new StringBuilder();

		// Instantiates a client
		try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

			// Builds the image annotation request
			List<AnnotateImageRequest> requests = new ArrayList<>();
			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			// Performs label detection on the image file
			BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					LOGGER.error("Error: {} %n", res.getError().getMessage());
				}

				// Creating a string consisting of all labels, space separated
				for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
					sb.append(annotation.getDescription());
					sb.append(" ");
				}
			}

			LOGGER.info("Food Labels detected: {}", sb);

		} catch (IOException e) {
			LOGGER.error("IO Exception while reading image: {}", e.getMessage());
			throw new NullResponseException(Constants.SERVER_ERROR_MESSAGE);

		}

		return sb.toString();

	}

}
