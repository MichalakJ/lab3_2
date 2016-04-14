/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.NewsReader;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Matchers.any;
/**
 *
 * @author Kuba
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { ConfigurationLoader.class, NewsReaderFactory.class } )
public class NewsLoaderTest {
    ConfigurationLoader configLoader;
    NewsReaderFactory newsReaderFactory;
    Configuration config;
    NewsReader newsReader;
    @Before
    public void setUpForTest() throws Exception {
        mockStatic(ConfigurationLoader.class);
	mockStatic(NewsReaderFactory.class);
        
        configLoader = mock(ConfigurationLoader.class);
	newsReaderFactory = mock(NewsReaderFactory.class);
	config = mock(Configuration.class);
	newsReader = mock(NewsReader.class);

	when(ConfigurationLoader.getInstance()).thenReturn(configLoader);
	when(configLoader.loadConfiguration()).thenReturn(config); 
        when(NewsReaderFactory.getReader(any(String.class))).thenReturn(newsReader);
                
        }
    
    @Test
    public void givenTwoNewsWithNoSubsciption_whenLoadNews_addPublicInfoCalledTwoTimes(){
        IncomingNews news = new IncomingNews();
        news.add(new IncomingInfo("asd", SubsciptionType.NONE));
        news.add(new IncomingInfo("bles", SubsciptionType.NONE));
        when(newsReader.read()).thenReturn(news);
        NewsLoader newsLoader = new NewsLoader();
        PublishableNews result = newsLoader.loadNews();
        
        assertThat(result.getPublicContent().size(), equalTo(2));
        
        
    }
}
