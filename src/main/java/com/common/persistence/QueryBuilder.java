package com.common.persistence;

import com.common.persistence.condition.Condition;
import com.common.persistence.contants.DBConstants;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.util.CollectionsUtil;

import javax.persistence.Entity;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @since 2015年12月2日
 */

public class QueryBuilder {

	private static final String DEFAULT_ALIAS = "q";
	private StringBuilder builder;
	private String alias;
	private boolean distinct;

	private List<Condition> conditions;

	private Map<String, Object> parameters;

	public QueryBuilder() {
		init();
	}

	private void init() {
		if (builder == null) {
			builder = new StringBuilder();
		}

		alias = DEFAULT_ALIAS;
		distinct = true;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public Map<String, Object> getParameters() {
		if (parameters == null) {
			parameters = CollectionsUtil.newHashMap();
		}
		return parameters;
	}

	public List<Condition> getConditions() {
		if (conditions == null) {
			conditions = CollectionsUtil.newArrayList();
		}
		return conditions;
	}

	public void addConditions(List<Condition> conditions) {
		if(conditions != null)
		{
			getConditions().addAll(conditions);
		}
	}

	private <T> String getEntityName(Class<T> entityClass) {
		String entityname = entityClass.getName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !entity.name().isEmpty()) {
			return entity.name();
		}
		return entityname.substring(entityname.lastIndexOf(".") + 1);
	}

	private void assembleQuery(StringBuilder builder, Map<String, Object> parameters, int suffix, Condition condition) {
		if (condition.getName() != null && condition.getOperate() != null) {
			if (condition.hasINOperate()) {
				putParameters4InCondition(condition, builder, parameters,suffix);
			}
			else if(condition.hasFindInSet()){
				builder.append(DBConstants.AND).append(condition.getOperate()).append("(:").append(condition.getName() + suffix).append(",").append(alias).append(DBConstants.DOT).append(condition.getName()).append(") >0");
				parameters.put(condition.getName() + suffix, condition.getValue());
			}
			else {
				if (condition.hasLikeOperate()) {
					builder.append(DBConstants.AND).append("lower(").append(alias).append(DBConstants.DOT)
							.append(condition.getName()).append(")");
				} else {
					builder.append(DBConstants.AND).append(alias).append(DBConstants.DOT).append(condition.getName());

				}
				builder.append(condition.getOperate()).append(" :").append("temp_" + suffix);

				parameters.put("temp_" + suffix, condition.getValue());
			}

		}
	}

	private void putParameters4InCondition(Condition condition, StringBuilder builder, Map<String, Object> parameters,int suffix) {
		builder.append(DBConstants.AND).append(alias).append(DBConstants.DOT).append(condition.getName())
				.append(condition.getOperate()).append(" (");

		if (condition.getValue().getClass().isArray()) {
			Object[] values = (Object[]) condition.getValue();
			for (int i = 0; i < values.length; i++) {
				builder.append(" :").append("temp_" + (i + suffix) ).append(",");
				parameters.put("temp_" + (i + suffix), values[i]);
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" )");
		}

		else if (condition.getValue() instanceof List<?>) {
			List<?> values = (List<?>) condition.getValue();
			for (int i = 0; i < values.size(); i++) {
				builder.append(" :").append("temp_" + (i + suffix)).append(",");

				parameters.put("temp_" + (i + suffix), values.get(i));
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" )");
		}

		else {
			// logger.error("Unsupported class type: " +
			// condition.getValue().getClass());
		}

	}

	public <T> String from(Class<T> entityClass) {
		String entityName = getEntityName(entityClass);
		builder.append(DBConstants.SELECT);
		if (distinct) {
			builder.append(DBConstants.DISTINCT);
		}
		builder.append(alias).append(DBConstants.FROM).append(entityName).append(" ").append(alias);

		return builder.toString();
	}

	public String where() {
		builder.append(DBConstants.WHERE);

		getConditions();
		getParameters();

		if (conditions != null && conditions.size() > 0) {
			int suffix = 1;
			for (Condition condition : conditions) {
				assembleQuery(builder, parameters, suffix, condition);
				if (condition.getValue() instanceof List<?>) {
					List<?> list = (List<?>) condition.getValue();
					suffix = suffix + list.size();
				}else if(condition.getValue().getClass().isArray()){
					Object[] objects = (Object[]) condition.getValue();
					suffix = suffix + objects.length;
				}else{
					suffix++;
				}
			}
		}
		return builder.toString();
	}

	public void orderBy(PageModel page) {
		
		if (page != null && (!StringUtils.isEmpty(page.getSort())) && (!StringUtils.isEmpty(page.getOrder()))) {
			builder.append(DBConstants.ORDER_BY)
				   .append(alias)
				   .append(DBConstants.DOT)
				   .append(page.getSort())
				   .append(" ")
				   .append(page.getOrder());
			if(page.getSecondSort() != null){
				builder.append(",")
						.append(alias)
						.append(DBConstants.DOT)
						.append(page.getSecondSort());
				if(page.getSecondOrder() != null){
					builder.append(" ").append(page.getSecondOrder());
				}
			}
		}
	}

	@Override
	public String toString() {
		return builder.toString();
	}

}
