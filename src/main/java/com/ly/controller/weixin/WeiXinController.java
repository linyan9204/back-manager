package com.ly.controller.weixin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.utils.ConnectorIoHanVang;
import com.ly.utils.Meg;

/**
* @ClassName: WeiXinController
* @Description: 
* @author linyan
* @date 2017年12月11日 上午11:26:25
*
*/
@RequestMapping("weixin")
@Controller
public class WeiXinController {

	
	@RequestMapping(value = "indexa")
	public void indexa(HttpServletRequest request,HttpServletResponse response){
		PrintWriter printWriter = null;
		String echostr = (String) request.getParameter("echostr");
		Enumeration<String> s = request.getParameterNames();
		while (s.hasMoreElements()) {
			String string = (String) s.nextElement();
			System.out.println(string+"<<<======>>>"+request.getParameter(string));
		}
//		Enumeration<String> sg = request.getAttributeNames();
//		while (sg.hasMoreElements()) {
//			String string = (String) sg.nextElement();
//			System.out.println(string+"<<<>>>"+request.getAttribute(string));
//		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = "";
			StringBuffer info=new StringBuffer();
			for (line = br.readLine(); line != null; line = br.readLine()) {
				info.append(line);
			}
			System.out.println(info);
			printWriter = response.getWriter();
			printWriter.print(echostr);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (printWriter!=null) {
				printWriter.close();
			}
		}
	}
	
	@RequestMapping(value = "testban" ,produces ="application/json;charset=utf8")
	@ResponseBody
	public Object testban(HttpServletRequest request ,HttpServletResponse response,String sdate,String edate){
		try {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			if (sdate==null||sdate.equals("")) {
				sdate = year+"-"+month+"-"+day;
			}
			if (edate==null||edate.equals("")) {
				edate = sdate+" 23:59:59";
			}
			ConnectorIoHanVang.sendMes(sdate,edate);
			String m = Meg.mes.replace("workcode=\"0\" status=\"0\" authority=\"0X11\" card_src=\"from_check\"", "").replace("time=", "").replace("id=", "").replace("name=", "").replace("\"", "");
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
