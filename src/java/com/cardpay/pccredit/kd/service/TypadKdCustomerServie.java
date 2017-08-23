package com.cardpay.pccredit.kd.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.ServerSideConstant;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.jnpad.model.JNPAD_SFTPUtil;
import com.cardpay.pccredit.kd.dao.TypadKdCustomerDao;
import com.cardpay.pccredit.kd.model.TypadKdCustomer;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.wicresoft.jrad.base.database.id.IDGenerator;

@Service
public class TypadKdCustomerServie {
	@Autowired
	private TypadKdCustomerDao commonDao;
	public List<TypadKdCustomer>selectSqCustomer(HttpServletRequest request){
		String chineseName = request.getParameter("chineseName");
		return commonDao.selectSqCustomer(chineseName);
	}
	
	public List<TypadKdCustomer>selectSqCustomerHistory(HttpServletRequest request){
		String chineseName = request.getParameter("chineseName");
		return commonDao.selectSqCustomerHistory(chineseName);
	}
	
	public List<TypadKdCustomer>selectOrder(HttpServletRequest request){
		String chineseName = request.getParameter("chineseName");
		return commonDao.selectOrder(chineseName);
	}
	
	public List<TypadKdCustomer> selectImageType( String id){
		
		return commonDao.selectImageType(id);
	}
	public List<TypadKdCustomer>selectAllIma(String id,String name){
		List<TypadKdCustomer>result=commonDao.selectImageByType(id, name);
		return result;
	}
	/* 上传影像资料专用 */
	public  void uploadYxzlFileBySpring(MultipartFile file,String id,String name) {
		String newFileName = null;
		String fileName = null;
		Map<String, String> map = new HashMap<String, String>();
		String path = Constant.FILE_PATH + id + File.separator;
		File tempDir = new File(path);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		try {
			// 取得上传文件
			if (file != null && !file.isEmpty()) {
				fileName = file.getOriginalFilename();
				File tempFile = new File(path
						+ file.getOriginalFilename());
				if (tempFile.exists()) {
					newFileName = IDGenerator.generateID() + "."
							+ file.getOriginalFilename().split("\\.")[1];
				} else {
					newFileName = file.getOriginalFilename();
				}
				File localFile = new File(path + newFileName);
				file.transferTo(localFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TypadKdCustomer t=new TypadKdCustomer();
		t.setID(IDGenerator.generateID());
		t.setAPPLY_ID(id);
		t.setUPLOAD_FILE_NAME(fileName);
		t.setUPLOAD_PATH(path + newFileName);
		t.setUPLOAD_TIME(new Date() );
		t.setREMARKS(name );
		commonDao.inserIma(t);
	}
	public void downLoadYxzlJn(HttpServletResponse response,String id) throws Exception{
		TypadKdCustomer v = commonDao.selectImaById(id);
			if(v!=null){
				String GIF = "image/gif;charset=GB2312";// 设定输出的类型
				String JPG = "image/jpeg;charset=GB2312";
				String BMP = "image/bmp";
			    String PNG = "image/png";
			    
				String imagePath = v.getUPLOAD_PATH();
				OutputStream output = response.getOutputStream();// 得到输出流
				if (imagePath.toLowerCase().endsWith(".jpg"))// 使用编码处理文件流的情况：
				{
					response.setContentType(JPG);// 设定输出的类型
					// 得到图片的真实路径

					// 得到图片的文件流
					InputStream imageIn = new FileInputStream(new File(imagePath));
					// 得到输入的编码器，将文件流进行jpg格式编码
					JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
					// 得到编码后的图片对象
					BufferedImage image = decoder.decodeAsBufferedImage();
					// 得到输出的编码器
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
					encoder.encode(image);// 对图片进行输出编码
					imageIn.close();// 关闭文件流
				} 

				else if (imagePath.toLowerCase().endsWith(".png")
						|| imagePath.toLowerCase().endsWith(".bmp")) {
					
					BufferedImage bi = ImageIO.read(new File(imagePath));
					
					if(imagePath.toLowerCase().endsWith(".png")){
						response.setContentType(PNG);
						ImageIO.write(bi, "png", response.getOutputStream());
					}else{
						response.setContentType(BMP);
						ImageIO.write(bi, "bmp", response.getOutputStream());
					}
					
				}
				output.close();
		}
	}
public TypadKdCustomer selectHistory(@Param(value = "id") String id){
	return commonDao.selectHistory(id);
}
public TypadKdCustomer selectSqCustomerBcl(@Param(value = "id") String id){
	return commonDao.selectSqCustomerBcl(id);
}
public void deleteIma( String id){
	commonDao.deleteIma(id);
}
}
