/**  
 * Connect.java
 * 2016年12月17日 下午2:25:50 
 */
package com.emacle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emacle.constant.Constant;
import com.emacle.pojo.FsMeta;

/**
 * 2016年12月17日 下午2:25:50
 */
public class DB {

	private static final Logger log = LoggerFactory.getLogger(DB.class);
	

	/**
	 * 获得数据库的连接
	 * @return
	 */
	public static Connection getDBConnection() throws SQLException {
		try {
			Class.forName(Constant.MYSQLDRIVER);
			return DriverManager.getConnection(Constant.MYSQL_URL,
					Constant.MYSQL_USERNAME, Constant.MYSQL_PASS);
		} catch (SQLException e) {
			log.info("getDBConnection error", e);
			throw e;
		} catch (ClassNotFoundException e1) {
			log.info("getDBConnection error c", e1);
		}
		return null;

	}
	
	/**
	 * @param md5
	 * @param tstorer
	 * @return int 2017年2月17日 下午3:42:21
	 * @throws SQLException
	 */
	public static int getAliMetaByMd5(String md5)
			throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		int count = 0;
		try {
			String sql = "select count(1) as count from alimeta where md5=? and uri like 'oss:%'";
			con = DB.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, md5);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			log.info("getAlimeta_cp error,exec md5=" + md5, e);
			throw e;
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;

	}
	
	public static int getAliLocalByMd5(String md5)
			throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		int count = 0;
		try {
			String sql = Constant.sql2;
			con = DB.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, md5);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			log.error("getAliLocalByMd5 error,exec md5=" + md5, e);
			throw e;
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
		
	}
	
	public static int getFsByMd5(String md5)
			throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		int count = 0;
		try {
			String sql = "select count(1) as count from fsmeta where md5=?";
			con = DB.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, md5);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			log.info("getAlimeta_cp error,exec md5=" + md5, e);
			throw e;
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
		
	}
	
	/**
	 * 获得起始目标的100个数据
	 * @param start
	 * @return
	 */
	public static List<FsMeta> getFsMetaByStart(int start) {
		List<FsMeta> list = new ArrayList<FsMeta>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			String sql = "select f.inode inode, f.username username, f.path path,f.md5 md5, "
					+ "a.uri uri, a.bucket bucket, a.reference reference, a.tstorer tstorer "
					+ "from fsmeta f left join alimeta a on a.md5=f.md5 "
					+ "where f.isdir=0 and a.uri like 'oss:%' and f.md5 is not NULL order by f.inode desc limit ? , 100";
			con = DB.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			rs = ps.executeQuery();
			while (rs.next()) {
				int inode = rs.getInt("inode");
				String username = rs.getString("username");
				String path = rs.getString("path");
				String md5 = rs.getString("md5");
				String uri = rs.getString("uri");
				String bucket = rs.getString("bucket");
				int reference = rs.getInt("reference");
				String tstorer = rs.getString("tstorer");

				FsMeta fsMeta = new FsMeta(inode, username, path, md5, uri, bucket, reference, tstorer);
				list.add(fsMeta);
			}
		} catch (SQLException e) {
			log.error("getFullAliMeta error,exec n=" + start, e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws SQLException {
		/*
		 * System.out.println(getMaxInode()); int a = getMaxInode();
		 * System.out.println(getAliMeta(a));
		 * System.out.println(getAliMetaByMd5Tstorer(
		 * "7dfa06a9076e8f2225cf256b52b64cb3",
		 * "TStorerCluster1.HDFSStorerBroker11"));
		 */
		// FullAliMeta f = new FullAliMeta(0, "oss://fdsfdsf", "fdsfdsf",
		// System.currentTimeMillis(), 2, "tongbupan_store", 1, "OSSS", "");
		//
		// System.out.println(insertAlimeta_mig_del(f));
	}

	public static List<FsMeta> getAlimeta(int start) {
		List<FsMeta> list = new ArrayList<FsMeta>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			String sql = Constant.sql;
			con = DB.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			rs = ps.executeQuery();
			while (rs.next()) {
				String md5 = rs.getString("md5");
				String uri = rs.getString("uri");
				String bucket = rs.getString("bucket");
				int reference = rs.getInt("reference");
				String tstorer = rs.getString("tstorer");

				FsMeta fsMeta = new FsMeta(md5, uri, bucket, reference, tstorer);
				list.add(fsMeta);
			}
		} catch (SQLException e) {
			log.error("getAliMeta error,exec n=" + start, e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static List<FsMeta> getFsMetaByMd5(String md5Str) {
		List<FsMeta> list = new ArrayList<FsMeta>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			String sql = Constant.getFsMetaByMd5;
			con = DB.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, md5Str);
			rs = ps.executeQuery();
			while (rs.next()) {
				int inode = rs.getInt("inode");
				String username = rs.getString("username");
				String path = rs.getString("path");
				String md5 = rs.getString("md5");

				FsMeta fsMeta = new FsMeta(inode, username, path, md5);
				list.add(fsMeta);
			}
		} catch (SQLException e) {
			log.error("getFsMetaByMd5 error,exec n=" + md5Str, e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
