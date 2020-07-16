package cn.kgc.tangcco.zwpl.utils;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUtility;

public class AsrVioce {
	SpeechRecognizer speechRecognizer;
	String resultText = "";
	public AsrVioce(){
		this.speechRecognizer = SpeechRecognizer.createRecognizer();
    }
	public void speech() {
		if (!speechRecognizer.isListening())
			speechRecognizer.startListening(listener);
		else
			speechRecognizer.stopListening();
	}
	public String text() {
		return resultText;
	}
	// 编写监听器,创建匿名内部类
	private RecognizerListener listener = new RecognizerListener() {
		/**
		 * 当在说话的过程中音量发生变化时会多次调用此函数，显示音量值
		 */
		@Override
		public void onVolumeChanged(int arg0) {
			DebugLog.Log("onVolumeChanged enter      " + arg0);
		}

		/**
		 * 获取听写结果. 获取RecognizerResult类型的识别结果
		 */
		@Override
		public void onResult(RecognizerResult result, boolean flag) {
			DebugLog.Log("onResult enter");
			//这个result就是远程解析的结果
			String strResult = null;
			//这里的捕获异常是我自己修改了JsonParser的这个静态方法，因为他在里面捕获了异常，所以我修改了，我在那里面又抛了一个异常
			//因为这个函数解析result的时候，如果不说话就会打印异常信息，所以受不了，我就把他修改了
			try {
				strResult = JsonParser.parseIatResult(result.getResultString());
			} catch (Exception e) {
				strResult = "";
			}
			resultText = result.getResultString();
			System.out.println(strResult);
		}

		/*
		 * 事件 扩展用接口，由具体业务进行约定
		 * 
		 * @see com.iflytek.cloud.speech.RecognizerListener#onEvent(int, int, int,
		 * java.lang.String)
		 */
		@Override
		public void onEvent(int arg0, int arg1, int arg2, String arg3) {

		}

		@Override
		public void onError(SpeechError arg0) {
			DebugLog.Log("onError enter");
		}

		@Override
		public void onEndOfSpeech() {
			DebugLog.Log("onEndOfSpeech enter");
		}

		/*
		 * 结束听写，恢复初始状态
		 * 
		 * @see com.iflytek.cloud.speech.RecognizerListener#onBeginOfSpeech()
		 */
		@Override
		public void onBeginOfSpeech() {
			DebugLog.Log("onBeginOfSpeech enter");
		}
	};
}
