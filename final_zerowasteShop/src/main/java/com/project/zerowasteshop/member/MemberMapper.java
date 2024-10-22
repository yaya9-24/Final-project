package com.project.zerowasteshop.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.zerowasteshop.donateitem.DonateItemVO;

@Mapper
public interface MemberMapper {

	public MemberVO selectOne(MemberVO vo);

	public int insertOK(MemberVO vo);

	public int updateOK(MemberVO vo);

	public int deleteOK(MemberVO vo);

	public MemberVO idCheck(String member_id);

	public MemberVO login(MemberVO vo);

}