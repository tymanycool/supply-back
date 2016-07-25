package net.shopin.supply.persistence;

import java.util.Map;

import net.shopin.supply.domain.entity.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long sid);
    
    Permission selectByParam(Map<String,Object> map);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}