package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankTransfer implements BankService {

	@Override
	public void execute(Model model) {
	
			 	HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
			 	String sendID = request.getParameter("sendId");
			 	String receiveID = request.getParameter("receiveId");
			 	String transferBalance = request.getParameter("transferBalance");
			 	DataSource dataSource = (DataSource) model.asMap().get("dataSource");
			 	Connection con = null;
			 	boolean result = false;
			
					  try {
							   con = dataSource.getConnection();
							   con.setAutoCommit(false);
							
								   BankDAO dao = new BankDAO(con);
								   result = dao.transfer(sendID, receiveID, transferBalance);
								
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
