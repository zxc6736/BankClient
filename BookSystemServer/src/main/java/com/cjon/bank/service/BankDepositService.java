package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;

public class BankDepositService implements BankService {

	@Override
	public void execute(Model model) {
		
		HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
		String memberID =request.getParameter("memberId");
		String memberBalance=request.getParameter("memberBalance");
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		Connection con = null;
		boolean result = false;

		try{
		
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			
			BankDAO dao = new BankDAO(con);
		
			result = dao.updateDeposit(memberID, memberBalance);
			
			if(result){
				con.commit();
			}else{
				con.rollback();
			}
	
			model.addAttribute("RESULT", true);
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}finally{
			try{
				con.close();
			}catch(Exception e){
				
			}
			
		}
		
	
	}

}
