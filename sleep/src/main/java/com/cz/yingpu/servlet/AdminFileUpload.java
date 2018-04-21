package com.cz.yingpu.servlet;

import com.cz.yingpu.frame.util.ImgCompress;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.StringUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileUpload
 */
public class AdminFileUpload extends HttpServlet {
	public static final long serialVersionUID = 1L;

	//前端自定义文件目录
	public static final String filepathdir = "filepathdir";
	
	//保存文件的文件夹名称
	public static final String uploadDirName="upload";
	//public static final String realpath="/webdata/app/images/";
	public static final String realpath="D:\\workspace\\.metadata\\.me_tcat7\\webapps\\upload\\sleep\\";
	public static final String realfilepath= realpath+uploadDirName;
	//http地址
	//public static final String httppath="http://39.106.27.146:22222/images/";
	public static final String httppath="http://192.168.1.187:8080/upload/sleep/";
	public static final String httpfilepath=httppath+uploadDirName;
	//callback的url的key
	public static final String callbackurlName="callbackurl";
	// 1. 创建工厂类
	DiskFileItemFactory factory = new DiskFileItemFactory();
	// 2. 创建FileUpload对象
	ServletFileUpload upload = new ServletFileUpload(factory);
	public AdminFileUpload() {
		super();
		
		 //设置上传文件的最大值
		//upload. setFileSizeMax(1024000);
		// 上传进度
		upload.setProgressListener(new ProgressListener() {
			long num = 0;
			@Override
			public void update(long bytesRead, long contentLength, int items) {
				long progress = bytesRead * 100 / contentLength;
				if (progress == num)
					return;
				num = progress;
				System.out.println("上传进度:" + progress + "%");
				// request.getSession().setAttribute("progress", progress);
			}
		});
	}
	/**
	 * 上传文件必须为POST方法
	 */
	@Override
	@SuppressWarnings({ "static-access", "deprecation" })	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Integer userId = 0 ;
		if(request.getParameter("userId")!=null){
			userId = StringUtil.str2Int(request.getParameter("userId").toString(), 0);
		}
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// 3. 判断是否是上传表单
		boolean b = ServletFileUpload.isMultipartContent(request);
		if (!b) { // 不是文件上传
			return;
		}
		// 4. 解析request，获得FileItem项
		List<FileItem> fileitems = null;
		try {
			fileitems = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();

		}
		if (fileitems == null) {
			return ;
		}
		// 创建文件
//		ServletContext context = getServletContext();
//		String dir = context.getRealPath("/")+"\\"+uploadDirName+"\\"+userId;
		String dir = realfilepath+"/"+userId;
		String dirpath = null;
	    String httppath=httpfilepath;
		// 5. 遍历集合,获取项目路径
	    String callbackurl=null;

		for (FileItem item : fileitems) {
			if(!item.isFormField()){
				continue;
			}
			if (callbackurlName.equals(item.getFieldName())) {//需要跳转的url
				callbackurl=item.getString();
			}else if (filepathdir.equals(item.getFieldName())) {
				if (item.getString().contains("\\.")) {// 包含路径.
					continue;
				}
				dirpath = item.getString();
			}
		}
		
//		if(callbackurl==null){
//			return;
//		}
		
		
		String allhttpfile="";

		if (dirpath != null) {
			dir = dir + "/" + dirpath;
			httppath=httppath+"/"+dirpath;
		}

		boolean isFrontEnd = false;
		// 5. 遍历集合
		for (FileItem item : fileitems) {
			if (item.isFormField()) {// 普通字段
				if ("from".equals(item.getFieldName()) && "frontend".equals(item.getString())) {
					isFrontEnd = true;
				}
				continue;
			}
			
			// 获得文件名
			String filename = item.getName();
			String prefix=filename.substring(filename.lastIndexOf(".")+1);
			filename = UUID.randomUUID().toString()+"."+prefix;
			
			Pattern excelPtn = Pattern.compile(".+\\.(?:xls|xlsx)$");
			Pattern imgPtn = Pattern.compile(".+\\.(?:jpg|png|bmp|jpeg|gif|ico)$");
			boolean isExcel = false, isImg = false;
			if (!(isExcel = excelPtn.matcher(filename).matches())
					&& !(isImg = imgPtn.matcher(filename).matches())) {
				out.print("上传出错: 不允许的文件格式！");
				return;
			}
			
			if (isFrontEnd) {
				if (isImg && item.getSize() > 5 * 1024 * 1024) {
					out.print("上传出错: 图片大小必须小于5M！");
					return;
				}
			}
			
			File f_dir=new File(dir);
			if(!f_dir.exists()){
				f_dir.mkdirs();	
			}
			
			
			File file = new File(dir+"/"+ filename);
			if(!file.exists()){
				file.createNewFile();
			}

			allhttpfile=allhttpfile+httppath+"/"+userId+"/"+filename+";";
//			urlList.add(httppath+"/"+userId+"/"+filename);
			// 获得流，读取数据写入文件
			InputStream in = item.getInputStream();
			FileOutputStream fos = new FileOutputStream(file);

			int len;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) > 0){
				fos.write(buffer, 0, len);
			}
			
			fos.close();
			in.close();
			item.delete(); // 删除临时文件
		}
		
		if(allhttpfile.endsWith(";")){
			allhttpfile=allhttpfile.substring(0, allhttpfile.length()-1);
		}
		  out.print(allhttpfile);
//		returnObject.setData(allhttpfile);
//		JSONObject json = JSONObject.fromObject(returnObject);//将java对象转换为json对象
//		String str = json.toString();//将json对象转换为字符串
//		out.print(str);
//		out.print("{" +allhttpfile+"}");
//		JSONObject json = new JSONObject();
//		json.put("url",urlList);
//		out.print(json.toString());
	    System.out.println(allhttpfile);
	}


	/**
	 * 将Base64字符串图片数据保存到文件中
	 * @param base64Data base64图片数据
	 * @param userId 用户ID，用户区分用户
 	 * @return 保存的图片文件URL
	 */
	public static String saveBase64ImageToFile(String base64Data, Integer userId) {
		String savePath = AdminFileUpload.realfilepath;
		String imgName = UUID.randomUUID().toString() + ".png";
		String urlPath = AdminFileUpload.httpfilepath + "/" + userId + "/" + imgName;
		ImgCompress.Base64ToImage(base64Data, savePath + "/" + userId + "/" + imgName);
		return urlPath;
	}

}
