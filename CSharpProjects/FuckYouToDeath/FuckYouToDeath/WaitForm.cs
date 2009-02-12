using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace FuckYouToDeath
{
    public partial class WaitForm : Form
    {
        public delegate void stopRun();

        private static stopRun stop = null;
        private static WaitForm waitForm = null;
        private bool jumpOffClosingWarning = false;

        private WaitForm()
        {
            InitializeComponent();
        }

        public static WaitForm GetWaitForm(stopRun stopr)
        {
            waitForm = new WaitForm();
            stop = stopr;
            return waitForm;
        }

        public static WaitForm GetWaitForm()
        {
            return waitForm;
        }

        public new void Close()
        {
            jumpOffClosingWarning = true;
            base.Close();
        }

        private void WaitForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!jumpOffClosingWarning)
            {
                DialogResult dr = MessageBox.Show("确认取消？", "取消...", MessageBoxButtons.YesNo,
                    MessageBoxIcon.Question, MessageBoxDefaultButton.Button2);
                if (dr == DialogResult.Yes)
                    stop();
                else
                    e.Cancel = true;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            jumpOffClosingWarning = false;
        }
    }
}
