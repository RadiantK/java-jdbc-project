package com.computers.config;

import com.computers.console.JoinConsole;
import com.computers.console.LoginConsole;
import com.computers.console.admin.AdminConsole;
import com.computers.console.admin.AdminBoardConsole;
import com.computers.console.admin.AdminComponentsConsole;
import com.computers.console.admin.AdminMemberListConsole;
import com.computers.console.admin.AdminPayInfoConsole;
import com.computers.console.general.BoardConsole;
import com.computers.console.general.BuyComponentsConsole;
import com.computers.console.general.ComponentsListConsole;
import com.computers.console.general.GeneralConsole;

public class ConsoleConfig {

	private LoginConsole loginColsole;
	private JoinConsole joinConsole;
	private AdminConsole adminConsole;
	private GeneralConsole generalConsole;
	private ComponentsListConsole componentsListConsole;
	private BuyComponentsConsole buyComponentsConsole;
	private BoardConsole boardConsole;
	private AdminMemberListConsole memberListConsole;
	private AdminPayInfoConsole payInfoConsole;
	private AdminComponentsConsole componentsConsole;
	private AdminBoardConsole adminBoardConsole;
	
	private ConsoleConfig() {

		loginColsole = new LoginConsole();
		joinConsole = new JoinConsole();
		adminConsole = new AdminConsole();
		generalConsole = new GeneralConsole();
		componentsListConsole = new ComponentsListConsole();
		buyComponentsConsole = new BuyComponentsConsole();
		boardConsole = new BoardConsole();
		memberListConsole = new AdminMemberListConsole();
		payInfoConsole = new AdminPayInfoConsole();
		componentsConsole = new AdminComponentsConsole();
		adminBoardConsole = new AdminBoardConsole();
	}

	private static ConsoleConfig config = null;
	
	// 싱글톤 형식 사용
	public static ConsoleConfig getInstance() {
		if(config == null) {
			config = new ConsoleConfig();
		}
		return config;
	}
	
	public LoginConsole getLoginColsole() {
		return loginColsole;
	}

	public JoinConsole getJoinConsole() {
		return joinConsole;
	}

	public AdminConsole getAdminConsole() {
		return adminConsole;
	}

	public GeneralConsole getGeneralConsole() {
		return generalConsole;
	}

	public ComponentsListConsole getComponentsListConsole() {
		return componentsListConsole;
	}

	public BuyComponentsConsole getBuyComponentsConsole() {
		return buyComponentsConsole;
	}

	public BoardConsole getBoardConsole() {
		return boardConsole;
	}

	public AdminMemberListConsole getMemberListConsole() {
		return memberListConsole;
	}

	public AdminPayInfoConsole getPayInfoConsole() {
		return payInfoConsole;
	}

	public AdminComponentsConsole getComponentsConsole() {
		return componentsConsole;
	}

	public AdminBoardConsole getAdminBoardConsole() {
		return adminBoardConsole;
	}
	
	
}
