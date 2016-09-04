package dn.sindy.waterfalls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class Utils {

	/**
	 * 判断手机是否有SD卡。
	 * 
	 * @return 有SD卡返回true，没有返回false。
	 */
	public static boolean hasSDCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}
	
	/**
	 * 将图片下载到SD卡缓存起来。
	 * 
	 * @param imageUrl
	 *            图片的URL地址。
	 */
	public static void downloadImage(String imageUrl,int columnWidth) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d("TAG", "monted sdcard");
		} else {
			Log.d("TAG", "has no sdcard");
		}
		HttpURLConnection con = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		File imageFile = null;
		try {
			URL url = new URL(imageUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(15 * 1000);
			con.setDoInput(true);
			con.setDoOutput(true);
			bis = new BufferedInputStream(con.getInputStream());
			imageFile = new File(getImagePath(imageUrl));
			fos = new FileOutputStream(imageFile);
			bos = new BufferedOutputStream(fos);
			byte[] b = new byte[1024];
			int length;
			while ((length = bis.read(b)) != -1) {
				bos.write(b, 0, length);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (con != null) {
					con.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (imageFile != null) {
			Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(
					imageFile.getPath(), columnWidth);
			if (bitmap != null) {
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
			}
		}
	}

	/**
	 * 获取图片的本地存储路径。
	 * 
	 * @param imageUrl
	 *            图片的URL地址。
	 * @return 图片的本地存储路径。
	 */
	public static String getImagePath(String imageUrl) {
		int lastSlashIndex = imageUrl.lastIndexOf("/");
		String imageName = imageUrl.substring(lastSlashIndex + 1);
		String imageDir = Environment.getExternalStorageDirectory()
				.getPath() + "/WaterFalls/";
		File file = new File(imageDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String imagePath = imageDir + imageName;
		return imagePath;
	}
}
