package com.computers.config;

import com.computers.dao.BoardDao;
import com.computers.dao.ComponentsDao;
import com.computers.dao.MemberDao;
import com.computers.dao.PaymentDao;
import com.computers.dao.ShippingInfoDao;
import com.computers.service.AdminComponentsService;
import com.computers.service.AdminMemberService;
import com.computers.service.AdminPayInfoService;
import com.computers.service.BoardListService;
import com.computers.service.ComponentsListService;
import com.computers.service.ComponentsService;
import com.computers.service.JoinService;
import com.computers.service.LoginService;
import com.computers.service.MemberService;

public class Config {
	
	private MemberDao memberDao;
	private BoardDao boardDao;
	private ComponentsDao componentsDao;
	private PaymentDao paymentDao;
	private ShippingInfoDao shippingInfoDao;
	
	private JoinService joinService;
	private LoginService loginService;
	private MemberService memberService;
	private ComponentsListService componentsService;
	private ComponentsService buyComponentsService;
	private BoardListService boardListService;
	private AdminMemberService adminMemberService;
	private AdminPayInfoService adminPayInfoService;
	private AdminComponentsService adminComponentsService;
	
	private Config() {
		memberDao = new MemberDao();
		componentsDao = new ComponentsDao();
		paymentDao = new PaymentDao();
		shippingInfoDao = new ShippingInfoDao();
		boardDao = new BoardDao();
		
		joinService = new JoinService(memberDao);
		loginService = new LoginService(memberDao);
		memberService = new MemberService(
				memberDao, shippingInfoDao, paymentDao, boardDao);
		componentsService = new ComponentsListService(componentsDao);
		buyComponentsService = new ComponentsService(
				componentsDao, shippingInfoDao, paymentDao);
		boardListService = new BoardListService(boardDao);
		adminMemberService = new AdminMemberService(memberDao);
		adminPayInfoService = new AdminPayInfoService(
				shippingInfoDao, paymentDao);
		adminComponentsService = new AdminComponentsService(componentsDao);
		
	}
	
	private static Config config = null;
	
	// 싱글톤 형식 사용
	public static Config getInstance() {
		if(config == null) {
			config = new Config();
		}
		return config;
	}

	public JoinService getJoinService() {
		return joinService;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public BoardDao getBoardDao() {
		return boardDao;
	}

	public ComponentsDao getComponentsDao() {
		return componentsDao;
	}

	public PaymentDao getPaymentDao() {
		return paymentDao;
	}

	public ShippingInfoDao getShippingInfoDao() {
		return shippingInfoDao;
	}

	public ComponentsListService getComponentsService() {
		return componentsService;
	}

	public ComponentsService getBuyComponentsService() {
		return buyComponentsService;
	}

	public BoardListService getBoardListService() {
		return boardListService;
	}

	public AdminMemberService getAdminMemberService() {
		return adminMemberService;
	}

	public AdminPayInfoService getAdminPayInfoService() {
		return adminPayInfoService;
	}

	public AdminComponentsService getAdminComponentsService() {
		return adminComponentsService;
	}
	
	
}
