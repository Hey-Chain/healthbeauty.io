/*!
 * Copyright &copy; 2017-2018 <a href="https://github.com/Hey-Chain">Hey-Chain</a> All rights reserved.
 * 业务通用公共方法
 * @author HeyChain
 * @version 2018-3-28
 */
//计算两个时间的时间差
function GetDateDiff(startTime, endTime, diffType) {
    diffType = diffType.toLowerCase(); 
    //var sTime = new Date(startTime);       
    //var eTime = new Date(endTime); 
    var sTime = getDateByString(startTime);
    var eTime = getDateByString(endTime);
    var divNum = 1; 
    switch (diffType) { 
        case"second": //秒                    
            divNum = 1000; 
            break; 
        case "minute": //分                     
            divNum = 1000 * 60; 
            break; 
        case "hour":    //小时                  
            divNum = 1000 * 3600; 
            break; 
        case "day":       //天               
            divNum = 1000 * 3600 * 24; 
            break; 
        default: break;             
    }
    var value = parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
    if (isNaN(value)) {
        value = 0;
    }
    return value;
}
//根据string类型的日期获得Date类型的日期
function getDateByString(d) {
    var arrayDate = d.split(' ');
    var arrayTime = arrayDate[1].split(':');
    var date = new Date(arrayDate[0]);
    date.setHours(arrayTime[0]);
    date.setMinutes(arrayTime[1]);
    date.setSeconds(arrayTime[2]);
    return date;
}
//时间格式化  加时分秒的
function getCurrentDateTime(date) {
    function p(s) {
        return s < 10 ? '0' + s : s;
    }
    var da = new Date(date); 
    var time = da.getTime();//- 8 * 3600 * 1000;
    var d = new Date();
    d.setTime(time);
    var vYear = d.getFullYear();
    var vMon = d.getMonth() + 1;
    var vDay = d.getDate();
    var h = d.getHours();
    var m = d.getMinutes();
    var se = d.getSeconds();
    s = vYear + "-" + p(vMon) + "-" + p(vDay) + " " + p(h) + ":" + p(m) + ":" + p(se);
    return s;
}
//获得当前时间
function getCurrentDate(date) {

    var yesterday_milliseconds = date.getTime() ;
    var yesterday = new Date();
    yesterday.setTime(yesterday_milliseconds);
    var strYear = yesterday.getFullYear();
    var strDay = yesterday.getDate();
    var strMonth = yesterday.getMonth() + 1;
    if (strMonth < 10) {
        strMonth = "0" + strMonth;
    }
    if (strDay < 10) {
        strDay = "0" + strDay;
    }
    var datastr = strYear + "-" + strMonth + "-" + strDay;
    return datastr;
}
/**Parses string formatted as YYYY-MM-DD to a Date object.
  * If the supplied string does not match the format, an
  * invalid Date (value NaN) is returned.
  * @param {string} dateStringInRange format YYYY-MM-DD, with year in
  * range of 0000-9999, inclusive.
  * @return {Date} Date object representing the string.
  */
function parseISO8601(dateStringInRange) {
    var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/,
        date = new Date(NaN), month,
        parts = isoExp.exec(dateStringInRange);

    if (parts) {
        month = +parts[2];
        date.setFullYear(parts[1], month - 1, parts[3]);
        if (month != date.getMonth() + 1) {
            date.setTime(NaN);
        }
    }
    return date;
}
// 获取日期的前n天时间
function getYestoday(date, day) {
    var yesterday_milliseconds = date.getTime() - 1000 * 60 * 60 * 24 * day;
    var yesterday = new Date();
    yesterday.setTime(yesterday_milliseconds);
    var strYear = yesterday.getFullYear();
    var strDay = yesterday.getDate();
    var strMonth = yesterday.getMonth() + 1;
    if (strMonth < 10) {
        strMonth = "0" + strMonth;
    }
    if (strDay < 10) {
        strDay = "0" + strDay;
    }
    datastr = strYear + "-" + strMonth + "-" + strDay;
    return datastr;
}
// 获取日期的后n天时间
function getFutureDate(date, day) {
    var yesterday_milliseconds = date.getTime() + 1000 * 60 * 60 * 24 * day;
    var yesterday = new Date();
    yesterday.setTime(yesterday_milliseconds);
    var strYear = yesterday.getFullYear();
    var strDay = yesterday.getDate();
    var strMonth = yesterday.getMonth() + 1;
    if (strMonth < 10) {
        strMonth = "0" + strMonth;
    }
    datastr = strYear + "-" + strMonth + "-" + strDay;
    return datastr;
};
