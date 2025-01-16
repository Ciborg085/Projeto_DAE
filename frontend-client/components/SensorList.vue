<template>
  <div class="sensor-list">
    <h2>Lista de Sensores</h2>

    <!-- Exibição de erro -->
    <div v-if="error" class="error">{{ error }}</div>

    <!-- Loader enquanto busca os dados -->
    <div v-if="loading" class="loading">Carregando sensores...</div>

    <!-- Lista de sensores -->
    <ul v-else>
      <li v-for="sensor in sensors" :key="sensor.id" class="sensor-item">
        <strong>ID:</strong> {{ sensor.id }} |
        <strong>Tipo:</strong> {{ sensor.type }} |
        <strong>Temperatura:</strong> {{ sensor.properties.temperature }} °C
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";
import { useRuntimeConfig } from '#app';

export default {
  data() {
    return {
      sensors: [],
      loading: true,
      error: null,
    };
  },
  methods: {
    async fetchSensors() {
      try {
        this.loading = true;
        this.error = null;

        const config = useRuntimeConfig();
        const apiUrl = config.public.API_URL;

        const response = await axios.get(`${apiUrl}/sensors`);
        let sensorsData = Array.isArray(response.data) ? response.data : [];

        this.sensors = sensorsData.slice(0, 10);

        if (this.sensors.length === 0) {
          this.error = "Nenhum sensor encontrado.";
        }

      } catch (err) {
        this.error = "Erro ao carregar os sensores!";
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    this.fetchSensors();
  },
};
</script>

<style scoped>
.sensor-list {
  padding: 2rem;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto; /* Centraliza horizontalmente */
  text-align: center; /* Centraliza o texto */
}

h2 {
  margin-bottom: 1rem;
  color: #333;
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
