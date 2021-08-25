package ${basePackage}.service;

import ${basePackage}.dto.${EntityName}DTO;
import ${basePackage}.entity.${EntityName};
import ${basePackage}.exception.BizException;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional

/**
 * ${tableComment} Service
 *
 * @author ${author}
 * @version ${version}
 * @date ${datetime}
*/
public interface ${EntityName}Service {

    /**
     * 查询列表
     *
     * @return 实体数据列表
     */
    List<${EntityName}DTO> findAll();

    /**
     * 分页查询列表
     *
     * @param pageNum  第几页
     * @param pageSize 一页几条
     * @param orderBy  排序，例如 "id desc, name asc"
     * @return 实体的分页列表
     */
    PageInfo<${EntityName}DTO> findByPage(int pageNum, int pageSize, String orderBy);

    /**
     * 根据ID查询
     *
     * @param id id
     * @return 单个实体
     */
    Optional<${EntityName}DTO> findById(Serializable id);

    /**
     * 保存一条
     *
     * @param dto dto
     * @return true stands for save successfully， false stands for save failed。
     */
    boolean save(${EntityName}DTO dto);

    /**
     * 全量修改
     *
     * @param dto dto
     * @return true stands for update successfully， false stands for update failed。
     */
    boolean updateById(${EntityName}DTO dto);

    /**
     * 保存多条， 开启事务。
     *
     * @param dtos dto
     * @return true stands for saveAll method successfully， false stands for saveAll method failed。
     */
    boolean saveAll(List<${EntityName}DTO> dtos) throws BizException;

    /**
     * 根据id删除一条
     *
     * @param id 主键id
     * @return true stands for delete successfully， false stands for delete failed。
     */
    boolean deleteById(Serializable id);

    /**
     * 根据根据id列表全部删除，开启事务。
     *
     * @param ids 主键id集合
     * @return true stands for delete successfully， false stands for delete failed。
     */
    boolean deleteAll(List<Serializable> ids) throws BizException;
}