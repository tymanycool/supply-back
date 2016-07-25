package net.shopin.supply.persistence;

import java.util.List;

import com.shopin.core.framework.base.persistence.BaseMapper;

import net.shopin.supply.domain.entity.Role;

public interface RoleMapper extends BaseMapper<Role>{
//    int deleteByPrimaryKey(Long sid);
//
//    int insert(Role record);
//
//    int insertSelective(Role record);
//
//    Role selectByPrimaryKey(Long sid);
//
//    int updateByPrimaryKeySelective(Role record);
//
//    int updateByPrimaryKey(Role record);
    
    public List getRoleByParam(Role role);
    
    public List getAllRole();
}