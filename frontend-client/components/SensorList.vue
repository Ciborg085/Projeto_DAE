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
import {useRuntimeConfig} from '#app';

export default {
  data() {
    return {
      sensors: [],  // Lista de sensores
      loading: true,  // Estado de carregamento
      error: null,    // Estado de erro
    };
  },
  methods: {
    async fetchSensors() {
      try {
        this.loading = true;
        this.error = null;

        // Acessa o runtimeConfig
        const config = useRuntimeConfig();
        const apiUrl = config.public.API_URL;

        // Realiza a chamada para a API de sensores
        const response = await axios.get(`${apiUrl}/sensors`);

        console.log("Resposta COMPLETA da API:", response);

        // Ajuste da verificação de resposta
        let sensorsData = [];

        if (Array.isArray(response.data)) {
          sensorsData = response.data;
        } else if (response.data && Array.isArray(response.data.sensors)) {
          sensorsData = response.data.sensors;
        } else if (response.data && response.data.length) {
          sensorsData = Object.values(response.data).flat();
        } else {
          throw new Error("Formato inesperado da resposta da API.");
        }

        this.sensors = sensorsData.slice(0, 10);

        if (this.sensors.length === 0) {
          this.error = "Nenhum sensor encontrado.";
        }

      } catch (err) {
        this.error = "Erro ao carregar os sensores! Verifique as permissões.";
        console.error("Erro ao buscar sensores:", err);
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
  padding: 1rem;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 1rem;
}

.sensor-item {
  margin-bottom: 0.5rem;
  padding: 0.5rem;
  border-bottom: 1px solid #e5e5e5;
}

.error {
  color: red;
  font-weight: bold;
}

.loading {
  color: #555;
}
</style>
