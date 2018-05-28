<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fncrm" uri="/WEB-INF/tlds/fncrm.tld" %>
<html>
<head>
	<title>客户信息管理</title>
	<meta name="decorator" content="default"/>
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
			
			getMemberBalance();
		});
		
		// 获取余额
		function getMemberBalance(){			
			var memberCardId = $('#memberCardId').val();
			if(memberCardId){
				$.getJSON('${ctx}/crm/crmMemberCard/getBalance/'+memberCardId , function(data) {
					if(typeof data == 'number'){
						$('#divBalance').text(data.toFixed(2));
					}else{
						$("#divMessage").text("获取会员卡余额失败！");
					}
				}).fail(function() {
					$("#divMessage").text("请求会员卡余额失败！");
				});
			}
		}

		function linkMemberCardBox(id){
			if($('#memberCardId').val()) 
				$("#messageBox").text("已有卡号.");
			
			$.jBox($("#linkMemberCardBox").html(), {title:"开卡", buttons:{"关闭":true}, submit: function(a,b,c,d){
				console.dir('linkMemberCardBox:'+id);
			}});
			$("#openCustomerId").val(id);
		}
		
		function getMemberCardInfo(inputMemberCard, isSaveMemberCard){
			loading('正在获取会员卡信息，请稍等...');
			$.getJSON('${ctx}/crm/crmMemberCard/byMemberCard/'+inputMemberCard , function(data) {
				top.$.jBox.closeTip();
				$('#openMemberCardId').val('');
				$('#openMemberCardNumber').val('');
				
				if(!data.isNewRecord){
					$('#openMemberCardId').val(data.id);
					$('#openMemberCardNumber').val(data.cardNumber);
					
					submitMemberCard();
				}else{
					$("#divMessage").text("没有找到该卡号信息，稍后先确认该卡号是否存在！");
				}
			}).fail(function() {
				top.$.jBox.closeTip();
				$("#divMessage").text("获取客户信息失败，请稍后再试！");
			});
		}
		
		function checkMemberCardInfo(inputMemberCard){
			if(window.event.code == 'Enter' || window.event.keyCode === 13){
				window.event.preventDefault();
				if(!inputMemberCard){
					return false;
				}
				getMemberCardInfo(inputMemberCard, false);
				return false;
			}
			return this;
		}
		
		function submitMemberCard(){
			var inputMemberCard = $('#openMemberCardNumber').val(); 
			if(!inputMemberCard){
				$("#divMessage").text("开卡失败，会员卡信息无效！");
				return false;
			}
			
			var memberCardId = $('#openMemberCardId').val(); 
			if(!memberCardId){
				getMemberCardInfo(inputMemberCard, true);				
			}else{//更新到会员信息界面上
				$('#memberCardId').val(memberCardId);
				$('#memberCardNumber').val(inputMemberCard);
				
				$('#linkMemberCardBoxForm').submit();
			}
		}
		
		function updateMemberBalanceBox(){
			$.jBox($("#memberBalanceBox").html(), {title:"充值", buttons:{"关闭":true}, submit: function(){
				console.dir('updateMemberBalanceBox:'+$('#inchargeMemberCardId').val());
			}});
			$('#inchargeMemberCardId').val($('#memberCardId').val());
		}
		
		function inchargeMemberCard(){
			var inputMemberCard = $('#inchargeMemberCardId').val(); 
			if(!inputMemberCard){
				$("#divInchargeMessage").text("充值失败，会员卡信息无效！");
				return false;
			}

			var inputMoney = $('#inchargeAmount').val(); 
			if(!inputMoney || inputMoney < 1){
				$("#divInchargeMessage").text("充值失败，充值金额无效！");
				return false;
			}

			$('#memberBalanceForm').submit();
		}

		function closeMemberCard(){
			// 发送任务完成请求
		    $.post('${ctx}/crm/crmCustomer/closeMemberCard', {
		    	customerId: $('#id').val(),
		        clearAmount: $('#divBalance').text()
		    }, function(data) {
		    	if(data == 'ok'){
		    		top.$.jBox.tip("退卡成功");
		    		$('#memberCardId').val('');
		    		$('#memberCardNumber').val('');
			        $('#divBalance').text('0.00');
		    	}else{
		    		top.$.jBox.tip(data);	
		    	}
		    });
			return false;
		}
		
		function saveFrom(){
			$('#inputForm').submit();
		}

	</script>
	<script type="text/template" id="linkMemberCardBox">
		<form id="linkMemberCardBoxForm" action="${ctx}/crm/crmCustomer/linkMemberCard" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在开卡，请稍等...');"><br/>
			<input id="openCustomerId" type="hidden" name="openCustomerId" />
			<input id="openMemberCardId" type="hidden" name="openMemberCardId" />
			<input id="openMemberCardNumber" type="text" name="openMemberCardNumber" autocomplete="off" onkeypress="checkMemberCardInfo(this.value)" />
			<br/><br/>
			<div id="divMessage"></div><br/>
			<input id="linkMemberCardBoxSubmit" class="btn btn-primary" type="button" onclick="submitMemberCard()" value="保    存   "/>　　
		</form>
	</script>
	<script type="text/template" id="memberBalanceBox">
		<form id="memberBalanceForm" action="${ctx}/crm/crmMemberCard/addMemberBalance" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在充值，请稍等...');"><br/>
			<input id="inchargeMemberCardId" type="hidden" name="inchargeMemberCardId" />
			金额：<input id="inchargeAmount" type="number" name="inchargeAmount" />
			<br/><br/>
			<div id="divInchargeMessage"></div><br/>
			<input id="inchargeSubmit" class="btn btn-primary" type="button" onclick="inchargeMemberCard()" value="   保    存   "/>　　
		</form>
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/crm/crmCustomer/">客户信息列表</a></li>
		<li class="active"><a href="${ctx}/crm/crmCustomer/form?id=${crmCustomer.id}">客户信息<shiro:hasPermission name="cust:crmCustomer:edit">${not empty crmCustomer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cust:crmCustomer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="crmCustomer" action="${ctx}/crm/crmCustomer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:hidden path="memberCardId"/> 
				<form:input path="memberCardNumber" htmlEscape="false" maxlength="32" class="input-medium" readonly="true" /><span class="help-inline"><font color="red">*</font> </span>
				
				<table style="width:250px; height: 50px; margin-top:10px; ">
					<tr>
						<td style="width:150px">余额：<span id="divBalance">0.00</span> 元</td>
						<td style="width:50px"><a href="javascript:updateMemberBalanceBox()">充值</a></td>
						<td style="width:50px"><a href="javascript:linkMemberCardBox('${crmCustomer.id}')">开卡</a></td>
						<td style="width:50px"><a href="#" onclick="return confirmx('确认要退卡 - 会清除所有余额?', closeMemberCard);">退卡</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="customerName" htmlEscape="false" maxlength="32" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="32" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-medium ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input path="age" htmlEscape="false" maxlength="11" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户组：</label>
			<div class="controls">
				<form:select path="customerGroup" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_group')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址：</label>
			<div class="controls">
				<form:textarea path="address" htmlEscape="false" rows="2" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信号：</label>
			<div class="controls">
				<form:input path="wechat" htmlEscape="false" maxlength="32" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${crmCustomer.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="idcard" htmlEscape="false" maxlength="16" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户组：</label>
			<div class="controls">
				<form:select path="customerGroup" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_group')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案号码：</label>
			<div class="controls">
				<form:input path="customerNo" htmlEscape="false" maxlength="16" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源：</label>
			<div class="controls">
				<form:select path="sourceType" class="input-medium ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('source_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">介绍人：</label>
			<div class="controls">
				<form:input path="introducer" htmlEscape="false" maxlength="32" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">qq号码：</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="16" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职业：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" maxlength="16" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cust:crmCustomer:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveFrom()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>