package net.shopin.supply.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopin.core.util.DateUtils;

import net.shopin.supply.domain.entity.PadPurchaseInfo;
import net.shopin.supply.persistence.PadPurchaseMapper;
import net.shopin.supply.service.IPadPurchaseInfoService;

@Service
public class PadPurchaseInfoServiceImpl implements IPadPurchaseInfoService {
	@Autowired
	private PadPurchaseMapper padPurchaseMapper;

	@Override
	public List<PadPurchaseInfo> getPadPurchaseInfo(Map<String, Object> map) throws Exception {
		List<PadPurchaseInfo> lists = this.padPurchaseMapper.selectPadPurchaseInfo(map);
		return lists;
	}
	

	@Override
	public Integer getCountOfPadPurchase() throws Exception {
		Integer count = this.padPurchaseMapper.getCountOfPadPurchase();
		return count;
	}

	@Override
	public Integer insertSelective(PadPurchaseInfo padPurchaseInfo) throws Exception {
		PadPurchaseInfo tmp = this.padPurchaseMapper.selectByForeignKey(padPurchaseInfo.getPadPurchaseBatchNo());
		if (tmp != null) {
			return -1;
		}
		
		padPurchaseInfo.setPadEnteredNum(0);  //已录入PAD的数量：默认为0。
		padPurchaseInfo.setPadOperatorTime(new Date());    
		Integer num = this.padPurchaseMapper.insertSelective(padPurchaseInfo);
		return num;
	}

	@Override
	public Integer deleteByPrimaryKey(Long sid) {
		Integer flag = this.padPurchaseMapper.deleteByPrimaryKey(sid);
		return flag;
	}

	@Override
	public Integer updateByPrimaryKeySelective(PadPurchaseInfo padPurchaseInfo) {

		Integer flag = this.padPurchaseMapper.updateByPrimaryKeySelective(padPurchaseInfo);
		return flag;
	}

	@Override
	public PadPurchaseInfo getPadPurchseInfoByBatchNo(String purchaseBatchNo) {
		PadPurchaseInfo padPurchaseInfo = this.padPurchaseMapper.selectByForeignKey(purchaseBatchNo);
		return padPurchaseInfo;
	}

}
