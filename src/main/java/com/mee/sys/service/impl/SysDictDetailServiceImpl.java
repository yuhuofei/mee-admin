package com.mee.sys.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.mee.common.util.DateUtil;
import com.mee.common.util.MeeResult;
import com.mee.core.configuration.ShiroUtils;
import com.mee.core.dao.DBSQLDao;
import com.mee.core.model.Page;
import com.mee.sys.entity.SysDictDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mee.common.service.SeqGenServiceImpl;
import com.mee.common.util.ResultBuild;

import java.lang.String;
import java.lang.Integer;
import java.time.LocalDateTime;

/**
 * 数据字典详情(SysDictDetail2) 业务接口
 *
 * @author  shadow
 * @version v1.0
 * @date    2023-05-15 10:27:31
*/
@Service
public class SysDictDetailServiceImpl {

    /**
    *   日志
    */
    private static final Logger LOG = LoggerFactory.getLogger(SysDictDetailServiceImpl.class);

    /**
    *   Dao
    */
    @Autowired
    public DBSQLDao dbSQLDao;
    
    /**
    * 主键生成器
    */
    @Autowired
    public SeqGenServiceImpl seqGenService;

    /**
     * 查询数据字典详情列表
     *
     * @param SysDictDetail2(or Map) 数据字典详情
     * @return 数据字典详情分页集合
    */
    public MeeResult list(Integer page_no, Integer page_size , String dict_id, String label, String value){
      LOG.info("接收到参数 {},{}, {},{},{}",page_no,page_size,dict_id,label,value);
      Map<String,Object> param = new HashMap<String,Object>(11,1);
      param.put("dict_id",(null==dict_id||"".equals(dict_id))?null:dict_id );
      param.put("label",(null==label||"".equals(label))?null:"%"+label+"%" );
      param.put("value",(null==value||"".equals(value))?null:"%"+value+"%" );
      Page list = dbSQLDao.list("com.mee.xml.SysDictDetail.findList",param,page_no,page_size);
      return ResultBuild.build(list);
    }

    /**
     * 数据字典详情::按主键查询
     *
     * @param id 数据字典详情主键
     * @return 数据字典详情
    */
    public MeeResult findById(String id){
      LOG.info("开始查询:{}",id);
      if(null==id || "".equals(id)){
        LOG.error("必要参数为空:{}",id);
        return ResultBuild.fail("必要参数为空[id]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("id",id);
      SysDictDetail sysDictDetail2 = dbSQLDao.findOne("com.mee.xml.SysDictDetail.findById", param);
      return ResultBuild.build(sysDictDetail2);
    }

    /**
     * 数据字典详情::新增
     *
     * @param SysDictDetail2(or Map) 数据字典详情
     * @return 插入条数
    */
    public MeeResult add(SysDictDetail sysDictDetail2){
      LOG.info("接收到参数 {}", sysDictDetail2);
      if(null == sysDictDetail2 || null==sysDictDetail2.getDict_id() || null==sysDictDetail2.getLabel() || null==sysDictDetail2.getValue() || null==sysDictDetail2.getSort() ){
          return ResultBuild.fail("参数缺失请检查~");
      }
      final LocalDateTime now = DateUtil.now();
      final String user_id = ShiroUtils.getUserId();
      // 主键
      sysDictDetail2.setId(seqGenService.genShortPrimaryKey());
      // 通用字段
      sysDictDetail2.setCreate_by(Long.parseLong(user_id));
      sysDictDetail2.setUpdate_by(Long.parseLong(user_id));
      sysDictDetail2.setCreate_time(now);
      sysDictDetail2.setUpdate_time(now);
      int insert_count = dbSQLDao.insert("com.mee.xml.SysDictDetail.insert",sysDictDetail2);
      return ResultBuild.build(insert_count);
    }


    /**
     * 数据字典详情::修改
     *
     * @param SysDictDetail2(or Map) 数据字典详情
     * @return 更新条数
    */
    public MeeResult update(SysDictDetail sysDictDetail2){
      LOG.info("接收到参数 {}",sysDictDetail2);
      if(null == sysDictDetail2 || null==sysDictDetail2.getId() || null==sysDictDetail2.getDict_id()||null==sysDictDetail2.getLabel()||null==sysDictDetail2.getValue()||null==sysDictDetail2.getSort() ){
          return ResultBuild.fail("必要参数缺失，请检查~");
      }
      final LocalDateTime now = DateUtil.now();
      final String user_id = ShiroUtils.getUserId();

      // 通用字段
      sysDictDetail2.setUpdate_by(Long.parseLong(user_id));
      sysDictDetail2.setUpdate_time(now);

      //int update_count = dbSQLDao.update("com.mee.module.sys.mapper.sys_dict_detail2.update",sysDictDetail2);
      int update_count = dbSQLDao.update("com.mee.xml.SysDictDetail.update",sysDictDetail2);
      LOG.info("已更新数据字典详情明细：{}->{}条",sysDictDetail2,update_count);
      return ResultBuild.build(update_count);
    }

    /**
     * 数据字典详情::删除
     *
     * @id 数据字典详情 主键
     * @return 删除条数
    */
    public MeeResult deleteById(String id){
      LOG.info("开始查询:{}",id);
      if(null==id || "".equals(id)){
          LOG.error("必要参数为空:{}",id);
          return ResultBuild.fail("必要参数为空[id]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("id",id);
      int delelet_count = dbSQLDao.delete("com.mee.xml.SysDictDetail.deleteById", param);
      return ResultBuild.build(delelet_count);
    }

    /**
     * 数据字典详情::批量删除
     *
     * @ids 数据字典详情 主键集合
     * @return 删除条数
    */
    public MeeResult deleteBatch(String[] ids){
      if( null==ids || 0==ids.length ){
        LOG.error("必要参数为空:{}",ids);
        return ResultBuild.fail("必要参数为空[id]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("list",ids);
      //int delete_count = dbSQLDao.delete("com.mee.module.sys.mapper.sys_dict_detail2.deleteBatch", param);
      int delete_count = dbSQLDao.delete("com.mee.xml.SysDictDetail.deleteBatch", param);
      LOG.info("已删除记录{}->{}条",ids,delete_count);
      return ResultBuild.build(delete_count);
    }

}
