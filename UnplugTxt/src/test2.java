import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class test2 {

	private static final String PATH = "C:\\Users\\dewafer\\Desktop\\电书\\传记\\姚明自传.jar";
	private static final String PATH2 = "C:\\Users\\dewafer\\Desktop\\电书\\传记\\姚明自传x";
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File f = new File(PATH);
		JarFile jar = new JarFile(f);
		File outf = new File(PATH2);
		outf.mkdir();
		
		
		for(Enumeration<JarEntry> e = jar.entries();e.hasMoreElements();){
			JarEntry entry = e.nextElement();
			test1.println(entry.getName());
			File f2 = new File(PATH2+"\\" + entry.getName());
			if(entry.isDirectory()){
				f2.mkdir();
			}else{
				write(jar.getInputStream(entry),new FileOutputStream(f2),(int) entry.getSize());
			}
		}
	}
	
	public static void write(InputStream in,OutputStream out,int size) throws IOException{
		byte[] buff = new byte[size];
		in.read(buff);
		out.write(buff);
		out.flush();
	}

}
