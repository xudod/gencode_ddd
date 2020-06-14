package com.xudod.gencode.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xudod.gencode.data.GenCodeInfo;
import freemarker.template.Configuration;

public class FileUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static void createFun(GenCodeInfo genCodeInfo, String templatePath, String filePathAndName, String templateName) {
		try {
			Configuration cfg = FreemarkerUtils.initFreemarkerConfiguration(templatePath);
			Map<String, Object> data = genCodeInfo.getTempData();
			data.put("tableComment", genCodeInfo.getTableComment());
			File file = new File(filePathAndName);
			if(!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				cfg.getTemplate(templateName + ".ftl").process(data, new FileWriter(file));
				logger.info(genCodeInfo.getModelName() + templateName + ".java 生成成功!");
			}else {
            	String needKeepStrH = getCreateBeforHead(filePathAndName);
            	String needKeepStrE = getCreateBeforEnd(filePathAndName);
            	cfg.getTemplate(templateName + ".ftl").process(data, new FileWriter(file));
            	String needKeepStringNew = getCreateAfter(filePathAndName);
            	File targetFile = new File(filePathAndName);
            	String fileContent = needKeepStrH + needKeepStringNew + needKeepStrE;
            	writeFile(targetFile, fileContent, "UTF-8");
				logger.info(genCodeInfo.getModelName() + templateName + ".java 存在，进行新老代码段拼接重新生成!");
			}
	    } catch (Exception e) {
	        throw new RuntimeException(templateName + ".java 生成失败!", e);
	    }
	}
	
    /**
     * 将文件读入的list中返回，每个element包含一行内容
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> readFile2List(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
        return readFile2List(fr);
    }
    /**
     * 将文件读入的list中返回，每个element包含一行内容
     * 
     * @param fr
     * @return
     * @throws IOException
     */
    public static List<String> readFile2List(InputStreamReader fr) throws IOException {
        List<String> fileList = new ArrayList<String>();
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null) {
            if (!StringUtils.isEmpty(line)) {
                fileList.add(line);
            }
        }
        br.close();
        fr.close();
        return fileList;
    }
    
	/**
	 * 创建目录
	 * <p>Title: createDir</p>  
	 * <p>Description: </p>  
	 * @param path
	 */
	public static void createDir(String path) {
		File f11 = new File(path);
		if(!f11.exists()) {
			f11.mkdirs();
		}
	}
	
	/**
	 * 在指定的路径下创建空指定名称的空文件
	 * <p>Title: creatFile</p> 
	 * <p>Description: </p>  
	 * @param fileName
	 * @param path
	 */
	public static void creatFile(String fileName, String path) {
		File file = new File(path,fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据文件路径、内容、字符集生成文件
	 * <p>Title: writeFile</p>  
	 * <p>Description: </p>  
	 * @param filePath 
	 * @param content
	 * @param fileEncoding
	 * @throws IOException
	 */
	public static void writeFile(File filePath, String content, String fileEncoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }
	
	/**
	 * 获取代码生成后的代码
	 * <p>Title: getCreateAfter</p>  
	 * <p>Description: </p>  
	 * @param filePathAndName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getCreateAfter(String filePathAndName) throws FileNotFoundException, IOException {
		FileReader frNew = new FileReader(filePathAndName);
		BufferedReader brNew = new BufferedReader(frNew);
		String lineNew = null;
		String needKeepStringNew = "\n";
		boolean needKeepStrMarkNew = false;
		while ((lineNew = brNew.readLine()) != null) {
		    if(needKeepStrMarkNew) {
		    	needKeepStringNew = needKeepStringNew + lineNew + "\n";
		    }
		    if ("/*代码分界head TODO*/".equals(lineNew.trim())) {
		    	needKeepStrMarkNew = true;
		    }
		    if ("/*代码分界end TODO*/".equals(lineNew.trim())) {
		    	needKeepStrMarkNew = false;
		    }
		}
		return needKeepStringNew;
	}

	/**
	 * 获取代码生成前头的代码
	 * <p>Title: getCreateBefor</p>  
	 * <p>Description: </p>  
	 * @param filePathAndName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getCreateBeforHead(String filePathAndName) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(filePathAndName);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		String needKeepString = "";
		boolean needKeepStrMark = true;
		while ((line = br.readLine()) != null) {
		    if(needKeepStrMark) {
		    	needKeepString = needKeepString + line + "\n";
		    }
		    if ("/*代码分界head TODO*/".equals(line.trim())) {
		    	needKeepStrMark = false;
		    }
		}
		return needKeepString;
	}
	
	/**
	 * 获取代码生成前尾的代码
	 * <p>Title: getCreateBefor</p>  
	 * <p>Description: </p>  
	 * @param filePathAndName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getCreateBeforEnd(String filePathAndName) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(filePathAndName);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		String needKeepString = "\n";
		boolean needKeepStrMark = false;
		while ((line = br.readLine()) != null) {
		    if(needKeepStrMark) {
		    	needKeepString = needKeepString + line + "\n";
		    }
		    if ("/*代码分界end TODO*/".equals(line.trim())) {
		    	needKeepStrMark = true;
		    }
		}
		return needKeepString;
	}
	
//	public static void main(String[] args) throws IOException {
//		copyDir("C:\\Users\\xudod\\Desktop\\中控Java二次开发demo", "C:\\Users\\xudod\\Desktop\\中控Java二次开发demotest");
//		这里会把“中控Java二次开发demo”这个文件夹复制到指定目录并重命名为“中控Java二次开发demotest”
//		System.out.println("复制完成");
//	}

	/**
	 * 复制指定文件或文件夹，到指定位置
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyDir(String oldPath, String newPath) throws IOException {
		File file = new File(oldPath);
		String[] filePath = file.list();
		if (!(new File(newPath)).exists()) {
			(new File(newPath)).mkdirs();
		}
		for (int i = 0; i < filePath.length; i++) {
			if ((new File(oldPath + File.separator + filePath[i])).isDirectory()) {
				copyDir(oldPath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
			}
			if (new File(oldPath + File.separator + filePath[i]).isFile()) {
				File source = new File(oldPath + File.separator + filePath[i]);
				File dest = new File(newPath + File.separator + filePath[i]);
				if (!(dest.exists())) {
					Files.copy(source.toPath(), dest.toPath());
				}
			}
		}
	}
	
//	/**
//	* 复制目录
//	* @param fromDir
//	* @param toDir
//	* @throws IOException
//	*/
//	public static void copyDir(String oldPath,String newPath) throws IOException{
//		//创建目录的File对象
//		File dirSouce = new File(oldPath);
//		//判断源目录是不是一个目录
//		if (!dirSouce.isDirectory()) {
//			//如果不是目录那就不复制
//			return;
//		}
//		//创建目标目录的File对象
//		File destDir = new File(newPath);	
//		//如果目的目录不存在
//		if(!destDir.exists()){
//			//创建目的目录
//			destDir.mkdir();
//		}
//		//获取源目录下的File对象列表
//		File[]files = dirSouce.listFiles();
//		for (File file : files) {
//			//拼接新的fromDir(fromFile)和toDir(toFile)的路径
//			String strFrom = oldPath + File.separator + file.getName();
//			System.out.println(strFrom);
//			String strTo = newPath + File.separator + file.getName();
//			System.out.println(strTo);
//			//判断File对象是目录还是文件
//			//判断是否是目录
//			if (file.isDirectory()) {
//				//递归调用复制目录的方法
//				copyDir(strFrom,strTo);
//			}
//			//判断是否是文件
//			if (file.isFile()) {
//				System.out.println("正在复制文件："+file.getName());
//				//递归调用复制文件的方法
//				copyFile(strFrom,strTo);
//			}
//		}
//	}
//	/**
//	* 复制文件
//	* @param fromFile
//	* @param toFile
//	* @throws IOException
//	*/
//	public static void copyFile(String fromFile,String toFile) throws IOException{
//		//字节输入流——读取文件
//		FileInputStream in = new FileInputStream(fromFile);
//		//字节输出流——写入文件
//		FileOutputStream out = new FileOutputStream(toFile);
//		//把读取到的内容写入新文件
//		//把字节数组设置大一些   1*1024*1024=1M
//		byte[] bs = new byte[1*1024*1024];	
//		int count = 0;
//		while((count = in.read(bs))!=-1){
//			out.write(bs,0,count);
//		}
//		//关闭流
//		in.close();
//		out.flush();
//		out.close();
//	}
	
}
