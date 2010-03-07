using System;
using System.Collections;
using System.IO;
using System.Text;

namespace littlesabisiigame2
{
	/// <summary>
	/// FileReader の概要の説明です。
	/// </summary>
	public class FileReader
	{
		public FileReader()
		{
			// 
			// TODO: コンストラクタ ロジックをここに追加してください。
			//
		}

		public static string[] GetContext(string file)
		{
			createFileIfNoExists(file);
			ArrayList list = new ArrayList();
			StreamReader reader = new StreamReader(file,Encoding.Unicode);
			while(-1<reader.Peek())
			{
				list.Add(reader.ReadLine());
			}
			reader.Close();

			string[] buff = new string[list.Count];
			for(int i=0;i<list.Count;i++)
			{
				buff[i] = list[i].ToString();
			}
			return buff;
		}

		public static void SaveContext(string file,string[] context)
		{
			string[] buff = clearSpaceLine(context);
			createFileIfNoExists(file);
			StreamWriter writer = new StreamWriter(file,false,Encoding.Unicode);
			for(int i=0;i<buff.Length;i++)
			{
				writer.WriteLine(buff[i]);
			}
			writer.Close();
		}

		private static void createFileIfNoExists(string file)
		{
			if(!File.Exists(file))
			{
				StreamWriter w = new StreamWriter(file,false,Encoding.Unicode);
				w.Close();
			}
		}

		private static string[] clearSpaceLine(string[] context)
		{
			///TODO:
			///get this done
			///return context;

			int nonspa_linesnum=0;
			for(int i=0;i<context.Length;i++)
			{
				if(context[i].Length >0)
					nonspa_linesnum++;
			}
			string[] buff = new string[nonspa_linesnum];
			for(int i=0,j=0;i<context.Length;i++)
			{
				if(context[i].Length >0 && j<nonspa_linesnum)
				{
					buff[j] = context[i];
					j++;
				}
			}
			return buff;
		}
	}
}
