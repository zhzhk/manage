package org.crawel.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.crawel.entity.GameData;
import org.crawel.entity.UrlData;
import org.crawel.service.UrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawelUtil {
	@Autowired
	private UrlDao urlDao;
	//ģ���¼����ȡuid
	public void LoginAction() throws ClientProtocolException, IOException{
		UrlData urlData = urlDao.getUrlData("1");
		String loginUsername = urlData.getUsername();
		String password = urlData.getPassword();
		String loginUrl = urlData.getWeb_site();
		String lanaguage = urlData.getLanguage();
		String uid = urlData.getUid();
		
		Response reponse = Request.Post(loginUrl)
		.bodyForm(Form.form().add("uid",uid).add("langx",lanaguage).add("mac","")
		.add("ver","").add("JE","").add("username",loginUsername).add("password",password)
		.add("checkbox","on").build()).execute();
		
		String uidString = reponse.returnContent().asString();
//		System.out.println(uidString);
		String pattern = "top.uid = \'(\\w+)\'";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(uidString);
		if(m.find()){
//			System.out.println(m.group(1));
			urlDao.updateUid(m.group(1), "2");
		}
		
	}
	
	public String CrawelData() throws ClientProtocolException, IOException, ParseException{
		UrlData urlData = urlDao.getUrlData("2");
		String crawelUrl = urlData.getWeb_site();
		String lanaguage = urlData.getLanguage();
		String uid = urlData.getUid();
		
		String dataUrl = crawelUrl+"?uid="+uid+"&rtype=r&langx="+lanaguage+"&mtype=3&page_no=0&league_id=&hot_game=undefined&sort_type=undefined&zbreload=1&l=ALL";
		
		Response rep = Request.Get(dataUrl).execute();
		String dataString = rep.returnContent().asString();
		
		String pattern1 = "parent.GameHead=new Array\\((.+)\\);";
		Pattern r1 = Pattern.compile(pattern1);
		Matcher m1 = r1.matcher(dataString);
//		System.out.println(m1.find());
		if(m1.find()){
//			System.out.println(m1.group(1));
		}
//		System.out.println(dataString);
		StringTokenizer st = new StringTokenizer(m1.group(1), ",");
		String[] data = new String[st.countTokens()];
		int i=0;
		while(st.hasMoreTokens()){
			data[i++] = st.nextToken();
		}
//		System.out.println(data[0]);
//		String dataString1 = "parent.GameFT[0]=new Array('2831448','08-03<br>10:00p<br><font color=red>Running Ball</font>','Ů���Ĺ�������(������)','40096','40095','����(Ů)','�ձ�(Ů)','H','1 / 1.5','0.67','0.57','O3','U3','0.65','0.57','1.09','5.10','4.25','��','˫','1.64','1.65','2831448','H','0.5','0.63','0.61','O1 / 1.5','U1 / 1.5','0.61','0.61','1.62','4.70','2.20','7','0','','N');\nparent.GameFT[1]=new Array('2831450','08-03<br>10:00p<br><font color=red>Running Ball</font>','Ů���Ĺ�������(������)','40098','40097','����(Ů)','�ձ�(Ů)','H','1.5','0.85','0.39','O3 / 3.5','U3 / 3.5','0.41','0.81','','','','','','','','2831450','H','0.5 / 1','0.89','0.35','O1.5','U1.5','0.34','0.88','','','','4','0','','N');parent.GameFT[2]=new Array('2841840','08-03<br>10:00p<br><font color=red>Running Ball</font>','��������','41204','41203','���ض��ƶ��� [��]','����Ī��ǿ��','H','1','0.44','0.80','O2.5','U2.5','0.51','0.71','1.08','6.30','3.80','��','˫','1.66','1.63','2841840','H','0 / 0.5','0.41','0.83','O1','U1','0.56','0.66','1.80','5.20','1.86','7','0','','N');function onLoad(){	";
//		String dataString1 = "[{\"id\":13,\"receiveTime\":\"2013-04-20 00:00:00 ������\",\"sensorType\":\"0 \",\"value\":\"19\"},{\"id\":14,\"receiveTime\":\"2013-04-21 00:00:00 ������\",\"sensorType\":\"1 \",\"value\":\"20\"},{\"id\":3,\"receiveTime\":\"2013-04-14 00:00:00 ������\",\"sensorType\":\"2 \",\"value\":\"20\"},{\"id\":4,\"receiveTime\":\"2013-04-15 00:00:00 ����һ\",\"sensorType\":\"3 \",\"value\":\"20\"},{\"id\":15,\"receiveTime\":\"2013-04-23 00:00:00 ���ڶ�\",\"sensorType\":\"5 \",\"value\":\"21\"},{\"id\":6,\"receiveTime\":\"2013-04-16 00:00:00 ���ڶ�\",\"sensorType\":\"6 \",\"value\":\"20\"},{\"id\":7,\"receiveTime\":\"2013-04-17 00:00:00 ������\",\"sensorType\":\"7 \",\"value\":\"22\"},{\"id\":8,\"receiveTime\":\"2013-04-18 00:00:00 ������\",\"sensorType\":\"8 \",\"value\":\"32\"},{\"id\":9,\"receiveTime\":\"2013-04-19 00:00:00 ������\",\"sensorType\":\"9 \",\"value\":\"21\"},{\"id\":10,\"receiveTime\":\"2013-04-19 00:00:00 ������\",\"sensorType\":\"10\",\"value\":\"15\"},{\"id\":11,\"receiveTime\":\"2013-04-19 00:00:00 ������\",\"sensorType\":\"11\",\"value\":\"20\"},{\"id\":12,\"receiveTime\":\"2013-04-20 00:00:00 ������\",\"sensorType\":\"12\",\"value\":\"18\"}]; }";
		String pattern2 = "parent.GameFT\\[\\d+\\]=new Array\\((.*)\\);";
		Pattern r2 = Pattern.compile(pattern2);		
		Matcher m2 = r2.matcher(dataString);
		int count = 0;
		List<GameData> gameDatas = new ArrayList<GameData>();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		while(m2.find()){
			count++;
//			System.out.println(m2.group(1));
//			System.out.println(m2.group(1).split(",")[1]);
//			StringTokenizer stz = new StringTokenizer(m2.group(1),",");
			String[] dataArray = m2.group(1).replaceAll("'", "").split(",");
			String[] timeArray = dataArray[1].split("<br>");
			long time = dateFormat.parse(year+"-"+timeArray[0]).getTime();
			Date date = new java.sql.Date(time);
			long detailTime = timeFormat.parse(year+"-"+timeArray[0]+" "+timeArray[1].substring(0,timeArray[1].length()-1)+":00").getTime();
			java.sql.Date dateTime = new java.sql.Date(detailTime);
			GameData gameData = new GameData();
			gameData.setMid(Integer.parseInt(dataArray[0]));
			gameData.setType("FT");
			gameData.setMb_mid(Integer.parseInt(dataArray[3]));
			gameData.setTg_mid(Integer.parseInt(dataArray[4]));
			gameData.setMb_team_cn(dataArray[5]);
			gameData.setTg_team_cn(dataArray[6]);
			
			gameData.setM_league_cn(dataArray[2]);
			gameData.setM_date(date);
			gameData.setM_time(timeArray[1]);
			gameData.setM_start(dateTime);
			gameData.setM_type("1");
			
			gameData.setShowTypeRB(dataArray[7]);
			gameData.setM_letb_rb(dataArray[8]);
			gameData.setMb_letb_rate_rb(dataArray[9]);
			gameData.setTg_letb_rate_rb(dataArray[10]);
			gameData.setMb_dime_rb(dataArray[11]);
			gameData.setTg_dime_rb(dataArray[12]);
			gameData.setMb_dime_rate_rb(dataArray[13]);
			gameData.setTg_dime_rate_rb(dataArray[14]);
			gameData.setMb_win_rate_rb(dataArray[15]);
			gameData.setTg_win_rate_rb(dataArray[16]);
			gameData.setM_flat_rate_rb(dataArray[17]);
			gameData.setS_single_rate_rb(dataArray[20]);
			gameData.setS_double_rate_rb(dataArray[21]);
			gameData.setShowTypeHRB(dataArray[23]);
			gameData.setM_letb_rb_h(dataArray[24]);
			gameData.setMb_letb_rate_rb_h(dataArray[25]);
			gameData.setTg_letb_rate_rb_h(dataArray[26]);
			gameData.setMb_dime_rb_h(dataArray[27]);
			gameData.setTg_dime_rb_h(dataArray[28]);
			gameData.setMb_dime_rate_rb_h(dataArray[29]);
			gameData.setTg_dime_rate_rb_h(dataArray[30]);
			gameData.setMb_win_rate_rb_h(dataArray[31]);
			gameData.setTg_win_rate_rb_h(dataArray[32]);
			gameData.setM_flat_rate_rb_h(dataArray[33]);
//			gameData.setHot(dataArray[36]);
			gameData.setSource_type("HG");
//			gameData.setHgid(Integer.parseInt(dataArray[22]));
//			gameData.setMore(dataArray[34]);
//			gameData.setEventid(dataArray[35]);
//			gameData.setPlay(dataArray[37]);
			gameDatas.add(gameData);
			
		}
		urlDao.saveGameDatas(gameDatas);
		System.out.println(count);
		return null;
	}
}
