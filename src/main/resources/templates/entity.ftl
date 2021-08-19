package ${entityPackage}.entity;

import lombok.Data;
import ${entityPackage}.entity.base.BaseEntity;
<#list  importList as import>
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
public class ${entityName} extends BaseEntity {

<#list fieldList as field>
    /**
     * ${field.comment}
     */
    private ${field.type} ${field.name};

</#list>
}