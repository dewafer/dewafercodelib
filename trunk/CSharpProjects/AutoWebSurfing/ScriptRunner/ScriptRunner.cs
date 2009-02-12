using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using InstructionRealization;
using ScriptRunnerParameters;
using WebBrowserController;

namespace ScriptRunner
{
    /// <summary>
    /// 该类能运行一个脚本文件
    /// </summary>
    public class Runner
    {
        public delegate void ScriptRunCompletedHandler(object sender, object eventArgs);
        public event ScriptRunCompletedHandler ScriptRunCompleted;

        private List<InstructionTuple> orders = null;
        private ScriptProcessor sp = null;
        private readonly RunnerParameters regs;

        /// <summary>
        /// 此属性提供系统传递进来的参数
        /// </summary>
        public string[] Args
        {
            get { return regs.Args; }
            set { regs.Args = value; }
        }

        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="insListPath">指令列表文件路径</param>
        /// <param name="theBrowser">控制对象</param>
        public Runner(string insListPath,WebBrowser theBrowser) 
        {
            sp = new ScriptProcessor(insListPath);
            regs = new RunnerParameters(theBrowser);
        }

        public Runner(string insListPath, Controller theController)
        {
            sp = new ScriptProcessor(insListPath);
            regs = new RunnerParameters(theController);
        }

        /// <summary>
        /// this is only for test use!
        /// </summary>
        /// <param name="insListPath"></param>
        private Runner(string insListPath)
        {
            sp = new ScriptProcessor(insListPath);
            //regs = new RunnerParameters();
        }

        /// <summary>
        /// 运行一个脚本
        /// </summary>
        /// <param name="scriptPath">脚本路径</param>
        public void RunScript(string scriptPath)
        {
            orders = sp.ProcessScript(scriptPath,regs);
            InstructionTupleSegment sg = new InstructionTupleSegment(
                orders, regs);
            sg.InstructionTupleSegmentRunCompleted = new InstructionTupleSegment.InstructionTupleSegmentRunCompletedHandler(ScriptRunCompleted);
            sg.Run();
        }

        /// <summary>
        /// 停止脚本的运行
        /// </summary>
        public void Stop()
        {
            regs.WebController.Canceling = true;
        }

        /// <summary>
        /// 检查某一个文件是否是符合语法规范的脚本文件。
        /// </summary>
        /// <param name="scriptFile">脚本文件s</param>
        /// <returns>如果是返回true，否则返回false</returns>
        public bool ScriptFileCheck(string scriptFile)
        {
            return sp.CheckFileIsScriptFile(scriptFile);
        }

        /// <summary>
        /// 检查某一个文件是否可以加载为指令列表文件
        /// </summary>
        /// <param name="insList"></param>
        /// <returns>可以的话返回true，不可以返回false</returns>
        public static bool InstructionListFileCheck(string insList)
        {
            return ScriptProcessor.CheckFileIsInstructionList(insList);
        }

        /// <summary>
        /// 获取脚本的具体信息
        /// </summary>
        /// <param name="path">脚本文件</param>
        /// <param name="checkScriptFile">是否检查脚本符合语法要求</param>
        /// <returns>脚本信息</returns>
        public ScriptInformation GetScriptInformation(string path, bool checkScriptFile)
        {
            return sp.GetScriptInformation(path, checkScriptFile);
        }

        /// <summary>
        /// 获取脚本的具体信息，不检查脚本语法正确性
        /// </summary>
        /// <param name="path">脚本文件</param>
        /// <returns>脚本信息</returns>
        public ScriptInformation GetScriptInformation(string path)
        {
            return sp.GetScriptInformation(path, false);
        }


        /*
        public void RunScript(string scriptPath)
        {
            orders = sp.ProcessScript(scriptPath,regs);

            bool metFirstSegmentEnd = false;
            List<InstructionTuple> firstSegment = new List<InstructionTuple>();
            List<Controller.RunAfterDocComplete> tasks = new List<Controller.RunAfterDocComplete>();
            foreach (InstructionTuple i in orders)
            {
                if (metFirstSegmentEnd)
                {
                    tasks.Add(new Controller.RunAfterDocComplete(i.Run));
                    if (i.theInstruction.IsSegmentEnd)
                    {
                        i.theParameters.RunnerParameters.WebController.PushEvent(tasks.ToArray());
                        tasks.Clear();
                    }
                }
                else
                {
                    firstSegment.Add(i);
                    if (i.theInstruction.IsSegmentEnd)
                        metFirstSegmentEnd = true;
                }
            }

            foreach (InstructionTuple i in firstSegment)
            {
                i.Run();
            }
        }
         */
    }
}
