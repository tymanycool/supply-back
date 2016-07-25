package net.shopin.supply.service;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.PadPurchaseInfo;

public interface IPadPurchaseInfoService {
	public List<PadPurchaseInfo> getPadPurchaseInfo(Map<String, Object> map) throws Exception;

	public Integer getCountOfPadPurchase() throws Exception;

	public Integer insertSelective(PadPurchaseInfo padPurchaseInfo) throws Exception;

	public Integer updateByPrimaryKeySelective(PadPurchaseInfo padPurchaseInfo);

	public Integer deleteByPrimaryKey(Long sid);
	
	public PadPurchaseInfo getPadPurchseInfoByBatchNo(String purchaseBatchNo);
}
