package org.octsrv.arduinoArrowGame;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) throws MqttException {
        if (e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }
        if (!(e.getDamager() instanceof Arrow)) {
            return;
        }
        if (!isCustomTargetEntity(e.getEntity())) {
            return;
        }
        ArduinoArrowGame.instance.mqttClient.publish(ArduinoArrowGame.MQTT_TOPIC, new MqttMessage("Quack".getBytes()));
    }

    private boolean isCustomTargetEntity(Entity e) {
        return e.getScoreboardTags().contains("CustomTarget");
    }
}
