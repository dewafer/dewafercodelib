using System;
using System.Collections.Generic;
using System.Text;
using System.Speech.Synthesis;
using System.Collections.ObjectModel;

namespace EasyReader2
{
    class MySpeecher
    {
        private static MySpeecher speecher = null;
        private SpeechSynthesizer sayer = new SpeechSynthesizer();

        public delegate void sayer_SpeakCompleted(object o,SpeakCompletedEventArgs e);
        public delegate void sayer_SpeakProgress(object o, SpeakProgressEventArgs e);

        private MySpeecher() {
        }

        public void SelectLocalCultureVoice()
        {
            sayer.SelectVoiceByHints(VoiceGender.NotSet, VoiceAge.NotSet, 0,
                System.Globalization.CultureInfo.CurrentCulture);
        }

        public static MySpeecher GetSpeecher()
        {
            if (speecher == null)
            {
                speecher = new MySpeecher();
            }
            return speecher;
        }

        public string[] GetInstalledVoiceNames()
        {
            ReadOnlyCollection<InstalledVoice> voices = sayer.GetInstalledVoices();
            string[] str = new string[voices.Count];
            for (int i = 0; i < voices.Count; i++)
            {
                str[i] = voices[i].VoiceInfo.Name;
            }
            return str;
        }

        public void SelectVoice(string name)
        {
            sayer.SelectVoice(name);
        }

        public VoiceInfo Voice
        {
            get
            {
                return sayer.Voice;
            }
        }

        public void Speak(string textToSpeak)
        {
            SpeakAsync(textToSpeak);
        }

        public Prompt SpeakAsync(string textToSpeak)
        {
            // from -10 to 10
            //sayer.Rate = 10;

            // from 0 to 100
            //sayer.Volume = 100;
            return sayer.SpeakAsync(textToSpeak);
            //return sayer.SpeakSsmlAsync(textToSpeak);
        }

        public void Pause()
        {
            sayer.Pause();
        }

        public void Resume()
        {
            sayer.Resume();
        }

        public void SetSpeakCompleteEventHandler(sayer_SpeakCompleted sayer_speakCompletedEventHandler)
        {
            sayer.SpeakCompleted += new EventHandler<SpeakCompletedEventArgs>(sayer_speakCompletedEventHandler);
        }

        public void SetSpeakProgressEventHandler(sayer_SpeakProgress sayer_SpeakProgressEventHandler)
        {
            sayer.SpeakProgress += new EventHandler<SpeakProgressEventArgs>(sayer_SpeakProgressEventHandler);
        }

        public void Stop()
        {
            sayer.SpeakAsyncCancelAll();
        }

        public void SaveVoiceToWaveFile(string textToSpeak,string path)
        {
            sayer.SetOutputToWaveFile(path);
            sayer.Speak(textToSpeak);
            sayer.SetOutputToDefaultAudioDevice();
        }

        public void SetVolumeAndRate(int volume, int rate)
        {
            sayer.Volume = volume;
            sayer.Rate = rate;
        }

        public int Volume
        {
            get
            {
                return sayer.Volume;
            }
        }

        public int Rate
        {
            get
            {
                return sayer.Rate;
            }
        }
    }
}
