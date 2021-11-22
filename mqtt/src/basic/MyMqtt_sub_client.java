package basic;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;



//mqttŬ���̾�Ʈ  - subscriber
	//Mqtt call back�������̽��� ���	
public class MyMqtt_sub_client implements MqttCallback{
	private MqttClient mqttclient; //���Ŀ�� ����� ���(subscriber, publicer��Ȱ ���)
	private MqttConnectOptions mqttoption; // mqtt�� ���������� ����
	public MyMqtt_sub_client init(String server, String clientId) {
		mqttoption = new MqttConnectOptions();
		mqttoption.setCleanSession(true);
		mqttoption.setKeepAliveInterval(30);
		try {
			mqttclient = new MqttClient(server, clientId); // ���Ŀ�� SUBSCRIBE�ϱ� ���� Ŭ���̾�Ʈ ��ü ����
			mqttclient.setCallback(this);//Ŭ���̾�Ʈ��ü�� �ݹ� ���� - subscrive�ϸ鼭 ������ ������ �޼ҵ� ȣ��
			mqttclient.connect(mqttoption);//mqttclient�� ���Ŀ�� ���� ����
			
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return this;
	}
		
	//Ŀ�ؼ��� ����� ȣ��
	@Override
	public void connectionLost(Throwable arg0) {
		
	}
	//�޼��� ������ �Ϸ�Ǹ� ȣ��
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
	}
	//�޼����� �����ϸ� ȣ��
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		System.out.println("________�޼��� ����__________");
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
