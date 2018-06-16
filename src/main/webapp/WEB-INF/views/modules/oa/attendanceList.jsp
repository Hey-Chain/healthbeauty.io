<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>就诊信息管理</title>
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
		<li class="active"><a href="${ctx}/oa/attendance/">就诊列表</a></li>
		<shiro:hasPermission name="oa:attendance:edit"><li><a href="${ctx}/oa/attendance/form">就诊登记</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="attendance" action="${ctx}/oa/attendance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCard" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>客户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="64" class="input-medium" autocomplete="false"/>
			</li>
			<li><label>就诊时间：</label>
				<input name="attendanceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${attendance.attendanceTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('attendance_status')}" itemLabel="label" itemValue="value" htmlEscape="false" />
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
				<th>客户姓名</th>
				<th>会员卡</th>
				<th>就诊时间</th>
				<th>预约编号</th>
				<th>就诊类型</th>
				<th>状态</th>
				<shiro:hasPermission name="oa:attendance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="attendance">
			<tr>
				<td><a href="${ctx}/oa/attendance/form?id=${attendance.id}">
					${attendance.customerName}</a>
				</td>
				<td>
					${attendance.memberCard}
				</td>
				<td>
					<fmt:formatDate value="${attendance.attendanceTime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					${attendance.reservationNumber}
				</td>
				<td>
					${fns:getDictLabel(attendance.attendanceType, 'attendance_type', '')}
				</td>
				<td>
					${fns:getDictLabel(attendance.status, 'attendance_status', '')}
				</td>
				<shiro:hasPermission name="oa:attendance:edit"><td>
    				<a href="${ctx}/oa/attendance/form?id=${attendance.id}">修改</a> |
    				<a href="${ctx}/finance/bill/form?attendanceId=${attendance.id}&customerId=${attendance.customerId}">开单</a> 
					<%-- | <a href="${ctx}/oa/attendance/delete?id=${attendance.id}" onclick="return confirmx('确认要删除该就诊信息吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>