package ${basePackage}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${basePackage}.dto.${EntityName}DTO;
import ${basePackage}.entity.${EntityName};
import ${basePackage}.exception.BizException;
import ${basePackage}.mapper.${EntityName}Mapper;
import ${basePackage}.service.${EntityName}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ${tableComment} Service Implement
 *
 * @author ${author}
 * @version ${version}
 * @date ${datetime}
 */
@Service
public class ${EntityName}ServiceImpl implements ${EntityName}Service {

    private final ${EntityName}Mapper ${entityName}Mapper;

    public ${EntityName}ServiceImpl(${EntityName}Mapper ${entityName}Mapper) {
        this.${entityName}Mapper = ${entityName}Mapper;
    }

    @Override
    public List<${EntityName}DTO> findAll() {
        List<${EntityName}> ${entityName}List = ${entityName}Mapper.selectList(new QueryWrapper<>());

        return ${entityName}List.stream().map(temp -> {
            ${EntityName}DTO dto = new ${EntityName}DTO();
            BeanUtils.copyProperties(temp, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public PageInfo<${EntityName}DTO> findByPage(int pageNum, int pageSize, String orderBy) {
        //orderBy 写数据库字段名而不是实体成员名，多个逗号分隔，例如 "id desc,name asc"
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<${EntityName}> ${entityName}List = ${entityName}Mapper.selectList(new QueryWrapper<>());

        return new PageInfo<>(${entityName}List.stream().map(temp -> {
            ${EntityName}DTO dto = new ${EntityName}DTO();
            BeanUtils.copyProperties(temp, dto);
            return dto;
        }).collect(Collectors.toList()));
    }

    @Override
    public Optional<${EntityName}DTO> findById(Serializable id) {
    ${EntityName} ${entityName} =${entityName}Mapper.selectById(id);

        if (null == ${entityName}) {
            return Optional.empty();
        }
        ${EntityName}DTO dto = new ${EntityName}DTO();
        BeanUtils.copyProperties(${entityName}, dto);
        return Optional.of(dto);
    }

    @Override
    public boolean save(${EntityName}DTO dto) {
    ${EntityName} ${entityName} =new ${EntityName}();
        BeanUtils.copyProperties(dto, ${entityName});
        int rows = ${entityName}Mapper.insert(${entityName});
        return rows == 1;
    }

    @Override
    public boolean updateById(${EntityName}DTO dto) {
    ${EntityName} ${entityName} =new ${EntityName}();
        BeanUtils.copyProperties(dto, ${entityName});
        int rows = ${entityName}Mapper.updateById(${entityName});
        return rows == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAll(List<${EntityName}DTO> dtos) throws BizException {
        for (${EntityName}DTO tem : dtos) {
        ${EntityName} ${entityName} =new ${EntityName}();
            BeanUtils.copyProperties(tem, ${entityName});
            int rows = ${entityName}Mapper.insert(${entityName});
            if (rows != 1) {
                throw new BizException("Batch insert failed， Rollback");
            }
        }
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        int rows = ${entityName}Mapper.deleteById(id);
        return rows == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAll(List<Serializable> ids) throws BizException {

        for (Serializable id : ids) {
            int rows = ${entityName}Mapper.deleteById(id);
            if (rows != 1) {
                throw new BizException("Batch delete failed， Rollback");
            }
        }
        return true;
    }
}