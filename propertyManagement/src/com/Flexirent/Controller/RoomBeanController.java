package com.Flexirent.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.Flexirent.DB.OpSqliteDB;
import com.Flexirent.Model.RentBean;
import com.Flexirent.Model.RoomBean;

public class RoomBeanController {
	//??
public List<RoomBean> getDetails(String roomNumber,String type,String status,String suburb){
		String sql = "select * from RENTAL_PROPERTY where 1=1 ";
		if(!roomNumber.equals("")){
			sql += " and roomnumber ='"+roomNumber+"'";
		}
		if(!type.equals("")){
			sql += " and type ='"+type+"'";
		}
		if(!status.equals("")){
			sql += " and status ='"+status+"'";
		}
		if(!suburb.equals("")){
			sql += " and suburb ='"+suburb+"'";
		}
		List<RoomBean> data=new ArrayList<RoomBean>();		
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			ResultSet res = prestmt.executeQuery();
			while (res.next()) {
				RoomBean roomBean = new RoomBean();
				roomBean.setId(res.getString("id"));
				roomBean.setType(res.getString("type"));
				roomBean.setStretNunber(res.getString("stretNunber"));
				roomBean.setStretName(res.getString("stretName"));		
				roomBean.setRoomNumber(res.getString("roomNumber"));
				roomBean.setSuburb(res.getString("suburb"));		
				roomBean.setStatus(res.getString("status"));		
				roomBean.setImage(res.getString("image"));		
				roomBean.setMaintaindate(res.getString("maintaindate"));		
				roomBean.setDescription(res.getString("description"));		
				data.add(roomBean);
			}
			dbConnection.close(prestmt, conn);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return data;
	}
	
//
	public RoomBean getRoomBeanByid(String roomid){
		String sql = "select * from RENTAL_PROPERTY where id=? ";
		RoomBean roomBean = new RoomBean();
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, roomid);
			ResultSet res = prestmt.executeQuery();
			while (res.next()) {
				roomBean.setId(res.getString("id"));
				roomBean.setType(res.getString("type"));
				roomBean.setStretNunber(res.getString("stretNunber"));
				roomBean.setStretName(res.getString("stretName"));		
				roomBean.setRoomNumber(res.getString("roomNumber"));
				roomBean.setSuburb(res.getString("suburb"));		
				roomBean.setStatus(res.getString("status"));		
				roomBean.setImage(res.getString("image"));
				roomBean.setMaintaindate(res.getString("maintaindate"));
				roomBean.setDescription(res.getString("description"));		
			}
			dbConnection.close(prestmt, conn);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return roomBean;
	}
	
	public boolean insertRoom(String type,String stretNunber,String stretName,String roomNumber,String suburb,String status,String image,String description){
		
		String sql = "insert into RENTAL_PROPERTY(id,type,stretNunber,stretName,roomNumber,suburb,status,image,description,maintaindate) values(?,?,?,?,?,?,?,?,?,'')  ";
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, Util.getUUID());
			prestmt.setString(2, type);
			prestmt.setString(3, stretNunber);
			prestmt.setString(4, stretName);
			prestmt.setString(5, roomNumber);
			prestmt.setString(6, suburb);
			prestmt.setString(7, status);
			prestmt.setString(8, image);
			prestmt.setString(9, description);
			int res = prestmt.executeUpdate();
			dbConnection.close(prestmt, conn);
			if(res>0){
				return true;
			}
				
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return false;
	}
	
	public String imputInsertRoom(String[] rowData){
		
		String sql = "insert into RENTAL_PROPERTY(id,type,stretNunber,stretName,roomNumber,suburb,status,image,description,maintaindate) values(?,?,?,?,?,?,?,?,?,'')  ";
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			String id=Util.getUUID();
			prestmt.setString(1, id);
			prestmt.setString(2, rowData[0]);
			prestmt.setString(3, rowData[1]);
			prestmt.setString(4, rowData[2]);
			prestmt.setString(5, rowData[3]);
			prestmt.setString(6, rowData[4]);
			prestmt.setString(7, rowData[5]);
			prestmt.setString(8, rowData[6]);
			prestmt.setString(9, rowData[7]);
			int res = prestmt.executeUpdate();
			dbConnection.close(prestmt, conn);
			if(res>0){
				
				return id;
			}
				
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return "";
	}
	
	public List<RentBean> getRentBeanByRoomid(String roomid){
		String sql = "select * from  RENTAL_RECORD inner join RENTAL_PROPERTY on RENTAL_PROPERTY.id=RENTAL_RECORD.roomid where RENTAL_RECORD.roomid =? ";
		List<RentBean> data=new ArrayList<RentBean>();		
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, roomid);
			ResultSet res = prestmt.executeQuery();
			while (res.next()) {
				RentBean model = new RentBean(
						res.getString("id"),
						res.getString("rentDate"),
						res.getString("estimatedReturnDate"),
						res.getString("actualRetuenDate"),
						res.getString("rentalFee"),
						res.getString("lateFee"),
						res.getString("customerName"),
						res.getString("roomid"),
						res.getString("type"),
						res.getString("stretNunber"),
						res.getString("stretName"),
						res.getString("roomNumber"),
						res.getString("suburb"),
						res.getString("status"),
						res.getString("image"),
						res.getString("description"));
				data.add(model);
			}
			dbConnection.close(prestmt, conn);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return data;
	}
	
	public RentBean getRentBeanNoReturnByRoomid(String roomid){
		String sql = "select * from  RENTAL_RECORD inner join RENTAL_PROPERTY on RENTAL_PROPERTY.id=RENTAL_RECORD.roomid where RENTAL_RECORD.roomid =? and RENTAL_RECORD.actualRetuenDate=''";
		RentBean model =null;		
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, roomid);
			ResultSet res = prestmt.executeQuery();
			while (res.next()) {
				 model = new RentBean(
						res.getString("id"),
						res.getString("rentDate"),
						res.getString("estimatedReturnDate"),
						res.getString("actualRetuenDate"),
						res.getString("rentalFee"),
						res.getString("lateFee"),
						res.getString("customerName"),
						res.getString("roomid"),
						res.getString("type"),
						res.getString("stretNunber"),
						res.getString("stretName"),
						res.getString("roomNumber"),
						res.getString("suburb"),
						res.getString("status"),
						res.getString("image"),
						res.getString("description"));
			}
			dbConnection.close(prestmt, conn);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return model;
	}
	
	public boolean insertRent(String rentDate,String estimatedReturnDate,String actualRetuenDate,String rentalFee,String lateFee,String customerName,String roomid){
		
		String sql = "insert into RENTAL_RECORD(id,rentDate,estimatedReturnDate,actualRetuenDate,rentalFee,lateFee,customerName,roomid) values(?,?,?,?,?,?,?,?)  ";
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, Util.getUUID());
			prestmt.setString(2, rentDate);
			prestmt.setString(3, estimatedReturnDate);
			prestmt.setString(4, actualRetuenDate);
			prestmt.setString(5, rentalFee);
			prestmt.setString(6, lateFee);
			prestmt.setString(7, customerName);
			prestmt.setString(8, roomid);
			int res = prestmt.executeUpdate();
			dbConnection.close(prestmt, conn);
			if(res>0){
				return true;
			}
				
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return false;
	}
	
	public boolean updateRent(String actualRetuenDate,String lateFee,String rentid){
		
		String sql = "update RENTAL_RECORD set actualRetuenDate=? , lateFee=? where id=?";
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, actualRetuenDate);
			prestmt.setString(2, lateFee);
			prestmt.setString(3, rentid);
			int res = prestmt.executeUpdate();
			dbConnection.close(prestmt, conn);
			if(res>0){
				return true;
			}
				
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return false;
	}
	
	public boolean updateStatus(String status,String id){
		
		String sql = "update RENTAL_PROPERTY set status=? where id=?";
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, status);
			prestmt.setString(2, id);
			int res = prestmt.executeUpdate();
			dbConnection.close(prestmt, conn);
			if(res>0){
				return true;
			}
				
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return false;
	}
	
	public boolean updateMaintaindate(String maintaindate,String id){
		
		String sql = "update RENTAL_PROPERTY set maintaindate=? where id=?";
		try {
			OpSqliteDB dbConnection = new OpSqliteDB();
			Connection conn = dbConnection.getCon();
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, maintaindate);
			prestmt.setString(2, id);
			int res = prestmt.executeUpdate();
			dbConnection.close(prestmt, conn);
			if(res>0){
				return true;
			}
				
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return false;
	}
}
