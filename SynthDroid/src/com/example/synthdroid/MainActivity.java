package com.example.synthdroid;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

		    private final int duracao = 4; // seconds
		    private final int amostragem = 44000;
		    private final int numAmostras = duracao * amostragem;
		    private final double buffer[] = new double[numAmostras];
		    private final double freqOfTone = 440; // hz
		    private final double freqMod = 100; //Hz

		    private final byte vetor_audio_PCM16[] = new byte[2 * numAmostras];
		    
		    @Override
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity_main);        
		    }

		    @Override
		    protected void onResume() {
		        super.onResume();
		    }
		    
		    
		    public void geraTom(double f, double m){        
		        for (int i = 0; i < numAmostras; ++i) {        	
		            
		        	// tom puro modulado por m Hz
		        	//buffer[i] = Math.sin(2 * Math.PI * i / (amostragem/f))*Math.cos(2 * Math.PI * i / (amostragem/m));
		        	
		        	// ruido branco modulado por m Hz
		            buffer[i] = Math.random()*Math.cos(2 * Math.PI * i / (amostragem/m));
		        }
		        
		        //filtroPB(buffer, ); // retorna novo buffer filtrado
		        
		        // converte o buffer em formato PCM16
		        int idx = 0;
		        for (final double dVal : new_buffer) {
		            // normaliza
		            final short val = (short) ((dVal * 32767));
		            // in 16 bit wav PCM, first byte is the low order byte
		            vetor_audio_PCM16[idx++] = (byte) (val & 0x00ff);
		            vetor_audio_PCM16[idx++] = (byte) ((val & 0xff00) >>> 8);

		        }
		        // toca rauuull!
		        playSound(); 
		    }
		    
		    
		    
		    double old_buffer[];
			double new_buffer[] = new double[numAmostras];
		    
		    public double[] filtroPB (double b[], double level){
		    	old_buffer = b;    	
		    	new_buffer[0] = old_buffer[0];
		    	
		    	for (int i = 1; i < numAmostras; i++){    		
		    		new_buffer[i] = (old_buffer[i] - new_buffer[i-1])/level;    		    		
		    	}
		    	    	
				return new_buffer;
		    	
		    }
		    
		    public void tocaih(View v){    	
		    	geraTom(300,100);
		    	//SystemClock.sleep(900);
		    	geraTom(400,200);
		    	
		    	geraTom(500,300);
		    	
		    	geraTom(600,400);
		    }

		    public void playSound(){
		        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
		                amostragem, AudioFormat.CHANNEL_OUT_STEREO,
		                AudioFormat.ENCODING_PCM_16BIT, vetor_audio_PCM16.length,
		                AudioTrack.MODE_STATIC);
		        audioTrack.write(vetor_audio_PCM16, 0, vetor_audio_PCM16.length);
		        audioTrack.play();        
		    }

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		}	

}
