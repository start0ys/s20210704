package com.oracle.s20210704.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.s20210704.model.JhCalendar;
import com.oracle.s20210704.model.JhRr;
import com.oracle.s20210704.model.SyMemberVO;
import com.oracle.s20210704.model.YsApv;
import com.oracle.s20210704.model.YsEmpCmt;
import com.oracle.s20210704.service.JhCalendarService;
import com.oracle.s20210704.service.JhPaging;
import com.oracle.s20210704.service.JhRrService;
import com.oracle.s20210704.service.SwMsgService;
import com.oracle.s20210704.service.YsApvService;
import com.oracle.s20210704.service.YsEmpCmtService;
import com.oracle.s20210704.service.YsPaging;



@Controller
public class YsController {

	@Autowired
	private YsEmpCmtService yecs;
	
	@Autowired
	private YsApvService    yas;
	
	@Autowired
	private JhRrService jrs;
	
	@Autowired
	private SwMsgService sms;
	
	@Autowired
	private JhCalendarService jcs;
	
	@GetMapping(value = "cmt/cmt")
	public String cmt(Model model , YsEmpCmt ysEmpCmt,String currentPage, HttpSession session, SyMemberVO  vo) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);				//?????? ????????? ??????
		model.addAttribute("svo",svo);
		
		int cmtTotal = yecs.cmtTotal();
		YsPaging yp = new YsPaging(cmtTotal, currentPage);
		ysEmpCmt.setStart(yp.getStart());
		ysEmpCmt.setEnd(yp.getEnd());
		List<YsEmpCmt> cmtList = yecs.cmtList(ysEmpCmt);
		model.addAttribute("cmtList", cmtList);
		model.addAttribute("yp", yp);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "cmt/cmt";
	}
	
	@PostMapping(value = "cmt/cmtSearch")
	public String searchDateCmt(Model model , YsEmpCmt ysEmpCmt,String currentPage, HttpSession session, SyMemberVO  vo) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);				//?????? ????????? ??????
		model.addAttribute("svo",svo);
		
		int cmtTotal = 0;
		//???????????? total ????????????
		//??????,?????? ???????????????
		if(ysEmpCmt.getSearchDept().equals("") && !ysEmpCmt.getSearchName().equals("")) { 
			cmtTotal = yecs.cmtNameTotal(ysEmpCmt);
		//??????,?????? ???????????????
		} else if(!ysEmpCmt.getSearchDept().equals("") && ysEmpCmt.getSearchName().equals("")) {
			cmtTotal = yecs.cmtDeptTotal(ysEmpCmt);
		//?????? ???????????????
		}else if(!ysEmpCmt.getSearchDept().equals("") && !ysEmpCmt.getSearchName().equals("")) {
			cmtTotal = yecs.cmtAllTotal(ysEmpCmt);	
		//??????,??????,?????? ???????????????
		}else {
			cmtTotal = yecs.cmtDateTotal(ysEmpCmt);	
		}
		
		List<YsEmpCmt> cmtList = null;
		
		//?????????
		YsPaging yp = new YsPaging(cmtTotal, currentPage);
		ysEmpCmt.setStart(yp.getStart());
		ysEmpCmt.setEnd(yp.getEnd());
		
		//???????????? ???????????? ????????? list ????????????
		//??????,?????? ???????????????
		if(ysEmpCmt.getSearchDept().equals("") && !ysEmpCmt.getSearchName().equals("")) {
			cmtList = yecs.cmtNameSearchList(ysEmpCmt);
		//??????,?????? ???????????????
		} else if(!ysEmpCmt.getSearchDept().equals("") && ysEmpCmt.getSearchName().equals("")) {
			cmtList = yecs.cmtDeptSearchList(ysEmpCmt);
		//?????? ???????????????
		}else if(!ysEmpCmt.getSearchDept().equals("") && !ysEmpCmt.getSearchName().equals("")) {
			cmtList = yecs.cmtAllSearchList(ysEmpCmt);		
		//??????,??????,?????? ???????????????
		}else {
			cmtList = yecs.cmtDateSearchList(ysEmpCmt);	
		}
		model.addAttribute("ysEmpCmt", ysEmpCmt);
		model.addAttribute("cmtList", cmtList);
		model.addAttribute("yp", yp);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "cmt/cmt";
		//??????????????? 2
	}
	
	@PostMapping(value = "cmt/absent")
	public String absent(Model model , YsEmpCmt ysEmpCmt, HttpSession session, SyMemberVO  vo) {
		
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);				//?????? ????????? ??????
		model.addAttribute("svo",svo);
		
		
		
		Date absent = ysEmpCmt.getAbsent();
		
		System.out.println(ysEmpCmt.getAbsent());
		
		List<YsEmpCmt> absentList = yecs.absentList(absent);
		
		System.out.println(absentList.get(0).getEmp_name());
		
		
		model.addAttribute("absent", absent);
		model.addAttribute("absentList", absentList);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);

		return "cmt/absent";
	}
	
	@GetMapping(value = "cmt/absentMD")
	public String absentMD(Model model ,int num, String dt,YsEmpCmt ysEmpCmt, HttpSession session, SyMemberVO  vo) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);				//?????? ????????? ??????
		model.addAttribute("svo",svo);
		
		String cmt_str = dt + "09:00:00";
		String cmt_end = dt + "18:00:00";
		System.out.println("num : "+num);
		System.out.println("dt : "+cmt_str);
		System.out.println("dt : "+cmt_end);
		ysEmpCmt.setEmp_num(num);
		ysEmpCmt.setMd_str(cmt_str);
		ysEmpCmt.setMd_end(cmt_end);
		yecs.cmtInsert(ysEmpCmt);
		return "redirect:cmt";
	}
	@GetMapping(value = "cmt/mycmt")
	public String mycmt(Model model ,HttpSession session, SyMemberVO  vo, YsEmpCmt ysEmpCmt,String currentPage) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("svo",svo);
		
		int mycmtTotal = yecs.mycmtTotal(emp_num);
		model.addAttribute("mycmtTotal", mycmtTotal);
				
		YsPaging yp = new YsPaging(mycmtTotal, currentPage);
		ysEmpCmt.setStart(yp.getStart());
		ysEmpCmt.setEnd(yp.getEnd());
		ysEmpCmt.setEmp_num(emp_num);
		
		List<YsEmpCmt> mycmtList = yecs.mycmtList(ysEmpCmt);
		model.addAttribute("mycmtList", mycmtList);
		model.addAttribute("yp", yp);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "cmt/mycmt";
	}
	@PostMapping(value = "cmt/cmtMD")
	public String mycmt(int state,String dt,int cmt_num,YsEmpCmt ysEmpCmt,String currentPage,RedirectAttributes redirect) {
		String cmt_str = null;
		String cmt_end = null;
		if(state == 1) {            //??????,??????
			cmt_str = dt + "09:01:00";
			cmt_end = dt + "21:01:00";
		}else if(state == 2) {      //??????,??????
			cmt_str = dt + "09:01:00";
			cmt_end = dt + "13:00:00";
		}else if(state == 3) {      //??????
			cmt_str = dt + "09:01:00";
			cmt_end = dt + "18:00:00";
		}else if(state == 4) {      //??????
			cmt_str = dt + "09:00:00";
			cmt_end = dt + "21:01:00";
		}else if(state == 5) {      //??????
			cmt_str = dt + "09:00:00";
			cmt_end = dt + "13:00:00";
		}else {                     //??????
			cmt_str = dt + "09:00:00";
			cmt_end = dt + "18:00:00";
		}
		ysEmpCmt.setCmt_num(cmt_num);
		ysEmpCmt.setMd_str(cmt_str);
		ysEmpCmt.setMd_end(cmt_end);
		yecs.cmtChange(ysEmpCmt);
		redirect.addAttribute("currentPage", currentPage);
		return "redirect:cmt";
	}
	
	/////////   ??????      /////////
	
	//snd??? ????????????..????????? ??????????????? ?????? ????????? ??????
	@GetMapping(value = "apv/apvSnd")
	public String apvSnd(Model model ,HttpSession session, SyMemberVO vo,YsApv ysApv,String currentPage) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("svo",svo);
		model.addAttribute("emp_num", emp_num);
		
		int rcv_num = emp_num;
		int rcvTotal = yas.rcvTotal(rcv_num);
		model.addAttribute("rcvTotal", rcvTotal);
		
		YsPaging yp = new YsPaging(rcvTotal, currentPage);
		ysApv.setStart(yp.getStart());
		ysApv.setEnd(yp.getEnd());
		ysApv.setApv_mid_emp(rcv_num);
		
		List<YsApv> rcvList = yas.apv_RcvList(ysApv);
		model.addAttribute("rcvList", rcvList);
		model.addAttribute("yp", yp);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "apv/apvSnd";
	}
	
	//rcv??? ????????????..????????? ??????????????? ?????? ????????? ??????
	@GetMapping(value = "apv/apvRcv")
	public String apvRcv(Model model ,HttpSession session, SyMemberVO vo,YsApv ysApv,String currentPage) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("svo",svo);
		model.addAttribute("emp_num", emp_num);
		
		int snd_num = emp_num;
		int sndTotal = yas.sndTotal(snd_num);
		model.addAttribute("sndTotal", sndTotal);
		
		YsPaging yp = new YsPaging(sndTotal, currentPage);
		ysApv.setStart(yp.getStart());
		ysApv.setEnd(yp.getEnd());
		ysApv.setApv_snd(snd_num);
		
		List<YsApv> sndList = yas.apv_SndList(ysApv);
		model.addAttribute("sndList", sndList);
		model.addAttribute("yp", yp);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "apv/apvRcv";
	}
	@GetMapping(value = "apv/apvWrite")
	public String apvWrite(Model model ,HttpSession session, SyMemberVO vo,YsApv ysApv) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("svo",svo);
		model.addAttribute("emp_num", emp_num);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		
		return "apv/apvWrite";
	}
	
	@PostMapping(value = "apv/apvWrite")
	public String apvInsert(HttpServletRequest request, MultipartFile file1,YsApv ysApv) throws Exception {
		if(file1.isEmpty()) { ysApv.setApv_pl_nm("");}
		else {			
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
			String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
			ysApv.setApv_pl_nm(saveName);
			
		}
		if(ysApv.getFnlChk() == 1) {        // ?????? ???????????? ?????? ?????? 
			System.out.println("??????x");
			yas.fnlRcvInsert(ysApv);
		}else {                             // ?????? ???????????? ?????? ??????
			System.out.println("?????? o");
			yas.midRcvInsert(ysApv);
		}
		return "redirect:apvRcv";
	}
	
	@GetMapping(value = "apv/apvRcvDetail")
	public String apvRcvDetail(Model model ,HttpSession session, SyMemberVO vo,YsApv ysApv,int sq) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("svo",svo);
		model.addAttribute("emp_num", emp_num);
		
		ysApv.setApv_sq(sq);
		ysApv.setRcv_num(emp_num);

		YsApv rcvDetail = yas.rcvDetail(ysApv);
		model.addAttribute("rcvDetail", rcvDetail);
		
		List<YsApv> apv_ing = yas.apv_ing(sq);
		model.addAttribute("apv_ing", apv_ing);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "apv/apvRcvDetail";
	}
	
	@PostMapping(value = "apv/apvok")
	public String apvok(YsApv ysApv) {
	
		if(ysApv.getApv_ok() == 1) {        //?????? ????????? ?????????????????????
			yas.midOk(ysApv);
		}else if(ysApv.getApv_ok() == 2) {  //?????? ????????? ?????????????????????
			yas.midToFnlOk(ysApv);
		}else if(ysApv.getApv_ok() == 3) {  //?????? ????????? ?????? ????????? ?????????????????????
			yas.fnlOk(ysApv);
		}

		return "redirect:apvSnd";
	}
	@GetMapping(value = "apv/apvno")
	public String apvnp(int sq,String nono, YsApv ysApv) {
		ysApv.setApv_sq(sq);
		ysApv.setApv_no(nono);
		yas.apv_no(ysApv);
		return "redirect:apvSnd";
	}
	
	@GetMapping(value = "apv/apvSndDetail")
	public String apvSndDetail(Model model ,HttpSession session, SyMemberVO vo, int sq) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("svo",svo);
		model.addAttribute("emp_num", emp_num);
		
		YsApv sndDetail = yas.sndDetail(sq);
		model.addAttribute("sndDetail", sndDetail);
		
		List<YsApv> apv_ing = yas.apv_ing(sq);
		model.addAttribute("apv_ing", apv_ing);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		YsApv ingEmp = yas.ingEmp(sq);
		model.addAttribute("ingEmp", ingEmp);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "apv/apvSndDetail";
	}
	
	@GetMapping(value = "apv/apvReWrite")
	public String apvReWrite(Model model ,HttpSession session, SyMemberVO vo, int sq) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("svo",svo);
		model.addAttribute("emp_num", emp_num);
		
		YsApv sndDetail = yas.sndDetail(sq);
		model.addAttribute("sndDetail", sndDetail);
		
		int unreadTotal = yas.unreadTotal(emp_num);
		int apvNoTotal  = yas.apvNoTotal(emp_num);
		model.addAttribute("unreadTotal", unreadTotal);
		model.addAttribute("apvNoTotal", apvNoTotal);
		
		YsApv nextEmp   = yas.nextEmp(sq);
		model.addAttribute("nextEmp", nextEmp);
		
		int unreadMsg = sms.unreadMsg(emp_num);
		model.addAttribute("unreadMsg", unreadMsg);
		
		return "apv/apvReWrite";
	}
	
	@PostMapping(value = "apv/apvReWrite")
	public String apvReInsert(HttpServletRequest request, MultipartFile file1, YsApv ysApv)throws Exception {
		System.out.println("??????1");
		if(ysApv.getApv_pl_nm().equals("change") || ysApv.getApv_pl_nm() == "change") {
			System.out.println("??????2");
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
			String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
			ysApv.setApv_pl_nm(saveName);			
		}
		System.out.println("??????3");
		if(ysApv.getFnlChk() == 1) {        // ?????? ???????????? ?????? ?????? 
			System.out.println("??????x");
			yas.fnlSndDelete(ysApv.getApv_sq());
			yas.fnlRcvInsert(ysApv);
		}else {                             // ?????? ???????????? ?????? ??????
			System.out.println("?????? o");
			yas.midSndDelete(ysApv.getApv_sq());
			yas.midRcvInsert(ysApv);
		}
		
		return "redirect:apvRcv";	
	}
	
	private String uploadFile(String orginalName, byte[] fileData, String uploadPath) throws Exception{
		UUID uid = UUID.randomUUID();
		// Directory ??????
		File fileDirectory = new File(uploadPath);
		if(!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}
		String savedName = uid.toString() + "_" + orginalName;
		File target = new File(uploadPath,savedName);
		FileCopyUtils.copy(fileData, target);
		return savedName;
	}
	
  @GetMapping(value = "apv/ysdownload")
  public void fileDownload(HttpServletRequest request, HttpServletResponse response,String fileName)
			throws ServletException, IOException {
		try {
			  request.setCharacterEncoding("utf-8");
			  String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
			  
			  // ???????????? ????????? ?????? ????????? filePath??? ??????
			  String filePath = uploadPath + fileName;
			  // ???????????? ????????? ?????????
			  File file = new File(filePath);
			  byte b[] = new byte[4096];
			  // page??? ContentType?????? ???????????? ????????? ?????? ???????????????
			  response.reset();
			  response.setContentType("application/octet-stream");
			  // ?????? ?????????
			  String Encoding = new String(fileName.getBytes("UTF-8"), "8859_1");
			  // ?????? ????????? ???????????? ??? ???????????? ?????? ????????? ???????????? ???????????? ??????
			  response.setHeader("Content-Disposition", "attachment; filename = " + Encoding);
			  // ????????? ?????? ????????? ???????????? ????????? ??????
			  FileInputStream in = new FileInputStream(filePath);
			  // ???????????? ????????? ?????? ????????? ???????????? ????????? ????????? ????????? ??????
			  ServletOutputStream out2 = response.getOutputStream();
			  int numRead;
			  // ????????? ?????? b??? 0??? ?????? numRead??? ?????? ????????? ?????? (??????)
			  while((numRead = in.read(b, 0, b.length)) != -1){
			  out2.write(b, 0, numRead);
			  }  
			  out2.flush();
			  out2.close();
			  in.close();
 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
	}
  
  
  @GetMapping(value = "apv/fileDelete")
	public void uploadFileDelete(HttpServletRequest request, Model model,String fileName) throws Exception{
	   try {
		  String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		  String deleteFile = uploadPath + fileName;
		  upFileDelete(deleteFile);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
  	private void upFileDelete(String deleteFileName)   throws Exception {
		File file = new File(deleteFileName); 
		if( file.exists() ){ 
			file.delete();
		}
	}
  	@GetMapping(value = "cmt/excelDown")

  	public void excelDown(HttpServletResponse response, YsEmpCmt ysEmpCmt) throws Exception {
  	    // ????????????
  		List<YsEmpCmt> cmtList = yecs.excelList();

  	    // ????????? ??????

  	    Workbook wb = new HSSFWorkbook();
  	    Sheet sheet = wb.createSheet("?????? ??????");
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
	    cell.setCellValue("????????????");
	    cell = row.createCell(8);
  	    cell.setCellStyle(headStyle);
  	    cell.setCellValue("????????????");
  	    cell = row.createCell(9);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("??????");
	    cell = row.createCell(10);
  	    cell.setCellStyle(headStyle);
  	    cell.setCellValue("??????");
  	    cell = row.createCell(11);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("?????????");

  	    // ????????? ?????? ??????
  	    for(YsEmpCmt cmt : cmtList) {
  	        row = sheet.createRow(rowNo++);
  	        row.setHeight((short)320);
  	        cell = row.createCell(2);
  	        cell.setCellStyle(bodyStyle);
  	        cell.setCellValue(cmt.getEmp_num());
  	        cell = row.createCell(3);
  	        cell.setCellStyle(bodyStyle);
  	        cell.setCellValue(cmt.getEmp_name());
  	        cell = row.createCell(4);
  	        cell.setCellStyle(bodyStyle);
  	        cell.setCellValue(cmt.getDcontent());
  	        cell = row.createCell(5);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(cmt.getTcontent());
	        cell = row.createCell(6);
  	        cell.setCellStyle(bodyStyle);
  	        cell.setCellValue(cmt.getRcontent());
  	        cell = row.createCell(7);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(cmt.getSrttime());
	        cell = row.createCell(8);
  	        cell.setCellStyle(bodyStyle);
  	        cell.setCellValue(cmt.getEndtime());
  	        cell = row.createCell(9);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(cmt.getCmt_date());
	        cell = row.createCell(10);
  	        cell.setCellStyle(bodyStyle);
  	        cell.setCellValue(state(cmt.getSrttime(), cmt.getEndtime()));
  	        cell = row.createCell(11);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(mdDate(cmt.getCmt_md()));

  	    }

  	    // ????????? ????????? ????????? ??????
  	    response.setContentType("ms-vnd/excel");
  	    response.setHeader("Content-Disposition", "attachment;filename=CmtList.xls");

  	    // ?????? ??????
  	    wb.write(response.getOutputStream());
  	    wb.close();

  	}
	private String state(String start,String end) throws ParseException {
		String state = "";
		String state1 = "";
		String state2 = "";
		SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.KOREA);
		java.util.Date srtTime    = f.parse(start);
		java.util.Date endTime    = f.parse(end);
		java.util.Date lateTime   = f.parse("09:00");
		java.util.Date earlyTime  = f.parse("16:00");
		java.util.Date extendTime = f.parse("19:00");
		java.util.Date normalTime = f.parse("18:00");
		long late   = srtTime.getTime()    - lateTime.getTime();
		long early  = earlyTime.getTime() - endTime.getTime();
		long extend = endTime.getTime()   - extendTime.getTime();
		if(late > 0 || early >= 0 || extend >= 0) {
			if(late > 0) { state1 = "??????"; }
			if(early >= 0) { state2 = "??????"; }
			if(extend >= 0) { state2 = "??????"; }
		}else { state1 = "??????"; }
		if(!state1.equals("") && !state2.equals("")) {
			state = state1+","+state2;
		}else { state = state1+state2; }
		return state;
	}
	private String mdDate(Date cmt_md) {
		String mdDate = "";
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd"); 
		if(cmt_md != null) { mdDate =fmt.format(cmt_md); }
		else { mdDate = "-";}
		return mdDate;
	}
	
	@GetMapping(value = "workInOut")
	public String workInOut(Model model, HttpSession session, SyMemberVO  vo, int inOut, JhRr jhRr, String currentPage3, JhCalendar jhCalendar,HttpServletRequest request, HttpServletResponse response) {
		int emp_num = (int)session.getAttribute("member");	//?????? ????????? ??????
		vo.setEmp_num(emp_num);								//?????? ????????? ??????
		SyMemberVO svo = jrs.show(vo);						//?????? ????????? ??????	
		model.addAttribute("emp_num",emp_num);				//?????? ????????? ??????
		model.addAttribute("svo",svo);	             		//?????? ????????? ??????
		
		int cmt_check = yecs.cmtCheck(emp_num);
		
		if(inOut == 1 && cmt_check == 1) {          //??????
			int workIn = yecs.workIn(emp_num);
			model.addAttribute("workIn", workIn);
		}else if(inOut == 2 && cmt_check == 2){     //??????
			int workOut = yecs.workOut(emp_num);
			model.addAttribute("workOut", workOut);
		}else if(inOut == 1 && cmt_check == 2) {    //????????????
			model.addAttribute("cmt_chk", 2);
		}else if(inOut == 2 && cmt_check == 1){		//????????????
			model.addAttribute("cmt_chk", 1);
		}
		
		int total = jrs.total3();							//????????????
		JhPaging jhpg3 = new JhPaging(total, currentPage3);	//????????????
		jhRr.setStart(jhpg3.getStart());					//????????????
		jhRr.setEnd(jhpg3.getEnd());						//????????????
		jhRr.setRr_type(3);									//????????????
		List<JhRr> listJhRr2 = jrs.listJhRr2(jhRr);			//????????????
		model.addAttribute("total3", total);				//????????????
		model.addAttribute("jhpg3", jhpg3);					//????????????	
		model.addAttribute("listJhRr2", listJhRr2);			//????????????
		String dep_num = jcs.depNum(emp_num);				//??????,dep_num ??????
		jhCalendar.setDep_num(dep_num);						//??????,dep_num ??????
		int unreadMsg = sms.unreadMsg(emp_num);				//???????????????
		model.addAttribute("unreadMsg", unreadMsg);			//???????????????
		
		Calendar calendar1 = Calendar.getInstance();								//??????
		jhCalendar.setEmp_num(emp_num);												//??????				
		String strType = (String)request.getParameter("type");						//??????
		
		
		if(strType != null && !strType.equals("")) {								//??????
		    int intYear     = Integer.parseInt(request.getParameter("curYear"));	//??????
		    int intMonth    = Integer.parseInt(request.getParameter("curMonth"));	//??????
		    int intDay      = Integer.parseInt(request.getParameter("curDay"));		//??????
			    if(intMonth > 12) {													//??????
		        intYear += 1;														//??????
		        intMonth = 1;														//??????
		    }																		//??????
		    if(intMonth < 1) {														//??????		
		        intYear -= 1;														//??????
		        intMonth = 12;														//??????
		    }																		//??????	
			    calendar1.set(intYear, intMonth-1, intDay);							//??????	
		}																			//??????
		model.addAttribute("today",       calendar1.getTime());						//??????		
		model.addAttribute("curYear",     calendar1.get(Calendar.YEAR));			//??????
		model.addAttribute("curMonth",    (calendar1.get(Calendar.MONTH) + 1));		//??????
		model.addAttribute("curDay",      calendar1.get(Calendar.DATE));			//??????
		calendar1.set(Calendar.DATE, 1); 											//??????
		model.addAttribute("firstDayOfMonth", calendar1.getTime());					//??????
		String first = String.format("%1$tF %1$tT", calendar1.getTime()).substring(0, 10);	//??????
		jhCalendar.setFirst(first);															//??????
		
		session.setAttribute("firstDayOfWeek", calendar1.get(Calendar.DAY_OF_WEEK));		//??????
		session.setAttribute("lastDayOfMonth", calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));	//??????
		calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));			//??????
		session.setAttribute("lastDayOfLastWeek", calendar1.get(Calendar.DAY_OF_WEEK));				//??????
		calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) + 1);							//??????
		calendar1.set(Calendar.DATE, 1);															//??????
		model.addAttribute("firstDayOfNextMonth", calendar1.getTime());								//??????
		String last =String.format("%1$tF %1$tT", calendar1.getTime()).substring(0, 10);			//??????
		jhCalendar.setLast(last);																	//??????	
		
        List<JhCalendar> list = jcs.list(jhCalendar);												//??????
        model.addAttribute("list", list);															//??????		

		int unreadTotal = yas.unreadTotal(emp_num);			//?????? ??????
		int apvNoTotal  = yas.apvNoTotal(emp_num);			//?????? ??????	
		model.addAttribute("unreadTotal", unreadTotal);		//?????? ??????
		model.addAttribute("apvNoTotal", apvNoTotal);		//?????? ??????
		
		
		
		return "mainpage";
	}

	
}
