using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace FileOperator
{
    public class Operator
    {
        public static string GetFullName(string path)
        {
            return new FileInfo(path).FullName;
        }

        public static string GetName(string path)
        {
            return new FileInfo(path).Name;
        }
    }
}
