package com.project.zerowasteshop.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

	List<OrderVO> selectAllPageBlock(int startRow, int pageBlock);

	int getTotalRows();

	List<OrderVO> searchListPageBlockId(String searchWord, int startRow, int pageBlock);

	List<OrderVO> searchListPageBlockPname(String searchWord, int startRow, int pageBlock);

	List<OrderVO> searchListPageBlockPayCheck(String searchWord, int startRow, int pageBlock);

	int getSearchTotalRowsId(String searchWord);

	int getSearchTotalRowsPname(String searchWord);

	int getSearchTotalRowsPayCheck(String searchWord);

}