/**
 * 
 */
package net.shopin.supply.persistence;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.PadPurchaseInfo;

/**
 * Title: PadPurchaseMapper
 * Description: 
 * @author SunYukun
 * @date 2016年3月10日 上午11:37:06
 */
public interface PadPurchaseMapper {
	public List<PadPurchaseInfo> selectPadPurchaseInfo(Map<String, Object> map) throws Exception;

	public Integer getCountOfPadPurchase() throws Exception;

	public Integer insertSelective(PadPurchaseInfo padPurchaseInfo) throws Exception;

	public Integer deleteByPrimaryKey(Long sid);

	public Integer updateByPrimaryKeySelective(PadPurchaseInfo padPurchaseInfo);
	
	public PadPurchaseInfo selectByForeignKey(String purchaseBatchNo);
}
