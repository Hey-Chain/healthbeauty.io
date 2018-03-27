/*!
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 * 业务通用公共方法
 * @author HeyChain
 * @version 2018-3-27
 */
$(document).ready(function() {
	
});

/// <summary>获取单据编号</summary>
function getBillNo() {
  var mydate = new Date();
  var year = mydate.getFullYear().toString().substring(2, 4);
  var mm = mydate.getMonth() + 1;

  var month = mm >= 10 ? "" + mm : "0" + mm;
  var day = mydate.getDate() >= 10 ? "" + mydate.getDate() : "0" + mydate.getDate();
  var hour = mydate.getHours() >= 10 ? "" + mydate.getHours() : "0" + mydate.getHours();
  var minute = mydate.getMinutes() >= 10 ? "" + mydate.getMinutes() : "0" + mydate.getMinutes();
  var second = mydate.getSeconds() >= 10 ? "" + mydate.getSeconds() : "0" + mydate.getSeconds();
  var mill = "";
  var mm = mydate.getMilliseconds();
  if (mm < 10) {
      mill = "00" + mm;
  }
  else if (mm < 100) {
      mill = "0" + mm;
  } else {
      mill = "" + mm;
  }
  return year + "" + month + "" + day + "" + hour + "" + minute + "" + second + "" + mill + (Math.floor(Math.random() * 3 + 7)).toString();
}