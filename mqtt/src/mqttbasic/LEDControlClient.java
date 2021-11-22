package mqttbasic;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class LEDControlClient implements MqttCallback{
	private MqttClient mqttclient;
	private MqttConnectOptions mqttoption;
	SerialArduinoLEDControl serialobj;
	OutputStream serialout;
	
	public LEDControlClient() {
		serialobj = new SerialArduinoLEDControl();
		serialobj.connect("COM5");
		serialout = serialobj.getOutput();
	}
	
	public LEDControlClient init(String server, String clientId) {
		mqttoption = new MqttConnectOptions();
		mqttoption.setCleanSession(true);
		mqttoption.setKeepAliveInterval(30);
		try {
			mqttclient = new MqttClient(server, clientId); 
			mqttclient.setCallback(this);
			mqttclient.connect(mqttoption);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public void connectionLost(Throwable arg0) {
		
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("________¸Þ¼¼Áö µµÂø__________");
		System.out.println(message+","+"topic : "+topic+", id : "+message.getId()+", patload : "+message.getPayload());
		String msg = new String(message.getPayload());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if(msg.equals("led_1")) {
						serialout.write('1');
					}else if(msg.equals("led_2")) {
						serialout.write('2');
					}else if(msg.equals("led_3")){
						serialout.write('3');
					}else if(msg.equals("led_4")) {
						serialout.write('4');
					}else if(msg.equals("led_5")) {
						serialout.write('5');
					}
			}catch(IOException e) {
				e.printStackTrace();
				}
			}
		}).start();
	}
	
	public boolean subscribe(String topic) {
		try {
			if(topic!= null) {
			mqttclient.subscribe(topic,0);
			}
		} catch(MqttException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		LEDControlClient client = new LEDControlClient();
		client.init("tcp://192.168.0.21:1883","myid").subscribe("led");
	}
}
