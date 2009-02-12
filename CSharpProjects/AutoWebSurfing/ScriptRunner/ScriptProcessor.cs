using System;
using System.Collections.Generic;
using System.Text;
using FileOperator;
using InstructionRealization;
using ScriptRunnerParameters;

namespace ScriptRunner
{
    /// <summary>
    /// 该类处理源文件，并转化为一段指令
    /// </summary>
    class ScriptProcessor
    {
        private string insListPath;
        private Instruction[] ins;

        /// <summary>
        /// 处理指令列表文件，该文件为指令排序
        /// </summary>
        /// <param name="InsListPath">指令列表的路径</param>
        public ScriptProcessor(string InsListPath)
        {
            this.insListPath = InsListPath;
            LoadInstructionList();
        }

        private class pairTuple
        {
            public int id;
            public string name;
            public Instruction ins = null;
        }

        private class fourTuple
        {
            public int id;
            public string ins;
            public string value1;
            public string value2;
            public string target;
        }

        //private readonly Instruction[] RealizedInstructions = 
        //{ new setValue(),new setObjValue() };

        /// <summary>
        /// 加载一个脚本文件，并返回一个经过排序且初始化了的可运行指令元组List<InstructionTuple>
        /// </summary>
        /// <param name="path">指令路径</param>
        /// <param name="regs">运行时必须参数</param>
        /// <returns>并返回一个经过排序且初始化了的可运行指令元组List<InstructionTuple></returns>
        private static List<InstructionTuple> processScript(string path, Instruction[] realized, RunnerParameters regs)
        {
            string[] orgLines = null;
            List<InstructionTuple> itlist = new List<InstructionTuple>();
            try
            {
                // 加载文件
                orgLines = TextFile.ReadCodeLines(path);
            }
            catch (Exception ex)
            {
                // 文件加载失败
                throw new ScriptFileLoadException(ex);
            }

            LinkedList<fourTuple> ftlines = new LinkedList<fourTuple>();
            LinkedListNode<fourTuple> lastInsertedNode = ftlines.First;

            // 最小行号
            int minimumLineNum = 0;
            // 最大行号
            int maximumLineNum = 0;

            // 开始翻译
            try
            {

                minimumLineNum = int.Parse(orgLines[0].Split(':')[0]);
                maximumLineNum = int.Parse(orgLines[orgLines.Length-1].Split(':')[0]);

                string[] splitedline;

                // 第一遍翻译，排序并查找最大最小行号
                foreach (string line in orgLines)
                {
                    fourTuple f = new fourTuple();

                    splitedline = line.Split(':');
                    if (splitedline.Length > 3)
                    {
                        splitedline[2] += ":";
                        for (int i = 3; i < splitedline.Length; i++)
                            splitedline[2] += splitedline[i];
                    }

                    f.id = int.Parse(splitedline[0]);

                    f.ins = splitedline[2].Split('|')[0];
                    f.value1 = splitedline[2].Split('|')[1];
                    f.value2 = splitedline[2].Split('|')[2];
                    f.target = splitedline[2].Split('|')[3];
                    if (f.id < minimumLineNum)
                        minimumLineNum = f.id;
                    if ( f.id > maximumLineNum)
                        maximumLineNum = f.id;

                    if (lastInsertedNode != null)
                    {
                        if (f.id < lastInsertedNode.Value.id)
                            lastInsertedNode = ftlines.AddBefore(lastInsertedNode, f);
                        else
                            lastInsertedNode = ftlines.AddAfter(lastInsertedNode, f);
                    }
                    else
                    {
                        lastInsertedNode = ftlines.AddFirst(f);
                    }
                }

                //Instruction[] realized = RealizedInstructions.RealizedIns;
                itlist = new List<InstructionTuple>();

                // 第二遍翻译，填入适当参数，构建指令元组
                foreach (fourTuple f in ftlines)
                {
                    Instruction ins = find(f.ins, realized);
                    InstructionParameters newip = new InstructionParameters(regs, f.value1, f.value2, f.target);
                    InstructionTuple newit = new InstructionTuple(ins, newip,f.id);
                    itlist.Add(newit);
                }

                // 第三遍，计算并回填跳转相对行号
                foreach (InstructionTuple it in itlist)
                {
                    if (it.theInstruction.IsJump)
                    {
                        // 检查行号返回
                        if (int.Parse(it.theParameters.Target) < minimumLineNum || 
                            int.Parse(it.theParameters.Target) > maximumLineNum)
                            throw new ScriptGrammarErrorException("跳转目标行号超出范围");

                        int targetId = int.Parse(it.theParameters.Target);
                        InstructionTuple target = findInsTupleById(targetId, itlist);
                        InstructionParameters old = it.theParameters;

                        it.newParameter(new InstructionParameters(
                            old.RunnerParameters,old.Value1,old.Value2,
                            itlist.IndexOf(target).ToString())
                            );

                    }
                }
            }
            catch (Exception ex)
            {
                // 翻译失败，有语法错误。
                throw new ScriptGrammarErrorException(ex);
            }
            return itlist;
        }

        /// <summary>
        /// 加载一个脚本文件，并返回一个经过排序且初始化了的可运行指令元组List<InstructionTuple>
        /// </summary>
        /// <param name="path">指令路径</param>
        /// <param name="regs">运行时必须参数</param>
        /// <returns>并返回一个经过排序且初始化了的可运行指令元组List<InstructionTuple></returns>
        public List<InstructionTuple> ProcessScript(string path, RunnerParameters regs)
        {
            return processScript(path, this.ins, regs);
        }

        /// <summary>
        /// 检查某一个文件是否是符合语法规范的脚本文件。
        /// </summary>
        /// <param name="scriptFile">脚本文件</param>
        /// <returns>如果是返回true，否则返回false</returns>
        public bool CheckFileIsScriptFile(string scriptFile)
        {
            try
            {
                processScript(scriptFile, this.ins, null);
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// 加载指令列表文件，并返回一个已经排序了的指令列表数组
        /// </summary>
        /// <param name="path">文件路径</param>
        /// <returns>返回一个已经排序了的指令列表数组</returns>
        private static Instruction[] loadInstructionList(string path)
        {
            string[] lines = null;
            Instruction[] realized = RealizedInstructions.RealizedIns;
            try
            {
                // 加载文件
                lines = TextFile.ReadCodeLines(path);
            }
            catch(Exception ex)
            {
                // 文件加载失败
                throw new InstructionListFileLoadException(ex);
            }

            Instruction[] newi = new Instruction[realized.Length];

            try
            {
                // 处理指令名与ID号
                pairTuple[] pairs = new pairTuple[lines.Length];
                string[] line;
                for (int i = 0; i < lines.Length; i++)
                {
                    line = lines[i].Split('=');
                    pairTuple pair = new pairTuple();
                    pair.id = int.Parse(line[0]);
                    pair.name = line[1];
                    pairs[i] = pair;
                }

                // 检查指令是否存在
                for (int i = 0; i < pairs.Length; i++)
                {
                    pairs[i].ins = find(pairs[i].name, realized);
                    newi[pairs[i].id] = pairs[i].ins;
                }

            }
            catch (Exception ex)
            {
                
                throw new InstructionListFileLoadException(ex);
            } return newi;
        }

        /// <summary>
        /// 在一组指令数组中查找字面名字为literalName的指令
        /// </summary>
        /// <param name="literalName">要查找的指令的字面名字</param>
        /// <param name="realized">起该数组中查找</param>
        /// <returns></returns>
        private static Instruction find(string literalName, Instruction[] realized)
        {
           for (int i = 0; i < realized.Length; i++)
           {
                if (realized[i].LiteralName.ToUpper() == literalName.ToUpper())
                {
                    // 找到返回
                    return realized[i];
                 }
           }
            // 没有找到抛出错误
           throw new InstructionNotRealizedException();
        }

        /// <summary>
        /// 以ID号在一组指令元组数组中查找一个指令元组
        /// </summary>
        /// <param name="id">id号</param>
        /// <param name="list">在该数组中找</param>
        /// <returns></returns>
        private static InstructionTuple findInsTupleById(int id, List<InstructionTuple> list)
        {
            foreach (InstructionTuple pu in list)
            {
                if (pu.Id == id)
                    // 找到返回
                    return pu;
            }
            // 没有找到跳出
            throw new ScriptGrammarErrorException("跳转目标行不存在。");
        }

        /// <summary>
        /// 加载指令列表，如指令列表更新，可使用该方法重新加载
        /// </summary>
        public void LoadInstructionList()
        {
            ins = loadInstructionList(this.insListPath);
        }

        /// <summary>
        /// 检查某一个文件是否可以加载为指令列表文件
        /// </summary>
        /// <param name="path"></param>
        /// <returns>可以的话返回true，不可以返回false</returns>
        public static bool CheckFileIsInstructionList(string path)
        {
            try
            {
                loadInstructionList(path);
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// 获取脚本的具体信息
        /// </summary>
        /// <param name="path">脚本文件</param>
        /// <param name="checkScriptFile">是否检查脚本符合语法要求</param>
        /// <returns>脚本信息</returns>
        public ScriptInformation GetScriptInformation(string path,bool checkScriptFile)
        {
            // 首先检查脚本文件是否符合脚本规范，如不符合则抛出异常，提示脚本语法错误
            if(checkScriptFile && !CheckFileIsScriptFile(path))
                throw new ScriptGrammarErrorException();

            // 读取信息行，以#@开头的行，返回后的行不含开头的#@
            string[] orgLines = TextFile.ReadInformationLines(path);

            StringBuilder theInfo = new StringBuilder();
            ScriptInformationCategory theCat = ScriptInformationCategory.Empty;
            ScriptInformation info = new ScriptInformation();

            // 搜索每一行
            for (int i = 0; i < orgLines.Length; i++)
            {
                // 如果以某一新标签开始
                if(startWithAnotherTag(orgLines[i],theCat))
                {
                    // 为类信息填充之前标签的信息
                    info.fillInformation(theCat, theInfo.ToString());
                    // 记录当前新的标签
                    theCat = getTheTag(orgLines[i]);
                    // 清空之前的信息
                    theInfo = new StringBuilder();
                    // 记录信息
                    theInfo.AppendLine(orgLines[i].Trim().Remove(0,theCat.ToString().Length + 2));
                }
                // 如果不以标签开始
                else
                {
                    // 继续为上一个搜索到的标签收集信息
                    if (theCat != ScriptInformationCategory.Empty)
                    {
                        // 超前搜索
                        for (int j = i; j < orgLines.Length; j++)
                        {
                            // 如果又以标签开始
                            if (startWithAnotherTag(orgLines[j],theCat))
                            {
                                // 超前搜索结束
                                i = j - 1;
                                // 为类信息填充信息
                                //info.fillInformation(theCat, theInfo.ToString());
                                // 收集信息清空
                                //theInfo = new StringBuilder();
                                break;
                            }
                            // 不是以标签开始
                            else
                            {
                                // 继续为上一个搜索到的标签收集信息
                                if(orgLines[j].StartsWith("["+theCat.ToString()+"]",StringComparison.OrdinalIgnoreCase))
                                    theInfo.AppendLine(orgLines[j].Trim().Remove(0,theCat.ToString().Length+2));
                                else
                                    theInfo.AppendLine(orgLines[j]);
                            }
                        }
                    }
                    // 如果上一个标签没有搜索到，则丢弃这些信息
                }
            }
            // 为类信息填充之前标签的信息
            info.fillInformation(theCat, theInfo.ToString());
            // 另外填充两个文件信息
            info.SetFileName(Operator.GetName(path));
            info.SetFileFullName(Operator.GetFullName(path));

            return info;
        }

        private static Dictionary<string,ScriptInformationCategory> initScriptInfoCatDic()
        {
            Dictionary<string,ScriptInformationCategory> sicl = 
                new Dictionary<string,ScriptInformationCategory>();
            foreach (ScriptInformationCategory c in Enum.GetValues(typeof(ScriptInformationCategory)))
            {
                sicl.Add("["+c.ToString().ToLower()+"]",c);
            }

            return sicl;
        }

        private static Dictionary<string, ScriptInformationCategory> scriptInfoCatTagDict = initScriptInfoCatDic();

        private ScriptInformationCategory getTheTag(string line)
        {
            ScriptInformationCategory cat = ScriptInformationCategory.Empty;
            foreach (string ic in scriptInfoCatTagDict.Keys)
            {
                if (line.Trim().StartsWith(ic,StringComparison.OrdinalIgnoreCase))
                {
                    scriptInfoCatTagDict.TryGetValue(ic, out cat);
                    break;
                }
            }
            return cat;
        }

        private bool startWithAnotherTag(string line, ScriptInformationCategory cat)
        {
            if (line.Trim().StartsWith("[" + cat.ToString().ToLower() + "]", StringComparison.OrdinalIgnoreCase))
                return false;
            else if (line.Trim()[0] != '[')
                return false;
            else
                return true;
        }
    }

    #region 以下为异常

    public class InstructionListFileLoadException : Exception
    {
        private static string errmsg = "指令列表读取失败，可能是指令列表文件丢失或指令列表有语法错误。";
        public InstructionListFileLoadException() : base(errmsg) { }
        public InstructionListFileLoadException(Exception innerException) : base(errmsg, innerException) { }
    }

    public class InstructionNotRealizedException : Exception
    {
        public InstructionNotRealizedException() : base("该指令未实现或不允许使用。") { }
    }

    public class ScriptGrammarErrorException : Exception
    {
        private static string errmsg = "脚本文件语法解析错误。";
        public ScriptGrammarErrorException() : base(errmsg) { }
        public ScriptGrammarErrorException(string msg) : base(msg) { }
        public ScriptGrammarErrorException(Exception innerException) : base(errmsg, innerException) { }
    }

    public class ScriptFileLoadException : Exception
    {
        private static string errmsg = "脚本文件读取失败。";
        public ScriptFileLoadException() : base(errmsg) { }
        public ScriptFileLoadException(Exception innerException) : base(errmsg, innerException) { }
    }

    #endregion

    #region 获取脚本信息

    /// <summary>
    /// 脚本信息所含所有可用的标签
    /// </summary>
    public enum ScriptInformationCategory
    {
        Author, Name, Description, Version, Date, Empty, Icon, Enabled
    }

    /// <summary>
    /// 此类包含脚本的头信息
    /// </summary>
    public class ScriptInformation
    {
        private string author;

        public string Author
        {
            get { return author; }
        }

        private string name;

        public string Name
        {
            get { return name; }
        }

        private string description;

        public string Description
        {
            get { return description; }
        }

        private string version;

        public string Version
        {
            get { return version; }
        }

        private string date;

        public string Date
        {
            get { return date; }
        }

        private string empty;

        /* no use
         * 
        public string Empty
        {
            get { return empty; }
        }
         */

        private string icon;

        public string Icon
        {
            get { return icon; }
            //set { icon = value; }
        }

        private string enabled;
        private bool _enabled = false;

        public bool Enabled
        {
            get 
            {
                bool.TryParse(enabled, out _enabled);
                return _enabled;
            }
            //set { enabled = value; }
        }

        private string fileName;

        public string FileName
        {
            get { return fileName; }
        }

        private string fileFullName;

        public string FileFullName
        {
            get { return fileFullName; }
        }

        internal ScriptInformation(string author, string name, 
            string description, string version, string data,string icon,bool enabled)
        {
            this.author = author;
            this.name = name;
            this.description = description;
            this.version = version;
            this.date = data;
            this.empty = "false";
            this.icon = icon;
            this._enabled = enabled;
        }

        internal ScriptInformation()
        {
            this.empty = "true";
        }

        internal void SetFileName(string name)
        {
            this.fileName = name;
        }

        internal void SetFileFullName(string name)
        {
            this.fileFullName = name;
        }

        internal void fillInformation(ScriptInformationCategory e, object inf)
        {
            try
            {
                this.GetType().GetField(e.ToString(),
                    System.Reflection.BindingFlags.NonPublic |
                    System.Reflection.BindingFlags.IgnoreCase |
                    System.Reflection.BindingFlags.Instance).SetValue(
                    this, inf);
            }
            catch (Exception)
            {
                //throw nothing;
            }
        }
    }

    #endregion
}
