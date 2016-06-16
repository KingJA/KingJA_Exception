package kingja.com.kingja_exception;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/16 11:25
 * 修改备注：
 */
public class SdCardManager {

    public static File getFileName(String dirPath,String fileName,Context context) {
        File mLogDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mLogDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()  +File.separator+ dirPath);
        } else {
            mLogDir = new File(context.getFilesDir().getAbsolutePath() +File.separator+  dirPath);
        }
        if (!mLogDir.exists()) {
            mLogDir.mkdirs();
        }
        return new File(mLogDir.getAbsolutePath()+File.separator+fileName);
    }

    public static String txt2Html(String dirPath,String fileName,Context contetxt) {
        StringBuilder sb = new StringBuilder("<html><body>");
        try {
            FileReader fr = new FileReader(SdCardManager.getFileName(dirPath,fileName,contetxt));
            BufferedReader br = new BufferedReader(fr);
            String line=br.readLine();
            while (line != null) {
                line=line.replace(" ","&nbsp;").replace("\n","<br>").replace("\t","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").replace("-","_");
                sb.append(line+"<br>");
                line=br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.append("</body></html>");
        return sb.toString();
    }
}
