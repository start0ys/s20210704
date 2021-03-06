package com.oracle.s20210704.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.s20210704.dao.YsApvDao;
import com.oracle.s20210704.model.YsApv;
import com.oracle.s20210704.model.YsEmpCmt;

@Service
public class YsApvServiceImpl implements YsApvService {

	@Autowired
	private YsApvDao yad;
	
	@Override
	public int rcvTotal(int rcv_num) {
		int rcvTotal = yad.rcvTotal(rcv_num);
		return rcvTotal;
	}
	
	@Override
	public List<YsApv> apv_RcvList(YsApv ysApv) {
		List<YsApv> apv_RcvList = yad.apv_RcvList(ysApv);
		return apv_RcvList;
	}

	@Override
	public int sndTotal(int snd_num) {
		int sndTotal = yad.sndTotal(snd_num);
		return sndTotal;
	}

	@Override
	public List<YsApv> apv_SndList(YsApv ysApv) {
		List<YsApv> apv_SndList = yad.apv_SndList(ysApv);
		return apv_SndList;
	}

	@Override
	public List<YsEmpCmt> rcvList1(int emp_num) {
		List<YsEmpCmt> rcvList1 = yad.rcvList1(emp_num);
		return rcvList1;
	}

	@Override
	public List<YsEmpCmt> rcvList2(int emp_num) {
		List<YsEmpCmt> rcvList2 = yad.rcvList2(emp_num);
		return rcvList2;
	}

	@Override
	public List<YsEmpCmt> rcvList3(int emp_num) {
		List<YsEmpCmt> rcvList3 = yad.rcvList3(emp_num);
		return rcvList3;
	}

	@Override
	public List<YsEmpCmt> rcvList4() {
		List<YsEmpCmt> rcvList4 = yad.rcvList4();
		return rcvList4;
	}

	@Override
	public List<YsEmpCmt> rcvList567(String rnk) {
		List<YsEmpCmt> rcvList567 = yad.rcvList567(rnk);
		return rcvList567;
	}

	@Override
	public List<YsEmpCmt> rcvList8() {
		List<YsEmpCmt> rcvList8 = yad.rcvList8();
		return rcvList8;
	}

	@Override
	public void midRcvInsert(YsApv ysApv) {
		yad.midRcvInsert(ysApv);
	}

	@Override
	public void fnlRcvInsert(YsApv ysApv) {
		yad.fnlRcvInsert(ysApv);
	}

	@Override
	public YsApv rcvDetail(YsApv ysApv) {
		YsApv rcvDetail = yad.rcvDetail(ysApv);
		return rcvDetail;
	}

	@Override
	public void midOk(YsApv ysApv) {
		yad.midOk(ysApv);
		
	}

	@Override
	public void midToFnlOk(YsApv ysApv) {
		yad.midToFnlOk(ysApv);
		
	}

	@Override
	public void fnlOk(YsApv ysApv) {
		yad.fnlOk(ysApv);
		
	}

	@Override
	public void apv_no(YsApv ysApv) {
		yad.apv_no(ysApv);
		
	}

	@Override
	public List<YsApv> apv_ing(int apv_sq) {
		List<YsApv> apv_ing = yad.apv_ing(apv_sq);
		return apv_ing;
	}

	@Override
	public int unreadTotal(int emp_num) {
		int unreadTotal = yad.unreadTotal(emp_num);
		return unreadTotal;
	}

	@Override
	public YsApv sndDetail(int apv_sq) {
		YsApv sndDetail = yad.sndDetail(apv_sq);
		return sndDetail;
	}

	@Override
	public int apvNoTotal(int emp_num) {
		int apvNoTotal = yad.apvNoTotal(emp_num);
		return apvNoTotal;
	}

	@Override
	public YsApv nextEmp(int apv_sq) {
		YsApv nextEmp = yad.nextEmp(apv_sq);
		return nextEmp;
	}

	@Override
	public void midSndDelete(int apv_sq) {
		yad.midSndDelete(apv_sq);	
	}

	@Override
	public void fnlSndDelete(int apv_sq) {
		yad.fnlSndDelete(apv_sq);		
	}

	@Override
	public YsApv ingEmp(int apv_sq) {
		YsApv ingEmp = yad.ingEmp(apv_sq);
		return ingEmp;
	}


}
