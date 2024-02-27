package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author root
 * @since 2024-02-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
