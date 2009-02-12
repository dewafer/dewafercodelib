using System;
using System.Collections.Generic;
using System.Text;
using ScriptRunnerParameters;


namespace ScriptRunner
{
    /// <summary>
    /// 指令段，该类代表一段已经排序完成的可运行的指令
    /// </summary>
    class InstructionTupleSegment
    {
        public delegate void InstructionTupleSegmentRunCompletedHandler(object sender, object eventArgs);
        public InstructionTupleSegmentRunCompletedHandler InstructionTupleSegmentRunCompleted;

        private List<InstructionTuple> tasks = new List<InstructionTuple>();
        private RunnerParameters regs;

        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="orders">一段已排序的指令</param>
        /// <param name="rex">指令运行参数</param>
        public InstructionTupleSegment(List<InstructionTuple> orders,RunnerParameters rex)
        {
            this.tasks = orders;
            this.regs = rex;
            this.Reset();
        }

        /// <summary>
        /// 运行该指令段
        /// </summary>
        public void Run()
        {
            InsPointerRegister p_reg = regs.PointerRegister;

            if (regs.WebController.Canceling)
            {
                Reset();
                return;
            }

            for (; p_reg.Peek() < tasks.Count; p_reg.Next())
            {
                InstructionTuple theOne = tasks[p_reg.Peek()];
                theOne.Run();
                if (theOne.theInstruction.IsSegmentEnd)
                {
                    theOne.theParameters.RunnerParameters.WebController.PushEvent(
                        new WebBrowserController.Controller.RunAfterDocComplete(this.Run));
                    p_reg.Next();
                    return;
                }
            }
            InstructionTupleSegmentRunCompleted(this, null);
            Console.WriteLine("Mission Completed.");
        }

        /// <summary>
        /// 重置该指令段
        /// </summary>
        public void Reset()
        {
            this.regs.PointerRegister.Reset();
            this.regs.WebController.ClearEvent();
            this.regs.WebController.Canceling = false;
        }

    }
}
