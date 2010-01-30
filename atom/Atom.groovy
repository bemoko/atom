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
 
package atom
 
import com.bemoko.live.platform.mwc.plugins.AbstractPlugin
import atom.domain.AtomEntry
import atom.domain.AtomFeed

class Atom extends AbstractPlugin {

	private def url	

	void initialise(Map map) {
		url=map.url
   }
	
	AtomFeed getFeed() {
        def feedResponse
        
        try{
            feedResponse = new XmlSlurper(false,false).parse(url)
    	}catch(Exception ex){
    	    // a failure could be caused by a fragile remote service
    	    // treat as a temporarily unavailable situation
    	    
    	    // to debug failures uncomment the line below
    	    //ex.printStackTrace()
    	    
            return null
        }
        
		AtomFeed feed=new AtomFeed(
            id :feedResponse.id,
            title :feedResponse.title,
            updated :feedResponse.updated,
            authors: feedResponse.author.list(),
            generator:['uri':feedResponse.generator.@uri.text(),'version':feedResponse.generator.@version.text(),'text':feedResponse.generator.text()],
            contributors: feedResponse.contributor.list(),
		  
            links:feedResponse.link.collect{
                ['href':it.@href.text(),'type':it.@type.text(),'rel':it.@rel.text(),'hreflang':it.@hreflang.text(),'title':it.@title.text(),'length':it.@length.text()]
            },
            category :feedResponse.category,
            icon :feedResponse.icon,
            logo :feedResponse.logo,
            rights :feedResponse.rights,
            subtitle :feedResponse.subtitle,
		)

         feedResponse.entry.each {  
            feed.entries += new AtomEntry(
                id :it.id,
                title :it.title,
                updated :it.updated,
                
                authors:it.author.collect{
                    ['name':it.name.text(),'uri':it.uri.text(),'email':it.email.text()]
                },
                content:it.content,
                summary:it.summary,
                
                links:it.link.collect{
                    ['href':it.@href.text(),'type':it.@type.text(),'rel':it.@rel.text(),'hreflang':it.@hreflang.text(),'title':it.@title.text(),'length':it.@length.text()]
                },
                category:it.category,
                contributors: it.contributor.collect{
                    ['name':it.name.text(),'uri':it.uri.text(),'email':it.email.text()]
                },
                rights: it.rights,
                published:it.published
             )
         }
		return feed		
	}  
}