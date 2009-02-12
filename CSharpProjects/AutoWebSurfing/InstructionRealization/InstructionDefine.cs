using System;
using System.Collections.Generic;
using System.Text;
using ScriptRunnerParameters;
using WebBrowserController;

namespace InstructionRealization
{
    /// <summary>
    /// 该类包含指令运行时传递的必须参数
    /// </summary>
    public class InstructionParameters
    {
        /// <summary>
        /// 运行时系统的参数
        /// </summary>
        public readonly RunnerParameters RunnerParameters;

        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="runparm">运行时系统的参数</param>
        /// <param name="value1">值段1</param>
        /// <param name="value2">值段2</param>
        /// <param name="target">目标值段</param>
        public InstructionParameters(RunnerParameters runparm, string value1, string value2, string target)
        {
            this.RunnerParameters = runparm;
            this.Value1 = value1;
            this.Value2 = value2;
            this.Target = target;
        }
        /// <summary>
        /// 值段1，只读
        /// </summary>
        public readonly string Value1;
        /// <summary>
        /// 值段2，只读
        /// </summary>
        public readonly string Value2;
        /// <summary>
        /// 目标值段，只读
        /// </summary>
        public readonly string Target;
    }

    /// <summary>
    /// 指令所继承的接口
    /// </summary>
    public interface Instruction
    {
        /// <summary>
        /// 该属性获取指令的字面名字，只读
        /// </summary>
        string LiteralName { get; }

        /// <summary>
        /// 该属性表示指令是否是某一段的段末，只读
        /// </summary>
        bool IsSegmentEnd { get; }

        /// <summary>
        /// 该属性表示指令是否是跳转指令，如是跳转指令则自动修正跳转行号，只读
        /// </summary>
        bool IsJump { get; }

        /// <summary>
        /// 指令的实现
        /// </summary>
        /// <param name="value">运行时必须参数</param>
        void Run(InstructionParameters value);
    }

    /// <summary>
    /// 该基类包含了一些基本的功能
    /// 使用该基类创建一个新指令可以减轻一些工作
    /// </summary>
    abstract class InstructionBase : Instruction
    {
        public virtual string LiteralName
        {
            get { return this.GetType().Name; }
        }

        public virtual bool IsSegmentEnd
        {
            get { return false; }
        }

        public virtual bool IsJump
        {
            get { return false; }
        }

        abstract public void Run(InstructionParameters value);

        public override string ToString()
        {
            return LiteralName;
        }
    }

    /// <summary>
    /// 指令运行时错误类
    /// </summary>
    public class InstructionRuntimeExcpetion : Exception
    {
        private const string msg = "指令运行时错误。";
        public InstructionRuntimeExcpetion(Exception innerExpcetion) : base(msg, innerExpcetion) { }
        public InstructionRuntimeExcpetion() : base(msg) { }
        public InstructionRuntimeExcpetion(string exmsg) : base(exmsg) { }
        public InstructionRuntimeExcpetion(string exmsg, Exception innerExpcetion) : base(exmsg, innerExpcetion) { }
    }


}
