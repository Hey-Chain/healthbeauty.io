<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>就诊信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/dateoperation.js" type="text/javascript"></script>
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
				$("#attendanceTime").val(getCurrentDateTime(new Date()));
				$('input[name=attendanceType]').get(0).checked = true;
			}
			
			$('#memberCard').keyup(function (event) { 
			   if (event.keyCode == "13") { 
				   event.preventDefault();
				   //获取客户信息
					loading('正在获取客户，请稍等...');
					
					var inputMemberCard = $('#memberCard').val();
					$.getJSON('${ctx}/crm/crmCustomer/byMemberCard/'+inputMemberCard , function(data) {
						top.$.jBox.closeTip();
						$('#customerId').val('');
						$('#customerName').val('');
						
						if(!data.isNewRecord){
							$('#customerId').val(data.id);
							$('#customerName').val(data.customerName);
						}else{
							$('#memberCard').val('');
							$("#messageBox").text("没有找到该卡号信息，稍后先确认会员信息！");
						}
					}).fail(function() {
						top.$.jBox.closeTip();
						$("#messageBox").text("获取客户信息失败，请稍后再试！");
					});
			   }
			})
		});
		
		function saveFrom(){
			$('#inputForm').submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/attendance/">就诊信息列表</a></li>
		<li class="active"><a href="${ctx}/oa/attendance/form?id=${attendance.id}">就诊信息<shiro:hasPermission name="oa:attendance:edit">${not empty attendance.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:attendance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="attendance" action="${ctx}/oa/attendance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCard" htmlEscape="false" maxlength="64" autocomplete="off" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group"> 	
			<label class="control-label">客户：</label>
			<div class="controls">
				<form:select path="customerId" class="input-mini">
					<form:options items="${customerList}" itemLabel="customerName" itemValue="id" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">就诊时间：</label>
			<div class="controls">
				<input id="attendanceTime" name="attendanceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${attendance.attendanceTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">就诊类型：</label>
			<div class="controls">
				<form:radiobuttons path="attendanceType" items="${fns:getDictList('attendance_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">咨询师：</label>
			<div class="controls">
				<form:select path="counselorId" class="input-mini">
					<form:options items="${counselorList}" itemLabel="name" itemValue="id" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预约编号：</label>
			<div class="controls">
				<form:hidden path="reservationId" />
				<form:input path="reservationNumber" htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-mini">
					<form:options items="${fns:getDictList('attendance_status')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:attendance:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveFrom()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>