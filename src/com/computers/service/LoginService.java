package com.computers.service;

import com.computers.dao.MemberDao;
import com.computers.entity.Member;
import com.computers.exception.MemberNotFoundException;
import com.computers.exception.WrongIdPasswordException;

public class LoginService {

	private MemberDao memberDao;
	
	public LoginService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public Member userLogin(String reqId, String reqPwd) {
		Member member = memberDao.findByLoginId(reqId);
		if(member == null) {
			throw new MemberNotFoundException();
		}
		if(!member.getPwd().equals(reqPwd)) {
			throw new WrongIdPasswordException();
		}
		
		return member;
	}
	
}
