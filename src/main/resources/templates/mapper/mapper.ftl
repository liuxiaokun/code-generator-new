package ${basePackage}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basePackage}.entity.${EntityName};
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@CacheNamespaceRef(${EntityName}Mapper.class)
public interface ${EntityName}Mapper extends BaseMapper<${EntityName}> {
}