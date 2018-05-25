<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户信息管理</title>
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
		<li class="active"><a href="${ctx}/crm/crmCustomer/">客户信息列表</a></li>
		<shiro:hasPermission name="cust:crmCustomer:edit"><li><a href="${ctx}/crm/crmCustomer/form">客户信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="crmCustomer" action="${ctx}/crm/crmCustomer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCardNumber" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="phone" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>会员卡号</th>
				<th>姓名</th>
				<th>手机号码</th>
				<th>性别</th>
				<th>年龄</th>
				<th>出生日期</th>
				<th>备注信息</th>
				<shiro:hasPermission name="cust:crmCustomer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="crmCustomer">
			<tr>
				<td>
					${crmCustomer.memberCardNumber}
				</td>
				<td><a href="${ctx}/crm/crmCustomer/form?id=${crmCustomer.id}">
					${crmCustomer.customerName}
				</a></td>
				<td>
					${crmCustomer.phone}
				</td>
				<td>
					${fns:getDictLabel(crmCustomer.sex, 'sex', '')}
				</td>
				<td>
					${crmCustomer.age}
				</td>
				<td>
					<fmt:formatDate value="${crmCustomer.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${crmCustomer.remarks}
				</td>
				<shiro:hasPermission name="cust:crmCustomer:edit"><td>
    				<a href="${ctx}/crm/crmCustomer/form?id=${crmCustomer.id}">修改</a> | 
    				<a href="${ctx}/oa/reservation/form?customerId=${crmCustomer.id}">预约</a> | 
    				<a href="${ctx}/oa/attendance/form?customerId=${crmCustomer.id}">就诊</a> | 
    				<a href="${ctx}/crm/crmCustomer/delete?id=${crmCustomer.id}" onclick="return confirmx('确认要删除该客户信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>