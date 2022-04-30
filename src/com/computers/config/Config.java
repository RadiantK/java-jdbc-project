package com.computers.config;

import com.computers.console.JoinConsole;
import com.computers.console.LoginConsole;
import com.computers.console.admin.AdminConsole;
import com.computers.console.general.BuyComponentsConsole;
import com.computers.console.general.ComponentsConsole;
import com.computers.console.general.GeneralConsole;
import com.computers.dao.BoardDao;
import com.computers.dao.ComponentsDao;
import com.computers.dao.MemberDao;
import com.computers.dao.PaymentDao;
import com.computers.dao.ShippingInfoDao;
import com.computers.service.BuyComponentsService;
import com.computers.service.ComponentsListService;
import com.computers.service.JoinService;
import com.computers.service.LoginService;
import com.computers.service.MemberService;

public class Config {

	private static Config getConfig() {
		return config;
	}
	
	private MemberDao memberDao;
	private BoardDao boardDao;
	private ComponentsDao componentsDao;
	private PaymentDao paymentDao;
	private ShippingInfoDao shippingInfoDao;
	
	private JoinService joinService;
	private LoginService loginService;
	private MemberService memberService;
	private ComponentsListService componentsService;
	private BuyComponentsService buyComponentsService;
	
	private LoginConsole loginColsole;
	private JoinConsole joinConsole;
	private AdminConsole adminConsole;
	private GeneralConsole generalConsole;
	private ComponentsConsole componentsConsole;
	private BuyComponentsConsole buyComponentsConsole;
	
	private Config() {
		memberDao = new MemberDao();
		componentsDao = new ComponentsDao();
		paymentDao = new PaymentDao();
		shippingInfoDao = new ShippingInfoDao();
		boardDao = new BoardDao();
		
		joinService = new JoinService(memberDao);
		loginService = new LoginService(memberDao);
		memberService = new MemberService(memberDao, shippingInfoDao, paymentDao);
		componentsService = new ComponentsListService(componentsDao);
		buyComponentsService = 
				new BuyComponentsService(componentsDao, shippingInfoDao, paymentDao);
		
		loginColsole = new LoginConsole();
		joinConsole = new JoinConsole();
		adminConsole = new AdminConsole();
		generalConsole = new GeneralConsole();
		componentsConsole = new ComponentsConsole();
		buyComponentsConsole = new BuyComponentsConsole();
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

	public ComponentsConsole getComponentsConsole() {
		return componentsConsole;
	}

	public ComponentsListService getComponentsService() {
		return componentsService;
	}

	public BuyComponentsConsole getBuyComponentsConsole() {
		return buyComponentsConsole;
	}

	public BuyComponentsService getBuyComponentsService() {
		return buyComponentsService;
	}
	
	
}
