<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费单管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/dateoperation.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/biz.js" type="text/javascript"></script>
	<script type="text/javascript">
		var projectListData = ${fns:toJson(projectList)};
		var unitListData = ${fns:getDictListJson('unit')};
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
				$('input[name=isPaid]').get(0).checked = true;
				$("#billTime").val(getCurrentDateTime(new Date()));
				$("#billNumber").val(getBillNo());
			}			

			$('#memberCard').keyup(function (event) { 
			   if (event.keyCode == "13") { 
				   event.preventDefault();
				   //获取客户信息
					loading('正在获取客户，请稍等...');
					
					var inputMemberCard = $('#memberCard').val();
					$.getJSON('${ctx}/cust/crmCustomer/byMemberCard/'+inputMemberCard , function(data) {
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
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				var rowIdx = $(this).attr('rowidx');
				var ctrlName = $(this).attr('name');
				if(ctrlName == 'billitemList['+rowIdx+'].projectId'){
					var rowData = billItemListData[rowIdx];
					$(this).val(rowData.projectId);	
				}
				else if(ctrlName == 'billitemList['+rowIdx+'].doctorId'){
					var rowData = billItemListData[rowIdx];
					$(this).val(rowData.doctorId);
				}
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			
			if(!getQueryString('id')){				
				changeProject(idx);	
			}
		}
		
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
		
		function changeProject(idx){
			$("#billitemList"+idx+"_originalprice").val('0.00');
			$("#billitemList"+idx+"_unit").val('');
			
			var selectedProjectId = $("#billitemList"+idx+"_projectId").val();
			if(selectedProjectId){
				for(var i = 0; i < projectListData.length; i ++){
					if(projectListData[i].id == selectedProjectId){
						$("#billitemList"+idx+"_originalprice").val(projectListData[i].price);
						
						for(var j = 0; j < unitListData.length; j ++){
							if(unitListData[j].value==projectListData[i].unitId){
								$("#billitemList"+idx+"_unit").val(unitListData[j].label);
								break;
							}	
						} 
						break;
					}
				}
			}
		}

		function saveFrom(){
			$('#inputForm').submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/finance/bill/">收费单列表</a></li>
		<li class="active"><a href="${ctx}/finance/bill/form?id=${bill.id}">收费单<shiro:hasPermission name="finance:bill:edit">${not empty bill.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="finance:bill:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bill" action="${ctx}/finance/bill/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="attendanceId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">收费单号：</label>
			<div class="controls">
				<form:input path="billNumber" htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCard" htmlEscape="false" maxlength="64" class="input-xlarge " />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户：</label>
			<div class="controls">
				<form:hidden path="customerId" />
				<form:input path="customerName" htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开单时间：</label>
			<div class="controls">
				<input id="billTime" name="billTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${bill.billTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否付款：</label>
			<div class="controls">
				<form:radiobuttons path="isPaid" items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">收费明细：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>项目</th>
								<th>医生</th>
								<th>单位</th>
								<th>数量</th>
								<th>原价</th>
								<th>成交价</th>
								<th>备注</th>
								<shiro:hasPermission name="finance:bill:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="billitemList">
						</tbody>
						<shiro:hasPermission name="finance:bill:edit"><tfoot>
							<tr><td colspan="9"><a href="javascript:" onclick="addRow('#billitemList', billitemRowIdx, billitemTpl);billitemRowIdx = billitemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="billitemTpl">//<!--
						<tr id="billitemList{{idx}}">
							<td class="hide">
								<input id="billitemList{{idx}}_id" name="billitemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="billitemList{{idx}}_delFlag" name="billitemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
		<select id="billitemList{{idx}}_projectId" name="billitemList[{{idx}}].projectId" rowIdx={{idx}} class="input-small" onchange="changeProject({{idx}})">
			<c:forEach items="${projectList}" var="dict">
				<option value="${dict.id}">${dict.projectName}</option>
			</c:forEach>
		</select>
							</td>
							<td>
		<select id="billitemList{{idx}}_doctorId" name="billitemList[{{idx}}].doctorId" rowIdx={{idx}} class="input-small">
			<c:forEach items="${doctorList}" var="dict">
				<option value="${dict.id}">${dict.name}</option>
			</c:forEach>
		</select>
							</td>
							<td>
								<input id="billitemList{{idx}}_unit" name="billitemList[{{idx}}].unit" type="text" value="{{row.unit}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="billitemList{{idx}}_quantity" name="billitemList[{{idx}}].quantity" type="text" value="{{row.quantity}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="billitemList{{idx}}_originalprice" name="billitemList[{{idx}}].originalprice" type="text" value="{{row.originalprice}}" maxlength="64" class="input-small required"/>
							</td>
							<td>

								<input id="billitemList{{idx}}_dealprice" name="billitemList[{{idx}}].dealprice" type="text" value="{{row.dealprice}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<textarea id="billitemList{{idx}}_remarks" name="billitemList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="finance:bill:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#billitemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var billitemRowIdx = 0, billitemTpl = $("#billitemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						var billItemListData = ${fns:toJson(bill.billitemList)}; 
						$(document).ready(function() {
							for (var i=0; i<billItemListData.length; i++){
								addRow('#billitemList', billitemRowIdx, billitemTpl, billItemListData[i]);
								billitemRowIdx = billitemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="finance:bill:edit"><input id="btnSubmit" class="btn btn-primary" type="button"  onclick="saveFrom()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>