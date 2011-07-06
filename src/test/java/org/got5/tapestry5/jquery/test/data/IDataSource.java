package org.got5.tapestry5.jquery.test.data;

import java.util.List;

import org.got5.tapestry5.jquery.test.data.Celebrity;

public interface IDataSource {
	public List<Celebrity> getAllCelebrities();
    public Celebrity getCelebrityById(long id);
    public void addCelebrity(Celebrity c);
    public List<Celebrity> getRange(int indexFrom, int indexTo);
}
