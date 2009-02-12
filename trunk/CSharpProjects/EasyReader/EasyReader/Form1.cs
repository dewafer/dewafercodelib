using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;


namespace EasyReader
{
    public partial class Form1 : Form
    {
        private MySpeecher speaker = MySpeecher.GetSpeecher();
        private bool paused = false;

        public Form1()
        {
            InitializeComponent();
            speaker.SetSpeakCompleteEventHandler(speakerCompletedEventHandler);
            speaker.SetSpeakProgressEventHandler(speakerProgressEventHandler);
            speaker.SelectLocalCultureVoice();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            speaker.Speak(this.textBox1.Text);
            this.button1.Enabled = false;
            this.button2.Enabled = false;
            this.button3.Enabled = true;
            this.button4.Enabled = false;
            this.button5.Enabled = true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            new VoiceSetForm().Show(this);
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void speakerCompletedEventHandler(object sender,EventArgs e)
        {
            //MessageBox.Show("我读完了");
            this.button1.Enabled = true;
            this.button2.Enabled = true;
            this.button3.Enabled = false;
            this.button4.Enabled = true;
            this.button5.Enabled = false;
            this.textBox1.Select(0, 0);
        }

        private void speakerProgressEventHandler(object sender, EventArgs e)
        {
            System.Speech.Synthesis.SpeakProgressEventArgs read = 
                (System.Speech.Synthesis.SpeakProgressEventArgs)e;
            this.textBox1.Select(read.CharacterPosition, read.CharacterCount);
            this.textBox1.ScrollToCaret();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            speaker.Stop();
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
                speaker.Pause();
                this.button5.Text = "Resume";
                paused = true;
            }
            else
            {
                speaker.Resume();
                this.button5.Text = "Pause";
                paused = false;
            }
        }
    }
}
