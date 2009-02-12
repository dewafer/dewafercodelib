using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.IO;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WebBrowserController;
using ScriptRunner;

namespace FuckYouToDeath
{
    public partial class MainForm : Form
    {

        private Controller ctrl = null;
        private ScriptsManager sm;
        private Runner sr;

        public MainForm()
        {
            InitializeComponent();

            ctrl = new Controller(this.webBrowser1);
            sm = new ScriptsManager();

            this.webBrowser1.StatusTextChanged += new EventHandler(webBrowser1_StatusTextChanged);
            this.pwdTextBox.TextBox.PasswordChar = '○';
            this.toolStripStatusLabel3.Text = Application.ProductName + " / " + Application.ProductVersion;
        }

        void webBrowser1_StatusTextChanged(object sender, EventArgs e)
        {
            this.toolStripStatusLabel1.Text = this.webBrowser1.StatusText;
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            try
            {
                sr = new Runner(Properties.Settings.Default.DEF_INSLIST_FILE, ctrl);
                sr.ScriptRunCompleted += finish_event;
            }
            catch (Exception ex)
            {
                MessageBox.Show("系统文件加载错误！请手动确认指令列表文件。\n\r\n\r错误详细信息：" +
                     ex.Message, "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                loadInsFile(true);
            }

            if (!Directory.Exists(Properties.Settings.Default.DEF_SCP_DIR))
                Directory.CreateDirectory(Properties.Settings.Default.DEF_SCP_DIR);
            sm.LoadScripts(Properties.Settings.Default.DEF_SCP_DIR, sr);
            initScriptToolStrip();
            //this.webBrowser1.Stop();
        }

        private void loadInsFile(bool exitOnFailue)
        {
            DialogResult dr = this.openFileDialog1.ShowDialog();
            if (dr == DialogResult.OK)
            {
                try
                {
                    sr = new Runner(this.openFileDialog1.FileName, ctrl);
                    Properties.Settings.Default.DEF_INSLIST_FILE = this.openFileDialog1.FileName;
                    Properties.Settings.Default.Save();
                }
                catch (Exception ex)
                {
                    MessageBox.Show("指令列表文件加载错误！请确认指令列表文件。\n\r\n\r错误详细信息：" +
                        ex.Message, "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    loadInsFile(exitOnFailue);
                }
            }
            else
            {
                if (exitOnFailue)
                    Application.Exit();
            }
        }

        private void webBrowser1_ProgressChanged(object sender, WebBrowserProgressChangedEventArgs e)
        {
            if (e.CurrentProgress == e.MaximumProgress || e.CurrentProgress < 0 || e.MaximumProgress <= 0)
            {
                this.toolStripProgressBar1.Visible = false;
                return;
            }
            else
                this.toolStripProgressBar1.Visible = true;
            this.toolStripProgressBar1.Value = (int)(e.CurrentProgress / (e.MaximumProgress / 100));
            Console.WriteLine(e.CurrentProgress + "/" + e.MaximumProgress + ":" + this.toolStripProgressBar1.Value);
        }

        private void goBackButton_Click(object sender, EventArgs e)
        {
            this.webBrowser1.GoBack();
        }

        private void goForwardButton_Click(object sender, EventArgs e)
        {
            this.webBrowser1.GoForward();
        }

        private void reflashButton_Click(object sender, EventArgs e)
        {
            this.webBrowser1.Refresh();
        }

        private void goHomeButton3_Click(object sender, EventArgs e)
        {
            //this.webBrowser1.GoHome();
            this.webBrowser1.Navigate(FuckYouToDeath.Properties.Settings.Default.JWC1_URL);
        }

        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            this.goBackButton.Enabled = this.webBrowser1.CanGoBack;
            this.goForwardButton.Enabled = this.webBrowser1.CanGoForward;
        }

        private void stopButton_Click(object sender, EventArgs e)
        {
            this.webBrowser1.Stop();
        }

        private bool login()
        {
            if (this.IdTextBox.Text == "" || this.pwdTextBox.Text == "")
            {
                MessageBox.Show("学号和密码不能为空", "", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
                return false;
            }
            ctrl.Navigate(FuckYouToDeath.Properties.Settings.Default.JWC1_URL.ToString());
            return true;
            //ctrl.DocComplete += new Controller.RunAfterDocComplete(login_event);

        }

        private void LoginButton_Click(object sender, EventArgs e)
        {
            //ctrl.DocComplete += new Controller.RunAfterDocComplete(login_event);
            //ctrl.PushEvent(login_event);
            //ctrl.Push(test_event);
            //ctrl.PushEvent(finish_event);

            ctrl.PushEvent(new Controller.RunAfterDocComplete(login_event));
            ctrl.SetFinallyRun(new Controller.EventQueueFinishedRun(finish_event));

            if (login())
                WaitForm.GetWaitForm(new WaitForm.stopRun(userCancel)).ShowDialog();
        }

        private void ExitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void intelligenceButton_Click(object sender, EventArgs e)
        {
            //ctrl.PushEvent(login_event);
            //ctrl.PushEvent(click_inquiryScore);
            //ctrl.PushEvent(click_inquiryTermScore);
            //ctrl.PushEvent(finish_event);

            ctrl.PushEvent(new Controller.RunAfterDocComplete(login_event));
            ctrl.PushEvent(new Controller.RunAfterDocComplete(click_inquiryScore));
            ctrl.PushEvent(new Controller.RunAfterDocComplete(click_inquiryTermScore));
            ctrl.SetFinallyRun(new Controller.EventQueueFinishedRun(finish_event));

            if (login())
                WaitForm.GetWaitForm(new WaitForm.stopRun(userCancel)).ShowDialog();
        }

        private void testButton_Click(object sender, EventArgs e)
        {
            /*
            string str = "成绩查询";
            System.Text.RegularExpressions.Regex reg = new System.Text.RegularExpressions.Regex("成绩查询");
            MessageBox.Show("match:" + reg.IsMatch(str).ToString());
             */

            //click_inquiryScore();
            WaitForm.GetWaitForm(new WaitForm.stopRun(userCancel)).ShowDialog();
            System.Threading.Thread.Sleep(3000);
            //WaitForm.GetWaitForm().Close();
            finish_event();
        }

        private void toolStripStatusLabel3_Click(object sender, EventArgs ea)
        {
            StringBuilder info = new StringBuilder();

            info.AppendLine("由dewafer制作，献给亲爱的同学们。");
            info.AppendLine();

            info.Append("主程序版本:");
            System.Reflection.AssemblyName a = System.Reflection.Assembly.Load("GetMyScore").GetName();
            info.AppendLine(a.Version.ToString());
            info.AppendLine();

            info.Append("WebBrowserController模块版本:");
            System.Reflection.AssemblyName b = System.Reflection.Assembly.Load("WebBrowserController").GetName();
            info.AppendLine(b.Version.ToString());
            info.AppendLine();

            info.Append("FileOperator模块版本:");
            System.Reflection.AssemblyName c = System.Reflection.Assembly.Load("FileOperator").GetName();
            info.AppendLine(c.Version.ToString());
            info.AppendLine();

            info.Append("ScriptRunner模块版本:");
            System.Reflection.AssemblyName d = System.Reflection.Assembly.Load("ScriptRunner").GetName();
            info.AppendLine(d.Version.ToString());
            info.AppendLine();

            info.Append("ScriptRunnerParameters模块版本:");
            System.Reflection.AssemblyName e = System.Reflection.Assembly.Load("ScriptRunnerParameters").GetName();
            info.AppendLine(e.Version.ToString());
            info.AppendLine();

            info.Append("InstructionRealization模块版本:");
            System.Reflection.AssemblyName f = System.Reflection.Assembly.Load("InstructionRealization").GetName();
            info.AppendLine(f.Version.ToString());
            info.AppendLine();


            MessageBox.Show(info.ToString(), "Nice!", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void ScriptsManagetoolStripButton_Click(object sender, EventArgs e)
        {
            sm.ShowDialog();
            initScriptToolStrip();
        }

        private ToolStripButton[] scriptToolStripButs;

        private void initScriptToolStrip()
        {
            if (scriptToolStripButs != null)
            {
                foreach (ToolStripItem ti in scriptToolStripButs)
                    this.ScriptsToolStrip.Items.Remove(ti);
            }

            scriptToolStripButs = new ToolStripButton[sm.ScriptInformationList.Length];

            ScriptInformation si;
            for (int i = 0; i < scriptToolStripButs.Length; i++)
            {
                si = sm.ScriptInformationList[i];
                scriptToolStripButs[i] = new ToolStripButton();
                scriptToolStripButs[i].Tag = si;
                if (si.Name.Length > 50)
                    scriptToolStripButs[i].Text = si.Name.Substring(0, 50).Replace("\r\n", "");
                else
                    scriptToolStripButs[i].Text = si.Name.Replace("\r\n", "");
                scriptToolStripButs[i].ImageTransparentColor = System.Drawing.Color.Magenta;
                scriptToolStripButs[i].AutoSize = true;
                scriptToolStripButs[i].DisplayStyle = ToolStripItemDisplayStyle.ImageAndText;
                Bitmap icon = Properties.Resources._35;
                try
                {
                    icon = new Bitmap(si.Icon);
                }
                catch (Exception)
                {
                    try
                    {
                        icon = new Bitmap(Properties.Settings.Default.DEF_SCP_DIR + "\\" + si.Icon);
                    }
                    catch (Exception)
                    {
                        scriptToolStripButs[i].DisplayStyle = ToolStripItemDisplayStyle.Text;
                    }
                }
                scriptToolStripButs[i].Image = icon;

                scriptToolStripButs[i].Click += new EventHandler(scriptToolStripButton_click);
            }

            this.ScriptsToolStrip.Items.AddRange(scriptToolStripButs);

        }

        private void scriptToolStripButton_click(object sender, EventArgs e)
        {
            //ToolStripButton b = (ToolStripButton)sender;
            //ScriptInformation si = (ScriptInformation)b.Tag;

            //if (this.IdTextBox.Text == "" || this.pwdTextBox.Text == "")
            //{
            //    MessageBox.Show("学号和密码不能为空", "", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
            //    return ;
            //}

            //string[] sysargs = new string[2];
            //sysargs[0] = this.IdTextBox.Text;
            //sysargs[1] = this.pwdTextBox.Text;

            //sr.Args = sysargs;
            //ctrl.SetFinallyRun(new Controller.EventQueueFinishedRun(finish_event));

            if (this.IdTextBox.Text == "" || this.pwdTextBox.Text == "")
            {
                MessageBox.Show("学号和密码不能为空", "", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
                return;
            }

            this.backgroundWorker1.RunWorkerAsync(sender);
            //try
            //{

            //    sr.RunScript(si.FileFullName);
            //}
            //catch (Exception ex)
            //{
            //    errorProcess(ex);
            //    return;
            //}
            WaitForm.GetWaitForm(new WaitForm.stopRun(userCancel)).ShowDialog();
        }

        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            ToolStripButton b = (ToolStripButton)e.Argument;
            ScriptInformation si = (ScriptInformation)b.Tag;


            string[] sysargs = new string[4];
            sysargs[0] = this.IdTextBox.Text;
            sysargs[1] = this.pwdTextBox.Text;
            sysargs[2] = this.getXueNian();
            sysargs[3] = this.getXueQi();

            sr.Args = sysargs;
            try
            {
                sr.RunScript(si.FileFullName);
            }
            catch (Exception ex)
            {
                errorProcess(ex);
                return;
            }
            //WaitForm.GetWaitForm(new WaitForm.stopRun(userCancel)).ShowDialog();
        }

        private void showDebugToolStripToolStripMenuItem_CheckedChanged(object sender, EventArgs e)
        {
            this.AdvToolStrip.Visible = this.showDebugToolStripToolStripMenuItem.Checked;
        }

        private void toolStripButton10_CheckedChanged(object sender, EventArgs e)
        {
            this.toolStripTextBox4.Focus();
        }

        private void toolStripTextBox4_Leave(object sender, EventArgs e)
        {
            this.toolStripButton10.Checked = false;
        }

        private void MainForm_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (toolStripButton10.Checked)
            {
                e.Handled = true;
                //MessageBox.Show(e.KeyChar + " Pressed");
                //MessageBox.Show(Control.MousePosition.ToString());

                Point p = this.webBrowser1.PointToClient(Control.MousePosition);
                TextForm n = new TextForm();

                HtmlElement he = this.webBrowser1.Document.GetElementFromPoint(p);
                n.Show(he.OuterHtml);
                n.Text = "OuterHtml";

                TextForm m = new TextForm();
                m.Show(he.InnerText);
                m.Text = "InnerText";

                TextForm l = new TextForm();
                l.Show(he.InnerHtml);
                l.Text = "InnerHtml";
                //MessageBox.Show(this.webBrowser.Document.GetElementFromPoint(p).OuterHtml);
            }

        }

        private void openTextFormToolStripMenuItem_Click(object sender, EventArgs e)
        {
            TextForm txf = new TextForm();
            txf.Show();
        }

        private void toolStripTextBox4_TextChanged(object sender, EventArgs e)
        {
            this.toolStripButton10.Checked = true;
            this.toolStripTextBox4.Text = "";
        }
    }
}
