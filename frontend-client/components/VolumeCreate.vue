<template>
    <div class="new-volume">
        <h2>Criar Novo Volume</h2>
        <form @submit.prevent="createVolume">
            <div>
                <div>
                    <label for="volume_id">Volume ID</label>
                    <input type="number" id="volume_id" v-model="newVolume.volume_id" required />
                </div>
                <div>
                    <label for="order_id">Order ID</label>
                    <input type="number" id="order_id" v-model="newVolume.order_id" required />
                </div>
                <div>
                    <label>Product ID</label>
                    <input type="number" id="product_id" v-model="newVolume.product_id" required />
                </div>
                <div>
                    <label>Quantity</label>
                    <input type="number" id="quantity" v-model="newVolume.quantity" required />
                </div>
            </div>
            <h3>Sensores</h3>
            <div v-for="(sensor, index) in newVolume.sensors" :key="index" class="sensor-item">
                <div>
                    <label>Sensor ID</label>
                    <input type="number" v-model="sensor.id" required />
                </div>
                <h4>Sensor Type</h4>
                <div class="sensor-type">
                    <div>
                    <label for="temperatureSensor">Temperature Sensor: </label>
                    <input type="radio" id="temperatureSensor" v-model="sensor.type" value="temperatureSensor" required />
                    </div>
                    <div>
                    <label for="pressureSensor">Pressure Sensor: </label>
                    <input type="radio" id="pressureSensor" v-model="sensor.type" value="pressureSensor" required />
                    </div>
                    <div>
                    <label for="geolocationSensor">Geolocation Sensor: </label>
                    <input type="radio" id="geolocationSensor" v-model="sensor.type" value="geolocationSensor" required />
                    </div>
                    <div>
                    <label for="multiSensor">Multi Sensor: </label>
                    <input type="radio" id="multiSensor" v-model="sensor.type" value="multiSensor" required />
                    </div>
                </div>
                <button type="button" @click="removeSensor(index)">Remover</button>
            </div>
            <button type="button" @click="addSensor">Adicionar Sensor</button> |
            <button type="submit">Criar Volume</button>
        </form>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRuntimeConfig } from '#app';
import { useAuthStore } from '@/stores/auth';

const config = useRuntimeConfig();
const authStore = useAuthStore();
const BASE_URL = `${config.public.API_URL}/loja/volumes`;

const newVolume = ref({
  volume_id: 0,
  order_id: 0,
  product_id: 0,
  quantity: 0,
  sensors: [],
});


function addSensor() {
  newVolume.value.sensors.push({
    id: 0,
    type:'',
  });
}
function removeVolume(index) {
  newVolume.value.sensors.splice(index, 1);
}

async function createVolume() {
  try {
    // Validate that there is at least one sensor

    const response = await fetch(`${BASE_URL}/`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authStore.token}`,
      },
      body: JSON.stringify(newVolume.value),
    });

    if (!response.ok) {
      console.log("Erro")
    }

    alert('Volume criado com sucesso!');
    // Reset the form
    newVolume.value = {
        volume_id: 0,
        order_id: 0,
        product_id: 0,
        quantity: 0,
        sensors: [],
    };
    alert("Volume created")
  } catch (err) {
    console.error(err);
    alert('Erro ao criar a volume.');
  }
}
</script>
<style>
.new-volume {
    padding: 1.5rem;
    margin: 5px auto; /* Center horizontally */
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    max-width: 400px; /* Optional: Set a max-width */
}

.new-volume-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh; /* Take the full viewport height */
    background-color: #f5f5f7; /* Optional background color */
}

.new-volume h2 {
    margin-bottom: 1rem;
}

.new-volume form > div {
    margin-bottom: 1rem;
}

.sensor-item {
    display: flexbox;
    gap: 1rem;
    align-items: center;
    margin-bottom: 0.5rem;
}

.sensor-type {
    display: flexbox;
    gap: 1rem;
    align-items: center;
    margin-bottom: 0.5rem;
}

</style>
