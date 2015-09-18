package org.got5.tapestry5.jquery.data;

import java.util.ArrayList;
import java.util.List;

public class MockDataSource implements IDataSource {
	
	private List<Celebrity> celebrities = new ArrayList<Celebrity>();
	private List<Celebrity> init = new ArrayList<Celebrity>();
	

	public MockDataSource() {
		if(init.size()==0)
		{	long id=0;
			init.add(new Celebrity(id++,"Britney", "Spearce", Formats.parseDate("12/02/1981"), Occupation.SINGER));
			init.add(new Celebrity(id++,"Bill", "Clinton", Formats.parseDate("08/19/1946"), Occupation.POLITICIAN));
			init.add(new Celebrity(id++,"Placido", "Domingo", Formats.parseDate("01/21/1941"), Occupation.SINGER));
			init.add(new Celebrity(id++,"Albert", "Einstein", Formats.parseDate("03/14/1879"), Occupation.SCIENTIST));
			init.add(new Celebrity(id++,"Ernest", "Hemingway", Formats.parseDate("07/21/1899"), Occupation.WRITER));
			init.add(new Celebrity(id++,"Luciano", "Pavarotti", Formats.parseDate("10/12/1935"), Occupation.SINGER));
			init.add(new Celebrity(id++,"Ronald", "Reagan", Formats.parseDate("02/06/1911"), Occupation.POLITICIAN));
			init.add(new Celebrity(id++,"Pablo", "Picasso", Formats.parseDate("10/25/1881"), Occupation.ARTIST));
			init.add(new Celebrity(id++,"Blaise", "Pascal", Formats.parseDate("06/19/1623"), Occupation.SCIENTIST));
			init.add(new Celebrity(id++,"Isaac", "Newton", Formats.parseDate("01/04/1643"), Occupation.SCIENTIST));
			init.add(new Celebrity(id++,"Antonio", "Vivaldi", Formats.parseDate("03/04/1678"), Occupation.COMPOSER));
			init.add(new Celebrity(id++,"Niccolo", "Paganini", Formats.parseDate("10/27/1782"), Occupation.MUSICIAN));
			init.add(new Celebrity(id++,"Johannes", "Kepler", Formats.parseDate("12/27/1571"), Occupation.SCIENTIST));
			init.add(new Celebrity(id++,"Franz", "Kafka", Formats.parseDate("07/03/1883"), Occupation.WRITER));
			init.add(new Celebrity(id++,"George", "Gershwin", Formats.parseDate("09/26/1898"), Occupation.COMPOSER));
		}
		celebrities.addAll(init);
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
	
	public void deleteCelebrity(long id) {
			Celebrity c = getCelebrityById(id);
			celebrities.remove(c);
			init.remove(c);
		}

	public List<Celebrity> getRange(int indexFrom, int indexTo) {
		List<Celebrity> result = new ArrayList<Celebrity>();
		for (int i = indexFrom; i <= indexTo; i++) {
			result.add(celebrities.get(i));
		}
		return result;
	}
	
	public void filter(String value) {
		List<Celebrity> result = new ArrayList<Celebrity>();
		celebrities = new ArrayList<Celebrity>(init);
		if(value==null || value.length()==0) {
			return;
		}
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
