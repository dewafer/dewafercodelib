using System;
using System.Collections.Generic;
using System.Text;

namespace ScriptRunnerParameters
{
    /// <summary>
    /// 指令运行寄存器类
    /// </summary>
    public class InsPointerRegister
    {
        private int pointer = 0;

        /// <summary>
        /// 寄存器进位
        /// </summary>
        /// <returns>返回进位前的值</returns>
        public int Next()
        {
            return pointer++;
        }

        /// <summary>
        /// 偷看一眼
        /// </summary>
        /// <returns>返回当前正在运行的指令在列表中的索引位置</returns>
        public int Peek()
        {
            return pointer;
        }

        /// <summary>
        /// 置指令寄存器为下一条指令的索引号
        /// </summary>
        /// <param name="n">下一条指令的索引号</param>
        public void SetNext(int n)
        {
            pointer = n - 1;
        }

        /// <summary>
        /// 重置寄存器
        /// </summary>
        public void Reset()
        {
            pointer = 0;
        }
    }
}
