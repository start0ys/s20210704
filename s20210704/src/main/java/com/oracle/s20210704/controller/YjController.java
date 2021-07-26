package com.oracle.s20210704.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s20210704.model.SyMemberVO;
import com.oracle.s20210704.model.YjEmp;
import com.oracle.s20210704.service.JhRrService;
import com.oracle.s20210704.service.YjEmpService;
import com.oracle.s20210704.service.YjPaging;



@Controller
public class YjController {

	@Autowired
	private YjEmpService es;
	@Autowired
	private JhRrService jrs;

	//사원정보관리 출력
	@RequestMapping(value = "emp/empList")
	public String empList(YjEmp emp, String currentPage, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);

		System.out.println("YjController Start empList...");
		int total = es.total();
		System.out.println("YjController total--> " + total);
		System.out.println("currentPage=>" + currentPage);
		//                     14     NULL(0,1....)
		YjPaging pg = new YjPaging(total, currentPage);
		emp.setStart(pg.getStart());   // 시작시 1
		emp.setEnd(pg.getEnd());       // 시작시 10 
		List<YjEmp> empList = es.empList(emp);
		System.out.println("YjEmpController list empList.size()=>" + empList.size());
		model.addAttribute("total", total);
		model.addAttribute("empList",empList);
		model.addAttribute("pg",pg);
		return "emp/empList";
		
	}
	
	@ResponseBody
	@PostMapping(value = "emp/updateRef")
	public String updateRef(@RequestBody YjEmp emp, HttpSession session, SyMemberVO  vo, Model model) {
		int emp_num = (int)session.getAttribute("member");
		vo.setEmp_num(emp_num);
		SyMemberVO svo = jrs.show(vo);
		model.addAttribute("emp_num",emp_num);
		model.addAttribute("svo",svo);
		
		emp.getEmpno();
		emp.getRef();
		System.out.println("YjEmpController updateRef Start...");
		System.out.println("YjEmpController updateR empno--> " + emp.getEmpno());
		System.out.println("YjEmpController updateR ref--> " + emp.getRef());
		int result = es.updateRef(emp);
		model.addAttribute("result", result);
		return "forward:emp/empList";
	}

}
