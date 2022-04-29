package com.computers.config;

import com.computers.console.JoinConsole;
import com.computers.console.LoginConsole;
import com.computers.console.admin.AdminConsole;
import com.computers.console.general.GeneralConsole;
import com.computers.dao.BoardDao;
import com.computers.dao.ComponentsDao;
import com.computers.dao.MemberDao;
import com.computers.dao.PaymentDao;
import com.computers.dao.ShippingInfoDao;
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
	
	private LoginConsole loginColsole;
	private JoinConsole joinConsole;
	private AdminConsole adminConsole;
	private GeneralConsole generalConsole;
	
	private Config() {
		memberDao = new MemberDao();
		componentsDao = new ComponentsDao();
		paymentDao = new PaymentDao();
		shippingInfoDao = new ShippingInfoDao();
		boardDao = new BoardDao();
		joinService = new JoinService(memberDao);
		loginService = new LoginService(memberDao);
		memberService = new MemberService(memberDao, shippingInfoDao, paymentDao);
		
		loginColsole = new LoginConsole();
		joinConsole = new JoinConsole();
		adminConsole = new AdminConsole();
		generalConsole = new GeneralConsole();
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

	public LoginConsole getLoginColsole() {
		return loginColsole;
	}

	public JoinConsole getJoinConsole() {
		return joinConsole;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public AdminConsole getAdminConsole() {
		return adminConsole;
	}

	public GeneralConsole getGeneralConsole() {
		return generalConsole;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	
}
