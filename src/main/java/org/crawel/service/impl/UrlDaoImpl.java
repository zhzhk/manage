package org.crawel.service.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.crawel.entity.GameData;
import org.crawel.entity.UrlData;
import org.crawel.service.UrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UrlDaoImpl implements UrlDao {
	@Autowired 
	private JdbcTemplate jdbcTemplate;

	@Override
	public UrlData getUrlData(String wtype) {
		UrlData urlData = jdbcTemplate.queryForObject(
				"select * from web_crawel_site where ID = ?",
				new Object[]{wtype},
				new RowMapper<UrlData>() {
				public UrlData mapRow(ResultSet rs, int rowNum) throws SQLException {
					UrlData urlData = new UrlData();
					urlData.setWeb_type(rs.getString("web_type"));
					urlData.setWeb_site(rs.getString("web_site"));
					urlData.setUsername(rs.getString("username"));
					urlData.setPassword(rs.getString("password"));
					urlData.setUid(rs.getString("uid"));
					urlData.setLanguage(rs.getString("language"));
					return urlData;
				}
			});
		return urlData;
	}

	@Override
	public void updateUid(String uid,String wtype) {
		String uuidSql = "update web_crawel_site set uid = ? where web_type = ?"; 
		jdbcTemplate.update(uuidSql, uid, wtype);
	}

	@Override
	public List<UrlData> getDataUrls() {
		List<UrlData> urlDatas = jdbcTemplate.query(
			"select uid,web_site from web_crawel_site where web_type = ?",
				new Object[]{2},
				new RowMapper<UrlData>() {
				public UrlData mapRow(ResultSet rs, int rowNum) throws SQLException {
					UrlData urlData = new UrlData();
					urlData.setUid(rs.getString("uid"));
					urlData.setWeb_site(rs.getString("web_site"));
					return urlData;
				}
		});
		return urlDatas;
	}

	@Override
	public int saveGameDatas(List<GameData> gameDatas) {
		List<GameData> batchUpdateDatas = new ArrayList<GameData>();
		List<GameData> batchInsertDatas = new ArrayList<GameData>();
		for(GameData gameData : gameDatas){
			String isExistSql = "select count(*) from web_football_matches_data where MID = ?";
			int rowRecord = jdbcTemplate.queryForObject(isExistSql,Integer.class,gameData.getMid());
			if(0==rowRecord){
				//²åÈë
				batchInsertDatas.add(gameData);
				
			}else if(1==rowRecord){
				//¸üÐÂ
				batchUpdateDatas.add(gameData);
			}
		}
		if(batchInsertDatas.size()>0){
			int[] icount = batchInsert(batchInsertDatas);
			System.out.println("----------------------"+icount.length);
		}
		if(batchUpdateDatas.size()>0){
			int[] ucount = batchUpdate(batchUpdateDatas);
			System.out.println("*******************************"+ucount.length);
		}
		return 0;
	}
	
	public int[] batchUpdate(final List<GameData> batchUpdateDatas){
		String uSql = "update web_football_matches_data set MB_Win_Rate_RB = ?,TG_Win_Rate_RB = ?,M_Flat_Rate_RB = ?,ShowTypeRB = ?,M_LetB_RB = ?,MB_LetB_Rate_RB = ?,"
				+ "TG_LetB_Rate_RB = ?,MB_Dime_RB = ?,TG_Dime_RB= ?,MB_Dime_Rate_RB = ?,TG_Dime_Rate_RB = ?,MB_Win_Rate_RB_H = ?,TG_Win_Rate_RB_H = ?,"
				+ "M_Flat_Rate_RB_H = ?,ShowTypeHRB = ?,M_LetB_RB_H = ?,MB_LetB_Rate_RB_H = ?,TG_LetB_Rate_RB_H = ?,MB_Dime_RB_H = ?,TG_Dime_RB_H = ?,"
				+ "MB_Dime_Rate_RB_H = ?,TG_Dime_Rate_RB_H = ?,S_Single_Rate_RB= ?,S_Double_Rate_RB = ?,RB_Show = ?,MB_Inball = ?,TG_Inball = ?,MB_Inball_HR = ?,"
				+ "TG_Inball_HR = ?,MB_Ball = ?,TG_Ball = ?,MB_Card = ?,TG_Card = ?,MB_Red = ?,TG_Red = ?,Hot = ?,isOpen = ?,isFinish = ?,isCancel = ?,"
				+ "isChecked = ?,isCheckout = ?,now_play = ?,source_type = ? where MID = ?";
		int[] updateCounts = jdbcTemplate.batchUpdate(uSql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, batchUpdateDatas.get(i).getMb_win_rate_rb());
				ps.setString(2, batchUpdateDatas.get(i).getTg_win_rate_rb());
				ps.setString(3, batchUpdateDatas.get(i).getM_flat_rate_rb());
				ps.setString(4, batchUpdateDatas.get(i).getShowTypeRB());
				ps.setString(5, batchUpdateDatas.get(i).getM_letb_rb());
				ps.setString(6, batchUpdateDatas.get(i).getMb_letb_rate_rb());
				ps.setString(7, batchUpdateDatas.get(i).getTg_letb_rate_rb());
				ps.setString(8, batchUpdateDatas.get(i).getMb_dime_rb());
				ps.setString(9, batchUpdateDatas.get(i).getTg_dime_rb());
				ps.setString(10, batchUpdateDatas.get(i).getMb_dime_rate_rb());
				ps.setString(11, batchUpdateDatas.get(i).getTg_dime_rate_rb());
				ps.setString(12, batchUpdateDatas.get(i).getMb_win_rate_rb_h());
				ps.setString(13, batchUpdateDatas.get(i).getTg_win_rate_rb_h());
				ps.setString(14, batchUpdateDatas.get(i).getM_flat_rate_rb_h());
				ps.setString(15, batchUpdateDatas.get(i).getShowTypeHRB());
				ps.setString(16, batchUpdateDatas.get(i).getM_letb_rb_h());
				ps.setString(17, batchUpdateDatas.get(i).getMb_letb_rate_rb_h());
				ps.setString(18, batchUpdateDatas.get(i).getTg_letb_rate_rb_h());
				ps.setString(19, batchUpdateDatas.get(i).getMb_dime_rb_h());
				ps.setString(20, batchUpdateDatas.get(i).getTg_dime_rb_h());
				ps.setString(21, batchUpdateDatas.get(i).getMb_dime_rate_rb_h());
				ps.setString(22, batchUpdateDatas.get(i).getTg_dime_rate_rb_h());
				ps.setString(23, batchUpdateDatas.get(i).getS_single_rate_rb());
				ps.setString(24, batchUpdateDatas.get(i).getS_double_rate_rb());
				ps.setString(25, batchUpdateDatas.get(i).getRb_show());
				ps.setInt(26, batchUpdateDatas.get(i).getMb_inball());
				ps.setInt(27, batchUpdateDatas.get(i).getTg_inball());
				ps.setInt(28, batchUpdateDatas.get(i).getMb_inball_hr());
				ps.setInt(29, batchUpdateDatas.get(i).getTg_inball_hr());
				ps.setInt(30, batchUpdateDatas.get(i).getMb_ball());
				ps.setInt(31, batchUpdateDatas.get(i).getTg_ball());
				ps.setInt(32, batchUpdateDatas.get(i).getMb_card());
				ps.setInt(33, batchUpdateDatas.get(i).getTg_card());
				ps.setInt(34, batchUpdateDatas.get(i).getMb_red());
				ps.setInt(35, batchUpdateDatas.get(i).getTg_red());
				ps.setString(36, batchUpdateDatas.get(i).getHot());
				ps.setString(37, batchUpdateDatas.get(i).getIsOpen());
				ps.setString(38, batchUpdateDatas.get(i).getIsFinish());
				ps.setString(39, batchUpdateDatas.get(i).getIsCancel());
				ps.setString(40, batchUpdateDatas.get(i).getIsChecked());
				ps.setString(41, batchUpdateDatas.get(i).getIsCheckout());
				ps.setString(42, batchUpdateDatas.get(i).getNow_play());
				ps.setString(43, batchUpdateDatas.get(i).getSource_type());
				ps.setInt(44, batchUpdateDatas.get(i).getMid());
			}
			
			@Override
			public int getBatchSize() {
				return batchUpdateDatas.size();
			}
		});
		return updateCounts;
	}
	
	public int[] batchInsert(final List<GameData> batchInsertDatas){
		String uSql = "insert into web_football_matches_data set MID = ?,Type = ?, MB_MID = ?, TG_MID = ?,MB_Team_cn = ?,TG_Team_cn = ?,M_Date = ?, M_Time = ?,"
				+ "M_Start = ?,M_League_cn = ?,MB_Win_Rate_RB = ?,TG_Win_Rate_RB = ?,M_Flat_Rate_RB = ?,ShowTypeRB = ?,M_LetB_RB = ?,MB_LetB_Rate_RB = ?,"
				+ "TG_LetB_Rate_RB = ?,MB_Dime_RB = ?,TG_Dime_RB= ?,MB_Dime_Rate_RB = ?,TG_Dime_Rate_RB = ?,MB_Win_Rate_RB_H = ?,TG_Win_Rate_RB_H = ?,"
				+ "M_Flat_Rate_RB_H = ?,ShowTypeHRB = ?,M_LetB_RB_H = ?,MB_LetB_Rate_RB_H = ?,TG_LetB_Rate_RB_H = ?,MB_Dime_RB_H = ?,TG_Dime_RB_H = ?,"
				+ "MB_Dime_Rate_RB_H = ?,TG_Dime_Rate_RB_H = ?,S_Single_Rate_RB= ?,S_Double_Rate_RB = ?,RB_Show = ?,MB_Inball = ?,TG_Inball = ?,MB_Inball_HR = ?,"
				+ "TG_Inball_HR = ?,MB_Ball = ?,TG_Ball = ?,MB_Card = ?,TG_Card = ?,MB_Red = ?,TG_Red = ?,Hot = ?,isOpen = ?,isFinish = ?,isCancel = ?,"
				+ "isChecked = ?,isCheckout = ?,now_play = ?,source_type = ?";
		int[] updateCounts = jdbcTemplate.batchUpdate(uSql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, batchInsertDatas.get(i).getMid());
				ps.setString(2, batchInsertDatas.get(i).getType());
				ps.setInt(3, batchInsertDatas.get(i).getMb_mid());
				ps.setInt(4, batchInsertDatas.get(i).getTg_mid());
				ps.setString(5, batchInsertDatas.get(i).getMb_team_cn());
				ps.setString(6, batchInsertDatas.get(i).getTg_team_cn());
				ps.setDate(7, (Date) batchInsertDatas.get(i).getM_date());
				ps.setString(8, batchInsertDatas.get(i).getM_time());
				ps.setDate(9, (Date) batchInsertDatas.get(i).getM_start());
				ps.setString(10, batchInsertDatas.get(i).getM_league_cn());
				ps.setString(11, batchInsertDatas.get(i).getMb_win_rate_rb());
				ps.setString(12, batchInsertDatas.get(i).getTg_win_rate_rb());
				ps.setString(13, batchInsertDatas.get(i).getM_flat_rate_rb());
				ps.setString(14, batchInsertDatas.get(i).getShowTypeRB());
				ps.setString(15, batchInsertDatas.get(i).getM_letb_rb());
				ps.setString(16, batchInsertDatas.get(i).getMb_letb_rate_rb());
				ps.setString(17, batchInsertDatas.get(i).getTg_letb_rate_rb());
				ps.setString(18, batchInsertDatas.get(i).getMb_dime_rb());
				ps.setString(19, batchInsertDatas.get(i).getTg_dime_rb());
				ps.setString(20, batchInsertDatas.get(i).getMb_dime_rate_rb());
				ps.setString(21, batchInsertDatas.get(i).getTg_dime_rate_rb());
				ps.setString(22, batchInsertDatas.get(i).getMb_win_rate_rb_h());
				ps.setString(23, batchInsertDatas.get(i).getTg_win_rate_rb_h());
				ps.setString(24, batchInsertDatas.get(i).getM_flat_rate_rb_h());
				ps.setString(25, batchInsertDatas.get(i).getShowTypeHRB());
				ps.setString(26, batchInsertDatas.get(i).getM_letb_rb_h());
				ps.setString(27, batchInsertDatas.get(i).getMb_letb_rate_rb_h());
				ps.setString(28, batchInsertDatas.get(i).getTg_letb_rate_rb_h());
				ps.setString(29, batchInsertDatas.get(i).getMb_dime_rb_h());
				ps.setString(30, batchInsertDatas.get(i).getTg_dime_rb_h());
				ps.setString(31, batchInsertDatas.get(i).getMb_dime_rate_rb_h());
				ps.setString(32, batchInsertDatas.get(i).getTg_dime_rate_rb_h());
				ps.setString(33, batchInsertDatas.get(i).getS_single_rate_rb());
				ps.setString(34, batchInsertDatas.get(i).getS_double_rate_rb());
				ps.setString(35, batchInsertDatas.get(i).getRb_show());
				ps.setInt(36, batchInsertDatas.get(i).getMb_inball());
				ps.setInt(37, batchInsertDatas.get(i).getTg_inball());
				ps.setInt(38, batchInsertDatas.get(i).getMb_inball_hr());
				ps.setInt(39, batchInsertDatas.get(i).getTg_inball_hr());
				ps.setInt(40, batchInsertDatas.get(i).getMb_ball());
				ps.setInt(41, batchInsertDatas.get(i).getTg_ball());
				ps.setInt(42, batchInsertDatas.get(i).getMb_card());
				ps.setInt(43, batchInsertDatas.get(i).getTg_card());
				ps.setInt(44, batchInsertDatas.get(i).getMb_red());
				ps.setInt(45, batchInsertDatas.get(i).getTg_red());
				ps.setString(46, batchInsertDatas.get(i).getHot());
				ps.setString(47, batchInsertDatas.get(i).getIsOpen());
				ps.setString(48, batchInsertDatas.get(i).getIsFinish());
				ps.setString(49, batchInsertDatas.get(i).getIsCancel());
				ps.setString(50, batchInsertDatas.get(i).getIsChecked());
				ps.setString(51, batchInsertDatas.get(i).getIsCheckout());
				ps.setString(52, batchInsertDatas.get(i).getNow_play());
				ps.setString(53, batchInsertDatas.get(i).getSource_type());
			}
			
			@Override
			public int getBatchSize() {
				return batchInsertDatas.size();
			}
		});
		return updateCounts;
	}
}
