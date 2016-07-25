/**
* 导购手动变价权限日志报表
* for demand
* feature https://tower.im/s/beCeH
* author qutengfei
*/
package net.shopin.supply.service.impl;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.vo.OperaLogManagementVO;
import net.shopin.supply.persistence.OperaLogManagementMapper;
import net.shopin.supply.service.IOperaLogManagementService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperaLogManagementServiceImpl implements IOperaLogManagementService {

	private Logger logger = Logger.getLogger(GuideinfoServiceImpl.class);
	
	@Autowired
	private OperaLogManagementMapper operaLogManagementMapper;
	
	/**
	 * 操作日志查询   	查询手动变价权限操作记录(带分页)
	 * */
	@Override
	public List<OperaLogManagementVO> getLogForGuideOperate(Map<String, Object> paramMap) {
		List<OperaLogManagementVO> operateLogList = this.operaLogManagementMapper.getLogForGuideOperate(paramMap);
		return operateLogList;
	}
	/**
	 * 操作日志查询统计个数
	 * */
	@Override
	public Integer getCountLogForGuideOperate(Map<String, Object> paramMap) {
		int count = this.operaLogManagementMapper.getCountLogForGuideOperate(paramMap);
		return count;
	}

}
