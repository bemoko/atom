/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2010 bemoko 
 */
 
package atom.domain
import java.text.SimpleDateFormat

class AtomFeed{
	private def RFC3339 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	private def RFC3339alternate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    
	List entries=[]

	//mandatory elements
	String id
	String title
	String updated

	Date getUpdated() { 
	   try{
	       return RFC3339.parse(updated)
	   }catch(java.text.ParseException e) {
	       return RFC3339alternate.parse(updated)
	   }
	}

	//recommended elements
	List links=[] //each link is a map of href, type, hreflang, title,length
	List authors=[] //each author is a map of name, uri and email

	//Optional elements
	String category
	List contributors=[] //each contributor is a map of name, uri and email
	def generator = [:] //generator is a map of uri, version, text
	String icon
	String logo
	String rights
	String subtitle
}