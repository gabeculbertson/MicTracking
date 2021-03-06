import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class Main {
	static int RightMicIndex = 5;
	static int LeftMicIndex = 6;
	
	public static void main(String[] args) {
		AudioFormat format = new AudioFormat(96000.0f, 16, 1, true, false);
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); 

		// print all audio devices
		// you will need to manually select the correct microphone
		Mixer.Info[] infos = AudioSystem.getMixerInfo();
		for(int i = 0; i < infos.length; i++){
			Mixer mixer = AudioSystem.getMixer(infos[i]);
			Line.Info[] lines = mixer.getTargetLineInfo();
			for(int j = 0; j < lines.length; j++){
				System.out.println(i + ": " + lines[j]);
			}
		}
		
		if (!AudioSystem.isLineSupported(info)) {
			System.out.print(info);
		} try {
			TargetDataLine line1 = getLine(format, infos[RightMicIndex]);
			TargetDataLine line2 = getLine(format, infos[LeftMicIndex]);
			while(true){
				int max1 = MicReader.readBlock(line1, format);
				int max2 = MicReader.readBlock(line2, format);
				System.out.println(max1 + "\t" + max2);
			}
		} catch (LineUnavailableException ex) {
			System.out.println(ex);
		}
	}
	
	static TargetDataLine getLine(AudioFormat format, Mixer.Info info) throws LineUnavailableException{
		TargetDataLine line = AudioSystem.getTargetDataLine(format, info);
		return line;
	}
}
