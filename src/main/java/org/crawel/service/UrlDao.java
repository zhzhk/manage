package org.crawel.service;

import java.util.List;

import org.crawel.entity.GameData;
import org.crawel.entity.UrlData;
import org.springframework.stereotype.Service;

@Service
public interface UrlDao {
	public UrlData getUrlData(String wtype);
	public void updateUid(String uid, String wtype);
	public List<UrlData> getDataUrls();
	public int saveGameDatas(List<GameData> gameDatas);
}
