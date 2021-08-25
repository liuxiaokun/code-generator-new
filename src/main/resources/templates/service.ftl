package ${basePackage}.service;

import ${basePackage}.dto.${entityName}DTO;
import ${basePackage}.entity.${entityName};
import ${basePackage}.exception.BizException;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional

/**
 * ${tableComment}
 *
 * @author ${author}
 * @version ${version}
 * @date ${datetime}
*/
public interface ${entityName}Service {

    /**
     * 查询列表
     *
     * @return List<${entityName}DTO>
     */
    List<${entityName}DTO> findAll();

    /**
     * 分页查询列表
     *
     * @param pageNum  第几页
     * @param pageSize 一页几条
     * @param orderBy  排序，例如 "id desc, name asc"
     * @return PageInfo<${entityName}DTO>
     */
    PageInfo<${entityName}DTO> findByPage(int pageNum, int pageSize, String orderBy);

    /**
     * 根据ID查询
     *
     * @param id id
     * @return ${entityName}DTO
     */
    Optional<${entityName}DTO> findById(Serializable id);

    /**
     * 保存一条
     *
     * @param dto dto
     * @return boolean
     */
    boolean save(${entityName}DTO dto);

    /**
     * 全量修改
     *
     * @param dto dto
     * @return boolean
     */
    boolean updateById(${entityName}DTO dto);

    /**
     * 保存多条
     *
     * @param dtos dto
     * @return boolean
     */
    boolean saveAll(List<${entityName}DTO> dtos) throws BizException;

    boolean deleteById(Serializable id);
}