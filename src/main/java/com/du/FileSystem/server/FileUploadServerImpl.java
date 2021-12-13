package com.du.FileSystem.server;

import com.du.FileSystem.control.FileControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadServerImpl implements FileUploadServer{

    private static final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");

    private static final boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");

    // 判断当前运行环境决定文件存放路径。
    private final static String RootPath = isLinux?"/WorkSpace/file" : "D:/test";

    @Override
    public boolean fileUploader(MultipartFile file){
        if(file.isEmpty()){
            return false;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);


        File dest = new File(RootPath + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
