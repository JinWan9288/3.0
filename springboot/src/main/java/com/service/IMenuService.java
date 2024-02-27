package com.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Menu;
import java.util.List;

/**
 * <p>
 *  role服务类
 * </p>
 *
 * @author root
 * @since 2024-02-14
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> findMenus(String menuname);
}
