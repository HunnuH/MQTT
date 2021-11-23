package com.controller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
public class MyMqtt_Pub_client {
	private MqttClient client;
	public MyMqtt_Pub_client(){
		try {
			client = new MqttClient("tcp://192.168.0.21:1883", "myid2");
			client.connect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	public boolean send(String topic, String msg) {
		MqttMessage message = new MqttMessage();
		message.setPayload(msg.getBytes()); 
		try {
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		} 
		return true;
	}
	
	public void close() {
		try {
		if(client != null) {
			client.disconnect();
				client.close(); 
			} 
		} catch (MqttException e) {
				e.printStackTrace();
			}
		
	}

	public static void main(String[] args) {
		MyMqtt_Pub_client sender = new MyMqtt_Pub_client();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 1;
				String msg = "";
				while(true) {
					if(i==5) {
						break;
					}else {
					if(i%2==1) {
						msg = "led_on";
					}else {
						msg = "led_off";
					}
				}
				sender.send("led", msg);
				i++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				sender.close();
			}
		}).start();
	}

}
