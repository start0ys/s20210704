package com.oracle.s20210704.controller;

import java.io.File;

import java.util.List;
import java.util.UUID;


import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.s20210704.model.SyMemberVO;
import com.oracle.s20210704.model.YjEmp;
import com.oracle.s20210704.service.JhRrService;
import com.oracle.s20210704.service.SwMsgService;
import com.oracle.s20210704.service.YjEmpService;
import com.oracle.s20210704.service.YjPaging;
import com.oracle.s20210704.service.YsApvService;

@Controller
public class YjController {

	@Autowired
	private YjEmpService es;
	@Autowired
	private JhRrService jrs;
	@Autowired
	private JavaMailSender  mailSender;
	@Autowired
	private YsApvService    yas;
	@Autowired
	private SwMsgService sms;

	
	//?????????????????? ?????? 
	@RequestMapping(value = "emp/empList")
	public String empList(YjEmp emp, String currentPage, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);


		System.out.println("YjController empList Start...");
		int total = es.total();
		System.out.println("YjController total--> " + total);
		System.out.println("currentPage=>" + currentPage);
		//                     14     NULL(0,1....)
		YjPaging pg = new YjPaging(total, currentPage);
		emp.setStart(pg.getStart());   // ????????? 1
		emp.setEnd(pg.getEnd());       // ????????? 10 
		
		String dept = es.deptSelect(emp_num);
		System.out.println("YjEmpController empList dept--> " + dept);
		
		String rank = es.rankSelect(emp_num);
		System.out.println("YjEmpController empList rank--> " + rank);
		
		List<YjEmp> empList = es.empList(emp);
		System.out.println("YjEmpController list empList.size()=>" + empList.size());
		
		model.addAttribute("total", total);
		model.addAttribute("dept", dept);
		model.addAttribute("rank", rank);
		model.addAttribute("empList",empList);
		model.addAttribute("pg",pg);
		return "emp/empList";
		
	}
	
	//???????????????
	@RequestMapping(value = "emp/updateRef", method = RequestMethod.POST)
	public String updateRef(int empno, int ref, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);

		
		emp.setEmpno(empno);
		emp.setRef(ref);
		System.out.println("YjEmpController updateRef Start...");
		System.out.println("YjEmpController updateRef empno--> " + empno);
		System.out.println("YjEmpController updateRef ref--> " + ref);
		int result = es.updateRef(emp);
		System.out.println("YjEmpController updateRef result--> " + result);
		model.addAttribute("result", result);
//		String referer = request.getHeader("Referer"); // ???????????? ?????? ???????????? ?????????.HttpServletRequest request
//		return "redirect:"+referer;//?????? ????????? ????????????
		return "forward:empList";
	}
	
	//???????????????
	@RequestMapping(value = "emp/searchList", method = RequestMethod.POST)
	public String searchList(int search, String keyword, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);


		String dept = es.deptSelect(emp_num);
		System.out.println("YjEmpController empList dept--> " + dept);
		
		emp.setKeyword(keyword);
		List<YjEmp> searchList = null;
		if(search==0) {
			searchList = es.searchListE(emp);
		}else {
			searchList = es.searchListD(emp);
		}
		System.out.println("YjEmpController list empList.size()=>" + searchList.size());

		model.addAttribute("dept", dept);
		model.addAttribute("keyword", keyword);
		model.addAttribute("empList",searchList);

		return "emp/searchList";

	}
	
	//?????????????????? ?????????
	@RequestMapping(value = "emp/myInfoUpdate")
	public String myInfoUpdate(YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);

		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);

		
		System.out.println("YjController myInfoUpdate Start...");
		List<YjEmp> myInfo = es.myInfo(emp_num);
		System.out.println("YjEmpController list myInfo.size()=>" + myInfo.size());	
		
		model.addAttribute("myInfo", myInfo);
		model.addAttribute("pw", null);
		
		
		return "emp/myInfoUpdate";
	}
	
	//?????????????????? ??????
	@RequestMapping(value = "emp/chkPw")
	public String chkPw(String pw, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);

		System.out.println("YjController chkPw Start...");
		System.out.println("YjController chkPw pw--> " + pw);
		String emp_pw = es.chkPw(emp_num);
		System.out.println("YjController chkPw emp_num--> " + emp_pw);
		if (pw.equals(emp_pw)) {
			System.out.println("?????????");
			model.addAttribute("pw1", pw);
		}else {
			System.out.println("??????");
			model.addAttribute("pw1", null);
		}

		return "forward:updatePw";
	}
	
	//???????????? ?????? ?????????
	@RequestMapping(value = "emp/updatePw")
	public String updatePw(YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		return "emp/updatePw";
	}

	//???????????? ??????
	@RequestMapping(value = "emp/changePw", method = RequestMethod.GET)
	public String changePw(@RequestParam(value = "newPw") String newPw, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		emp.setNewpw(newPw);
		emp.setEmp_num(emp_num);
		System.out.println("YjEmpController changePw Start...");
		System.out.println("YjEmpController changePw newPw--> " + newPw);

		int result = es.changePw(emp);
		System.out.println("YjEmpController changePw result--> " + result);
		model.addAttribute("result", result);
		
		
		return "forward:updatePw";
	}	
	
	//?????? ?????? ??????
	@RequestMapping(value = "emp/updateInfo", method = RequestMethod.POST)
	public String updateInfo(HttpServletRequest request, MultipartFile myImg, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) 
			throws Exception{
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);

		
		//????????????
		System.out.println("YjController writeEmp fileupload Start...");
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
	    String savedName = uploadFile(myImg.getOriginalFilename(), myImg.getBytes(), uploadPath);
	    System.out.println("YjController writeEmp fileupload savedName-->" + savedName);
	    emp.setPh_path(savedName);
	    
	    emp.setEmp_num(emp_num);
		int result = es.updateInfo(emp);
		System.out.println("YjEmpController updateInfo Start...");
		System.out.println("YjEmpController updateInfo result--> " + result);
		
		return "forward:myInfoUpdate";
		
	}

	
	//?????????????????????
	@RequestMapping(value = "emp/empWrite")
	public String empWrite(YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);

		
		List<YjEmp> deptList = es.deptList();
		model.addAttribute("deptList", deptList);
		
		return "emp/empWrite";
	}
	
	//?????? selectbox??? ??? list?????????
	@RequestMapping(value = "selectTeam", method= RequestMethod.GET)
	@ResponseBody
	public List<YjEmp> getTeam(String dcode) {
			System.out.println("YjController get_team Start...");
	        List<YjEmp> teamList = es.teamList(dcode);
	        
	        return teamList;
	}
	
	//?????? selectbox??? ?????? list?????????
	@RequestMapping(value = "selectRnk", method= RequestMethod.GET)
	@ResponseBody
	public List<YjEmp> getRnk(String dcode) {
			System.out.println("YjController get_team Start...");
	        List<YjEmp> rankList = es.rankList(dcode);
	        
	        return rankList;
	}
	
	//????????????
	@RequestMapping(value = "emp/writeEmp", method = RequestMethod.POST)
	public String writeEmp(HttpServletRequest request, MultipartFile myImg, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) 
		throws Exception {
		//????????????
		System.out.println("YjController writeEmp fileupload Start...");
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
	    String savedName = uploadFile(myImg.getOriginalFilename(), myImg.getBytes(), uploadPath);
	    System.out.println("YjController writeEmp fileupload savedName-->" + savedName);
	    emp.setPh_path(savedName);   
		
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);	
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);

		 
		//??????????????????/????????????
	    System.out.println("YjController writeEmp empnum/pw Start...");
		String seq = String.format("%03d", es.countEmp()+1);
		System.out.println("seq : "+seq);
		System.out.println("?????? : "+emp.getEmp_hiredate());		
		String hiredate = emp.getEmp_hiredate().replace("-", "").substring(2, 6);		
		String empno = hiredate + seq;
		System.out.println("YjController writeEmp empnum--> " + empno);
		emp.setEmp_num(Integer.parseInt(empno));
		String tempPassword = (int) (Math.random() * 999999) + 1 + "";	
		System.out.println("YjController writeEmp pw--> " + tempPassword);
		emp.setEmp_pw(tempPassword);
		
		//?????? insert
		int result = es.writeEmp(emp);
		System.out.println("YjEmpController writeEmp Start...");
		System.out.println("YjEmpController writeEmp result--> " + result);
		
		//email??????
		System.out.println("mailSending...");
		String tomail = emp.getEmp_email();              // ?????? ?????? ?????????
		System.out.println(tomail);
		String setfrom = "jobis210704@gmail.com";
		String title = "???????????????."; 	
		
		try {
			// Mime ???????????? Internet ?????? Format
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom);    // ??????????????? ??????????????? ?????? ??????????????? ??????
			messageHelper.setTo(tomail);       // ???????????? ?????????
			messageHelper.setSubject(title);   // ??????????????? ????????? ????????????
			messageHelper.setText("?????????(????????????)????????? : " + empno + "?????? ????????????????????? : " + tempPassword); // ?????? ??????
			System.out.println("?????? ????????????????????? : " + tempPassword);
			mailSender.send(message);
			System.out.println("?????? ??????");   // ?????? ??????
//			s.tempPw(u_id, tempPassword)  ;// db??? ??????????????? ????????????????????? ????????????
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("?????? ??????");  // ?????? ?????? ??????
		}		
		
		
		return "forward:empList";
	}
	
	//?????????????????????
	@RequestMapping(value = "emp/empMng")
	public String empMng(int empno, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);	
		
		
		model.addAttribute("empno", empno);
		List<YjEmp> empMng = es.empMng(empno);
		model.addAttribute("empMng", empMng);
		
		List<YjEmp> deptList = es.deptList();
		model.addAttribute("deptList", deptList);
		
		
		return "emp/empMng";
	}
	
	//????????????
	@RequestMapping(value = "emp/mngEmp", method = RequestMethod.POST)
	public String mngEmp(YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);	
		
		System.out.println("YjController mngEmp Start...");
		int result = es.updateEmp(emp);
		System.out.println("YjController mngEmp result-->" + result);
		model.addAttribute("result", result);
		
		return "emp/empMng";
	}
	
	//????????????
	@RequestMapping(value = "emp/deleteEmp")
	public String deleteEmp(int empno, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);	
		
		System.out.println("YjController depeteEmp empno--> " + empno);
		int result2 = es.deleteEmp(empno);
		System.out.println("YjController deleteEmp Start...");
		System.out.println("YjController deleteEmp result--> " + result2);
		model.addAttribute("result2", result2);
		
		return "emp/empMng";
	}
	
	//???????????? ?????????
	@RequestMapping(value = "emp/deptUpdate")
	public String deptUpdate(YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);			
		
		System.out.println("YjController deptUpdate Start...");
		List<YjEmp> dtList = es.dtList();
		model.addAttribute("dtList", dtList);
		
		return "emp/deptUpdate";
	}
	
	//????????????
	@RequestMapping(value = "emp/addDept", method = RequestMethod.POST)
	public String addDept(String dept, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);	
		
		emp.setDept(dept);
		String dcode = "1" + Integer.toString(es.countDept()+1) + "0";
		emp.setDcode(dcode);
		String tcode = "1" + Integer.toString(es.countDept()+1) + "1";
		emp.setTcode(tcode);
		String team = dept.substring(0,2) + "1" + "???";
		emp.setTeam(team);
		System.out.println("YjController addDept Start...");
		System.out.println("YjController addDept dept-->" + dept);
		System.out.println("YjController addDept team-->" + team);
		
		int result = es.addDept(emp);
		System.out.println("YjController addDept result--> " + result);
		 
		return "forward:deptUpdate";
	}
	
	//??? ??????
	@RequestMapping(value = "emp/deleteTeam")
	public String deleteTeam(String team, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);	
		
		System.out.println("YjController deleteTeam Start...");
		System.out.println("YjController deleteTeam team-->" + team);
		
		int result = es.deleteTeam(team);
		System.out.println("YjController deleteTeam result--> " + result);
		 
		return "forward:deptUpdate";		
	}
	
	//?????????
	@RequestMapping(value = "emp/addTeam")
	public String addTeam(String dept, YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);	

		emp.setDept(dept);
		String tcode = "1" + es.getcodetD(dept) + Integer.toString(es.countTeam(dept)+1);
		emp.setTcode(tcode);
		String team = dept.substring(0,2) + Integer.toString(es.countTeam(dept)+1) + '???';
		emp.setTeam(team);
		System.out.println("YjController addTeam Start...");
		int result = es.addTeam(emp);
		System.out.println("YjController addTeam result--> " + result);		
		
		return "forward:deptUpdate";
	}
	

		  //???????????? Excel??????
		  
		  @GetMapping(value = "emp/excelDownload") 
		  public void excelDown(HttpServletResponse response, YjEmp emp) throws Exception {
		  
		  System.out.println("YjController excelDown Start..."); 
		  List<YjEmp> empListE = es.empListE();
		  
		  Workbook wb = new HSSFWorkbook(); Sheet sheet = wb.createSheet("????????????"); 
		  Row row = null; 
		  Cell cell = null; 
		  int rowNo = 2;
		  
	        // ??? ?????? ??????
          for (int i=2; i<=11; i++){
             sheet.autoSizeColumn(i);
             sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+(short)1024);
          }

         // ????????? ????????? ?????????
         CellStyle headStyle = wb.createCellStyle();

         // ?????? ???????????? ????????????.
         headStyle.setBorderTop(BorderStyle.THIN);
         headStyle.setBorderBottom(BorderStyle.THIN);
         headStyle.setBorderLeft(BorderStyle.THIN);
         headStyle.setBorderRight(BorderStyle.THIN);

         // ???????????? ??????????????????.
         headStyle.setFillForegroundColor(HSSFColorPredefined.INDIGO.getIndex());
         //headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
         headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

         // ???????????? ????????? ???????????????.
         headStyle.setAlignment(HorizontalAlignment.CENTER);
         headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

         // ???????????? ?????? ????????? ???????????? ??????
         CellStyle bodyStyle = wb.createCellStyle();
         bodyStyle.setBorderTop(BorderStyle.THIN);
         bodyStyle.setBorderBottom(BorderStyle.THIN);
         bodyStyle.setBorderLeft(BorderStyle.THIN);
         bodyStyle.setBorderRight(BorderStyle.THIN);
         bodyStyle.setAlignment(HorizontalAlignment.CENTER);
         bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

         //?????? ??????
         Font headerFont = wb.createFont();
         headerFont.setColor(IndexedColors.WHITE.getIndex());
         headerFont.setBold(true);
         headStyle.setFont(headerFont); // ?????? ????????????
         
         // ?????? ??????
         row = sheet.createRow(rowNo++);
         row.setHeight((short)470);
         cell = row.createCell(2);
         cell.setCellStyle(headStyle);
         cell.setCellValue("????????????");
         cell = row.createCell(3);
         cell.setCellStyle(headStyle);
         cell.setCellValue("??????");
         cell = row.createCell(4);
         cell.setCellStyle(headStyle);
         cell.setCellValue("??????");
         cell = row.createCell(5);
         cell.setCellStyle(headStyle);
         cell.setCellValue("??????");
         cell = row.createCell(6);
         cell.setCellStyle(headStyle);
         cell.setCellValue("??????");
         cell = row.createCell(7);
         cell.setCellStyle(headStyle);
         cell.setCellValue("?????????");
         cell = row.createCell(8);
         cell.setCellStyle(headStyle);
         cell.setCellValue("????????????");
         cell = row.createCell(9);
         cell.setCellStyle(headStyle);
         cell.setCellValue("??????");
         cell = row.createCell(10);
         cell.setCellStyle(headStyle);
         cell.setCellValue("?????????");


         // ????????? ?????? ??????
         for(YjEmp emp1 : empListE) {
             row = sheet.createRow(rowNo++);
             row.setHeight((short)320);
             cell = row.createCell(2);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getEmp_num());
             cell = row.createCell(3);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getEmp_name());
             cell = row.createCell(4);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getDept());
             cell = row.createCell(5);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getTeam());
             cell = row.createCell(6);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getRank());
             cell = row.createCell(7);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getEmp_email());
             cell = row.createCell(8);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getEmp_phnum());
             cell = row.createCell(9);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getAddress());
             cell = row.createCell(10);
             cell.setCellStyle(bodyStyle);
             cell.setCellValue(emp1.getEmp_hiredate());


         }

         // ????????? ????????? ????????? ??????
         response.setContentType("ms-vnd/excel");
         response.setHeader("Content-Disposition", "attachment;filename=EmpList.xls");

         // ?????? ??????
         wb.write(response.getOutputStream());
         wb.close();

			
}
	
	  //???????????? ?????????
	  private String uploadFile(String originalName, byte[] fileData , String uploadPath) 
			  throws Exception {
	     UUID uid = UUID.randomUUID();
	   // requestPath = requestPath + "/resources/image";
	    System.out.println("uploadPath->"+uploadPath);
	    // Directory ?????? 
		File fileDirectory = new File(uploadPath);
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
			System.out.println("???????????? ?????? ?????? : " + uploadPath);
		}
	
	    String savedName = uid.toString() + "_" + originalName;
	    System.out.println("savedName: " + savedName);
	    File target = new File(uploadPath, savedName);
	//	    File target = new File(requestPath, savedName);
	    FileCopyUtils.copy(fileData, target);   // org.springframework.util.FileCopyUtils
	    
	    return savedName;
	  }	
}
