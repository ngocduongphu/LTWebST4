package vn.iotstar.dao.impl;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.models.UserModel;

public class UserDaoImpl extends DBConnectSQL implements IUserDao{
	
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	public List<UserModel> findAll(){
		String sql = "SELECT * FROM users";

		List<UserModel> list = new ArrayList<>(); // Tao 1 list de truyen du lieu

		try {
			conn = super.getDBConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())/* Next tung dong toi cuoi bang */ {
				list.add(new UserModel(rs.getInt("id"), 
						
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("images"),
						rs.getString("fullname"), 
						rs.getString("email"), 
						rs.getString("phone"),
						rs.getInt("roleid"),
						rs.getDate("createDate")));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) conn.close();
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return null;
	}
	
	public UserModel findById(int id) {
		String sql = "SELECT * FROM users WHERE id = ? ";
		try {
			conn = new DBConnectSQL().getDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setPassWord(rs.getString("password"));
				user.setImages(rs.getString("images"));
				user.setFullName(rs.getString("fullname"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setCreatedDate(rs.getDate("createdDate"));
				return user; 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
			return null;
	};
	
	public void insert(UserModel user) {
	    String sql = "INSERT INTO users ( username, password, images, fullname, phone, email, roleid, createdDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    try {
	        conn = super.getDBConnection();
	        ps = conn.prepareStatement(sql);
	        
	        ps.setString(2, user.getUserName());
	        ps.setString(4, user.getPassWord());
	        ps.setString(5, user.getImages());
	        ps.setString(3, user.getFullName());	        
	        ps.setString(1, user.getEmail());	
	        ps.setString(7, user.getPhone());
	        ps.setInt(6, user.getRoleid());        
	        ps.setDate(8, user.getCreatedDate());
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	public UserModel findByUserName(String username) {
		String sql = "SELECT * FROM users WHERE username = ? ";
		try {
			conn = super.getDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setPassWord(rs.getString("password"));
				user.setImages(rs.getString("images"));
				user.setFullName(rs.getString("fullname"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));				
				user.setCreatedDate(rs.getDate("createdDate"));
				return user; 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
			return null;
	}

	
	public static void main(String[] args) {
		try {
			IUserDao userDao = new UserDaoImpl(); 
			System.out.println(userDao.findByUserName("duongpn"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkExistEmail(String email) {
		boolean duplicate = false;
		String query = "SELECT * FROM users WHERE email = ?";
		try {
			conn = super.getDBConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;

	}

	@Override
	public boolean checkExistUsername(String username) {
		boolean duplicate = false;
		String query = "SELECT * FROM users WHERE username = ?";
		try {
			conn = super.getDBConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;

	}

	@Override
	public boolean checkExistPhone(String phone) {
		boolean duplicate = false;
		String query = "SELECT * FROM users WHERE phone = ?";
		try {
			conn = super.getDBConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, phone);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;
	}

	public int editProfile(UserModel user) {
		int ketQua = 0;
		String query = "UPDATE users SET images = ?, fullname = ?, email=?, phone = ? WHERE id=?";
		try {
			conn = super.getDBConnection();
			ps = conn.prepareStatement(query);
			ps.setString(3, user.getImages());
			ps.setString(2, user.getFullName());
			ps.setString(1, user.getEmail());	
			ps.setString(4, user.getPhone());
			ps.setInt(5, user.getId());
			ketQua = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ketQua;
	}
	
	
	
}
