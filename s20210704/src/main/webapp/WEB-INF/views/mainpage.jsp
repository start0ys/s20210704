<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<title>JOBIS</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/SpringMain.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
html, body, h1, h2, h3, h4, h5 {font-family: "Open Sans", sans-serif}
</style>
<body class="w3-theme-l5">
<c:if test="${workIn > 0 }"><script type="text/javascript">alert("출근하셨습니다");</script></c:if>
<c:if test="${workOut > 0 }"><script type="text/javascript">alert("퇴근하셨습니다");</script></c:if>
<c:if test="${cmt_chk == 1 }"><script type="text/javascript">alert("이전 출근 기록이 존재하지 않습니다\n문제가 있으시면 인사팀에 연락하시기 바랍니다");</script></c:if>
<c:if test="${cmt_chk == 2 }"><script type="text/javascript">alert("이전 퇴근 기록이 존재하지 않습니다\n문제가 있으시면 인사팀에 연락하시기 바랍니다");</script></c:if>
<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
    <a href="main" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Home</a>
  <div class="w3-dropdown-hover w3-hide-small">
    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-file-text fa-fw w3-margin-right"></i>전자결재</button>     
    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
      <a href="apv/apvWrite" class="w3-bar-item w3-button">결재 서류 작성</a>
      <a href="apv/apvSnd" class="w3-bar-item w3-button">받은 결재</a>
      <a href="apv/apvRcv" class="w3-bar-item w3-button">보낸 결재</a>
    </div>
  </div>
  <a href="calendar/calendar" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Account Settings"><i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i>일정</a>
  <a href="rr/rr" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Messages"><i class="fa fa-list-alt fa-fw w3-margin-right"></i>자료실</a>
  <div class="w3-dropdown-hover w3-hide-small">
    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-commenting-o fa-fw w3-margin-right fa-flip-horizontal"></i>게시판</button>     
    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
      <a href="board/noticeList" class="w3-bar-item w3-button">공지사항</a>
      <a href="board/surveyList" class="w3-bar-item w3-button">설문</a>
      <a href="board/clubList" class="w3-bar-item w3-button">동호회</a>
    </div>
  </div>
  <c:if test="${svo.dcontent == '인사부' || svo.dcontent == '임원'  }">
	   <div class="w3-dropdown-hover w3-hide-small">
	    <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-cog fa-fw w3-margin-right" aria-hidden="true"></i>관리</button>     
	    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
	      <a href="emp/empList" class="w3-bar-item w3-button">사원 정보 관리</a>
	      <a href="cmt/cmt" class="w3-bar-item w3-button">사원 근태 관리</a>
	    </div>
	  </div>
  </c:if>
  
  <a href="logout" class="w3-bar-item w3-button w3-hide-small w3-right w3-padding-large w3-hover-white" title="My Account">
    <i class="fa fa-sign-out w3-margin-right"></i>Logout
  </a>
 </div>
</div> 

<!-- Navbar on small screens -->
<div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
  <a href="#" class="w3-bar-item w3-button w3-padding-large">안보임</a> <!-- 이 줄은 안보이는 줄입니다 -->
  <button onclick="myFunction1('Demo1')" class="w3-bar-item w3-button w3-padding-large">전자결재</button>
  	<div id="Demo1" class="w3-hide w3-bar-block">
    	<a href="apv/apvWrite" class="w3-button w3-block w3-theme-l5 w3-left-align">결재 서류 작성</a>
        <a href="apv/apvSnd" class="w3-button w3-block w3-theme-l5 w3-left-align">받은 결재</a>
        <a href="apv/apvRcv" class="w3-button w3-block w3-theme-l5 w3-left-align">보낸결재</a>
    </div>
  <a href="calendar/calendar" class="w3-bar-item w3-button w3-padding-large">일정</a>
  <a href="rr/rr" class="w3-bar-item w3-button w3-padding-large">자료실</a>
  <button onclick="myFunction1('Demo2')" class="w3-bar-item w3-button w3-padding-large">게시판</button>
  	<div id="Demo2" class="w3-hide w3-bar-block">
    	<a href="board/noticeList" class="w3-button w3-block w3-theme-l5 w3-left-align">공지사항</a>
        <a href="board/surveyList" class="w3-button w3-block w3-theme-l5 w3-left-align">설문조사</a>
        <a href="board/clubList" class="w3-button w3-block w3-theme-l5 w3-left-align">동호회</a>
    </div>
    <c:if test="${svo.dcontent == '인사부' || svo.dcontent == '임원'  }">
	   <button onclick="myFunction1('Demo4')" class="w3-bar-item w3-button w3-padding-large">관리</button>
	  	<div id="Demo4" class="w3-hide w3-bar-block">
	    	<a href="emp/empList" class="w3-button w3-block w3-theme-l5 w3-left-align">사원 정보 관리</a>
	        <a href="cmt/cmt" class="w3-button w3-block w3-theme-l5 w3-left-align">사원 근태 관리</a>
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
         <p class="w3-center"><img src="images/LUCY.jpg" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
<!--          <p><button onclick="">출근</button><button onclick="">퇴근</button></p> -->
         <hr>
         <p><i class="fa fa-id-badge fa-fw w3-margin-right w3-text-theme"></i> ${svo.emp_name }</p>
         <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${svo.dcontent } / ${svo.rcontent }</p>
         <a href="cmt/mycmt" style="display: block; text-decoration: none;"><i class="fa fa-clock-o fa-fw w3-margin-right w3-text-theme"></i> 나의 근태정보</a><p>
         <a href="emp/myInfoUpdate" style="display: block; text-decoration: none;"><i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i> 개인정보수정</a>
        </div>
      </div>
      <br>
      
      <!-- Accordion -->
      <div class="w3-card w3-round">
        <div class="w3-white">
          <button onclick="myFunction('Demo3')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-envelope fa-fw w3-margin-right" ></i><span class="w3-badge w3-right w3-small w3-green">1</span> 쪽지</button>
          <div id="Demo3" class="w3-hide w3-bar-block">
          	<a href="note/sendNote" class="w3-button w3-block w3-theme-l5 w3-left-align">쪽지 보내기</a>
            <a href="note/sentNote" class="w3-button w3-block w3-theme-l5 w3-left-align">보낸 쪽지함</a>
            <a href="note/receiveNote" class="w3-button w3-block w3-theme-l5 w3-left-align"><span class="w3-badge w3-right w3-small w3-green">1</span>받은 쪽지함</a>
          </div>
          <a href="apv/apvSnd" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-file-text fa-fw w3-margin-right"></i><span class="w3-badge w3-right w3-small w3-green">3</span> 결재</a>
          <a href="board/surveyList" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-check-square-o fa-fw w3-margin-right"></i><span class="w3-badge w3-right w3-small w3-green">2</span> 설문</a>
        </div>      
      </div>
      <br>
      
      <!-- Interests --> 
      <div class="w3-card w3-round w3-white w3-hide-small">
        <div class="w3-container">
          <p>채팅</p>
          <hr>
		  <p>인사부<p>
          <p>관리부<p>
          <p>개발부<p>
        </div>
      </div>
      <br>

    
    <!-- End Left Column -->
    </div>
    
    <!-- Middle Column -->
    <div class="w3-col m7">
    
      <div class="w3-row-padding">
        <div class="w3-col m12">
          <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
              <h4><i class="fa fa-check-square-o fa-fw w3-margin-right"></i>오늘의 일정</h4>
              <p contenteditable="true" class="w3-border w3-padding">일정목록</p>
            </div>
          </div>
        </div>
      </div>
      
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <h4><i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i>전체 일정</h4><br>
        <hr class="w3-clear">
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>

      </div>
      
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <h4><i class="fa fa-bullhorn fa-fw w3-margin-right"></i>공지사항</h4><br>
        <hr class="w3-clear">
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
      </div>  

      
    <!-- End Middle Column -->
    </div>
    
    <!-- Right Column -->
    <div class="w3-col m2">
      <div class="w3-card w3-round w3-white w3-center w3-center">
        <div class="w3-container">
          <p>
          	<button class="w3-button w3-block" style=" border-radius:10px;box-shadow:#d6d6d6 1px 3px 2px;text-align: center; width: 40%; display: inline; background-color:#607084; color:white; margin-right: 6%;" onclick="location.href='workInOut?inOut=1'" >출근</button>
         	<button class="w3-button w3-block" style=" border-radius:10px;box-shadow:#d6d6d6 1px 3px 2px;text-align: center; width: 40%; display: inline; background-color:#d20404; color:white;" onclick="location.href='workInOut?inOut=2'">퇴근</button>
          </p>
        </div>
        
      </div>
      <br>
      <div class="w3-card w3-round w3-white w3-center w3-center">
        <div class="w3-container">
           <a href="#"></a>
           <p><button class="w3-button w3-block w3-theme-l4" style=" border-radius:10px;box-shadow:#d6d6d6 1px 3px 2px;text-align: left;"><i class="fa fa-chevron-circle-right fa-fw w3-margin-right"></i>사용방법 바로가기</button></p>
           <p><button class="w3-button w3-block w3-theme-l4" style=" border-radius:10px;box-shadow:#d6d6d6 1px 3px 2px;text-align: left;" onclick="location.href='rr/rr?rr_type=0'"><i class="fa fa-chevron-circle-right fa-fw w3-margin-right"></i>문서양식 바로가기</button></p>
           <p><button class="w3-button w3-block w3-theme-l4" style=" border-radius:10px;box-shadow:#d6d6d6 1px 3px 2px;text-align: left;" onclick="location.href='board/noticeList'"><i class="fa fa-chevron-circle-right fa-fw w3-margin-right"></i>공지사항 바로가기</button></p>
           <p><button class="w3-button w3-block w3-theme-l4" style=" border-radius:10px;box-shadow:#d6d6d6 1px 3px 2px;text-align: left;" onclick="location.href='board/clubList'"><i class="fa fa-chevron-circle-right fa-fw w3-margin-right"></i>동호회 바로가기</button></p>
        </div>
        
      </div>

    <!-- End Right Column -->
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
</script>

</body>
</html> 

