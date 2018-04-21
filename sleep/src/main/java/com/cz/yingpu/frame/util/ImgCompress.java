package com.cz.yingpu.frame.util;

import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

import com.sun.image.codec.jpeg.*;

/**
 * 图片压缩处理
 * 
 * @author 崔素强
 */
public class ImgCompress {

	private Image img;
	private int width;
	private int height;

	public static void main(String[] args) throws Exception {
		System.out.println("开始：" + new Date().toString());
		String lodPath = "D:\\temp\\p6.jpg"; // 原图文件路径
		String newPaht = "D:\\temp\\p6_1.jpg"; // 压缩文件路径
		int size = 0; // 图片文件大小
		int widthV = 5000; // 初始定值需的宽度
		int heightV = 5000; // 初始定值需的高度
		int num = 300; // 常量需要减去的宽高
		ImgCompress imgCom = new ImgCompress();
		size = (int) new File(lodPath).length() / 1024; // 原图大小KB
		System.out.println("原图大小" + size);
		while (size > 300) {
			imgCom.resizeFix(widthV, heightV, lodPath, newPaht);
			size = (int) new File(newPaht).length() / 1024;
			int i = size / 300; // 当前图片大小是需要压缩制定大小的倍数
			widthV = widthV - (num * i);
			heightV = heightV - (num * i);
			System.out.println("size:" + size + "===== i:" + i + "== widthV:"
					+ widthV + "=== heightV:" + heightV);
		}
		System.out.println("结束：" + new Date().toString());
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 */
	public void resizeFix(int w, int h, String lodPath, String newPath)
			throws IOException {
		File file = new File(lodPath);// 读入文件
		img = ImageIO.read(file); // 构造Image对象
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长

		if (width / height > w / h) {
			resizeByWidth(w, newPath);
		} else {
			resizeByHeight(h, newPath);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public void resizeByWidth(int w, String newPath) throws IOException {
		int h = (int) (height * w / width);
		resize(w, h, newPath);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public void resizeByHeight(int h, String newPath) throws IOException {
		int w = (int) (width * h / height);
		resize(w, h, newPath);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public void resize(int w, int h, String newPath) throws IOException {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File destFile = new File(newPath);
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// 可以正常实现bmp、png、gif转jpg
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG编码
		out.close();
	}

	/**
	 * 将Base64位编码的图片进行解码，并保存到指定目录
	 * 
	 * @param base64
	 *            base64编码的图片信息
	 * @return
	 */
	public static void Base64ToImage(String base64, String imgUrl) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			FileOutputStream write = new FileOutputStream(new File(imgUrl));
			byte[] decoderBytes = decoder.decodeBuffer(base64);
			write.write(decoderBytes);
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String ImageToBase64(String img, String type) {
		File f = new File(img);
		try {
			BufferedImage bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, type, baos);
			byte[] bytes = baos.toByteArray();

			return new sun.misc.BASE64Encoder().encodeBuffer(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
