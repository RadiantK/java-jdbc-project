package com.computers.service;

import java.sql.Connection;

import com.computers.dao.MemberDao;
import com.computers.dto.MemberRequest;
import com.computers.entity.Member;
import com.computers.exception.DuplicateMemberException;
import com.computers.util.DataUtil;

public class JoinService {

	private MemberDao memberDao;
	
	public JoinService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public int memberJoin(MemberRequest req) {
		Member member = memberDao.findById(req.getId());
		if(member != null) {
			throw new DuplicateMemberException();
		}
		Connection con = DataUtil.getConnection();
		
		Member newMember = new Member(
				req.getId(), req.getPwd(), req.getMname(), req.getEmail(),
				req.getPhone(), null, 0, null);
		int n =  memberDao.insert(con, newMember);
		if(n < 1) {
			DataUtil.rollback(con);
			return n;
		}
		DataUtil.commit(con);
		DataUtil.close(con);
		return n;
	}
}
