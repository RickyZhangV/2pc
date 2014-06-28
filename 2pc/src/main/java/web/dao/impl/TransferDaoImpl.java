package web.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.springframework.stereotype.Repository;

import web.form.UserAccount;
import web.dao.TransferDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Repository
public class TransferDaoImpl implements TransferDao {

	@Override
	public int transferAccount(int accountId, int targetAccountId,BigDecimal amount) {
		// TODO Auto-generated method stub

		List list = new ArrayList();
		UserTransaction userTransaction = null;
		DataSource dataSource_1 = null;
		DataSource dataSource_2 = null;

		Connection conn_1=null;
		Connection conn_2=null;
		
		PreparedStatement ps_1 = null;
		PreparedStatement ps_2 = null;
		// TODO Auto-generated method stub
		if (accountId == 0 || targetAccountId == 0 || amount ==null)
			return 0;
		String sql = "UPDATE tbl_user_account SET balance=  ? WHERE accountId= ?";
		
		
		/***
		 * 逻辑顺序
		 * 1. 先给targetAccountId 加钱
		 * 2. 给accountId 扣款
		 * 正常逻辑应该先扣在加，但是我们这里相反，因为这样可以看到事务回滚的过程
		 */
		
		try{
			Context context = new InitialContext();
			
			//获取数据源，得到connection
			dataSource_1 = (DataSource) context.lookup("java:jboss/mysql1");
			dataSource_2 = (DataSource) context.lookup("java:jboss/mysql2");
			conn_1 = dataSource_1.getConnection();
			conn_2 = dataSource_2.getConnection();
			
			//获取UserTransaction，开启分布式事务
			userTransaction = (UserTransaction) context.lookup("java:comp/UserTransaction");
	
			System.out.println("===========事务开始===========");
			userTransaction.begin();

			//查询两个账户的余额
			Map account = (Map) this.queryBalance(accountId).get(0);
			Map targetAccount = (Map) this.queryBalance(targetAccountId).get(0);
			BigDecimal accountBalance = new BigDecimal(account.get("item3").toString());
			BigDecimal targetAccountBalance = new BigDecimal(targetAccount.get("item3").toString());

			
			
			//1.给targetAccount加款
			ps_1 = conn_1.prepareStatement(sql);
			ps_1.setObject(1, targetAccountBalance.add(amount));
			ps_1.setObject(2, targetAccountId);
			ps_1.executeUpdate();

			//2.从account中扣款
			ps_2 = conn_2.prepareStatement(sql);
			if(accountBalance.subtract(amount).intValue() < 0)
				throw new SQLException("转账金额超过此用户所持有的金额");
			ps_2.setObject(1,accountBalance.subtract(amount) );
			ps_2.setObject(2, accountId);
			ps_2.executeUpdate();

			userTransaction.commit();
			System.out.println("===========事务提交===========");
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			if(userTransaction != null)
				try {
					userTransaction.rollback();			
					System.out.println("===========事务回滚===========");
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
		}finally{
			try {
				if(ps_1 != null)
					ps_1.close();
				if(ps_2 != null)
					ps_2.close();
				if(conn_1 != null)
					conn_1.close();
				if(conn_2 != null)
					conn_2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 1;
	}

	@Override
	public List<Map> queryBalance(int accountId) {
		List list = new ArrayList();
		DataSource dataSource = null;
		Connection conn=null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		// TODO Auto-generated method stub
		if (accountId == 0)
			return null;
		String sql = "SELECT accountId ,username,balance FROM tbl_user_account WHERE accountId = ?";
		try{
			Context context = new InitialContext();
			//虽然使用了XA数据源，但是没启用UserTranscation
			dataSource = (DataSource) context.lookup("java:jboss/mysql1");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setObject(1, accountId);
			rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 1; i <= columnCount; i++) {
					map.put("item"+i, rs.getObject(i));
				}
				list.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
