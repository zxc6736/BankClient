package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cjon.bank.dto.BankDTO;

public class BankDAO {

	private Connection con;

	public BankDAO(Connection con) {
		this.con = con;

	}

	public ArrayList<BankDTO> selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();

		try {

			String sql = "select * from bank_member_tb";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getString("member_balance"));
				list.add(dto);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				rs.close();
				pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	public boolean updateDeposit(String memberID, String memberBalance) {
		PreparedStatement pstmt = null;
		boolean result = false;

		String sql = "update bank_member_tb set member_balance = member_balance+? where member_id=?";
		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberID);

			int count = pstmt.executeUpdate();

			if (count == 1) {
				
				PreparedStatement pstmts = null;
				result = true;

				String sqls = "insert into bank_statement_tb(MEMBER_ID,KIND,MONEY) values(?,?,?)";
				System.out.println("insert 해야됨");
				try {

					pstmts = con.prepareStatement(sqls);
					pstmts.setString(1, memberID);
					pstmts.setString(2, "입금");
					pstmts.setInt(3, Integer.parseInt(memberBalance));
					
					pstmts.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {

						pstmts.close();

					} catch (SQLException e2) {
						e2.printStackTrace();
					}

				}

			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				pstmt.close();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

		}
		return result;

	}

	public boolean updateWithdraw(String memberID, String memberBalance) {
		PreparedStatement pstmt = null;
		boolean result = false;

		String sql = "update bank_member_tb set member_balance = member_balance-? where member_id=?";
		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberID);

			int count = pstmt.executeUpdate();
			if (count == 1) {
				PreparedStatement pstmts = null;
				result = true;

				String sqls = "insert into bank_statement_tb(MEMBER_ID,KIND,MONEY) values(?,?,?)";
				System.out.println("insert 해야됨");
				try {

					pstmts = con.prepareStatement(sqls);
					pstmts.setString(1, memberID);
					pstmts.setString(2, "출금");
					pstmts.setInt(3, Integer.parseInt(memberBalance));
					
					pstmts.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {

						pstmts.close();

					} catch (SQLException e2) {
						e2.printStackTrace();
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				pstmt.close();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

		}
		return result;

	}

	public ArrayList<BankDTO> selectOne(String memberID) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();

		try {

			String sql = "select * from bank_member_tb where member_id like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + memberID + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getString("member_balance"));
				list.add(dto);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				rs.close();
				pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	public boolean transfer(String sendID, String receiveID, String transferBalance) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		boolean result = false;

		try {

			String sql = "update bank_member_tb set member_balance = member_balance-? where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, transferBalance);
			pstmt.setString(2, sendID);

			int count = pstmt.executeUpdate();

			String sql1 = "update bank_member_tb set member_balance = member_balance+? where member_id=?";
			pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1, transferBalance);
			pstmt1.setString(2, receiveID);

			int count1 = pstmt1.executeUpdate();

			if (count1 == 1) {
				PreparedStatement pstmts = null;
				result = true;

				String sqls = "insert into bank_transfer_history_tb(SEND_MEMBER_ID,RECEIVE_MEMBER_ID,TRANSFER_MONEY) values(?,?,?)";
				System.out.println("insert 해야됨");
				try {

					pstmts = con.prepareStatement(sqls);
					pstmts.setString(1, sendID);
					pstmts.setString(2, receiveID);
					pstmts.setInt(3, Integer.parseInt(transferBalance));
					
					pstmts.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {

						pstmts.close();

					} catch (SQLException e2) {
						e2.printStackTrace();
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				pstmt.close();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

		}
		return result;
	}

	public ArrayList<BankDTO> viewInfo(String memberID) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();

		try {

			String sql = "select * from bank_transfer_history_tb where SEND_MEMBER_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setMemberId(rs.getString("HISTORY_SQ"));
				dto.setMemberName(rs.getString("SEND_MEMBER_ID"));
				dto.setMemberAccount(rs.getString("RECEIVE_MEMBER_ID"));
				dto.setMemberBalance(rs.getString("TRANSFER_MONEY"));
				list.add(dto);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				rs.close();
				pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;

	}
}
