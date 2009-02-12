using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;
using System.Windows.Forms;
using ScriptRunner;

namespace TestControl
{
    class Program
    {
        static void Main(string[] args)
        {
            /*
             * Details
             * 
            ScriptProcessor sp = new ScriptProcessor("G:\\Desktop\\instructionList.txt");
            foreach (Instruction i in sp.AvailableInstructions)
            {
                Console.WriteLine("实现了指令：{0}", i.LiteralName);
            }
            Console.WriteLine("instruction list file load Done");
            List<InstructionTuple> itlist = sp.ProcessScript("G:\\Desktop\\4tuple.txt",new RunnerParameters(null));
            foreach (InstructionTuple it in itlist)
            {
                Console.WriteLine(it.theInstruction.LiteralName + ":(" +
                    it.theParameters.Value1 + "," + it.theParameters.Value2 + "," +
                    it.theParameters.Target + ")");
            }
             */
            //Runner runner = new Runner(@"G:\Desktop\AutoWebSurfing\InstructionRealization\instructionList.txt");
            //runner.RunScript("G:\\Desktop\\testscript.txt");

            ScriptInformation info = runner.GetScriptInformation(@"G:\Desktop\AutoWebSurfing\InstructionRealization\4tuple.txt");

            Console.WriteLine("Author:" + info.Author);
            Console.WriteLine("name:" + info.Name);
            Console.WriteLine("description:" + info.Description);
            Console.WriteLine("verions:" + info.Version);
            Console.WriteLine("data:" + info.Data);

            Console.ReadKey();
        }
    }
}
