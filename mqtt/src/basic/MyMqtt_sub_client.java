package basic;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;



//mqtt클라이언트  - subscriber
	//Mqtt call back인터페이스를 상속	
public class MyMqtt_sub_client implements MqttCallback{
	private MqttClient mqttclient; //브로커와 통신을 담당(subscriber, publicer역활 담당)
	private MqttConnectOptions mqttoption; // mqtt의 연걸정보를 설정
	public MyMqtt_sub_client init(String server, String clientId) {
		mqttoption = new MqttConnectOptions();
		mqttoption.setCleanSession(true);
		mqttoption.setKeepAliveInterval(30);
		try {
			mqttclient = new MqttClient(server, clientId); // 브로커에 SUBSCRIBE하기 위한 클라이언트 객체 생성
			mqttclient.setCallback(this);//클라이언트객체에 콜백 셋팅 - subscrive하면서 적절한 시점에 메소드 호출
			mqttclient.connect(mqttoption);//mqttclient가 브로커에 연결 설정
			
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return this;
	}
		
	//커넥션이 종료시 호출
	@Override
	public void connectionLost(Throwable arg0) {
		
	}
	//메세지 전송이 완료되면 호출
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
	}
	//메세지가 도착하면 호출
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		System.out.println("________메세지 도착__________");
		System.out.println(message+","+"topic : "+topic+", id : "+message.getId()+", patload : "+message.getPayload());
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
		MyMqtt_sub_client client = new MyMqtt_sub_client();
		client.init("tcp://192.168.0.21:1883","myid").subscribe("led");
	}
}
