<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>付款单管理</title>
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
		<li class="active"><a href="${ctx}/finance/payment/">付款单列表</a></li>
		<shiro:hasPermission name="finance:payment:edit"><li><a href="${ctx}/finance/payment/form">付款单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="payment" action="${ctx}/finance/payment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCard" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>付款时间：</label>
				<input name="paymentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${payment.paymentTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>付款方式：</label>
				<form:input path="paymentType" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>付款单号</th>
				<th>付款时间</th>
				<th>付款方式</th>
				<th>应款金额</th>
				<th>付款金额</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="finance:payment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="payment">
			<tr>
				<td>
					${payment.customerName}
				</td>
				<td>
					${payment.memberCard}
				</td>
				<td><a href="${ctx}/finance/payment/form?id=${payment.id}">
					${payment.paymentNumber}</a>
				</td>
				<td>
					<fmt:formatDate value="${payment.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(payment.paymentType, 'payment_type', '')}
				</td>
				<td>
					<fmt:formatNumber value="${payment.payable}" />
				</td>
				<td>
					<fmt:formatNumber value="${payment.amount}" />
				</td>
				<td>
					<fmt:formatDate value="${payment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${payment.remarks}
				</td>
				<shiro:hasPermission name="finance:payment:edit"><td>
    				<a href="${ctx}/finance/payment/form?id=${payment.id}">修改</a> | 
					<a href="${ctx}/finance/payment/delete?id=${payment.id}" onclick="return confirmx('确认要删除该付款单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>