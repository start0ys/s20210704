<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html> 
<title>JOBIS</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/SpringMain.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
 
<style>
html, body, h1, h2, h3, h4, h5 {font-family: "Open Sans", sans-serif}
.w3-col.m7{width:73.33333%}
.btn2{
   color: #fff;
   background-color: #384f76;
   
   border-color: #384f76;
   border-radius: 6px;
 }
</style>
<script type="text/javascript">

function cWrite() {	
	var w = (window.screen.width/2) - 200;
	var h = (window.screen.height/2) - 200;
	var url = "cwriteView";
	window.open(url,"cPlanWrite","width=400,height=400,left="+w+",top="+h);
}
function dWrite(y,m,d){
	var w = (window.screen.width/2) - 200;
	var h = (window.screen.height/2) - 200;
	var url = "cpForm?curYear="+y+"&curMonth="+m+"&curDay="+d;
	window.open(url,"cPlanList","width=400,height=400,left="+w+",top="+h);
}
</script>
<c:set var="type"        value="${type}"/>
<c:set var="curYear"    value="${curYear}"/>
<c:set var="curMonth"    value="${curMonth}"/>
<c:set var="curDay"        value="${curDay}"/>
<body class="w3-theme-l5">

<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
    <a href="../main" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Home</a>
  <div class="w3-dropdown-hover w3-hide-small">
    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-file-text fa-fw w3-margin-right"></i>????????????</button>     
    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
      <a href="../apv/apvWrite" class="w3-bar-item w3-button">?????? ?????? ??????</a>
      <a href="../apv/apvSnd" class="w3-bar-item w3-button">?????? ??????</a>
      <a href="../apv/apvRcv" class="w3-bar-item w3-button">?????? ??????</a>
    </div>
  </div>
  <a href="calendar" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Account Settings"><i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i>??????</a>
  <a href="../rr/rr" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Messages"><i class="fa fa-list-alt fa-fw w3-margin-right"></i>?????????</a>
  <div class="w3-dropdown-hover w3-hide-small">
    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-commenting-o fa-fw w3-margin-right fa-flip-horizontal"></i>?????????</button>     
    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
      <a href="../board/noticeList" class="w3-bar-item w3-button">????????????</a>
      <a href="../board/clubList" class="w3-bar-item w3-button">?????????</a>
    </div>
  </div>
  <c:if test="${svo.dcontent == '?????????' || svo.dcontent == '??????'  }">
	   <div class="w3-dropdown-hover w3-hide-small">
	    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-cog fa-fw w3-margin-right" aria-hidden="true"></i>??????</button>      
	    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
	      <a href="../emp/empList" class="w3-bar-item w3-button">?????? ?????? ??????</a>
	      <a href="../cmt/cmt" class="w3-bar-item w3-button">?????? ?????? ??????</a>
	    </div>
	  </div>
  </c:if>
  
  <a href="../" class="w3-bar-item w3-button w3-hide-small w3-right w3-padding-large w3-hover-white" title="My Account">
    <i class="fa fa-sign-out w3-margin-right"></i>Logout
  </a>
 </div>
</div> 

<!-- Navbar on small screens -->
<div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
  <a href="#" class="w3-bar-item w3-button w3-padding-large">?????????</a> <!-- ??? ?????? ???????????? ???????????? -->
  <button onclick="myFunction1('Demo1')" class="w3-bar-item w3-button w3-padding-large">????????????</button>
  	<div id="Demo1" class="w3-hide w3-bar-block">
    	<a href="../apv/apvWrite" class="w3-button w3-block w3-theme-l5 w3-left-align">?????? ?????? ??????</a>
        <a href="../apv/apvSnd" class="w3-button w3-block w3-theme-l5 w3-left-align">?????? ??????</a>
        <a href="../apv/apvRcv" class="w3-button w3-block w3-theme-l5 w3-left-align">????????????</a>
    </div>
  <a href="calendar" class="w3-bar-item w3-button w3-padding-large">??????</a>
  <a href="../rr/rr" class="w3-bar-item w3-button w3-padding-large">?????????</a>
  <button onclick="myFunction1('Demo2')" class="w3-bar-item w3-button w3-padding-large">?????????</button>
  	<div id="Demo2" class="w3-hide w3-bar-block">
    	<a href="../board/noticeList" class="w3-button w3-block w3-theme-l5 w3-left-align">????????????</a>
        <a href="../board/clubList" class="w3-button w3-block w3-theme-l5 w3-left-align">?????????</a>
    </div>
    <c:if test="${svo.dcontent == '?????????' || svo.dcontent == '??????'  }">
	   <button onclick="myFunction1('Demo4')" class="w3-bar-item w3-button w3-padding-large">??????</button>
	  	<div id="Demo4" class="w3-hide w3-bar-block">
	    	<a href="../emp/empList" class="w3-button w3-block w3-theme-l5 w3-left-align">?????? ?????? ??????</a>
	        <a href="../cmt/cmt" class="w3-button w3-block w3-theme-l5 w3-left-align">?????? ?????? ??????</a>
	    </div>
    </c:if>
</div>

<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">    
  <!-- The Grid -->
  <div class="w3-row">
    <!-- Left Column -->
    <div class="w3-col m3">
      <!-- Profile -->
      <div class="w3-card w3-round w3-white">
        <div class="w3-container">
         <h4 class="w3-center">My Profile</h4>
         <p class="w3-center"><c:if test="${svo.ph_path eq null }"><img src="../images/LUCY.jpg" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></c:if>
                        <c:if test="${svo.ph_path ne null }"><img src="../upload/${svo.ph_path }" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></c:if></p>
         <hr>
         <p><i class="fa fa-id-badge fa-fw w3-margin-right w3-text-theme"></i> ${svo.emp_name }</p>
         <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${svo.dcontent } / ${svo.rcontent }</p>
         <a href="../cmt/mycmt" style="display: block; text-decoration: none;"><i class="fa fa-clock-o fa-fw w3-margin-right w3-text-theme"></i> ?????? ????????????</a><p>
         <a href="../emp/myInfoUpdate" style="display: block; text-decoration: none;"><i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i> ??????????????????</a>
        </div>
      </div>
      <br>
      
      <!-- Accordion -->
      <div class="w3-card w3-round">
        <div class="w3-white">
        <button onclick="myFunction('Demo3')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-envelope fa-fw w3-margin-right" ></i><c:if test="${unreadMsg > 0 }"><span class="w3-badge w3-right w3-small w3-green">${unreadMsg }</span></c:if> ?????????</button>
<div id="Demo3" class="w3-hide w3-bar-block">
<a href="../message/sendMsg" class="w3-button w3-block w3-theme-l5 w3-left-align">????????? ?????????</a>
<a href="../message/sentMsg" class="w3-button w3-block w3-theme-l5 w3-left-align">?????? ?????????</a>
<a href="../message/rcvMsg" class="w3-button w3-block w3-theme-l5 w3-left-align"><c:if test="${unreadMsg > 0 }"><span class="w3-badge w3-right w3-small w3-green">${unreadMsg }</span></c:if>?????? ?????????</a></div>
          <a href="../apv/apvSnd" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-file-text fa-fw w3-margin-right"></i><c:if test="${unreadTotal > 0 }"><span class="w3-badge w3-right w3-small w3-green">${unreadTotal }</span></c:if><c:if test="${apvNoTotal > 0 }"><span class="w3-badge w3-right w3-small w3-red">${apvNoTotal }</span></c:if>
 ??????</a>
          <button onclick="nwindow()" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fas fa-comment-dots fa-fw w3-margin-right"></i><span class="w3-badge w3-right w3-small w3-green"></span>??????</button>
        </div>      
      </div>
      <br>

    
    <!-- End Left Column -->
    </div>
    
    <!-- Middle Column -->
     <div class="w3-col m7" >
    
      <div class="w3-row-padding">
        <div class="w3-col m12">
          <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
<!--               <h1 onclick="cWrite()"><i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i><b>??????</b></h1><hr> -->
              <h1><i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i><b>??????</b></h1><hr>
             <FORM name="theForm">
			<%-- base table --%>
			<TABLE cellpadding="0" cellspacing="0" border="0" bgcolor="#ffffff" width="800" height="650">
			    <TR>
			          <TD align="center" width="365" style="font-size: 15pt">
			              <A href="calendar?type=MONTH&curYear=<c:out value="${curYear}"/>&curMonth=<c:out value="${curMonth-1}"/>&curDay=<c:out value="${curDay}"/>">???</a>
			                  <c:out value="${curYear}"/> ??? &nbsp;&nbsp;  <c:out value="${curMonth}"/> ??? 
			              <A href="calendar?type=MONTH&curYear=<c:out value="${curYear}"/>&curMonth=<c:out value="${curMonth+1}"/>&curDay=<c:out value="${curDay}"/>">???</a>
			          </TD>
			    </TR>
			    <tr><td align="center"><input type="button" value="?????? ??????" onclick="cWrite()" class="btn2"></td></tr>
			    <TR height="3">
			        <TD colspan="2"></TD>
			    </TR>
			    <TR>
			          <TD align="center" colspan="3" valign="top">
			          <%-- body table --%>
			          <TABLE border="0" cellspacing="0" cellpadding="0">
			              <TR>
			                  <TD valign="top" style="border:#666666 1px solid; padding:5px" align="center">
			                  <%-- month outline table --%>
			                    <TABLE border="0" cellspacing="0" cellpadding="0">
			                    <TR height="30">
			                        <TD align=center>
			                            <FONT color=red>?????????</FONT>
			                        </TD>
			                        <TD align=center width="110">?????????</TD>
			                        <TD align=center width="110">?????????</TD>
			                        <TD align=center width="110">?????????</TD>
			                        <TD align=center width="110">?????????</TD>
			                        <TD align=center width="110">?????????</TD>
			                        <TD align=center width="110">?????????</TD>
			                    </TR>
			                    <TR><TD colspan=7 bgcolor=#888888 height=1></TD></TR>
			                    <TR><TD colspan=7 bgcolor=#ffffff height=5></TD></TR>
			                    <TR>
			                          <TD colspan=7>
			                          <%-- month content table --%>
			                          <TABLE border='0' cellspacing='1' cellpadding='0' bgcolor=#dddddd>
			                              <TR>
			                                <c:if test="${firstDayOfWeek != '1'}">
			                                  <%-- ?????? ?????? ?????? ???????????? ?????? ??????????????? ????????? ????????????.--%>
			                                  <c:forEach var="i" begin="1" end="${firstDayOfWeek-1}">
			                                    <TD width="110" height="120" class="uline" valign="top" align="right" style="padding:5">
			                                    </TD>
			                                </c:forEach>
			                                </c:if>
			                                
			                                <%-- ??? ?????? ???????????? ????????? ????????? ??????(??????)??? ???????????? --%>
			                                <c:set var="dbIndex" value="0"/>
			                                  <c:forEach var="currentDay" begin="1" end="${lastDayOfMonth}">                                    
			                                    <TD bgcolor="#ffffff" style="padding:5; cursor: pointer;" onclick='dWrite("${curYear}","${curMonth}","${currentDay}")'>
			                                        <TABLE cellpadding="0" cellsping="0" border="0" width="110">
			                                        <TR>
			                                            <TD height="10" width="110" class="uline" valign="top" align="right">
			                                                <c:choose>			                                                
			                                                    <c:when test="${((currentDay-(8-firstDayOfWeek)) % 7) == 1}">
			                                                        <!-- ????????? -->
			                                                        <FONT color="red">                                                    
			                                                            <c:out value="${currentDay}"/>  
			                                                        </FONT>
			                                                    </c:when>
			                                                    <c:otherwise>
			                                                        <c:out value="${currentDay}"/>
			                                                    </c:otherwise>
			                                                </c:choose>
			                                            <!-- </A> -->                                            
			                                            </TD>
			                                        </TR> 
			                                        <TR>
			                                            <TD height="110" width="110" valign="top"> 
<%-- 			                                            <c:if test=""> --%>
					                                          <TABLE>
					                                              <c:forEach var="dayIndex" items="${list}">
					                                              	  <input type="hidden" id="dep_num" name="dep_num" value="${dep_num }">
					                                                  <c:if test="${currentDay == dayIndex.cal_date}">
					                                                      <TR><TD>                                                        
					                                                          <i class="fa fa-circle" style="color: ${dayIndex.cal_bgcolor};"></i>  ${dayIndex.cal_title}                                        
					                                                      <c:set var="dbIndex" value='${dbIndex + 1}'/> 
					                                                      </TD></TR>
					                                                  </c:if>
					                                              </c:forEach>
					                                          </TABLE>
<%-- 			                                            </c:if> --%>
			                                            </TD>
			                                        </TR>
			                                        </TABLE>        
			                                    </TD>
			                                    <%-- ?????? ????????? ????????????(?????????)?????? ??? ?????? ????????? ?????? ???????????? ?????? ?????? ????????????. --%>
			                                    <c:if test="${((currentDay-(8-firstDayOfWeek)) % 7) == 0}">
			                                        </TR>
			                                        <TR>
			                                    </c:if>
			                                </c:forEach>
			
			                                <%-- ?????? ?????? ?????? ????????? ?????? ?????? ??????????????? ????????? ????????????.--%>
			                                <c:if test="${lastDayOfLastWeek != '7'}">
			                                <c:forEach var="i" begin="1" end="${7-lastDayOfLastWeek}">
			                                    <TD width=110 height=120 class=uline valign=top align=right style='padding:5'>
			                                    </TD>
			                                </c:forEach>
			                                </c:if>                            
			                            </TR>
			                        </TABLE>
			                        <%-- end month content table --%>
			                        </TD>
			                    </TR>
			                  </TABLE>
			                  <%-- end month outline table --%>
			                  </TD>
			            </TR>
			        </TABLE>
			        <%-- end body table --%>
			        </TD>
			    </TR>
			      <TR height=10><TD></TD></TR>
			    <TR>
			        <TD align=right></TD>
			    </TR>
			</table>
			<%-- end base table --%>
			</FORM>

            </div>
          </div>
        </div>
      </div>
      

      
    <!-- End Middle Column -->
    </div>
    
    
  <!-- End Grid -->
  </div>
  
<!-- End Page Container -->
</div>
<br>


<!-- Footer -->
<footer class="w3-container w3-theme-d5">
  <p>&copy copyright is reserved by JOBIS/<a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
</footer>
 
<script>
// Accordion
function myFunction(id) {
  var x = document.getElementById(id);
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
    x.previousElementSibling.className += " w3-theme-l1";
  } else { 
    x.className = x.className.replace("w3-show", "");
    x.previousElementSibling.className = 
    x.previousElementSibling.className.replace(" w3-theme-l1", "");
  }
}
function myFunction1(id) {
	  var x = document.getElementById(id);
	  if (x.className.indexOf("w3-show") == -1) {
	    x.className += " w3-show";
	    x.previousElementSibling.className += " w3-theme-d2";
	  } else { 
	    x.className = x.className.replace("w3-show", "");
	    x.previousElementSibling.className = 
	    x.previousElementSibling.className.replace(" w3-theme-d2", "");
	  }
}

// Used to toggle the menu on smaller screens when clicking on the menu button
function openNav() {
  var x = document.getElementById("navDemo");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else { 
    x.className = x.className.replace(" w3-show", "");
  }
}
function nwindow(){
    var url="../chat/chat";
    window.open(url,"","width=600,height=805,location=no");
}
</script>

</body>
</html> 

