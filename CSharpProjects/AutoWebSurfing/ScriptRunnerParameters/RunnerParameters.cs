using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using WebBrowserController;

namespace ScriptRunnerParameters
{

    /// <summary>
    /// 运行时参数
    /// </summary>
    public class RunnerParameters
    {
        private InsPointerRegister p_reg = new InsPointerRegister();
        private Stack<HtmlElement> he_stack = new Stack<HtmlElement>();
        private Controller web;
        private string[] args;

        /// <summary>
        /// 此属性提供系统传递进来的参数
        /// </summary>
        public string[] Args
        {
            get { return args; }
            set { args = value; }
        }

        /// <summary>
        /// 使用目标框架标志
        /// </summary>
        public bool UseTargetFrame = false;

        /// <summary>
        /// 使用目标框架的名字
        /// </summary>
        public string TargetFrameId = "";

        /// <summary>
        /// 获取控制的对象
        /// </summary>
        public Controller WebController
        {
            get { return web; }
            //set { web = value; }
        }

        /// <summary>
        /// 控制对象堆栈
        /// </summary>
        public Stack<HtmlElement> HtmlElementStack
        {
            get { return he_stack; }
            //set { he_stack = value; }
        }

        /// <summary>
        /// 有系统传递进来的可用参数
        /// </summary>
        public string[] SystemArgs
        {
            get { return args; }
        }
        
        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="theBrowser">要控制的对象</param>
        public RunnerParameters(WebBrowser theBrowser)
        {
            this.web = new Controller(theBrowser);
        }

        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="theBrowser">要控制的对象</param>
        /// <param name="args">传递入的参数</param>
        public RunnerParameters(WebBrowser theBrowser, string[] args)
        {
            this.web = new Controller(theBrowser);
            this.args = args;
        }

        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="theController">传入控制器</param>
        public RunnerParameters(Controller theController)
        {
            this.web = theController;
        }

        /// <summary>
        /// 构造器
        /// </summary>
        /// <param name="theController">传入控制器</param>
        /// <param name="args">传递入的参数</param>
        public RunnerParameters(Controller theController, string[] args)
        {
            this.web = theController;
            this.args = args;
        }

        /// <summary>
        /// this is for test only
        /// </summary>
        private RunnerParameters()
        {
            this.web = null;
        }

        /// <summary>
        /// 指令寄存器
        /// </summary>
        public InsPointerRegister PointerRegister
        {
            get { return p_reg; }
            //set { p_reg = value; }
        }
    }
}
