package org.got5.tapestry5.jquery.unit.spock

import org.apache.tapestry5.json.JSONObject
import org.got5.tapestry5.jquery.utils.JQueryUtils;

import spock.lang.Specification;
import spock.lang.Unroll

class TestJQueryUtilsMergeSpockTest extends Specification{
	
	@Unroll
	def "test the merge tool"() {
		when:
			JQueryUtils.merge(val1, val2)
			
		then:
			val1 == result
		
		where:
			val1													| val2													| result	
			null													| null													| null
			new JSONObject("value", "test")							| null													| new JSONObject("value", "test")
			new JSONObject("value", "test", "value2", "test2")		| new JSONObject("value", "test4", "value3", "test3")	| new JSONObject("value", "test4", "value2", "test2","value3", "test3")
	}
}
