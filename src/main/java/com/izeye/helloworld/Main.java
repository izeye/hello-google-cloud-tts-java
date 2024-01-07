package com.izeye.helloworld;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Hello world for Google Cloud TTS.
 *
 * @author Johnny Lim
 */
public class Main {
    public static void main(String[] args) throws IOException {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText("Hello, world!")
                    .build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US")
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            ByteString audioContent = response.getAudioContent();

            try (OutputStream out = new FileOutputStream("output.mp3")) {
                out.write(audioContent.toByteArray());;
                System.out.println("Audio content written to file \"output.mp3\"");
            }
        }
    }
}
