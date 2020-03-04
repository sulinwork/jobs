package com.sulin.jobweb.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;

/***
 *
 * @author sulin
 */
public interface CommonMapper<E> extends Mapper<E>, SelectByExampleMapper<E> {
}
