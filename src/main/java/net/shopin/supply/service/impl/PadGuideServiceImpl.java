//package net.shopin.supply.service.impl;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import net.shopin.supply.domain.entity.GuideLog;
//import net.shopin.supply.domain.entity.GuideSupply;
//import net.shopin.supply.domain.entity.PadBaseinfo;
//import net.shopin.supply.domain.entity.PadGuide;
//import net.shopin.supply.domain.entity.PadLog;
//import net.shopin.supply.persistence.PadBaseinfoMapper;
//import net.shopin.supply.persistence.PadSupplyMapper;
//import net.shopin.supply.persistence.PadLogMapper;
//import net.shopin.supply.service.IPadGuideService;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
//import com.shopin.core.framework.exception.ShopinException;
//
///**
//* @ClassName: PadGuideServiceImpl 
//* @author zhangq
//* @date 2014-12-23 下午07:00:35 
//*
// */
//@Service
//public class PadGuideServiceImpl implements IPadGuideService {
//
//	private Logger logger = Logger.getLogger(PadGuideServiceImpl.class);
//	
//	@Autowired
//	private PadBaseinfoMapper padBaseinfoMapper;
//	
//	@Autowired
//	private PadSupplyMapper padGuideMapper;
//	
//	@Autowired
//	private PadLogMapper padLogMapper;
//
//	/**
//	 * 导购领用PAD
//	 */
//	public Integer receivePad(PadGuide padGuide){
//		Integer result = 0;
//		
//		SecurityContext ctx = SecurityContextHolder.getContext();         
//	    Authentication auth = ctx.getAuthentication();
//	    String user =auth.getName();
//		
//		result = this.padGuideMapper.insert(padGuide);
//		if(result!=1){
//			throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
//		}else{
//			
//			int padNo = padGuide.getPadNo();
//			PadBaseinfo padBaseinfo = this.padBaseinfoMapper.selectPadByPadNo(padNo);
//			if(null != padBaseinfo){
//				padBaseinfo.setPadStatus(2);//2 已领用
//				this.padBaseinfoMapper.update(padBaseinfo);
//			}
//			
//			PadLog padLog = new PadLog();
//			padLog.setDescription("系统时间:'"+new Date()+"'编号为'"+padGuide.getGuideNo()+"'的导购领用编号为'"+padGuide.getPadNo()+"'的PAD,登录名："+padGuide.getLoginName()+",密码："+padGuide.getPassword());
//			padLog.setPadNo(padBaseinfo.getPadNo());
//			padLog.setOperatTime(new Date());
//			padLog.setOperator("zhangqing");
////			padLog.setOperator(user);
//			
//			result = this.padLogMapper.insert(padLog);
//			if(result!=1){
//				throw new ShopinException(ErrorCode.SAVE_PADLOG_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_PADLOG_ERROR.getMemo());
//			}
//		}
//		return result;
//	}
//	
//	/**
//	 * 导购归还PAD
//	 */
//	@Override
//	public Integer delGuidePad(int sid) {
//		
//		SecurityContext ctx = SecurityContextHolder.getContext();         
//        Authentication auth = ctx.getAuthentication();
//        String user =auth.getName();
//		
//		Integer result = 0;
//		long sidl = Long.valueOf(sid);
//		PadGuide padGuide = this.padGuideMapper.get(sidl);
//		if(padGuide != null){
//			result = this.padGuideMapper.delete(sidl);
//			if(result!=1){
//				throw new ShopinException(ErrorCode.UPDATE_SUCCESS.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
//			}else{
//				PadLog padLog = new PadLog();
//				padLog.setDescription("系统时间:'"+new Date()+"'编号为'"+padGuide.getGuideNo()+"'的导购归还编号为'"+padGuide.getPadNo()+"'的PAD,登录名："+padGuide.getLoginName()+",密码："+padGuide.getPassword()+",操作员："+user);
//				padLog.setPadNo(padGuide.getPadNo());
//				padLog.setOperatTime(new Date());
//				padLog.setOperator("zhangqing");
////				padLog.setOperator(user);
//				
//				result = this.padLogMapper.insert(padLog);
//				if(result!=1){
//					throw new ShopinException(ErrorCode.SAVE_PADLOG_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_PADLOG_ERROR.getMemo());
//				}
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 根据导购编号获取该导购下的pad信息
//	 */
//	@Override
//	public List<PadGuide> selectListByParam(Map paramMap) {
//		List<PadGuide> list = this.padGuideMapper.selectListByParam(paramMap);
//		return list;
//	}
//	
//	
//}
