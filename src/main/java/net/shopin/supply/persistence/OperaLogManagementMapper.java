/**
* 导购手动变价权限日志报表
* for demand
* feature https://tower.im/s/beCeH
* author qutengfei
*/
package net.shopin.supply.persistence;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.vo.OperaLogManagementVO;

import com.shopin.core.framework.base.persistence.BaseMapper;

public interface OperaLogManagementMapper extends BaseMapper<OperaLogManagementVO>{

    /**
	 * 操作日志查询   	查询手动变价权限操作记录(带分页)
	* @Title: getLogForGuideOperate
	* @param @param request guideNoId导购编号，typeId操作类型，operatorIdId操作人ID,operatorId操作人姓名，operatTimeId操作时间，descriptionId操作描述，typeDescId操作类型描述
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @author qutengfei
	* @throws
	 */
    public List<OperaLogManagementVO> getLogForGuideOperate(Map<String,Object> paramMap);

    /**
	 * 操作日志查询 ，统计个数
	* @Title: getLogForGuideOperate
	* @param @param request guideNoId导购编号，typeId操作类型，operatorIdId操作人ID,operatorId操作人姓名，operatTimeId操作时间，descriptionId操作描述，typeDescId操作类型描述
	* @param @param response
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @author qutengfei
	* @throws
	 */
    public Integer getCountLogForGuideOperate(Map<String,Object> paramMap);
}
