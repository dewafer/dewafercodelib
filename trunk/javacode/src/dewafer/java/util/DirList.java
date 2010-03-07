package dewafer.java.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 
 * 列出目录中符合正则表达式的文件夹。
 * 
 * @author dewafer
 * @version 2010/3/7
 */
public class DirList {
    /**
     * 一个示例<br/>
     * 列出目录中符合正则表达式的文件夹
     * @param regex 正则表达式
     * @return FilenameFilter 实例
     */
    public static FilenameFilter filter(final String regex) {
        // anonymous inner class
    	// 匿名内部类
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            public boolean accept(File dir, String name){
                return pattern.matcher(name).matches();
            }
        };
     }
     /**
      * main函数
     * @param args 要检索的根文件夹
     */
    public static void main(String[] args) {
        File path = new File(".");
        String[] list;
        if(args.length == 0)
            list = path.list();
        else
            list = path.list(filter(args[0]));
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String dirItem : list)
            System.out.println(dirItem);
    }
}