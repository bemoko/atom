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

/*
 * Test implementation of atom feed processing against reference example available from http://www.atomenabled.org
 *
 * groovy atom/tests/AtomTest.groovy 
 */ 
package atom.tests

import groovy.util.GroovyTestCase
import atom.Atom

class AtomFeedTest extends GroovyTestCase{
    
    def feedSupply=new Atom()
    def testUrl="file:///"+new File(getClass().protectionDomain.codeSource.location.path).parent+"/AtomTestSample.xml"
    def feed
    
    void setUp() {
        feedSupply.initialise(['url':testUrl])
        feed=feedSupply.getFeed()
    }
    
    void testFeed() {
        assertNotNull("Feed does not contain any content",feed)
        assertEquals("Feed title is incorrect",'dive into mark',feed.title)
        assertEquals("Feed subtitle is incorrect",'A <em>lot</em> of effort went into making this effortless',feed.subtitle)
        assertEquals("Feed updated is incorrect",Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'","2005-07-31T12:29:29Z"),feed.updated)
        assertEquals("Feed id is incorrect",'tag:example.org,2003:3',feed.id)
        assertEquals("feed links count is incorrect",2,feed.links.size())
        assertEquals("Feed link rel is incorrect",'alternate',feed.links[0].rel)
        assertEquals("Feed link type is incorrect",'text/html',feed.links[0].type)
        assertEquals("Feed link hreflang is incorrect",'en',feed.links[0].hreflang)
        assertEquals("Feed link href is incorrect",'http://example.org/',feed.links[0].href)
        assertEquals("Feed rights is incorrect",'Copyright (c) 2003, Mark Pilgrim',feed.rights)
        assertEquals("Feed generator uri is incorrect",'http://www.example.com/',feed.generator.uri)
        assertEquals("Feed generator version is incorrect",'1.0',feed.generator.version)
        assertEquals("Feed generator text is incorrect",'Example Toolkit',feed.generator.text)
    }
    
    void testItems() {
        assertNotNull("Feed does not contain any content",feed)
        assertEquals("Feed has incorrect number of entries",2,feed.entries.size())
        
        def entry1=feed.entries[0]
        assertEquals("Entry title is incorrect",'Atom draft-07 snapshot',entry1.title)
        assertEquals("Entry has incorrect number of links",2,entry1.links.size())
        
        def link1=entry1.links[1]
        assertEquals("Entry link rel is incorrect",'enclosure',link1.rel)
        assertEquals("Entry link type is incorrect",'audio/mpeg',link1.type)
        assertEquals("Entry link length is incorrect",'1337',link1.length)
        assertEquals("Entry link href is incorrect",'http://example.org/audio/ph34r_my_podcast.mp3',link1.href)
        
        assertEquals("Entry id is incorrect",'tag:example.org,2003:3.2397',entry1.id)
        assertEquals("Entry updated is incorrect",Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'","2005-07-31T12:29:29Z"),entry1.updated)
        assertEquals("Entry published is incorrect",Date.parse("yyyy-MM-dd'T'HH:mm:ssZ","2003-12-13T08:29:29-0400"),entry1.published)
        
        assertEquals("Entry has incorrect number of authors",1,entry1.authors.size())
        assertEquals("Entry author name is incorrect",'Mark Pilgrim',entry1.authors[0].name)
        assertEquals("Entry author uri is incorrect",'http://example.org/',entry1.authors[0].uri)
        assertEquals("Entry author email is incorrect",'f8dy@example.com',entry1.authors[0].email)
        
        assertEquals("Entry has incorrect number of contributors",2,entry1.contributors.size())
        assertEquals("Entry contributor name is incorrect",'Sam Ruby',entry1.contributors[0].name)
        
        assertTrue("Entry content is incorrect",entry1.content.contains('The Atom draft is finished'))
    }
}