using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace FileOperator
{
    public class TextFile
    {
        /// <summary>
        /// 打开一个文本文件，读取所有文本，然后关闭该文件。
        /// </summary>
        /// <param name="path">文件的路径</param>
        /// <returns>返回读取的String</returns>
        public static String Read(string path)
        {
            if (!File.Exists(path))
                throw new FileNotFoundException();
            return File.ReadAllText(path);

        }

        /// <summary>
        /// 打开一个文本文件，读取文件的所有行，然后关闭该文件。
        /// </summary>
        /// <param name="path">文件的路径</param>
        /// <returns>返回读取的string数组</returns>
        public static string[] ReadAllLines(string path)
        {
            if (!File.Exists(path))
                throw new FileNotFoundException();
            return File.ReadAllLines(path);
        }

        /// <summary>
        /// 打开一个文本文件，读取文件的代码行，忽视以#开头的注释行和空白行，然后关闭该文件。
        /// </summary>
        /// <param name="path">文件的路径</param>
        /// <returns>返回读取的string数组</returns>
        public static string[] ReadCodeLines(string path)
        {
            string ignoreStartChar = "#";
            return readLineStartWithOut(path, ignoreStartChar);
        }

        /// <summary>
        /// 打开一个文本文件，读取信息行，返回以#@开头的行，忽视其他行和空白行，然后关闭该文件。
        /// </summary>
        /// <param name="path"></param>
        /// <returns></returns>
        public static string[] ReadInformationLines(string path)
        {
            string ignoreStartChar = "#@";
            return readLineStartWith(path, ignoreStartChar);
        }

        /// <summary>
        /// 打开一个文本文件，读取文件每一行，忽视以ignoreStartChar开头的行和空白行，然后关闭该文件。
        /// </summary>
        /// <param name="path">文件的路径</param>
        /// <returns>返回读取的string数组</returns>
        private static string[] readLineStartWithOut(string path, string ignoreStartChar)
        {
            if (!File.Exists(path))
                throw new FileNotFoundException();

            string[] allLines = File.ReadAllLines(path);
            List<string> l = new List<string>();

            foreach (string line in allLines)
            {
                if (!line.Trim().StartsWith(ignoreStartChar) && line.Length != 0)
                {
                    l.Add(line);
                }
            }
            return l.ToArray();
        }

        /// <summary>
        /// 打开一个文本文件，读取文件每一行，返回以ignoreStartChar开头的行（开头不包含ignoreStartChar）和非空白行，然后关闭该文件。
        /// </summary>
        /// <param name="path">文件的路径</param>
        /// <returns>返回读取的string数组</returns>
        private static string[] readLineStartWith(string path, string ignoreStartChar)
        {
            if (!File.Exists(path))
                throw new FileNotFoundException();

            string[] allLines = File.ReadAllLines(path);
            List<string> l = new List<string>();

            string tmp;
            foreach (string line in allLines)
            {
                if (line.Trim().StartsWith(ignoreStartChar) &&
                    (tmp=line.Trim().Remove(0, ignoreStartChar.Length)).Length>0)
                {
                    l.Add(tmp);
                }
            }
            return l.ToArray();
        }

        /// <summary>
        /// 打开一个文本文件，写入所有文本，然后关闭该文件。
        /// </summary>
        /// <param name="path">要写入的路径</param>
        /// <param name="contents">要写入的内容</param>
        /// <param name="overwrite">是否覆盖</param>
        /// <exception cref="FileOperator.FileAlreadyExistsException">
        /// 丢出FileOperator.FileAlreadyExistsException，表示文件已经存在
        /// </exception>
        public static void Write(string path,string contents, bool overwrite)
        {
            if (!overwrite)
            {
                if (File.Exists(path))
                {
                    throw new FileAlreadyExistsException("文件已经存在");
                }
            }
            File.WriteAllText(path, contents);
     
        }

        /// <summary>
        /// 打开一个文本文件，写入所有行，然后关闭该文件。
        /// </summary>
        /// <param name="path">要写入的路径</param>
        /// <param name="contents">要写入的内容</param>
        /// <param name="overwrite">是否覆盖</param>
        /// <exception cref="FileOperator.FileAlreadyExistsException">
        /// 丢出FileOperator.FileAlreadyExistsException，表示文件已经存在
        /// </exception>
        public static void WriteAllLines(string path, string[] contents, bool overwrite)
        {
            if (!overwrite)
            {
                if (File.Exists(path))
                {
                    throw new FileAlreadyExistsException("文件已经存在");
                }
            }
            File.WriteAllLines(path, contents);

        }
    }


    /// <summary>
    /// 如果此错误被抛出，表明目标文件已经存在
    /// </summary>
    public class FileAlreadyExistsException : Exception
    {
        public FileAlreadyExistsException(string message):base(message)
        {
        }
    }

}
