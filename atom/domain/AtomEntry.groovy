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

class AtomEntry{
    private def RFC3339 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    private def RFC3339alternate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    
    //mandatory elements
    String id
    String title
    String updated
    
    //recommended elements
    List authors=[] //each author is a map of name, uri and email
    String content
    def links=[] //each link is a map of href, type, hreflang, title,length
    String summary
	
    //optional elements
    String category
    def contributors=[] //each contributor is a map of name, uri and email
    AtomFeed source
    String rights
    String published
    
    // date conversion getters
    Date getUpdated() { 
        try{
            return RFC3339.parse(updated)
        }catch(java.text.ParseException e) {
            //use numerical timezone offset.
            //However ATOM uses : in the timezone which is not compatible with SimpleDateFormat Z
            //eg. 2003-12-13T08:29:29-04:00
            return RFC3339alternate.parse(updated.substring(0, 22) + updated.substring(23, 25))
        }
    }
    Date getPublished() { 
        try{
            return RFC3339.parse(published)
        }catch(java.text.ParseException e) {
            //use numerical timezone offset.
            //However ATOM uses : in the timezone which is not compatible with SimpleDateFormat Z
            //eg. 2003-12-13T08:29:29-04:00
            return RFC3339alternate.parse(published.substring(0, 22) + published.substring(23, 25))
        }
    }       
}