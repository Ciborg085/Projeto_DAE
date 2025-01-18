<template>
  <div class="sensor-list">
    <h2>Lista de Sensores</h2>

    <!-- Mensagens de erro -->
    <div v-if="error" class="error">{{ error }}</div>

    <!-- Loader enquanto busca os dados -->
    <div v-if="loading" class="loading">A carregar sensores...</div>

    <!-- Lista de sensores (quando tudo OK) -->
    <ul v-else>
      <li v-for="sensor in sensors" :key="sensor.id" class="sensor-item">
        <strong>ID:</strong> {{ sensor.id }} |
        <strong>Tipo:</strong> {{ sensor.type }}

        <!-- Exibe propriedades de acordo com sensor.type -->
        <template v-if="sensor.type === 'temperatureSensor'">
            <br />
            <strong>Temperatura:</strong> {{ sensor.properties.temperature }} °C
        </template>

        <template v-else-if="sensor.type === 'pressureSensor'">
            <br />
            <strong>Pressão:</strong> {{ sensor.properties.pressure }} hPa
        </template>

        <template v-else-if="sensor.type === 'multiSensor'">
            <br />
            <strong>Temperatura:</strong> {{ sensor.properties.temperature }} °C
            <br />
            <strong>Pressão:</strong> {{ sensor.properties.pressure }} hPa
            <br />
            <strong>Latitude:</strong> {{ sensor.properties.latitude }}
            <br />
            <strong>Longitude:</strong> {{ sensor.properties.longitude }}
        </template>

        <template v-else-if="sensor.type === 'geolocationSensor'">
            <br />
            <strong>Latitude:</strong> {{ sensor.properties.latitude }}
            <br />
            <strong>Longitude:</strong> {{ sensor.properties.longitude }}
        </template>

        <!-- Se houver outro tipo não previsto -->
        <template v-else>
          <br />
          <em>Tipo de sensor não reconhecido.</em>
        </template>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRuntimeConfig } from '#app';
import { useAuthStore } from '@/stores/auth';

const sensors = ref<any[]>([]);
const loading = ref(true);
const error = ref<string|null>(null);

const config = useRuntimeConfig();
const API_URL = config.public.API_URL;

// Store com o token
const authStore = useAuthStore();

// Função auxiliar que converte "temperatureSensor" -> "TEMPERATURE", etc.
function normalizeSensorType(rawType: string): string {
  const lower = rawType.toLowerCase();
  if (lower.includes('temperature')) return 'TEMPERATURE';
  if (lower.includes('pressure'))    return 'PRESSURE';
  if (lower.includes('geolocation')) return 'GEOLOCATION';
  if (lower.includes('multi'))       return 'MULTI';
  return 'UNKNOWN';
}

// Função que busca a lista de sensores
async function fetchSensors() {
  try {
    loading.value = true;
    error.value = null;

    const response = await axios.get(`${API_URL}/sensors`, {
      headers: {
        Authorization: `Bearer ${authStore.token}`,
      },
    });

    let data = Array.isArray(response.data) ? response.data : [];

    data = data.map(sensor => {
      const type = normalizeSensorType(sensor.type || 'UNKNOWN');
      // Se não houver sensor.properties, garante que fica um objeto vazio
      const props = sensor.properties || {};

      return {
        ...sensor,
        // substitui "type" por algo padronizado (TEMPERATURE, PRESSURE, etc.)
        type,

        // Copiamos as propriedades
        temperature: props.temperature ?? null,
        pressure: props.pressure ?? null,
        latitude: props.latitude ?? null,
        longitude: props.longitude ?? null,
      };
    });

    sensors.value = data.slice(0, 10);

    if (sensors.value.length === 0) {
      error.value = "Nenhum sensor encontrado.";
    }
  } catch (err) {
    console.error(err);
    error.value = "Erro ao carregar os sensores!";
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  authStore.initAuth();
  fetchSensors();
});
</script>

<style scoped>
.sensor-list {
  padding: 2rem;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.sensor-item {
  margin-bottom: 0.5rem;
  padding: 0.8rem;
  background-color: #f5f5f7;
  border-radius: 8px;
  border: 1px solid #e5e5e5;
  text-align: left;
}

.error {
  color: red;
  font-weight: bold;
}

.loading {
  color: #555;
}
</style>
