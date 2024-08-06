package org.octsrv.arduinoArrowGame;

import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public final class ArduinoArrowGame extends JavaPlugin {
    private static final String MQTT_BROKER_URL = "tcp://broker.emqx.io:1883";
    public static final String MQTT_TOPIC = "zlcsc/2024/arduino-arrow-game";

    public static ArduinoArrowGame instance;
    public MqttClient mqttClient;


    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            mqttClient = new MqttClient(MQTT_BROKER_URL, "SPIGOT__" + getServer().getBukkitVersion() + "__" + getServer().getIp());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("emqx");
            options.setPassword("public".toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            mqttClient.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        instance = this;
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            mqttClient.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
