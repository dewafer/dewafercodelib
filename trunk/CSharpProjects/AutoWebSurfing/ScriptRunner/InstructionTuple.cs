using System;
using System.Collections.Generic;
using System.Text;
using InstructionRealization;

namespace ScriptRunner
{
    /// <summary>
    /// 指令元组类，该类代表一个可运行的指令单元
    /// </summary>
    class InstructionTuple
    {
        /// <summary>
        /// 指令行号
        /// </summary>
        public readonly int Id;

        private readonly Instruction ins;
        /// <summary>
        /// 指令，只读
        /// </summary>
        public Instruction theInstruction
        {
            get { return ins; }
        }

        private InstructionParameters param;
        /// <summary>
        /// 指令运行参数，只读
        /// </summary>
        public InstructionParameters theParameters
        {
            get { return param; }
        }

        /// <summary>
        /// 使用该方法可以更换一个可运行指令的参数
        /// </summary>
        /// <param name="newOne">新参数</param>
        /// <returns>返回旧的参数</returns>
        public InstructionParameters newParameter(InstructionParameters newOne)
        {
            InstructionParameters old = this.param;
            this.param = newOne;
            return old;
        }

        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="ins">指令</param>
        /// <param name="param">指令运行参数</param>
        /// <param name="id">指令行号</param>
        public InstructionTuple(Instruction ins, InstructionParameters param,int id)
        {
            this.ins = ins;
            this.param = param;
            this.Id = id;
        }

        /// <summary>
        /// 运行该指令元组
        /// </summary>
        public void Run()
        {
            if (param.RunnerParameters.WebController.Canceling)
                return;
            ins.Run(param);
        }
    }
}
