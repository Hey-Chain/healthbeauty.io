<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医疗项目管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaProject/">医疗项目列表</a></li>
		<shiro:hasPermission name="oa:oaProject:edit"><li><a href="${ctx}/oa/oaProject/form">医疗项目添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaProject" action="${ctx}/oa/oaProject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目编号：</label>
				<form:input path="projectCode" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目编号(助记码)</th>
				<th>项目名称</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="oa:oaProject:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaProject">
			<tr>
				<td><a href="${ctx}/oa/oaProject/form?id=${oaProject.id}">
					${oaProject.projectCode}
				</a></td>
				<td>
					${oaProject.projectName}
				</td>
				<td>
					<fmt:formatDate value="${oaProject.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaProject.remarks}
				</td>
				<shiro:hasPermission name="oa:oaProject:edit"><td>
    				<a href="${ctx}/oa/oaProject/form?id=${oaProject.id}">修改</a>
					<a href="${ctx}/oa/oaProject/delete?id=${oaProject.id}" onclick="return confirmx('确认要删除该医疗项目吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>