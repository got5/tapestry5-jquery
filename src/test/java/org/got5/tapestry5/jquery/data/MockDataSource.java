package org.got5.tapestry5.jquery.data;

import java.util.ArrayList;
import java.util.List;

public class MockDataSource implements IDataSource {
	private List<Celebrity> celebrities = new ArrayList<Celebrity>();

	public MockDataSource() {
		addCelebrity(new Celebrity("Britney", "Spearce", Formats
				.parseDate("12/02/1981"), Occupation.SINGER));
		addCelebrity(new Celebrity("Bill", "Clinton", Formats
				.parseDate("08/19/1946"), Occupation.POLITICIAN));
		addCelebrity(new Celebrity("Placido", "Domingo", Formats
				.parseDate("01/21/1941"), Occupation.SINGER));
		addCelebrity(new Celebrity("Albert", "Einstein", Formats
				.parseDate("03/14/1879"), Occupation.SCIENTIST));
		addCelebrity(new Celebrity("Ernest", "Hemingway", Formats
				.parseDate("07/21/1899"), Occupation.WRITER));
		addCelebrity(new Celebrity("Luciano", "Pavarotti", Formats
				.parseDate("10/12/1935"), Occupation.SINGER));
		addCelebrity(new Celebrity("Ronald", "Reagan", Formats
				.parseDate("02/06/1911"), Occupation.POLITICIAN));
		addCelebrity(new Celebrity("Pablo", "Picasso", Formats
				.parseDate("10/25/1881"), Occupation.ARTIST));
		addCelebrity(new Celebrity("Blaise", "Pascal", Formats
				.parseDate("06/19/1623"), Occupation.SCIENTIST));
		addCelebrity(new Celebrity("Isaac", "Newton", Formats
				.parseDate("01/04/1643"), Occupation.SCIENTIST));
		addCelebrity(new Celebrity("Antonio", "Vivaldi", Formats
				.parseDate("03/04/1678"), Occupation.COMPOSER));
		addCelebrity(new Celebrity("Niccolo", "Paganini", Formats
				.parseDate("10/27/1782"), Occupation.MUSICIAN));
		addCelebrity(new Celebrity("Johannes", "Kepler", Formats
				.parseDate("12/27/1571"), Occupation.SCIENTIST));
		addCelebrity(new Celebrity("Franz", "Kafka", Formats
				.parseDate("07/03/1883"), Occupation.WRITER));
		addCelebrity(new Celebrity("George", "Gershwin", Formats
				.parseDate("09/26/1898"), Occupation.COMPOSER));
	}

	public List<Celebrity> getAllCelebrities() {
		return celebrities;
	}

	public Celebrity getCelebrityById(long id) {
		for (Celebrity c : celebrities) {
			if (c.getId() == id)
				return c;
		}
		return null;
	}

	public void addCelebrity(Celebrity c) {
		long newId = celebrities.size();
		c.setId(newId);
		celebrities.add(c);
	}

	public List<Celebrity> getRange(int indexFrom, int indexTo) {
		List<Celebrity> result = new ArrayList<Celebrity>();
		for (int i = indexFrom; i <= indexTo; i++) {
			result.add(celebrities.get(i));
		}
		return result;
	}
	
	public void filter(String value) {
		celebrities = new MockDataSource().getAllCelebrities(); 
		if(value==null || value.length()==0) {
			return;
		}
		List<Celebrity> result = new ArrayList<Celebrity>();
		for (Celebrity c : celebrities) {
			if (c.getFirstName().toUpperCase().contains(value.toUpperCase()) || 
					c.getLastName().toUpperCase().contains(value.toUpperCase())||
					c.getOccupation().name().toUpperCase().contains(value.toUpperCase())){
				
				result.add(c);
			}
		}
		celebrities = result;
	}
}
