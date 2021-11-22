package basic;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Mymqtt_Pub_client {
	private MqttClient client;
	public Mymqtt_Pub_client() {
		try {
			client = new MqttClient("tcp://192.168.0.21:1883", "myid2");
			client.connect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	public boolean send(String topic, String msg) {
		try {
			MqttMessage message = new MqttMessage();
			message.setPayload(msg.getBytes());
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return true;
	}
	public void close() {
		if(client!=null) {
			try {
				client.disconnect();
				client.close();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Mymqtt_Pub_client sender = new Mymqtt_Pub_client();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i=1;
				String msg = "";
				while(true) {
					if(i==6) {
						break;
					}else {
						if(i==1) {
							msg = "led_1";
						}else if(i==2){
							msg = "led_2";
						}else if(i==3) {
							msg = "led_3";
						}else if(i==4) {
							msg = "led_4";
						}else {
							msg = "led_5";
						}
					}
					sender.send("led", msg);
					i++;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					
					}
				}
				sender.close();
			}
		}).start();
	}
}
