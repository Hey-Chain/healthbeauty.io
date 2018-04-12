<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>付款单管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/dateoperation.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/biz.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			if(!getQueryString('id')){
				$("#paymentTime").val(getCurrentDateTime(new Date()));
				$("#billNumber").val(getBillNo());
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/finance/payment/">付款单列表</a></li>
		<li class="active"><a href="${ctx}/finance/payment/form?id=${payment.id}">付款单<shiro:hasPermission name="finance:payment:edit">${not empty payment.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="finance:payment:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="payment" action="${ctx}/finance/payment/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">收费单：</label>
			<div class="controls">
				<form:hidden path="billId" />
				<form:input path="billNumber" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款时间：</label>
			<div class="controls">
				<input id="paymentTime" name="paymentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${payment.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款方式：</label>
			<div class="controls">
				<form:select path="paymentType" class="input-mini">
					<form:options items="${fns:getDictList('payment_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应付金额：</label>
			<div class="controls">
				<form:input path="payable" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="finance:payment:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>