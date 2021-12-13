package com.du.FileSystem.control;

import com.du.FileSystem.server.FileUploadServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileControl {
    private final static Logger logger = LoggerFactory.getLogger(FileControl.class);

    private static final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");

    private static final boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");

    // 判断当前运行环境决定文件存放路径。
    private final static String RootPath = isLinux?"/WorkSpace/file" : "D:/test";

    @Autowired
    FileUploadServer fileUploadServer;

    @GetMapping("/")
    public String fileControl(Model model){
        List<String> files = new ArrayList<String>();
//        String path = "D:/test";		//要遍历的路径
        File file = new File(RootPath);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isFile()) {
//                files.add(fs[i].toString());
                //文件名，不包含路径
//                String fileName = fs[i].getName();
                files.add(fs[i].getName());
            }
            if (fs[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        model.addAttribute("path",RootPath);
        model.addAttribute("file",files);
        return "index";
    }


    /**
     * 实现文件上传
     * */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file){
        boolean result = fileUploadServer.fileUploader(file);
        if(result)
            return "true";
        return "false";
    }

    /**
     * 实现多文件上传
     * */
    @RequestMapping(value="multifileUpload",method=RequestMethod.POST)
    /**public @ResponseBody String multifileUpload(@RequestParam("fileName")List<MultipartFile> files) */
    public @ResponseBody String multifileUpload(HttpServletRequest request){
        boolean result;
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");

        if(files.isEmpty()){
            return "false";
        }

        String path = "D:/test" ;

        for(MultipartFile file:files){
            result = fileUploadServer.fileUploader(file);
            if(!result)
                return "false";
        }
        return "true";
    }


    @RequestMapping("/download")
    public String downLoad(@RequestParam("fileName") String filename,
                           @RequestParam("path") String filepath,
                           HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(filepath+"/"+filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            try {
                response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                // 修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
                response.setHeader("Content-Length", String.valueOf(fis.getChannel().size()));
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer, 0, buffer.length);
                    os.flush();
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
