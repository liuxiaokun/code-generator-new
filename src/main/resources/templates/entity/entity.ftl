package ${basePackage}.entity;

import lombok.Data;
import ${basePackage}.entity.base.BaseEntity;
<#list importList as import>
import ${import};
</#list>

/**
 * ${tableComment}
 *
 * @author ${author}
 * @version ${version}
 * @date ${datetime}
 */
@Data
public class ${EntityName} extends BaseEntity {

<#list fieldList as field>
    /**
     * ${field.comment}
     */
    private ${field.type} ${field.name};

</#list>
}