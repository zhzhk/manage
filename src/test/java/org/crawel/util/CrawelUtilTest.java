package org.crawel.util;

import java.io.IOException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.crawel.util.CrawelUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application.xml"})
public class CrawelUtilTest {
	@Autowired
	private CrawelUtil crawelUtil;

	public void setCrawelUtil(CrawelUtil crawelUtil) {
		this.crawelUtil = crawelUtil;
	}

	@Test
	public void test() throws ClientProtocolException, IOException{
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
//		UrlData urlData = urlDao.getUrlData();
//		System.out.println(urlData.getWeb_site());
		crawelUtil.LoginAction();
	}
	
	@Test
	public void testCrawelData() throws ClientProtocolException, IOException, ParseException{
		crawelUtil.CrawelData();
	}
}
