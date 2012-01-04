package wyq.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertyTool {

    private Properties _prop;

    public PropertyTool() {
	_prop = new Properties();
    }

    public PropertyTool(Properties defaults) {
	_prop = defaults;
    }

    public static PropertyTool LoadFromFile(File f, boolean usingXML)
	    throws FileNotFoundException, IOException {
	if (!f.exists()) {
	    return new PropertyTool();
	}
	Properties def = new Properties();
	if (!usingXML) {
	    def.load(new FileReader(f));
	} else {
	    def.loadFromXML(new FileInputStream(f));
	}
	return new PropertyTool(def);
    }

    public static PropertyTool LoadFromFile(File f)
	    throws FileNotFoundException, IOException {
	return LoadFromFile(f, false);
    }

    public static PropertyTool LoadFromFile(String file)
	    throws FileNotFoundException, IOException {
	return LoadFromFile(new File(file), false);
    }

    /**
     * 
     * @see java.util.Hashtable#clear()
     */
    public void clear() {
	_prop.clear();
    }

    /**
     * @return
     * @see java.util.Hashtable#clone()
     */
    public Object clone() {
	return _prop.clone();
    }

    /**
     * @param value
     * @return
     * @see java.util.Hashtable#contains(java.lang.Object)
     */
    public boolean contains(Object value) {
	return _prop.contains(value);
    }

    /**
     * @param key
     * @return
     * @see java.util.Hashtable#containsKey(java.lang.Object)
     */
    public boolean containsKey(Object key) {
	return _prop.containsKey(key);
    }

    /**
     * @param value
     * @return
     * @see java.util.Hashtable#containsValue(java.lang.Object)
     */
    public boolean containsValue(Object value) {
	return _prop.containsValue(value);
    }

    /**
     * @return
     * @see java.util.Hashtable#elements()
     */
    public Enumeration<Object> elements() {
	return _prop.elements();
    }

    /**
     * @return
     * @see java.util.Hashtable#entrySet()
     */
    public Set<Entry<Object, Object>> entrySet() {
	return _prop.entrySet();
    }

    /**
     * @param o
     * @return
     * @see java.util.Hashtable#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
	return _prop.equals(o);
    }

    /**
     * @param key
     * @return
     * @see java.util.Hashtable#get(java.lang.Object)
     */
    public Object get(Object key) {
	return _prop.get(key);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
     */
    public String getProperty(String key, String defaultValue) {
	return _prop.getProperty(key, defaultValue);
    }

    /**
     * @param key
     * @return
     * @see java.util.Properties#getProperty(java.lang.String)
     */
    public String getProperty(String key) {
	return _prop.getProperty(key);
    }

    /**
     * @return
     * @see java.util.Hashtable#hashCode()
     */
    public int hashCode() {
	return _prop.hashCode();
    }

    /**
     * @return
     * @see java.util.Hashtable#isEmpty()
     */
    public boolean isEmpty() {
	return _prop.isEmpty();
    }

    /**
     * @return
     * @see java.util.Hashtable#keys()
     */
    public Enumeration<Object> keys() {
	return _prop.keys();
    }

    /**
     * @return
     * @see java.util.Hashtable#keySet()
     */
    public Set<Object> keySet() {
	return _prop.keySet();
    }

    /**
     * @param out
     * @see java.util.Properties#list(java.io.PrintStream)
     */
    public void list(PrintStream out) {
	_prop.list(out);
    }

    /**
     * @param out
     * @see java.util.Properties#list(java.io.PrintWriter)
     */
    public void list(PrintWriter out) {
	_prop.list(out);
    }

    /**
     * @param inStream
     * @throws IOException
     * @see java.util.Properties#load(java.io.InputStream)
     */
    public void load(InputStream inStream) throws IOException {
	_prop.load(inStream);
    }

    /**
     * @param reader
     * @throws IOException
     * @see java.util.Properties#load(java.io.Reader)
     */
    public void load(Reader reader) throws IOException {
	_prop.load(reader);
    }

    /**
     * @param in
     * @throws IOException
     * @throws InvalidPropertiesFormatException
     * @see java.util.Properties#loadFromXML(java.io.InputStream)
     */
    public void loadFromXML(InputStream in) throws IOException,
	    InvalidPropertiesFormatException {
	_prop.loadFromXML(in);
    }

    /**
     * @return
     * @see java.util.Properties#propertyNames()
     */
    public Enumeration<?> propertyNames() {
	return _prop.propertyNames();
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
     */
    public Object put(Object key, Object value) {
	return _prop.put(key, value);
    }

    /**
     * @param t
     * @see java.util.Hashtable#putAll(java.util.Map)
     */
    public void putAll(Map<? extends Object, ? extends Object> t) {
	_prop.putAll(t);
    }

    /**
     * @param key
     * @return
     * @see java.util.Hashtable#remove(java.lang.Object)
     */
    public Object remove(Object key) {
	return _prop.remove(key);
    }

    /**
     * @param out
     * @param comments
     * @deprecated
     * @see java.util.Properties#save(java.io.OutputStream, java.lang.String)
     */
    public void save(OutputStream out, String comments) {
	_prop.save(out, comments);
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
     */
    public Object setProperty(String key, String value) {
	return _prop.setProperty(key, value);
    }

    /**
     * @return
     * @see java.util.Hashtable#size()
     */
    public int size() {
	return _prop.size();
    }

    /**
     * @param out
     * @param comments
     * @throws IOException
     * @see java.util.Properties#store(java.io.OutputStream, java.lang.String)
     */
    public void store(OutputStream out, String comments) throws IOException {
	_prop.store(out, comments);
    }

    /**
     * @param writer
     * @param comments
     * @throws IOException
     * @see java.util.Properties#store(java.io.Writer, java.lang.String)
     */
    public void store(Writer writer, String comments) throws IOException {
	_prop.store(writer, comments);
    }

    /**
     * @param os
     * @param comment
     * @param encoding
     * @throws IOException
     * @see java.util.Properties#storeToXML(java.io.OutputStream,
     *      java.lang.String, java.lang.String)
     */
    public void storeToXML(OutputStream os, String comment, String encoding)
	    throws IOException {
	_prop.storeToXML(os, comment, encoding);
    }

    /**
     * @param os
     * @param comment
     * @throws IOException
     * @see java.util.Properties#storeToXML(java.io.OutputStream,
     *      java.lang.String)
     */
    public void storeToXML(OutputStream os, String comment) throws IOException {
	_prop.storeToXML(os, comment);
    }

    /**
     * @return
     * @see java.util.Properties#stringPropertyNames()
     */
    public Set<String> stringPropertyNames() {
	return _prop.stringPropertyNames();
    }

    /**
     * @return
     * @see java.util.Hashtable#toString()
     */
    public String toString() {
	return _prop.toString();
    }

    /**
     * @return
     * @see java.util.Hashtable#values()
     */
    public Collection<Object> values() {
	return _prop.values();
    }

}
