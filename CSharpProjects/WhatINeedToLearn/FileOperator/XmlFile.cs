using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;

namespace FileOperator
{
    /// <summary>
    /// 此类包含xml文件的打开、读取、写入等操作。
    /// 不可直接实例化，使用Open方法得到此类实例。
    /// </summary>
     public class XmlFile
    {
        private XmlFile() { }
        private static XmlFile xmlFile = null;
        private static XmlReader file = null;

         /// <summary>
         /// 打开一个XML文件，注意打开后必须使用Close关闭。
         /// </summary>
         /// <param name="path">打开文件的路径</param>
         /// <returns>返回打开后文件的实例</returns>
        public static XmlFile Open(string path)
        {
            if (file != null)
                file.Close();

            xmlFile = new XmlFile();
            XmlReaderSettings settings = new XmlReaderSettings();

            // TODO: set xml reader settings
            // xml reader settings here
            // for example:
            //settings.CloseInput = true;

            file = XmlReader.Create(path,settings);

            return xmlFile;
        }
        
         /// <summary>
         /// 如果已经打开过文件了，可直接使用不带参数的Open方法获得打开的XML文件。
         /// 注意打开后必须使用Close关闭。
         /// </summary>
         /// <returns>返回已经打开的XML文件</returns>
        public static XmlFile Open()
        {
            if (xmlFile != null && file != null)
                return xmlFile;
            else
                throw new System.IO.FileNotFoundException();
        }

         /// <summary>
         /// 读取内容
         /// 该方法读取所有的XML内容，不包含xml头
         /// </summary>
         /// <returns>读取的string</returns>
        public string Read()
        {
            file.MoveToContent();
            return file.ReadOuterXml();
        }

         /// <summary>
         /// 读取完成后的XML必须关闭
         /// </summary>
        public void Close()
        {
            file.Close();
            xmlFile = null;
            file = null;
        }
    }
}
