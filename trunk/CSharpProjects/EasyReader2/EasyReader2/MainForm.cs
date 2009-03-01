using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;


namespace EasyReader2
{
    public partial class MainForm : Form
    {
        private MySpeecher speaker = MySpeecher.GetSpeecher();
        private bool paused = false;
        private bool isReading = false;
        private string openFile = "";
        private int readCharPosition = 0;
        private int lastCharPos = 0;

        public MainForm()
        {
            InitializeComponent();
            speaker.SetSpeakCompleteEventHandler(speakerCompletedEventHandler);
            speaker.SetSpeakProgressEventHandler(speakerProgressEventHandler);
            speaker.SelectLocalCultureVoice();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            speaker.Speak(this.textBox1.Text.Substring(lastCharPos));
            isReading = true;

            // read
            this.button1.Enabled = false;
            this.readToolStripMenuItem.Enabled = false;
            // settings
            this.button2.Enabled = false;
            this.settingsToolStripMenuItem.Enabled = false;
            // stop
            this.button3.Enabled = true;
            this.stopToolStripMenuItem.Enabled = true;
            // save as
            this.button4.Enabled = false;
            this.saveAsToolStripMenuItem.Enabled = false;
            // pause
            this.button5.Enabled = true;
            this.pauseToolStripMenuItem.Enabled = true;
            // open
            this.button6.Enabled = false;
            this.openToolStripMenuItem.Enabled = false;
            // continue last time
            this.button9.Enabled = false;
            this.readFromLastPositionToolStripMenuItem.Enabled = false;
            // start from selection
            this.button8.Enabled = false;

            notifyIsReading();


        }

        private void button2_Click(object sender, EventArgs e)
        {
            new VoiceSetForm().Show(this);
        }

        private void speakerCompletedEventHandler(object sender,EventArgs e)
        {
            //MessageBox.Show("我读完了");

            // read
            this.button1.Enabled = true;
            this.readToolStripMenuItem.Enabled = true;
            this.button9.Enabled = true;
            // settings
            this.button2.Enabled = true;
            this.settingsToolStripMenuItem.Enabled = true;
            // stop
            this.button3.Enabled = false;
            this.stopToolStripMenuItem.Enabled = false;
            // save as
            this.button4.Enabled = true;
            this.saveAsToolStripMenuItem.Enabled = true;
            // pause
            this.button5.Enabled = false;
            this.pauseToolStripMenuItem.Enabled = false;
            // open
            this.button6.Enabled = true;
            this.openToolStripMenuItem.Enabled = true;
            // continue last time
            this.button9.Enabled = true;
            this.readFromLastPositionToolStripMenuItem.Enabled = true;
            // start from selection
            this.button8.Enabled = true;

            this.textBox1.Select(0, 0);
            isReading = false;
            notifyReadOver();
        }

        private void speakerProgressEventHandler(object sender, EventArgs e)
        {
            System.Speech.Synthesis.SpeakProgressEventArgs read = 
                (System.Speech.Synthesis.SpeakProgressEventArgs)e;
            this.textBox1.Select(read.CharacterPosition + lastCharPos, read.CharacterCount);
            this.textBox1.ScrollToCaret();
            readCharPosition = read.CharacterPosition;
            //notifyIsReading();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            pause();
            if (this.openFile.Length > 0 && this.readCharPosition > 0)
            {
                DialogResult dr = MessageBox.Show("保存当前阅读位置吗？", "提问", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (dr == DialogResult.Yes)
                {
                    savePosition();
                }
            }
            resume();
            speaker.Stop();
            this.lastCharPos = 0;
            this.readCharPosition = 0;
        }

        private void button4_Click(object sender, EventArgs e)
        {
            DialogResult dr = this.saveFileDialog1.ShowDialog();
            if (dr == DialogResult.OK)
            {
                speaker.SaveVoiceToWaveFile(this.textBox1.Text, this.saveFileDialog1.FileName);
            }
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            this.TopMost = this.checkBox1.Checked;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            if (!paused)
            {
                pause();
            }
            else
            {
                resume();
            }
        }

        private void pause()
        {
            string text;
            text = "继续";
            speaker.Pause();
            this.button5.Text = text;
            this.pauseToolStripMenuItem.Text = text;
            paused = true;
        }

        private void resume()
        {
            string text;
            text = "暂停";
            speaker.Resume();
            this.button5.Text = text;
            this.pauseToolStripMenuItem.Text = text;
            paused = false;
        }

        private void button6_Click(object sender, EventArgs e)
        {
            if (openFile.Length == 0)
            {
                DialogResult dr = this.openFileDialog1.ShowDialog();
                if (dr == DialogResult.OK)
                {
                    openFile = this.openFileDialog1.FileName;
                    ExecuteOpenFile();
                }
            }
            else
            {
                this.openFile = "";
                this.textBox1.Text = "";
                this.textBox1.ReadOnly = false;
                this.button6.Text = "打开...";
                this.openToolStripMenuItem.Text = "打开...";
            }
        }

        private void ExecuteOpenFile()
        {
            this.textBox1.Text = TextFile.Read(openFile);
            this.textBox1.ReadOnly = true;
            this.button6.Text = "关闭";
            this.openToolStripMenuItem.Text = "关闭";
            notifyIsReading();
        }

        private void MainForm_DragDrop(object sender, DragEventArgs e)
        {
            DataObject obj = (DataObject)e.Data;
            this.openFile = obj.GetFileDropList()[0];
            ExecuteOpenFile();
            //this.textBox1.Text = TextFile.Read(openFile);

        }

        private void MainForm_DragEnter(object sender, DragEventArgs e)
        {

            if (isReading)
            {
                e.Effect = DragDropEffects.None;
                return;
            }

            DataObject dropObj = (DataObject)e.Data;
            string file = "";

            if (dropObj.ContainsFileDropList())
                file = dropObj.GetFileDropList()[0];

            if (file.EndsWith("txt", true, System.Globalization.CultureInfo.CurrentCulture))
            {
                e.Effect = DragDropEffects.Copy;
            }
            else
            {
                e.Effect = DragDropEffects.None;
            }
        }

        private void button7_Click(object sender, EventArgs e)
        {
            this.Hide();
            this.notifyIcon1.Visible = true;

            //notifyIcon1_Click(sender, e);
            notifyIsReading();

        }

        private void notifyIsReading()
        {
            string title;
            string text;
            if (isReading)
            {
                int len = this.textBox1.Text.Length;
                int pos = readCharPosition;
                title = "EasyReader2";
                text = "正在为您和全世界人民服务...\n";
                text = "正在朗读：" + openFile + "\n";
                //text += "完成：" + (pos * 100 / len +1)+ "%\n";
                text += "剩余：" + (len - pos) * 100 / len  + "%";
            }
            else
            {
                title = "EasyReader2";
                text = "等待为您和全世界人民服务：\n";
                if (this.textBox1.Text.Length == 0)
                    text += "要读的内容为空。";
                else
                {
                    if (this.openFile.Length > 0)
                        text += "要读文件：" + this.openFile + "\n";
                    text += "长度：" + this.textBox1.Text.Length + "个字。";
                }
                    
            }

            this.notifyIcon1.BalloonTipText = text;
            this.notifyIcon1.BalloonTipTitle = title;

            this.notifyIcon1.ShowBalloonTip(3000);
        }

        private void notifyReadOver()
        {
            string title;
            string text;
            title = "EasyReader2";
            text = "我读完了";

            this.notifyIcon1.BalloonTipText = text;
            this.notifyIcon1.BalloonTipTitle = title;

            this.notifyIcon1.ShowBalloonTip(3000);
        }

        private void notifyIcon1_DoubleClick(object sender, EventArgs e)
        {
            this.Show();
            this.notifyIcon1.Visible = false;
        }

        private void restoreToolStripMenuItem_Click(object sender, EventArgs e)
        {
            notifyIcon1_DoubleClick(sender, e);
        }

        private void readToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button1_Click(sender, e);
        }

        private void stopToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button3_Click(sender, e);
        }

        private void pauseToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button5_Click(sender, e);
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button6_Click(sender, e);
        }

        private void settingsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button2_Click(sender, e);
        }

        private void saveAsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button4_Click(sender, e);
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button10_Click(sender, e);
        }

        private void notifyIcon1_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                notifyIsReading();
            }
        }

        private void button9_Click(object sender, EventArgs e)
        {
            EasyReader2.Properties.Settings.Default.Reload();
            this.lastCharPos = EasyReader2.Properties.Settings.Default.LastCharPos;
            this.openFile = EasyReader2.Properties.Settings.Default.LastFile;
            if (openFile.Length > 0)
            {
                ExecuteOpenFile();
                button1_Click(sender, e);
                //resume();
            }
        }

        private void savePosition()
        {
            //pasue();
            EasyReader2.Properties.Settings.Default.LastCharPos = this.readCharPosition + this.lastCharPos;
            EasyReader2.Properties.Settings.Default.LastFile = this.openFile;
            EasyReader2.Properties.Settings.Default.Save();
            //resume();
        }

        private void button10_Click(object sender, EventArgs e)
        {
            button3_Click(sender, e);
            this.speaker.Stop();
            this.notifyIcon1.Visible = false;
            Application.Exit();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            string f = EasyReader2.Properties.Settings.Default.LastFile;
            int p = EasyReader2.Properties.Settings.Default.LastCharPos;
            if (f.Length > 0 && p > 0)
            {
                this.button9.Enabled = true;
                this.readFromLastPositionToolStripMenuItem.Enabled = true;
            }
            else
            {
                this.button9.Enabled = false;
                this.readFromLastPositionToolStripMenuItem.Enabled = false;
            }
        }

        private void button8_Click(object sender, EventArgs e)
        {
            if (this.textBox1.SelectionLength > 0)
            {
                this.lastCharPos = this.textBox1.SelectionStart;
                button1_Click(sender, e);
            }
        }

        private void readFromLastPositionToolStripMenuItem_Click(object sender, EventArgs e)
        {
            button9_Click(sender, e);
        }

        private void linkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            System.Diagnostics.Process.Start("explorer.exe","http://hi.baidu.com/mwxp2/blog/item/539ffccd10b59d590fb345cd.html");
        }
    }
}
