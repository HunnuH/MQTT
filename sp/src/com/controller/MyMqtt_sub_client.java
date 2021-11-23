package com.controller;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMqtt_sub_client implements MqttCallback {
	private MqttClient mqttclient;
	private MqttConnectOptions mqttoption;
	public MyMqtt_sub_client init (String server, String clientId) {
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
		System.out.println("¸Þ¼¼Áö µµÂø");
		System.out.println(message+","+"topic : "+topic+", id : "+message.getId()+", patload : "+message.getPayload());
	}
	
	public boolean subscribe(String topic) {
		
			try {
				if(topic!= null) {
				mqttclient.subscribe(topic,0);
				}
			} catch (MqttException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
	public static void main(String[] args) {
		MyMqtt_sub_client client = new MyMqtt_sub_client();
		client.init("tcp://192.168.0.16:1883", "myid").subscribe("btn");
	}
}
