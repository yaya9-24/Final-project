package com.project.zerowasteshop.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.zerowasteshop.coupon.CouponVO;

@Mapper
public interface OrderMapper {

	List<CouponVO> getAvailableCouponsForUser(String member_id);

	int getOrderAmount(String merchant_uid);

	void saveOrder(OrderVO order);

	void updateOrderState(String merchant_uid, String orderState);

	void saveOrderItem(OrderItemVO item);

	OrderVO selectOneOrder(String merchant_uid);

	void updateReusing(Map<String, Object> params);

	List<OrderItemVO> selectOneItem(String merchantUid);

	List<OrderJoinProductVO> selectAllPageBlockByUser(int startRow, int pageBlock, String userId);

	int getTotalRowsByUser(String userId);

	List<OrderJoinProductVO> selectAllByUser(String merchant_uid);

	OrderVO getOrderInfo(String merchant_uid);

	void deleteOrder(String merchant_uid);

	void deleteOrderItem(String merchant_uid);
	
    /**
     * 전체 판매량을 기준으로 상위 N개의 베스트셀러 상품을 조회
     */
    List<BestsellerDTO> findTopSellingProducts(int limit);

	int getTotalPrice(String merchant_uid);


}
