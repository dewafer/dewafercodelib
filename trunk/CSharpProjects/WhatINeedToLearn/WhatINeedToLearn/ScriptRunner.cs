using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WhatINeedToLearn
{
    public partial class ScriptRunnerForm : Form
    {
        public ScriptRunnerForm()
        {
            InitializeComponent();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            this.panel2.BringToFront();
            this.button3.Enabled = true;
            this.button4.Enabled = false;
            this.button5.Enabled = true;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            this.panel1.BringToFront();
            this.button3.Enabled = false;
            this.button4.Enabled = true;
            this.button5.Enabled = false;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            if (this.textBox1.Text == "")
            {
                this.panel1.BringToFront();
                MessageBox.Show("必须有指令列表！");
                return;
            }

            if (this.textBox2.Text == "")
            {
                this.panel2.BringToFront();
                MessageBox.Show("必须有脚本文件！");
                return;
            }

            this.DialogResult = DialogResult.OK;
            this.Close();
        }

        private void button6_Click(object sender, EventArgs e)
        {
            this.openFileDialog1.InitialDirectory = this.textBox2.Text;
            DialogResult dr = this.openFileDialog1.ShowDialog();
            if (dr == DialogResult.OK)
                this.textBox2.Text = this.openFileDialog1.FileName;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.openFileDialog1.InitialDirectory = this.textBox1.Text;
            DialogResult dr = this.openFileDialog1.ShowDialog();
            if (dr == DialogResult.OK)
                this.textBox1.Text = this.openFileDialog1.FileName;
        }
    }
}
