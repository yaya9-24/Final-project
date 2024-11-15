package com.project.zerowasteshop.member.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.zerowasteshop.member.MemberVO;


@Mapper
public interface AdminMapper {

	public List<MemberVO> selectAll();

	public int getTotalRows();

	public MemberVO selectOne(MemberVO vo);

	public List<MemberVO> searchListPageBlockId(String searchWord, int startRow, int pageBlock);

	public List<MemberVO> searchListPageBlockName(String searchWord, int startRow, int pageBlock);

	public int getSearchTotalRowsId(String searchWord);

	public int getSearchTotalRowsName(String searchWord);

	public List<MemberVO> selectAllPageBlock(int startRow, int pageBlock);

	public List<MemberVO> searchListId(String searchWord);

	public List<MemberVO> searchListName(String searchWord);

	public List<MemberVO> searchListPageBlockAdminId(String searchWord, int startRow, int pageBlock);

	public int insertOK(MemberVO vo);

	public int updateOK(MemberVO vo);

	public int deleteOK(MemberVO vo);

	public MemberVO login(MemberVO vo);

	public MemberVO findById(String member_id);

	public MemberVO idCheck(String member_id);

	public int userUpdateOK(MemberVO vo);

	public int getSearchTotalRowsAdcheck(String string);

}
