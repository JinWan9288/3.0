package com.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Role;

import java.util.List;

/**
 * <p>
 *  role服务类
 * </p>
 *
 * @author root
 * @since 2024-02-14
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getMenu(Integer roleId);
}
