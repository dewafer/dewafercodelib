using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using FileOperator;

namespace WhatINeedToLearn
{
    public partial class TextForm : Form
    {
        private string filePath = null;
        public TextForm()
        {
            InitializeComponent();
        }
        public void Show(System.Windows.Forms.IWin32Window owner, String text)
        {
            this.Show(owner);
            this.textBox1.Text = text;
        }

        public void Show(String text)
        {
            this.Show();
            this.textBox1.Text = text;
        }

        private void 退出ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void 另存为ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DialogResult dr = this.saveFileDialog1.ShowDialog();
            if (dr == DialogResult.OK)
            {
                this.filePath = this.saveFileDialog1.FileName;
                save();
            }
        }

        /// <summary>
        /// 此方法演示了TextFile的写入Write方法
        /// </summary>
        private void save()
        {
            TextFile.Write(filePath, this.textBox1.Text, true);
        }

        /// <summary>
        /// 此方法演示了TextFile的读取Read方法
        /// </summary>
        private void open()
        {
            this.textBox1.Text = TextFile.Read(filePath);
        }

        private void 保存ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (filePath == null)
                另存为ToolStripMenuItem_Click(sender, e);
            else
                save();
        }

        private void 打开ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.openFileDialog1.Filter = "文本文件（*.txt）|*.txt";
            this.openFileDialog1.Title = "打开文件...";

            DialogResult dr = this.openFileDialog1.ShowDialog();
            if (dr == DialogResult.OK)
            {
                this.filePath = this.openFileDialog1.FileName;
                open();
            }
        }

        private void 以XML文件打开ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.openFileDialog1.Filter = "XML文件（*.xml）|*.xml";
            this.openFileDialog1.Title = "以XML文件打开...";

            DialogResult dr = this.openFileDialog1.ShowDialog();
            if (dr ==DialogResult.OK)
            {
                this.filePath = this.openFileDialog1.FileName;
                openXML();
            }
        }


        /// <summary>
        /// 此方法演示了XmlFile的Open和Read方法
        /// </summary>
        private void openXML()
        {
            XmlFile xfile = XmlFile.Open(filePath);
            this.textBox1.Text = xfile.Read();
            xfile.Close();
        }

        private void 自动换行ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.textBox1.WordWrap = this.自动换行ToolStripMenuItem.Checked;
        }

        private void 新建ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.textBox1.Text = "";
            this.filePath = null;
        }

    }
}
