package br.icmc.lasdpc.AgentMQTTProject;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

public class MyAgent extends Agent {
	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	private MqttClient client;
	int cont = 0;

	protected void setup() {
		
		// Register the agent in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("MyAgentType");
		sd.setName("MyAgentName");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Connect to the MQTT broker
		try {
			client = new MqttClient("tcp://127.0.0.1:1883", getAID().getLocalName(), new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			// options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			client.connect(options);
		} catch (MqttException me) {
			me.printStackTrace();
		}

		// Subscribe to a topic
		try {
			client.subscribe("casa/Temperatura");
		} catch (MqttException me) {
			me.printStackTrace();
		}

		// Add the behaviour to receive messages
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				
				//Make a delay in the execution in 0.5 second
				block(500) ;
				
				// Receive messages from the subscribed topic
				// ...

				// Publish a message to a topic
				String text_message = "HelloFromJADE-MQTT_"+(cont++); 
				MqttMessage message = new MqttMessage(text_message.getBytes());
				message.setQos(0);
				try {
					client.publish("casa/Temperatura", message);
					
				} catch (MqttException me) {
					me.printStackTrace();
				}
			}
		});
		
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {				
								
				client.setCallback(new MqttCallback() {
		            @Override
		            public void connectionLost(Throwable cause) {
		                System.out.println("Connection lost");
		            }

		            @Override
		            public void messageArrived(String topic, MqttMessage message) throws Exception {
		                System.out.println("Message arrived: " + new String(message.getPayload()));
		            }

					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						// TODO Auto-generated method stub
						
					}		            
		        });
		        try {		            
		            client.subscribe("casa/Temperatura");
		        } catch (MqttException e) {
		            e.printStackTrace();
		        }
			}
		});
	}
}
