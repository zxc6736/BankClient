package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class viewInfo implements BankService {

	@Override
	public void execute(Model model) {
		HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
	 	String memberID = request.getParameter("memberId");
	 	DataSource dataSource = (DataSource) model.asMap().get("dataSource");
	 	Connection con = null;
	 	ArrayList<BankDTO> list = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			BankDAO dao = new BankDAO(con);
			list = dao.viewInfo(memberID);
			if(list != null){
				con.commit();
			}else{
				con.rollback();
			}
			
			model.addAttribute("RESULT",list);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

	}

}
