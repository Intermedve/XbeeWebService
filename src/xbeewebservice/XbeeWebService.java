/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xbeewebservice;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

/**
 *
 * @author Jimmy
 */
public class XbeeWebService {

    public static SQL sql = new SQL();
    public static MQTT mqtt = new MQTT();
    public static BlockingConnection mqtt_conn;
    public static Message mess = null;
    
    public static void main(String[] args) {
        
        //Create MQTT connection and subscribe to
        //the "sensornetwork.inf.unideb.hu" broker on "test" topic
        try{
        mqtt.setHost("sensornetwork.inf.unideb.hu", 1883);
        mqtt.setClientId("xbeeWS");

        mqtt_conn = mqtt.blockingConnection();
        mqtt_conn.connect();

        Topic[] topics = {new Topic("test", QoS.AT_LEAST_ONCE), new Topic("test", QoS.AT_MOST_ONCE)};
        byte[] qoses = mqtt_conn.subscribe(topics);
        }catch (Exception ex){
            System.out.print("Exception at MQTT initialization: " + ex + "\n");
        }
        
        while(true){
            receive_mqtt_message();
        }
    }
    
    
    //Execute mqtt receive and data save to sql database
    public static void receive_mqtt_message(){
        
        try {
            mess = mqtt_conn.receive();
        } catch (Exception ex) {
            System.out.print("Exception at MQTT receive: " + ex + "\n");
        }
        
        //If the mqqt package came from "test" topic save it ot our database
        if (mess.getTopic().equals("test")) {
            sql.SaveData(new String(mess.getPayload()));
            System.out.print(new String(mess.getPayload()) + "\n"); //for debug
        }
        
        mess.ack();
    }
    

}
