<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费单管理</title>
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
		<li class="active"><a href="${ctx}/finance/bill/">收费单列表</a></li>
		<%-- <shiro:hasPermission name="finance:bill:edit"><li><a href="${ctx}/finance/bill/form">收费单添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="bill" action="${ctx}/finance/bill/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCard" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>客户：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>开单时间：</label>
				<input name="billTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${bill.billTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>客户</th>
				<th>会员卡号</th>
				<th>收费单</th>
				<th>开单时间</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="finance:bill:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bill">
			<tr>
				<td>
					${bill.customerName}
				</td>
				<td>
					${bill.memberCard}
				</td>
				<td><a href="${ctx}/finance/bill/form?id=${bill.id}">
					${bill.billNumber}</a>
				</td>
				<td>
					<fmt:formatDate value="${bill.billTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${bill.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bill.remarks}
				</td>
				<shiro:hasPermission name="finance:bill:edit"><td>
					<a href="${ctx}/finance/payment/form?billId=${bill.id}">付款</a> | 
    				<a href="${ctx}/finance/bill/form?id=${bill.id}">修改</a> | 
					<a href="${ctx}/finance/bill/delete?id=${bill.id}" onclick="return confirmx('确认要删除该收费单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>