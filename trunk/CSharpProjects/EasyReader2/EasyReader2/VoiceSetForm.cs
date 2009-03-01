using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;


namespace EasyReader2
{
    public partial class VoiceSetForm : Form
    {
        private MySpeecher speaker = MySpeecher.GetSpeecher();
        public VoiceSetForm()
        {
            InitializeComponent();
        }

        private void VoiceSetForm_Load(object sender, EventArgs e)
        {
            foreach (string name in speaker.GetInstalledVoiceNames())
            {
                this.comboBox1.Items.Add(name);
                if (name.Equals(speaker.Voice.Name))
                {
                    this.comboBox1.SelectedItem = name;
                }
            }
            this.trackBar1.Value = speaker.Volume;
            this.trackBar2.Value = speaker.Rate;
            //MessageBox.Show(speaker.Rate.ToString());
        }

        private void button1_Click(object sender, EventArgs e)
        {
            speaker.SelectVoice(this.comboBox1.SelectedItem.ToString());
            speaker.SetVolumeAndRate(this.trackBar1.Value, this.trackBar2.Value);
            this.Close();
        }
    }
}
