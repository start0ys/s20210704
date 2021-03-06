<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
.warning{
	border: 2.5px solid #384f76; 
	width: 610px;
	padding: 50px;
	margin: 0 auto;
	border-radius: 60px;
	
}
</style>
<body class="w3-theme-l5">
<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
    <a href="../main" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Home</a>
  <div class="w3-dropdown-hover w3-hide-small">
    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-file-text fa-fw w3-margin-right"></i>전자결재</button>     
    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
      <a href="apvWrite" class="w3-bar-item w3-button">결재 서류 작성</a>
      <a href="apvSnd" class="w3-bar-item w3-button">받은 결재</a>
      <a href="apvRcv" class="w3-bar-item w3-button">보낸 결재</a>
    </div>
  </div>
  <a href="../calendar/calendar" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Account Settings"><i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i>일정</a>
  <a href="../rr/rr" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Messages"><i class="fa fa-list-alt fa-fw w3-margin-right"></i>자료실</a>
  <div class="w3-dropdown-hover w3-hide-small">
    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-commenting-o fa-fw w3-margin-right fa-flip-horizontal"></i>게시판</button>     
    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
      <a href="../board/noticeList" class="w3-bar-item w3-button">공지사항</a>
      <a href="../board/clubList" class="w3-bar-item w3-button">동호회</a>
    </div>
  </div>
  <c:if test="${svo.dcontent == '인사부' || svo.dcontent == '임원'  }">
	   <div class="w3-dropdown-hover w3-hide-small">
	    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-cog fa-fw w3-margin-right" aria-hidden="true"></i>관리</button>      
	    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
	      <a href="../emp/empList" class="w3-bar-item w3-button">사원 정보 관리</a>
	      <a href="../cmt/cmt" class="w3-bar-item w3-button">사원 근태 관리</a>
	    </div>
	  </div>
  </c:if>
  
  <a href="../logout" class="w3-bar-item w3-button w3-hide-small w3-right w3-padding-large w3-hover-white" title="My Account">
    <i class="fa fa-sign-out w3-margin-right"></i>Logout
  </a>
 </div>
</div> 

<!-- Navbar on small screens -->
<div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
  <a href="#" class="w3-bar-item w3-button w3-padding-large">안보임</a> <!-- 이 줄은 안보이는 줄입니다 -->
  <button onclick="myFunction1('Demo1')" class="w3-bar-item w3-button w3-padding-large">전자결재</button>
  	<div id="Demo1" class="w3-hide w3-bar-block">
    	<a href="apvWrite" class="w3-button w3-block w3-theme-l5 w3-left-align">결재 서류 작성</a>
        <a href="apvSnd" class="w3-button w3-block w3-theme-l5 w3-left-align">받은 결재</a>
        <a href="apvRcv" class="w3-button w3-block w3-theme-l5 w3-left-align">보낸결재</a>
    </div>
  <a href="../calendar/calendar" class="w3-bar-item w3-button w3-padding-large">일정</a>
  <a href="../rr/rr" class="w3-bar-item w3-button w3-padding-large">자료실</a>
  <button onclick="myFunction1('Demo2')" class="w3-bar-item w3-button w3-padding-large">게시판</button>
  	<div id="Demo2" class="w3-hide w3-bar-block">
    	<a href="../board/noticeList" class="w3-button w3-block w3-theme-l5 w3-left-align">공지사항</a>
        <a href="../board/clubList" class="w3-button w3-block w3-theme-l5 w3-left-align">동호회</a>
    </div>
    <c:if test="${svo.dcontent == '인사부' || svo.dcontent == '임원'  }">
	   <button onclick="myFunction1('Demo4')" class="w3-bar-item w3-button w3-padding-large">관리</button>
	  	<div id="Demo4" class="w3-hide w3-bar-block">
	    	<a href="../emp/empList" class="w3-button w3-block w3-theme-l5 w3-left-align">사원 정보 관리</a>
	        <a href="../cmt/cmt" class="w3-button w3-block w3-theme-l5 w3-left-align">사원 근태 관리</a>
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
         <a href="../cmt/mycmt" style="display: block; text-decoration: none;"><i class="fa fa-clock-o fa-fw w3-margin-right w3-text-theme"></i> 나의 근태정보</a><p>
         <a href="../emp/myInfoUpdate" style="display: block; text-decoration: none;"><i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i> 개인정보수정</a>
        </div>
      </div>
      <br>
      
      <!-- Accordion -->
      <div class="w3-card w3-round">
        <div class="w3-white">
<button onclick="myFunction('Demo3')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-envelope fa-fw w3-margin-right" ></i><c:if test="${unreadMsg > 0 }"><span class="w3-badge w3-right w3-small w3-green">${unreadMsg }</span></c:if> 메시지</button>
<div id="Demo3" class="w3-hide w3-bar-block">
<a href="../message/sendMsg" class="w3-button w3-block w3-theme-l5 w3-left-align">메시지 보내기</a>
<a href="../message/sentMsg" class="w3-button w3-block w3-theme-l5 w3-left-align">보낸 메시지</a>
<a href="../message/rcvMsg" class="w3-button w3-block w3-theme-l5 w3-left-align"><c:if test="${unreadMsg > 0 }"><span class="w3-badge w3-right w3-small w3-green">${unreadMsg }</span></c:if>받은 메시지</a></div>
          <a href="apvSnd" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-file-text fa-fw w3-margin-right"></i><c:if test="${unreadTotal > 0 }"><span class="w3-badge w3-right w3-small w3-green">${unreadTotal }</span></c:if><c:if test="${apvNoTotal > 0 }"><span class="w3-badge w3-right w3-small w3-red">${apvNoTotal }</span></c:if> 결재</a>
          <button onclick="nwindow()" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fas fa-comment-dots fa-fw w3-margin-right"></i><span class="w3-badge w3-right w3-small w3-green"></span>채팅</button>
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
              <h1><i class="fa fa-file-text fa-fw w3-margin-right"></i><b>받은 결재함</b></h1><hr>
              <div>
              	  <span>
	              	  <a href="apvRcv" style="text-decoration: none; color: black; margin-left: 5px;">
		              	  <b>보낸 결재함 바로가기</b>
	              	  	  <c:if test="${apvNoTotal > 0 }"><span class="w3-badge w3-small w3-red">${apvNoTotal }</span></c:if>
	              	  </a>
              	  </span>
			   </div>
			   <c:if test="${rcvTotal > 0 }">
	               <table border="1" style="width: 100%; text-align: center;">
	              	<tr style="background-color:#384f76; color: white; "><th style="width: 10%;">결제구분</th><th style="width: 55%;">제목</th><th style="width: 10%;">제출자</th><th style="width: 13%;">제출일</th><th style="width: 10%;">진행상황</th></tr>
	              	<c:forEach var="rcvList" items="${rcvList }">
	              		<tr>
		              		<c:set var="midChk" value="0"/>
	              			<c:if test="${rcvList.apv_type == '비용신청' && svo.rcontent == '대리'}"><c:set var="midChk" value="${midChk + 1 }"/></c:if>
	              			<c:if test="${rcvList.apv_type == '사업보고' && svo.rcontent == '부장'}"><c:set var="midChk" value="${midChk + 1 }"/></c:if>
	              			<c:if test="${rcvList.apv_type == '인사보고' && svo.rcontent == '팀장'}"><c:set var="midChk" value="${midChk + 1 }"/></c:if>
	              			<c:if test="${rcvList.apv_type == '행사보고' && svo.rcontent == '팀장'}"><c:set var="midChk" value="${midChk + 1 }"/></c:if>
	              			<td>${rcvList.apv_type }</td>
	              			<td><a href="apvRcvDetail?sq=${rcvList.apv_sq }" style="text-decoration: none; color: black;">${rcvList.apv_title }</a></td>
	              			<td>${rcvList.srt_name }</td>
	              			<td>${rcvList.apv_date }</td>
	              			<td>
	              				<c:if test="${rcvList.apv_ok == 0 }"><span style="margin: 0px; color: red;">진행중</span></c:if>
	              				<c:if test="${rcvList.apv_ok == 1 && midChk == 0}">승인(중간)</c:if>
	              				<c:if test="${rcvList.apv_ok == 1 && midChk > 0}"><span style="margin: 0px; color: red;">승인(중간)</span></c:if>
	              				<c:if test="${rcvList.apv_ok == 2 && rcvList.apv_fnl != emp_num}">승인(중간)</c:if>
	              				<c:if test="${rcvList.apv_ok == 2 && rcvList.apv_fnl == emp_num}"><span style="margin: 0px; color: red;">승인(중간)</span></c:if>
	              				<c:if test="${rcvList.apv_ok == 3 }">승인(최종)</c:if>
	              				<c:if test="${rcvList.apv_ok == 4 }">반려</c:if>
	              			</td>
	              		</tr>
	              	</c:forEach>
	              </table>
	              <div style="margin-top: 3px;">              	  
	              	  <div>
	              	  	  <a href="apvWrite" style="text-decoration: none; color: black; float: right;"><button type="button" class="btn2">결재작성</button></a>
	              	  </div>
	              </div>
              </c:if>
              <c:if test="${rcvTotal == 0 }">
                <div style=" margin:35px 0 70px 0; text-align: center; ">  
					<div class="warning">
						<b>받은 결재</b>가 <span style="background-color: yellow; color: red; font-weight: bold;">존재하지 않습니다</span><p>
						결재작성 또는 뒤로가기버튼을 눌러주세요<p>
						<div style="margin-top: 20px;">
							<input type="button" value="뒤로가기" class="btn2" onclick="history.go(-1);">
							<a href="apvWrite" style="text-decoration: none; color: black;"><button type="button" class="btn2">결재작성</button></a>
						</div>
					</div>
			     </div>	
              </c:if>
              
              <div class="w3-center" style="margin-left: 76px;">
					<div class="w3-bar w3-border" style=" margin: 10px 0px;border: 1px solid #7d97a5;">
						<c:if test="${yp.startPage > yp.pageBlock }">
							<a href="apvSnd?currentPage=${yp.startPage-yp.pageBlock}" class="w3-bar-item w3-button">&laquo;</a>
						</c:if>
						<c:forEach var="i" begin="${yp.startPage }" end="${yp.endPage }">
							<c:if test="${yp.currentPage == i }">
								<a href="apvSnd?currentPage=${i}" class="w3-bar-item w3-button" style="background-color: #384f76; color: white;">${i}</a>
							</c:if>
							<c:if test="${yp.currentPage != i }">
								<a href="apvSnd?currentPage=${i}" class="w3-bar-item w3-button">${i}</a>
							</c:if>
						</c:forEach>
						<c:if test="${yp.endPage < yp.totalPage }">
							<a href="apvSnd?currentPage=${yp.startPage+yp.pageBlock}" class="w3-bar-item w3-button">&raquo;</a>
						</c:if>
					</div>
				</div>
           

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
function nwindow(){
    var url="../chat/chat";
    window.open(url,"","width=600,height=805,location=no");
}
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
</script>

</body>
</html> 

