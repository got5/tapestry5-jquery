package org.got5.tapestry5.jquery.test.pages.docs.mecanism;

import java.util.ArrayList;
import java.util.List;

import org.got5.tapestry5.jquery.utils.JQueryTabData;

public class Services
{
	public List<JQueryTabData> getListTabData()
	{
				
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
        listTabData.add(new JQueryTabData("Services","block1"));
        return listTabData;
	}
}
