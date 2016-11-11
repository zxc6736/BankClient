package com.cjon.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.service.BankDepositService;
import com.cjon.bank.service.BankSelectAllMemberService;
import com.cjon.bank.service.BankSelectOne;
import com.cjon.bank.service.BankService;
import com.cjon.bank.service.BankTransfer;
import com.cjon.bank.service.BankWithdrawService;
import com.cjon.bank.service.viewInfo;

@Controller
public class BankController {

	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	private BankService service;
	//우리는 view로 JSP를 이용하지 않지만 JSP를 이용할거면 String이 return되어야 하고 JSON을 결과값으로
	//사용하려면 void사용
	//우리는 클라이언트로부터 callback값을 받아야 해요
	//출력이 JSP가 아니라 Stream을 열어서 클라이언트에게 JSON을 전송해야 해요
	
	@RequestMapping(value="/selectAllMember")
	public void selectAllMember(HttpServletRequest request, HttpServletResponse response, Model model){
		
		//입력처리
		String callback = request.getParameter("callback");
		
		//로직처리
		service = new BankSelectAllMemberService();
		model.addAttribute("dataSource",dataSource);
		service.execute(model);
		
		//결과처리 model에서 끄집어 내어요
		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			
			response.getWriter().println(callback+"("+json+")");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	@RequestMapping(value="/deposit")
	public void deposit(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String callback = request.getParameter("callback");
		service = new BankDepositService();

		model.addAttribute("dataSource",dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result= (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback+"("+result+")");
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@RequestMapping(value="/withdraw")
	public void withdraw(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String callback = request.getParameter("callback");
		service = new BankWithdrawService();

		model.addAttribute("dataSource",dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result= (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback+"("+result+")");
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	@RequestMapping(value="/selectOne")
	public void selectOne(HttpServletRequest request, HttpServletResponse response, Model model){

		//입력처리
		String callback = request.getParameter("callback");
		
		//로직처리
		service = new BankSelectOne();
		model.addAttribute("dataSource",dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		//결과처리 model에서 끄집어 내어요
		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			
			response.getWriter().println(callback+"("+json+")");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/transfer")
	
	public void transfer(HttpServletRequest request, HttpServletResponse response, Model model){

			
		String callback = request.getParameter("callback");
		service = new BankTransfer();

		model.addAttribute("dataSource",dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result= (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback+"("+result+")");
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@RequestMapping(value="/viewInfo")
	public void viewInfo(HttpServletRequest request, HttpServletResponse response, Model model){

		//입력처리
		String callback = request.getParameter("callback");
		
		//로직처리
		service = new viewInfo();
		model.addAttribute("dataSource",dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		System.out.println(request.getParameter("memberId"));
		
		//결과처리 model에서 끄집어 내어요
		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			
			response.getWriter().println(callback+"("+json+")");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
