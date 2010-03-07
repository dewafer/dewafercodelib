package dewafer.java.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 
 * �г�Ŀ¼�з���������ʽ���ļ��С�
 * 
 * @author dewafer
 * @version 2010/3/7
 */
public class DirList {
    /**
     * һ��ʾ��<br/>
     * �г�Ŀ¼�з���������ʽ���ļ���
     * @param regex ������ʽ
     * @return FilenameFilter ʵ��
     */
    public static FilenameFilter filter(final String regex) {
        // anonymous inner class
    	// �����ڲ���
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            public boolean accept(File dir, String name){
                return pattern.matcher(name).matches();
            }
        };
     }
     /**
      * main����
     * @param args Ҫ�����ĸ��ļ���
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