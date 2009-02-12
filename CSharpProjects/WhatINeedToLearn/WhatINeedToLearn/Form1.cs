using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using ScriptRunner;

namespace WhatINeedToLearn
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            this.NavigateUrlTextBox.Text = "jwc1.usst.edu.cn";
        }

        // 导航
        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            if (this.NavigateUrlTextBox.Text == "")
                this.webBrowser.Navigate("http://www.baidu.com");
            else
                this.webBrowser.Navigate(this.NavigateUrlTextBox.Text);
        }

        // 获取URL
        private void webBrowser_Navigated(object sender, WebBrowserNavigatedEventArgs e)
        {
            this.webBrowserUrlLable.Text = this.webBrowser.Url.AbsoluteUri;
        }

        //查看源代码
        private void toolStripButton1_Click_1(object sender, EventArgs e)
        {
            TextForm tf = new TextForm();
            if (this.webBrowser.DocumentText != "")
            {
                //this.webBrowser.Document.Encoding.
                StreamReader sreader = new StreamReader(this.webBrowser.DocumentStream,
                    Encoding.GetEncoding(this.webBrowser.Document.Encoding));
                tf.Show(sreader.ReadToEnd());

            }
            else
                tf.Show("No Document Loaded...");
        }

        // 百度搜索“啊” - 注册事件并导航到百度
        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            this.webBrowser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(webBrowser_searchADocumentCompleted);
            this.webBrowser.Navigate("http://www.baidu.com");
        }

        // 百度搜索“啊” - 事件响应
        void webBrowser_searchADocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            HtmlDocument doc = this.webBrowser.Document;
            doc.All["kw"].SetAttribute("value", "啊");
            doc.All["f"].InvokeMember("submit");
            this.webBrowser.DocumentCompleted -= webBrowser_searchADocumentCompleted;
        }

        // 点击百度“更多>>”超链接 - 事件响应
        void webBrowser_clickfirstOneDocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            TextForm tf = new TextForm();
            HtmlDocument doc = this.webBrowser.Document;
            foreach (HtmlElement he in doc.All)
            {
                if (he.InnerText == "更多>>")
                {
                    tf.Show(he.InnerText);
                    he.InvokeMember("click");
                }
            }
            this.webBrowser.DocumentCompleted -= webBrowser_clickfirstOneDocumentCompleted;
        }

        // 查看“zhuti”内源代码 - 事件响应
        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            if ( this.webBrowser.Url==null || !this.webBrowser.Url.Host.Contains("jwc1.usst.edu.cn"))
                return;
            HtmlWindowCollection frames = this.webBrowser.Document.Window.Frames;
            HtmlWindow iframe = frames["zhuti"];
            if (iframe != null)
            {
                TextForm tf = new TextForm();
                tf.Show(iframe.Document.Body.InnerHtml);
            }
        }

        // 点击百度“更多>>”超链接 - 注册事件并导航到百度
        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            this.webBrowser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(webBrowser_clickfirstOneDocumentCompleted);
            this.webBrowser.Navigate("http://www.baidu.com");
        }

        // Google注册 自动选择“阿尔巴尼亚” - 注册时间并导航到google注册
        private void toolStripButton5_Click(object sender, EventArgs e)
        {
            this.webBrowser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(webBrowser_albDocumentCompleted);
            this.webBrowser.Navigate("https://www.google.com/accounts/NewAccount");
        }

        // Google注册 自动选择“阿尔巴尼亚” - 事件响应
        void webBrowser_albDocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            TextForm tf = new TextForm();
            HtmlElement ele = this.webBrowser.Document.All["loc"];
            ele.SetAttribute("value", "AL");
            this.webBrowser.DocumentCompleted -= webBrowser_albDocumentCompleted;
        }

        // 登录jwc1并查询2008-2009-1学期的成绩 - 事件注册并导航到jwc1
        private void toolStripButton6_Click(object sender, EventArgs e)
        {
            this.webBrowser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(webBrowser_findScoreDocumentCompleted);
            this.webBrowser.Navigate("http://jwc1.usst.edu.cn");
        }

        // 登录jwc1并查询2008-2009-1学期的成绩 - 事件响应
        private int stage = 0;
        void webBrowser_findScoreDocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            this.toolStripLabel4.Text = stage.ToString();
            switch (stage)
            {
                case 0:

                    //System.Threading.Thread.Sleep(5000);
                    System.Threading.Thread.SpinWait(3000);

                    HtmlElement idtxtbox = this.webBrowser.Document.All["TextBox1"];
                    HtmlElement pwdtxtbox = this.webBrowser.Document.All["TextBox2"];
                    HtmlElement form = this.webBrowser.Document.All["Button1"];
                    idtxtbox.SetAttribute("value", this.toolStripTextBox1.Text);
                    pwdtxtbox.SetAttribute("value", this.toolStripTextBox2.Text);
                    stage++;
                    form.InvokeMember("click");
                    break;

                case 1:

                    //System.Threading.Thread.Sleep(5000);
                    System.Threading.Thread.SpinWait(3000);

                    foreach (HtmlElement link in this.webBrowser.Document.Links)
                    {
                        if (link.InnerText == "成绩查询")
                        {
                            stage++;
                            link.InvokeMember("click");
                            break;
                        }
                    }
                    break;


                case 2:

                    //System.Threading.Thread.Sleep(5000);
                    System.Threading.Thread.SpinWait(3000);

                    HtmlDocument doc = this.webBrowser.Document.Window.Frames["zhuti"].Document;

                    HtmlElement xuenian = doc.All["ddlXN"];
                    HtmlElement xueqi = doc.All["ddlXQ"];
                    HtmlElement button = doc.All["btn_xq"];

                    xuenian.SetAttribute("value", "2008-2009");
                    xueqi.SetAttribute("value", "1");
                    stage++;
                    button.InvokeMember("click");

                    break;

                case 3:

                    stage = 0;
                    this.webBrowser.DocumentCompleted -= webBrowser_findScoreDocumentCompleted;
                    break;
            }

        }

        // 点击按钮
        private void toolStripButton7_Click(object sender, EventArgs e)
        {
            foreach (HtmlElement link in this.webBrowser.Document.Links)
            {
                if (link.InnerText == this.toolStripTextBox3.Text)
                {
                    link.InvokeMember("click");
                    break;
                }
            }
        }

        private void 退出ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void 文本查看器ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            TextForm tf = new TextForm();
            tf.Show();
        }

        private void 向导ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            GuideForm gf = new GuideForm();
            gf.Show();
        }


        private string insListPath;
        private string scriptPath;
        private Runner sc_run;
        private bool readytorun = false ;

        
        private void toolStripButton8_Click(object sender, EventArgs e)
        {
            

            ScriptRunnerForm sf = new ScriptRunnerForm();
            if (sf.ShowDialog() != DialogResult.OK)
                return;

            insListPath = sf.textBox1.Text;
            scriptPath = sf.textBox2.Text;

            //try
            //{
            sc_run = new Runner(insListPath, this.webBrowser);
            this.toolStripButton9.Enabled = true;
            sc_run.ScriptRunCompleted += new Runner.ScriptRunCompletedHandler(sc_run_ScriptRunCompleted);
            readytorun = true;
            //  run.RunScript(scriptPath);
            //}
            //catch (Exception ex)
            //{
            /*
             * 
                StringBuilder msg = new StringBuilder("发生错误：");
                msg.Append("    错误消息:");
                msg.AppendLine(ex.Message);
                msg.Append("    来源:");
                msg.AppendLine(ex.Source);
                msg.Append("    内部错误消息:");
                msg.AppendLine(ex.InnerException.Message);
                msg.Append("    来源:");
                msg.AppendLine(ex.Source);
                MessageBox.Show( msg.ToString());
             */
            //}

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.webBrowser.GoHome();
            this.toolStripButton9.Enabled = false;
        }

        private void toolStripButton9_Click(object sender, EventArgs e)
        {
            if (sc_run == null)
                return;
            sc_run.RunScript(scriptPath);
        }

        void sc_run_ScriptRunCompleted(object sender, object eventArgs)
        {
            MessageBox.Show("script run finished");
        }

        private bool got_flag = false;
        private void Form1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (got_flag)
            {
                e.Handled = true;
                //MessageBox.Show(e.KeyChar + " Pressed");
                //MessageBox.Show(Control.MousePosition.ToString());

                Point p = this.webBrowser.PointToClient(Control.MousePosition);
                TextForm n = new TextForm();

                HtmlElement he = this.webBrowser.Document.GetElementFromPoint(p);
                n.Show(he.OuterHtml);
                n.Text = "OuterHtml";
                TextForm m = new TextForm();
                m.Show(he.InnerText);
                m.Text = "InnerText";
                //MessageBox.Show(this.webBrowser.Document.GetElementFromPoint(p).OuterHtml);
            }
        }

        private void toolStripButton10_CheckedChanged(object sender, EventArgs e)
        {
            //MessageBox.Show(Control.MousePosition.X + " " + Control.MousePosition.Y);
            this.webBrowser.WebBrowserShortcutsEnabled = false;
            got_flag = this.toolStripButton10.Checked;
            this.toolStripTextBox4.Focus();
        }

        private void toolStripTextBox4_Leave(object sender, EventArgs e)
        {
            this.toolStripButton10.Checked = false;
            //MessageBox.Show("me left");
        }

        private void webBrowser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            if (this.webBrowser.ReadyState == WebBrowserReadyState.Complete && readytorun)
                this.toolStripButton9.Enabled = true;
            else
                this.toolStripButton9.Enabled = false;
        }

    }
}
