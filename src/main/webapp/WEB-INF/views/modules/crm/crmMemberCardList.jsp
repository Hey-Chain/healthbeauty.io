<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员卡管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/crm/crmMemberCard/">会员卡列表</a></li>
		<shiro:hasPermission name="crm:crmMemberCard:edit"><li><a href="${ctx}/crm/crmMemberCard/form">会员卡添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="crmMemberCard" action="${ctx}/crm/crmMemberCard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>卡号：</label>
				<form:input path="cardNumber" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>使用标记：</label>
				<form:select path="useFlag" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('use_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>卡号</th>
				<th>使用标记</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="crm:crmMemberCard:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="crmMemberCard">
			<tr>
				<td><a href="${ctx}/crm/crmMemberCard/form?id=${crmMemberCard.id}">
					${crmMemberCard.cardNumber}
				</a></td>
				<td>
					${fns:getDictLabel(crmMemberCard.useFlag, 'use_flag', '')}
				</td>
				<td>
					<fmt:formatDate value="${crmMemberCard.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${crmMemberCard.remarks}
				</td>
				<shiro:hasPermission name="crm:crmMemberCard:edit"><td>
    				<a href="${ctx}/crm/crmMemberCard/form?id=${crmMemberCard.id}">修改</a>
					<a href="${ctx}/crm/crmMemberCard/delete?id=${crmMemberCard.id}" onclick="return confirmx('确认要删除该会员卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>