package com.project.zerowasteshop.member.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.zerowasteshop.donateitem.DonateItemVO;
import com.project.zerowasteshop.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
	
	@Autowired
	AdminMapper mapper;
	
	// 비밀번호 암호화하는 객체도 bean 으로 등록이 되어 있다.
	@Autowired
	private PasswordEncoder encoder;
	
	public List<MemberVO> selectAll() {
		
		return mapper.selectAll();
	}

	public List<MemberVO> selectAllPageBlock(int cpage, int pageBlock) {
		//mysql인 경우 limit 시작행을 얻어내는 알고리즘 필요
		//예: 1페이지(0,pageBlock), 2페이지(5,pageBlock), 3페이지(10,pageBlock)
		int startRow=(cpage-1)*pageBlock;
		log.info("startRow:{}",startRow);
		log.info("pageBlock:{}",pageBlock);
		return mapper.selectAllPageBlock(startRow,pageBlock);
	}

	public int getTotalRows() {
		return mapper.getTotalRows();
	}

	public MemberVO selectOne(MemberVO vo) {
		return mapper.selectOne(vo);
	}

	public List<MemberVO> searchList(String searchKey, String searchWord) {
		if(searchKey.equals("member_id")) {
			return mapper.searchListId("%"+searchWord+"%");
		}else {
			return mapper.searchListName("%"+searchWord+"%");
		}	
	}
	
	public List<MemberVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
		int startRow=(cpage-1)*pageBlock;	//mysql은 limit처리 시 0행부터 시작 (+1 안해도 됨)
		log.info("startRow:{}",startRow);
		log.info("pageBlock:{}",pageBlock);
		
		if(searchKey.equals("member_id")) {
			return mapper.searchListPageBlockId("%"+searchWord+"%",startRow,pageBlock);
		}else if(searchKey.equals("adCheck")){
			return mapper.searchListPageBlockAdminId("%"+searchWord+"%",startRow,pageBlock);		
		}else {
			return mapper.searchListPageBlockName("%"+searchWord+"%",startRow,pageBlock);		
		}
	}

	public int getSearchTotalRows(String searchKey, String searchWord) {
		if(searchKey.equals("member_id")) {
			return mapper.getSearchTotalRowsId("%"+searchWord+"%");
		}else if(searchKey.equals("adCheck")){
			return mapper.getSearchTotalRowsAdcheck("%"+searchWord+"%");		
		}else {
			return mapper.getSearchTotalRowsName("%"+searchWord+"%");
		}
	}

	public int insertOK(MemberVO vo) {
		String encodedPwd = encoder.encode(vo.getPw());
		vo.setPw(encodedPwd);
		return mapper.insertOK(vo);
	}

	public int adminUpdateOK(MemberVO vo) {
		String encodedPwd = encoder.encode(vo.getPw());
		vo.setPw(encodedPwd);
		return mapper.updateOK(vo);
		
	}

	public int deleteOK(MemberVO vo) {
		return mapper.deleteOK(vo);
	}

	public MemberVO idCheck(String member_id) {
		return mapper.idCheck(member_id);
	}

	public int userUpdateOK(MemberVO vo) {
		return mapper.userUpdateOK(vo);
	}





}
