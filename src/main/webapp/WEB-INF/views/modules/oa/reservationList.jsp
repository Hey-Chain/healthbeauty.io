<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预约管理</title>
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
		<li class="active"><a href="${ctx}/oa/reservation/">预约列表</a></li>
		<shiro:hasPermission name="oa:reservation:edit"><li><a href="${ctx}/oa/reservation/form">预约添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reservation" action="${ctx}/oa/reservation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCard" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>客户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>预约时间：</label>
				<input name="reservationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${reservation.reservationTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>预约编号：</label>
				<form:input path="reservationNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-bordered table-condensed">
		<thead>
			<tr>
				<th>预约时间</th>
				<th>编号</th>
				<th>客户</th>
				<th>会员卡号</th>
				<th>医生</th>
				<th>预约项目</th>
				<th>状态</th>
				<th>备注</th>
				<shiro:hasPermission name="oa:reservation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reservation">
			<tr>
				<td>
					<fmt:formatDate value="${reservation.reservationTime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td><a href="${ctx}/oa/reservation/form?id=${reservation.id}">${reservation.reservationNumber}</a>
				</td>
				<td>
					${reservation.customerName}
				</td>
				<td>
					${reservation.memberCard}
				</td>
				<td>
					${reservation.doctorName}
				</td>
				<td>
					${reservation.projectName}
				</td>
				<td>
					${fns:getDictLabel(reservation.status, 'remedy_flag', '')}
				</td>
				<td>
					${reservation.remarks}
				</td>
					<shiro:hasPermission name="oa:reservation:edit"><td>
					<a href="${ctx}/oa/attendance/form?reservationId=${reservation.id}&reservationNumber=${reservation.reservationNumber}&customerId=${reservation.customerId}">就诊登记</a> | 
					<a href="${ctx}/oa/reservation/delete?id=${reservation.id}" onclick="return confirmx('确认要删除该预约吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>