using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using WebBrowserController;

namespace InstructionRealization
{

    /// <summary>
    /// 此类包含一个列表，该列表保存所有已经实现了的指令的新对象。
    /// </summary>
    public class RealizedInstructions
    {
        /// <summary>
        /// 此列列表包含所有已经实现的指令的新对象
        /// </summary>
        public readonly static Instruction[] RealizedIns = 
        {
            new SetValue(),new SetObjValue(),new Jump() , new SetTargetFrame(),
            new WaitDocToLoad(), new GetLink(),new waitAMoment(),new Navigate(),
            new InvokeMethod(),new InvokeObjMethod()
        };
    }


    // 以下实现的指令必须在以上的列表中注册
    #region 指令实现区

    class SetValue : InstructionBase
    {
        override public void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::SetValue.Run");
            Controller web = value.RunnerParameters.WebController;
            try
            {
                string target = value.Target;
                if (target.StartsWith("SYS_VALUE="))
                {
                    int pos = int.Parse(target.Split('=')[1]);
                    target = value.RunnerParameters.SystemArgs[pos];
                }
                if (value.RunnerParameters.UseTargetFrame)
                {
                    web.SetElementAttrib(value.RunnerParameters.TargetFrameId,
                        value.Value1, value.Value2, target);
                }
                else
                {
                    web.SetElementAttrib(value.Value1, value.Value2, target);
                }

            }
            catch (Exception ex)
            {

                throw new InstructionRuntimeExcpetion(ex);
            }
        }
    }

    class SetObjValue : InstructionBase
    {
        override public void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::SetObjValue.Run");
            Controller web = value.RunnerParameters.WebController;
            try
            {
                string target = value.Target;
                if (target.StartsWith("SYS_VALUE="))
                {
                    int pos = int.Parse(target.Split('=')[1]);
                    target = value.RunnerParameters.SystemArgs[pos];
                }
                web.SetElementAttrib(value.RunnerParameters.HtmlElementStack.Pop(),
                    value.Value1, target);
            }
            catch (Exception ex)
            {
                
                throw new InstructionRuntimeExcpetion(ex);
            }
        }
    }

    class Jump : InstructionBase
    {
        public override bool IsJump
        {
            get { return true; }
        }
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::Jump.Run");
            int nextins = int.Parse(value.Target);
            value.RunnerParameters.PointerRegister.SetNext(nextins);
        }
    }

    class SetTargetFrame : InstructionBase
    {
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::SetTargetFrame.Run");
            if (value.Target == null || value.Target == "")
            {
                value.RunnerParameters.UseTargetFrame = false;
            }
            else
            {
                value.RunnerParameters.UseTargetFrame = true;
                value.RunnerParameters.TargetFrameId = value.Target;
            }
        }
    }

    /// <summary>
    /// Dummy Instruction
    /// </summary>
    class WaitDocToLoad : InstructionBase
    {
        public override bool IsSegmentEnd
        {
            get { return true; }
        }
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::WaitDocToLoad.Run");
            // this instruction has no means to run
            //throw new NotImplementedException();
        }
    }

    class GetLink : InstructionBase
    {
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::GetObj.Run");
            try
            {
                HtmlElement finded;
                if (value.RunnerParameters.UseTargetFrame)
                {
                    finded = value.RunnerParameters.WebController.FindLinks(
                        value.RunnerParameters.TargetFrameId, value.Value1)[0];
                    value.RunnerParameters.HtmlElementStack.Push(finded);
                }
                else
                {
                    finded = value.RunnerParameters.WebController.FindLinks(value.Value1)[0];
                    value.RunnerParameters.HtmlElementStack.Push(finded);
                }

            }
            catch (Exception ex)
            {

                throw new InstructionRuntimeExcpetion(ex);
            }
        }
    }

    /* 
     * 暂时不实现一下两条指令
     * 
    class GetValue : InstructionBase
    {
        //UNDONE 暂不实现这个指令
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::GetValue.Run");
            throw new NotImplementedException();
        }
    } 

    class GetObjValue : InstructionBase
    {
        //UNDONE 暂不实现这个指令
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::GetObjValue.Run");
            throw new NotImplementedException();
        }
    }
     */

    class waitAMoment : InstructionBase
    {
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::waitAMoment.Run");
            int time = 0;
            if(int.TryParse(value.Value1,out time))
                value.RunnerParameters.WebController.WaitAMoment(time);
            else
                value.RunnerParameters.WebController.WaitAMoment();
        }
    }

    class Navigate : InstructionBase
    {
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::Navigate.Run");
            value.RunnerParameters.WebController.Navigate(value.Target);
        }
    }

    class InvokeMethod : InstructionBase
    {
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::InvokeMethod.Run");
            try
            {
                if (value.RunnerParameters.UseTargetFrame)
                {
                    value.RunnerParameters.WebController.TriggerElementMethod(
                        value.RunnerParameters.TargetFrameId, value.Value1, value.Value2);
                }
                else
                {
                    value.RunnerParameters.WebController.TriggerElementMethod(
                        value.Value1, value.Value2);
                }

            }
            catch (Exception ex)
            {

                throw new InstructionRuntimeExcpetion(ex);
            }
        }
    }

    class InvokeObjMethod : InstructionBase
    {
        public override void Run(InstructionParameters value)
        {
            Console.WriteLine("Instruction::InvokeMethod.Run");
            try
            {
                value.RunnerParameters.WebController.TriggerElementMethod(
                    value.RunnerParameters.HtmlElementStack.Pop(), value.Value1);
            }
            catch (Exception ex)
            {

                throw new InstructionRuntimeExcpetion(ex);
            }
        }
    }
    #endregion
}
