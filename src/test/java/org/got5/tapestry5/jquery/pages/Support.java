package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;


public class Support {
	static final private String[] ALL_MAKES = new String[] { "Honda", "Toyota" };
	static final private String[] NO_MODELS = new String[] {};
	static final private String[] HONDA_MODELS = new String[] { "Accord", "Civic", "Jazz" };
	static final private String[] TOYOTA_MODELS = new String[] { "Camry", "Corolla" };

	// Screen fields

	@Property
	private String carMake;

	@Property
	private String carModel;

	@Property
	private String keywords;

	@Property
	private String[] carMakes;

	@Property
	@SuppressWarnings("unused")
	private String[] carModels;

	// Other pages

	
	@InjectComponent
	private org.apache.tapestry5.corelib.components.Zone carModelZone;

	@Inject
	private ComponentResources componentResources;

	@Inject
	private Request request;

	// The code

	void setupRender() {
		if (carMakes == null) {
			carMakes = ALL_MAKES;
			carModels = NO_MODELS;
		}
	}

	Object onValueChangedFromCarMake(String carMake) {
		this.carMake = carMake;
		carMakes = ALL_MAKES;

		if (carMake == null) {
			carModels = NO_MODELS;
		}
		else if (carMake.equals(carMakes[0])) {
			carModels = HONDA_MODELS;
		}
		else if (carMake.equals(carMakes[1])) {
			carModels = TOYOTA_MODELS;
		}
		else {
			carModels = NO_MODELS;
		}
		carModel = null;

		return request.isXHR() ? carModelZone.getBody() : null;
	}

	Object onSuccess() {
		return Support.class;
	}

	Object onGoHome() {
		componentResources.discardPersistentFieldChanges();
		return Support.class;
	}
}
